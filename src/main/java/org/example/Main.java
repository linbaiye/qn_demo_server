package org.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private final ServerBootstrap serverBootstrap;

    private final EventLoopGroup workerGroup;

    private final EventLoopGroup bossGroup;

    private final GameServer gameServer;

    public Main() {
        workerGroup = new NioEventLoopGroup();
        bossGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        gameServer = new GameServer();
    }


    public void setupGameServer() throws InterruptedException {
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 4096)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline()
                                .addLast("messageDecoder", new MessageDecoder())
                                .addLast("messageHandler", new MessageHandler(gameServer))
                                .addLast("packetLengthAppender", new LengthFieldPrepender(4))
                                .addLast("messageEncoder", new MessageEncoder())
                        ;
                    }
                });
        serverBootstrap.bind(9999).sync().channel();
    }


    public static void main(String[] args) throws InterruptedException {
        var main = new Main();
        main.setupGameServer();
    }
}