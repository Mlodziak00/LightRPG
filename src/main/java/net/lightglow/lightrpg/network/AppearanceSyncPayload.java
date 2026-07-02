package net.lightglow.lightrpg.network;

import net.lightglow.lightrpg.LightRPG;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record AppearanceSyncPayload (
        Identifier race,
        boolean gender,
        Identifier hairStyle,
        Identifier eyeStyle,
        Identifier clothingStyle
    ) implements CustomPayload {
    public static final Id<AppearanceSyncPayload> ID =
            new Id<>(LightRPG.id("appearance_sync"));

    public static final PacketCodec<RegistryByteBuf, AppearanceSyncPayload> CODEC =
            PacketCodec.tuple(
                    Identifier.PACKET_CODEC, AppearanceSyncPayload::race,
                    PacketCodecs.BOOL, AppearanceSyncPayload::gender,
                    Identifier.PACKET_CODEC, AppearanceSyncPayload::hairStyle,
                    Identifier.PACKET_CODEC, AppearanceSyncPayload::eyeStyle,
                    Identifier.PACKET_CODEC, AppearanceSyncPayload::clothingStyle,
                    AppearanceSyncPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
