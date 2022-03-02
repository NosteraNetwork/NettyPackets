package me.mstn.example.client;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import me.mstn.app.protocol.EnotixPacket;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientNetworkHandler {

    @Setter
    private static Channel masterChannel;

    @Getter
    private static final ConcurrentLinkedQueue<EnotixPacket> packetQueue = new ConcurrentLinkedQueue<>();

    public static void sendServerPacket(EnotixPacket packet) {
        if (isConnected()) {
            masterChannel.writeAndFlush(packet);
        } else {
            packetQueue.offer(packet);
        }
    }

    private static boolean isConnected() {
        return masterChannel != null && masterChannel.isActive() && masterChannel.isOpen();
    }

}
