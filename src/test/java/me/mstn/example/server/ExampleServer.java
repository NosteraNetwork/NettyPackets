package me.mstn.example.server;

import me.mstn.beenetty.BeeServer;
import me.mstn.beenetty.protocol.PacketManager;
import me.mstn.beenetty.protocol.handler.BeePacketDecoder;
import me.mstn.beenetty.protocol.handler.BeePacketEncoder;
import me.mstn.beenetty.protocol.handler.NettyPacketLengthDeserializer;
import me.mstn.beenetty.protocol.handler.NettyPacketLengthSerializer;
import me.mstn.example.common.HelloWorldPacket;

public class ExampleServer {

    private BeeServer beeServer;

    public static void main(String[] args) {
        new ExampleServer().start();
    }

    public void start() {
        PacketManager.registerPacket(1001, HelloWorldPacket.class);

        this.beeServer = new BeeServer(1337).bind(
                () -> {
                    System.out.println("Sever started on 1337 port");
                },
                (channel) -> {
                    channel.pipeline()
                            .addLast(new NettyPacketLengthDeserializer())
                            .addLast(new BeePacketDecoder())
                            .addLast(new NettyPacketLengthSerializer())
                            .addLast(new BeePacketEncoder())
                            .addLast(new ServerPacketHandler());

                    //For example, send packet after client connection:
                    channel.writeAndFlush(new HelloWorldPacket("Hello, Client!"));
                }
        );
    }
    public void shutdownApplication() {
        beeServer.shutdown(() -> {
            System.out.println("Server closed");
        });
    }

}