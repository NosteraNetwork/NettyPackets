package net.nostera.dev.protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class PacketManager {

    private static final Map<Integer, Class<? extends AbstractPacket>> packetMap = new ConcurrentHashMap<>();

    public static void registerPacket(int id, Class<? extends AbstractPacket> packet) {
        packetMap.put(id, packet);
    }

    public static int getPacketId(Class<? extends AbstractPacket> clazz) {
        for (Integer id : packetMap.keySet()) {
            if (packetMap.get(id).equals(clazz)) {
                return id;
            }
        }

        return -1;
    }

    public static AbstractPacket createInstance(int id) throws IllegalAccessException, InstantiationException {
        return packetMap.get(id).newInstance();
    }

}
