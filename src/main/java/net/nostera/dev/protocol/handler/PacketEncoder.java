package net.nostera.dev.protocol.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.nostera.dev.protocol.AbstractPacket;
import net.nostera.dev.protocol.PacketManager;
import net.nostera.dev.protocol.ProtocolException;

import java.io.IOException;

public final class PacketEncoder extends MessageToByteEncoder<AbstractPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractPacket packet, ByteBuf byteBuf) throws IOException, ProtocolException {
        int id = PacketManager.getPacketId(packet.getClass());

        if (id == -1) {
            throw new ProtocolException("Packet " + packet.getClass().getSimpleName() + " was not registered");
        }

        byteBuf.writeInt(id);
        packet.write(byteBuf);
    }

}
