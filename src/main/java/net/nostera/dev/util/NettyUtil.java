package net.nostera.dev.util;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public final class NettyUtil {

    public static EventLoopGroup getGroup() {
        return isEpoll() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
    }

    public static Class<? extends ServerChannel> getServerChannel() {
        return isEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
    }

    public static Class<? extends SocketChannel> getSocketChannel() {
        return isEpoll() ? EpollSocketChannel.class : NioSocketChannel.class;
    }

    public static boolean isEpoll() {
        return Epoll.isAvailable();
    }

}
