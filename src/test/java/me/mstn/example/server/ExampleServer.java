package me.mstn.example.server;

import me.mstn.app.NettyServer;
import me.mstn.app.protocol.PacketManager;
import me.mstn.app.protocol.handler.PacketDecoder;
import me.mstn.app.protocol.handler.PacketEncoder;
import me.mstn.app.protocol.handler.NettyPacketLengthDeserializer;
import me.mstn.app.protocol.handler.NettyPacketLengthSerializer;
import me.mstn.example.common.HelloWorldPacket;

public class ExampleServer {

    private NettyServer nettyServer;

    public static void main(String[] args) {
        new ExampleServer().start();
    }

    public void start() {
        PacketManager.registerPacket(1001, HelloWorldPacket.class);

        this.nettyServer = new NettyServer(1337).bind(
                () -> {
                    System.out.println("Sever started on 1337 port");
                },
                (channel) -> {
                    channel.pipeline()
                            .addLast(new NettyPacketLengthDeserializer())
                            .addLast(new PacketDecoder())
                            .addLast(new NettyPacketLengthSerializer())
                            .addLast(new PacketEncoder())
                            .addLast(new ServerPacketHandler());

                    //For example, send packet after client connection:
                    channel.writeAndFlush(new HelloWorldPacket("Hello, Client!"));
                }
        );
    }
    public void shutdownApplication() {
        nettyServer.shutdown(() -> {
            System.out.println("Server closed");
        });
    }

}