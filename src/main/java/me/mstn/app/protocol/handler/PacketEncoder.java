package me.mstn.app.protocol.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.mstn.app.protocol.EnotixPacket;
import me.mstn.app.protocol.PacketManager;
import me.mstn.app.protocol.ProtocolException;

import java.io.IOException;

public final class PacketEncoder extends MessageToByteEncoder<EnotixPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, EnotixPacket packet, ByteBuf byteBuf) throws IOException, ProtocolException {
        int id = PacketManager.getPacketId(packet.getClass());

        if (id == -1) {
            throw new ProtocolException("Packet " + packet.getClass().getSimpleName() + " was not registered");
        }

        byteBuf.writeInt(id);
        packet.write(byteBuf);
    }

}
