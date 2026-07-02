package net.lightglow.lightrpg.network;

import net.lightglow.lightrpg.LightRPG;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record AppearanceColorSyncPayload(
        int skinColor,
        int hairColor,
        int eyeColor,
        int clothingColor
    ) implements CustomPayload {
    public static final Id<AppearanceColorSyncPayload> ID =
            new Id<>(LightRPG.id("appearance_color_sync"));

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
