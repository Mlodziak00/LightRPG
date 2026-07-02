package net.lightglow.lightrpg.trait;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lightglow.lightrpg.client.sound.VampireSizzleSound;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

@Environment(EnvType.CLIENT)
public final class VampireClientTrait {
    private static VampireSizzleSound activeSound;

    public static void tick(ClientPlayerEntity player, float targetVolume) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (targetVolume > 0f) {
            if (activeSound == null || activeSound.isDone()) {
                activeSound = new VampireSizzleSound(player);
                activeSound.setTargetVolume(targetVolume);
                client.getSoundManager().play(activeSound);
            }
            activeSound.setTargetVolume(targetVolume);
        } else if (activeSound != null) {
            activeSound.setTargetVolume(0f);
        }
    }
}
