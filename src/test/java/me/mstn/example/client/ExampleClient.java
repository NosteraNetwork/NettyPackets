package me.mstn.example.client;

import me.mstn.beenetty.BeeClient;
import me.mstn.beenetty.protocol.PacketManager;
import me.mstn.beenetty.protocol.handler.BeePacketDecoder;
import me.mstn.beenetty.protocol.handler.BeePacketEncoder;
import me.mstn.beenetty.protocol.handler.NettyPacketLengthDeserializer;
import me.mstn.beenetty.protocol.handler.NettyPacketLengthSerializer;
import me.mstn.example.common.HelloWorldPacket;

public class ExampleClient {

    private BeeClient beeClient;

    public static void main(String[] args) {
        new ExampleClient().connect();
    }

    public void connect() {
        PacketManager.registerPacket(1001, HelloWorldPacket.class);

        this.beeClient = new BeeClient(1337).connect(
                () -> {
                    System.out.println("Connected to Server successfully!");
                },
                () -> {
                    System.out.println("Unable connect to Server.");
                },
                (channel) -> {
                    channel.pipeline()
                            .addLast(new NettyPacketLengthDeserializer())
                            .addLast(new BeePacketDecoder())
                            .addLast(new NettyPacketLengthSerializer())
                            .addLast(new BeePacketEncoder())
                            .addLast(new ClientPacketHandler());
                }
        );
    }
    public void shutdownApplication() {
        beeClient.disconnect(() -> {
            System.out.println("Client disconnected");
        });
    }

}
