package net.lightglow.racialfeats.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.lightglow.racialfeats.component.entity.PlayerAppearanceComponent;
import net.lightglow.racialfeats.component.entity.PlayerImpactfulComponent;
import net.lightglow.racialfeats.helper.RaceHelper;
import net.lightglow.racialfeats.reg.RaceRegistry;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModNetworking {

    public static void registerS2CNetworking(){
        PayloadTypeRegistry.playS2C().register(
                AppearanceSyncPayload.ID,
                AppearanceSyncPayload.CODEC
        );

    }
    public static void registerC2SNetworking(){

        PayloadTypeRegistry.playC2S().register(
                AppearanceSyncPayload.ID,
                AppearanceSyncPayload.CODEC
        );
        ServerPlayNetworking.registerGlobalReceiver(
                AppearanceSyncPayload.ID,
                (payload, context) -> {
                    ServerPlayerEntity player = context.player();

                    context.server().execute(() -> {
                        var compR = PlayerImpactfulComponent.KEY.get(player);
                        var comp = PlayerAppearanceComponent.KEY.get(player);
                        RaceHelper.clearRaceAttributes(player, RaceRegistry.get(compR.getRace()));

                        compR.setRace(payload.race());
                        comp.setMale(payload.gender());
                        comp.setHairStyle(payload.hairStyle());
                        comp.setEyeType(payload.eyeStyle());
                        comp.setClothingType(payload.clothingStyle());

                        compR.sync();
                        comp.sync();
                        RaceHelper.applyRace(player, RaceRegistry.get(compR.getRace()));
                    });
                }
        );
        PayloadTypeRegistry.playC2S().register(
                AppearanceColorSyncPayload.ID,
                AppearanceColorSyncPayload.CODEC
        );
        ServerPlayNetworking.registerGlobalReceiver(
                AppearanceColorSyncPayload.ID,
                (payload, context) -> {
                    ServerPlayerEntity player = context.player();

                    context.server().execute(() -> {
                        var comp = PlayerAppearanceComponent.KEY.get(player);

                        comp.setSkinColor(payload.skinColor());
                        comp.setHairColor(payload.hairColor());
                        comp.setEyeColor(payload.eyeColor());
                        comp.setClothingColor(payload.clothingColor());

                        comp.sync();
                    });
                }
        );

        PayloadTypeRegistry.playS2C().register(
                OpenCharCustomScreenPayload.ID,
                OpenCharCustomScreenPayload.CODEC
        );
    }
}
