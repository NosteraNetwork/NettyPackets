package me.mstn.example.client;

import net.nostera.dev.NettyClient;
import net.nostera.dev.protocol.PacketManager;
import net.nostera.dev.protocol.handler.PacketDecoder;
import net.nostera.dev.protocol.handler.PacketEncoder;
import net.nostera.dev.protocol.handler.NettyPacketLengthDeserializer;
import net.nostera.dev.protocol.handler.NettyPacketLengthSerializer;
import me.mstn.example.common.HelloWorldPacket;

public class ExampleClient {

    private NettyClient nettyClient;

    public static void main(String[] args) {
        new ExampleClient().connect();
    }

    public void connect() {
        PacketManager.registerPacket(1001, HelloWorldPacket.class);

        this.nettyClient = new NettyClient(1337).connect(
                () -> {
                    System.out.println("Connected to Server successfully!");
                },
                () -> {
                    System.out.println("Unable connect to Server.");
                },
                (channel) -> {
                    channel.pipeline()
                            .addLast(new NettyPacketLengthDeserializer())
                            .addLast(new PacketDecoder())
                            .addLast(new NettyPacketLengthSerializer())
                            .addLast(new PacketEncoder())
                            .addLast(new ClientPacketHandler());
                }
        );
    }
    public void shutdownApplication() {
        nettyClient.disconnect(() -> {
            System.out.println("Client disconnected");
        });
    }

}
