package org.example;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Slf4j
public class GameServer implements Runnable, EventListener {
    private int playerId = 0;
    private Coordinate coordinate = new Coordinate(1, 3);
    private final Map<Channel, List<Object>> pendingMessages = new HashMap<>();
    private final Map<Channel, Player> channelPlayerMap = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(GameServer.class);

    private record DelayedMessage(long arrivedTime, Channel channel, Object message) {
        public boolean isTimeUp(long current) {
            return current - arrivedTime >= 100;
        }
    }

    private final List<DelayedMessage> delayedMessages = new ArrayList<>();

    public void handleChannelClosed(Channel channel) {
        addMessage(channel, new ConnectionClosedMessage(channel));
    }

    private void addMessage(Channel channel, Object message) {
        synchronized (delayedMessages) {
            DelayedMessage delayedMessage = new DelayedMessage(System.currentTimeMillis(), channel, message);
            delayedMessages.add(delayedMessage);
        }
    }

    public void onMessageArrived(Channel channel, Object message) {
        addMessage(channel, message);
    }


    private void handleLogin(Channel channel) {
        if (channelPlayerMap.containsKey(channel)) {
            return;
        }
        Player player = new Player(playerId++, coordinate, this);
        coordinate = new Coordinate(coordinate.x() + 1, coordinate.y());
        channel.writeAndFlush(new LoginOkMessage(player.getId(), player.getCoordinate()));
        channelPlayerMap.forEach((c, p) -> {
            c.writeAndFlush(new ShowMessage(player.getId(), player.getCoordinate()));
            channel.writeAndFlush(new ShowMessage(p.getId(), p.getCoordinate()));
        });
        channelPlayerMap.put(channel, player);
    }


    private void handleMessages(Channel channel, List<Object> messages) {
        for (Object message : messages) {
            if (message instanceof LoginMessage) {
                handleLogin(channel);
            } else if (message instanceof ConnectionClosedMessage closedMessage) {
                Player player = channelPlayerMap.get(closedMessage.channel());
                if (player == null)
                    return;
                channelPlayerMap.forEach((c, p) -> {
                    if (!c.equals(closedMessage.channel()))
                        c.writeAndFlush(new RemovePlayerMessage(player.getId()));
                });
                channelPlayerMap.remove(closedMessage.channel());
                LOGGER.debug("Channel closed.");
            } else if (message instanceof MoveInput moveInput) {
                var player = channelPlayerMap.get(channel);
                if (player != null)
                    player.handle(moveInput);
            }
        }
    }

    private void handleMessages() {
        Iterator<DelayedMessage> iterator = delayedMessages.iterator();
        long now = System.currentTimeMillis();
        while (iterator.hasNext()) {
            var dmsg = iterator.next();
            if (dmsg.isTimeUp(now)) {
                pendingMessages.putIfAbsent(dmsg.channel(), new ArrayList<>());
                pendingMessages.get(dmsg.channel()).add(dmsg.message());
                iterator.remove();
            }
        }
        pendingMessages.forEach(this::handleMessages);
        pendingMessages.clear();
    }

    @Override
    public void run() {
        int millis = 10;
        long accumulated = System.currentTimeMillis();
        try {
            while (true) {
                handleMessages();
                long current = System.currentTimeMillis();
                while (accumulated <= current) {
                    channelPlayerMap.values().forEach(p -> p.update(millis));
                    accumulated += millis;
                }
                Thread.sleep(millis);
            }
        } catch (Exception e){
            LOGGER.error("exception ", e);
        }
    }


    @Override
    public void onPlayerEvent(PlayerMoveEvent message) {
        channelPlayerMap.forEach((c, p) -> {
            if (p.getId() != message.playerId())
                c.writeAndFlush(message);
        });
    }
}
