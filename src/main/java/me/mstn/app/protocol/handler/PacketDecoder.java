package me.mstn.app.protocol.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.mstn.app.protocol.EnotixPacket;
import me.mstn.app.protocol.PacketManager;

import java.util.List;

public final class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        int id = byteBuf.readInt();

        EnotixPacket packet = PacketManager.createInstance(id);
        packet.read(byteBuf);
        out.add(packet);
    }

}
