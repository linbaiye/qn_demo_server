package org.example;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameServer implements Runnable {
    private int playerId = 0;
    private Vector2 coordinate = new Vector2(1, 3);
    private static class PlayerChannel {
        private final Channel channel;
        private final Player player;
        private PlayerChannel(Channel channel, Player player) {
            this.channel = channel;
            this.player = player;
        }
    }
    private Map<ChannelId, PlayerChannel> channelPlayerMap = new HashMap<>();

    private List<Message> messages = new ArrayList<>();

    public synchronized void handleLogin(Channel channel) {
        if (channelPlayerMap.containsKey(channel.id())) {
            return;
        }
        Player player = new Player(playerId++, coordinate);
        channelPlayerMap.put(channel.id(), new PlayerChannel(channel, player));
        coordinate = new Vector2(coordinate.x() + 1, coordinate.y());
        channelPlayerMap.values().forEach(playerChannel -> {
            var tmp = playerChannel.player;
            channel.writeAndFlush(new ShowMessage(tmp.getId(), tmp.getCoordinate()));
        });
    }

    public synchronized void handleChannelClosed(Channel channel) {
        var playerChannel = channelPlayerMap.remove(channel.id());
        if (playerChannel == null)
            return;
    }

    public synchronized void handleMessage(Object message) {

    }

    @Override
    public void run() {
        try {
            while (true)
                Thread.sleep(500);
        } catch (Exception e){ }
    }
}
