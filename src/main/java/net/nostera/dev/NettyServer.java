package net.nostera.dev;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import net.nostera.dev.util.NettyUtil;

import java.util.function.Consumer;

public final class NettyServer {

    private final int port;

    private final EventLoopGroup bossGroup = NettyUtil.getGroup();
    private final EventLoopGroup workerGroup = NettyUtil.getGroup();

    private ChannelFuture channelFuture;

    public NettyServer(int port) {
        this.port = port;
    }

    public NettyServer bind(Runnable serverReady, Consumer<SocketChannel> serverInit) {
        Thread serverThread = new Thread(() -> {
            try {
                channelFuture = new ServerBootstrap()
                        .group(bossGroup, workerGroup)
                        .channel(NettyUtil.getServerChannel())
                        .childHandler(
                                new ChannelInitializer<SocketChannel>() {
                                    @Override
                                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                                        serverInit.accept(socketChannel);
                                    }
                                }
                        )
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .bind(port)
                        .syncUninterruptibly();

                serverReady.run();

                channelFuture.channel().closeFuture().syncUninterruptibly();
            } finally {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        });

        serverThread.start();

        return this;
    }

    public void shutdown(Runnable closed) {
        if (channelFuture.channel().isOpen()) {
            channelFuture.channel().close().syncUninterruptibly();
        }

        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();

        closed.run();
    }

}
