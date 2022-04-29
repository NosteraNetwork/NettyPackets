package net.nostera.dev.protocol.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.nostera.dev.protocol.AbstractPacket;
import net.nostera.dev.protocol.PacketManager;

import java.util.List;

public final class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        int id = byteBuf.readInt();

        AbstractPacket packet = PacketManager.createInstance(id);
        packet.read(byteBuf);
        out.add(packet);
    }

}
