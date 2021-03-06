package me.mstn.example.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import net.nostera.dev.protocol.AbstractPacket;
import me.mstn.example.common.HelloWorldPacket;

@AllArgsConstructor
public class ServerPacketHandler extends SimpleChannelInboundHandler<AbstractPacket> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //Logic: Client connected to server
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //Logic: Client disconnected from server

        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractPacket packet) throws Exception {
        //Logic: Client send packet to Server, handle.

        if (packet instanceof HelloWorldPacket) {
            HelloWorldPacket helloWorldPacket = (HelloWorldPacket) packet;

            System.out.println("Received message from Client: " + helloWorldPacket.getMessage());
        }
    }
}
