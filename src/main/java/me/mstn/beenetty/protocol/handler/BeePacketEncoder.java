package me.mstn.beenetty.protocol.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.mstn.beenetty.protocol.BeePacket;
import me.mstn.beenetty.protocol.PacketManager;
import me.mstn.beenetty.protocol.ProtocolException;

import java.io.IOException;

public final class BeePacketEncoder extends MessageToByteEncoder<BeePacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, BeePacket packet, ByteBuf byteBuf) throws IOException, ProtocolException {
        int id = PacketManager.getPacketId(packet.getClass());

        if (id == -1) {
            throw new ProtocolException("Packet " + packet.getClass().getSimpleName() + " was not registered");
        }

        byteBuf.writeInt(id);
        packet.write(byteBuf);
    }

}
