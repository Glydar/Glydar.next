package org.glydar.core.protocol.packet;

import io.netty.buffer.ByteBuf;

import org.glydar.core.protocol.Packet;
import org.glydar.core.protocol.PacketType;
import org.glydar.core.protocol.ProtocolHandler;
import org.glydar.core.protocol.Remote;
import org.glydar.core.protocol.RemoteType;

public class Packet18ServerFull implements Packet {

    @Override
    public PacketType getPacketType() {
        return PacketType.SERVER_FULL;
    }

    @Override
    public void writeTo(RemoteType receiver, ByteBuf buf) {
    }

    @Override
    public <T extends Remote> void dispatchTo(ProtocolHandler<T> handler, T remote) {
        handler.handle(remote, this);
    }
}