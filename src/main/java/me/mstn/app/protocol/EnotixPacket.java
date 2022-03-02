package me.mstn.app.protocol;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public abstract class EnotixPacket {

    public void write(ByteBuf byteBuf) throws IOException {

    }

    public void read(ByteBuf byteBuf) throws IOException {

    }

    protected void writeStringArray(ByteBuf byteBuf, String[] arr) {
        byteBuf.writeInt(arr.length);
        for (String s : arr) {
            writeString(byteBuf, s);
        }
    }

    protected String[] readStringArray(ByteBuf byteBuf) {
        int length = byteBuf.readInt();
        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            array[i] = readString(byteBuf);
        }

        return array;
    }

    protected void writeString(ByteBuf byteBuf, String s) {
        byte[] bArr = s.getBytes();
        byteBuf.writeInt(bArr.length);
        byteBuf.writeBytes(bArr);
    }

    protected String readString(ByteBuf byteBuf) {
        byte[] bArr = new byte[byteBuf.readInt()];
        byteBuf.readBytes(bArr);

        return new String(bArr);
    }

}
