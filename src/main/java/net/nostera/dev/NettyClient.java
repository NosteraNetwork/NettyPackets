package net.nostera.dev;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import net.nostera.dev.util.NettyUtil;

import java.util.function.Consumer;

public final class NettyClient {

    private final int serverPort;

    private final EventLoopGroup workerGroup = NettyUtil.getGroup();

    private ChannelFuture channelFuture;

    public NettyClient(int serverPort) {
        this.serverPort = serverPort;
    }

    public NettyClient connect(Runnable clientReady, Runnable connectionFailed, Consumer<SocketChannel> clientInit) {
        Thread clientThread = new Thread(() -> {
            try {
                channelFuture = new Bootstrap()
                        .group(workerGroup)
                        .channel(NettyUtil.getSocketChannel())
                        .handler(
                                new ChannelInitializer<SocketChannel>() {
                                    @Override
                                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                                        clientInit.accept(socketChannel);
                                    }
                                }
                        )
                        .connect("127.0.0.1", serverPort);

                channelFuture.addListener((ChannelFutureListener) channelFuture -> {
                    if (!channelFuture.isSuccess()) {
                        connectionFailed.run();
                    }
                });

                channelFuture.syncUninterruptibly();
                clientReady.run();
                channelFuture.channel().closeFuture().syncUninterruptibly();
            } finally {
                workerGroup.shutdownGracefully();
            }
        });

        clientThread.start();

        return this;
    }

    public void disconnect(Runnable disconnected) {
        if (channelFuture.channel().isOpen()) {
            channelFuture.channel().close().syncUninterruptibly();
        }

        workerGroup.shutdownGracefully();

        disconnected.run();
    }

}
