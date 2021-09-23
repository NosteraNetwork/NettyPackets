package me.mstn.example.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import me.mstn.beenetty.protocol.BeePacket;
import me.mstn.example.common.HelloWorldPacket;

import java.net.InetSocketAddress;

@AllArgsConstructor
public class ClientPacketHandler extends SimpleChannelInboundHandler<BeePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //Logic: Client connected to Server

        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();

        if (address.getHostName().equals("127.0.0.1")) {
            ClientNetworkHandler.setMasterChannel(ctx.channel());

            while (!ClientNetworkHandler.getPacketQueue().isEmpty()) {
                ClientNetworkHandler.sendServerPacket(ClientNetworkHandler.getPacketQueue().poll());
            }
        }
    }

    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        //Logic: Server closed the connection

        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BeePacket packet) throws Exception {
        //Logic: Server send packet to Client, handle.

        if (packet instanceof HelloWorldPacket) {
            HelloWorldPacket helloWorldPacket = (HelloWorldPacket) packet;

            System.out.println("Received message from Server: " + helloWorldPacket.getMessage());
            System.out.println("Sending message...");

            ClientNetworkHandler.sendServerPacket(
                    new HelloWorldPacket("Hello, Server!")
            );
        }
    }

}
