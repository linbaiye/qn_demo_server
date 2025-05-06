package org.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDecoder.class);
    private final GameServer gameServer;

    public MessageHandler(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.debug("Received message {}.", msg);
        if (msg instanceof LoginMessage)
            gameServer.handleLogin(ctx.channel());
        else
            gameServer.handleMessage(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        gameServer.handleChannelClosed(ctx.channel());
    }
}
