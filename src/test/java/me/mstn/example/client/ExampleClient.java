package me.mstn.example.client;

import me.mstn.app.NettyClient;
import me.mstn.app.protocol.PacketManager;
import me.mstn.app.protocol.handler.PacketDecoder;
import me.mstn.app.protocol.handler.PacketEncoder;
import me.mstn.app.protocol.handler.NettyPacketLengthDeserializer;
import me.mstn.app.protocol.handler.NettyPacketLengthSerializer;
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
