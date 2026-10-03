package net.lightglow.racialfeats.network;

import net.lightglow.racialfeats.RacialFeats;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record AppearanceColorSyncPayload(
        int skinColor,
        int hairColor,
        int eyeColor,
        int clothingColor
    ) implements CustomPayload {
    public static final Id<AppearanceColorSyncPayload> ID =
            new Id<>(RacialFeats.id("appearance_color_sync"));

    public static final PacketCodec<RegistryByteBuf, AppearanceColorSyncPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.INTEGER, AppearanceColorSyncPayload::skinColor,
                    PacketCodecs.INTEGER, AppearanceColorSyncPayload::hairColor,
                    PacketCodecs.INTEGER, AppearanceColorSyncPayload::eyeColor,
                    PacketCodecs.INTEGER, AppearanceColorSyncPayload::clothingColor,
                    AppearanceColorSyncPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
