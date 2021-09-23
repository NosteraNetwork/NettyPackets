package me.mstn.beenetty.protocol.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.mstn.beenetty.protocol.BeePacket;
import me.mstn.beenetty.protocol.PacketManager;

import java.util.List;

public final class BeePacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        int id = byteBuf.readInt();

        BeePacket packet = PacketManager.createInstance(id);
        packet.read(byteBuf);
        out.add(packet);
    }

}
