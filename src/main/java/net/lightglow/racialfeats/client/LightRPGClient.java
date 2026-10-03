package net.lightglow.racialfeats.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.lightglow.racialfeats.client.screen.CharacterCustomizationScreen;
import net.lightglow.racialfeats.item.greatshield.GreatShieldItem;
import net.lightglow.racialfeats.network.OpenCharCustomScreenPayload;
import net.lightglow.racialfeats.reg.WeaponRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class LightRPGClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for (GreatShieldItem greatshieldItem : WeaponRegistry.SHIELDS){
            ModelPredicateProviderRegistry.register(greatshieldItem, Identifier.of("minecraft:blocking"), (stack, world, entity, seed) -> {
                if (entity == null){
                    return 0.0F;
                }
                return entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F;
            });
        }

        /*ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            if (minecraftClient.player != null) {
                RaceTraitDispatcher.tickClient(minecraftClient.player);
            }
        });*/

        ClientPlayNetworking.registerGlobalReceiver(OpenCharCustomScreenPayload.ID,
                (openCharCustomScreenPayload, context) -> context.client().setScreen(new CharacterCustomizationScreen(openCharCustomScreenPayload.changeRace())));

    }
}
