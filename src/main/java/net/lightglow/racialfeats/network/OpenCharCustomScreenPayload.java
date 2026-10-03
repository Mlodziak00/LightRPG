package net.lightglow.racialfeats.network;

import net.lightglow.racialfeats.RacialFeats;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record OpenCharCustomScreenPayload(boolean changeRace) implements CustomPayload {
    public static final Id<OpenCharCustomScreenPayload> ID =
            new Id<>(RacialFeats.id("open_char_screen_for_player_payload"));

    public static final PacketCodec<RegistryByteBuf, OpenCharCustomScreenPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.BOOL, OpenCharCustomScreenPayload::changeRace,
                    OpenCharCustomScreenPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
