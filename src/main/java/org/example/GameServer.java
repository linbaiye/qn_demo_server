package org.example;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameServer implements Runnable {
    private int playerId = 0;
    private Vector2 coordinate = new Vector2(1, 3);
    private final Map<Channel, List<Object>> pendingMessages = new HashMap<>();
    private final Map<Channel, Player> channelPlayerMap = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(GameServer.class);

    public synchronized void handleChannelClosed(Channel channel) {
        synchronized (pendingMessages) {
            List<Object> objects = pendingMessages.get(channel);
            if (objects != null)
                objects.add(new ConnectionClosedMessage(channel));
        }
    }

    public void onMessageArrived(Channel channel, Object message) {
        synchronized (pendingMessages) {
           pendingMessages.putIfAbsent(channel, new ArrayList<>());
           pendingMessages.get(channel).add(message);
        }
    }


    private void handleLogin(Channel channel) {
        if (channelPlayerMap.containsKey(channel)) {
            return;
        }
        Player player = new Player(playerId++, coordinate);
        coordinate = new Vector2(coordinate.x() + 1, coordinate.y());
        channel.write(new LoginOkMessage(player.getId(), coordinate));
        channelPlayerMap.forEach((c, p) -> {
            c.writeAndFlush(new ShowMessage(player.getId(), player.getCoordinate()));
            channel.write(new ShowMessage(p.getId(), p.getCoordinate()));
        });
        channel.flush();
        channelPlayerMap.put(channel, player);
    }


    private void handleMessages(Channel channel, List<Object> messages) {
        for (Object message : messages) {
            if (message instanceof LoginMessage) {
                handleLogin(channel);
            } else if (message instanceof ConnectionClosedMessage closedMessage) {
                channelPlayerMap.remove(closedMessage.channel());
                pendingMessages.remove(closedMessage.channel());
                LOGGER.debug("Channel closed.");
            }
        }
    }

    private void handleMessages() {
        Map<Channel, List<Object>> messages;
        synchronized (pendingMessages) {
            messages = new HashMap<>(pendingMessages);
        }
        messages.forEach(this::handleMessages);
    }



    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(10);
                handleMessages();
            }
        } catch (Exception e){ }
    }
}
