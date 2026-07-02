package net.lightglow.lightrpg.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.event.client.player.ClientPlayerBlockBreakEvents;
import net.lightglow.lightrpg.client.player.renderer.ClothesPlayerGeoRenderer;
import net.lightglow.lightrpg.client.player.renderer.EyesPlayerGeoRenderer;
import net.lightglow.lightrpg.client.player.renderer.HairPlayerGeoRenderer;
import net.lightglow.lightrpg.client.player.renderer.RacePlayerGeoRenderer;
import net.lightglow.lightrpg.client.screen.CharacterCustomizationScreen;
import net.lightglow.lightrpg.item.greatshield.GreatShieldItem;
import net.lightglow.lightrpg.network.OpenCharCustomScreenPayload;
import net.lightglow.lightrpg.reg.WeaponRegistry;
import net.lightglow.lightrpg.trait.imp.RaceTraitDispatcher;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
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
