package org.example;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.HashMap;
import java.util.Map;

public class GameServer implements Runnable {
    private long playerId = 0;
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


    public synchronized void handleLogin(Channel channel) {
        if (channelPlayerMap.containsKey(channel.id())) {
            return;
        }
        Player player = new Player(playerId++, coordinate);
        channelPlayerMap.put(channel.id(), new PlayerChannel(channel, player));
        channel.writeAndFlush(new ShowMessage(player.getId(), player.getCoordinate()));
        coordinate = new Vector2(coordinate.x() + 1, coordinate.y());
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

    }
}
