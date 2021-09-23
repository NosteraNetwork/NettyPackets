package me.mstn.example.common;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.mstn.beenetty.protocol.BeePacket;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldPacket extends BeePacket {

    @Getter
    private String message;

    @Override
    public void write(ByteBuf byteBuf) throws IOException {
        writeString(byteBuf, message);
    }

    @Override
    public void read(ByteBuf byteBuf) throws IOException {
        message = readString(byteBuf);
    }

}