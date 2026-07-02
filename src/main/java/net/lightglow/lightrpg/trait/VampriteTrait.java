package net.lightglow.lightrpg.trait;

import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.client.sound.VampireSizzleSound;
import net.lightglow.lightrpg.reg.SoundRegistry;
import net.lightglow.lightrpg.trait.imp.RaceTrait;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class VampriteTrait extends RaceTrait {

    private static final int MAX_EXPOSURE = 220;
    private static final int DAMAGE_INTERVAL = 5;
    private static final float MAX_VOLUME = 0.4f;

    private int sunTicks;
    private int sunDamageTicks;

    private static VampireSizzleSound activeSound;

    private boolean isInSun(ServerPlayerEntity player) {
        if (player.isCreative() || player.isCreative()) return false;
        if (!player.getWorld().isDay()) return false;
        if (player.getWorld().isRaining()) return false;

        BlockPos pos = player.getBlockPos();
        return player.getWorld().isSkyVisible(pos);
    }

    @Override
    public void onTick(ServerPlayerEntity player) {
        super.onTick(player);
        boolean inSun = isInSun(player);

        if (inSun) {
            sunTicks = Math.min(MAX_EXPOSURE, sunTicks + 1);
        } else {
            sunTicks = Math.max(0, sunTicks - 1);
        }

        handleDamage(player);
    }

    @Override
    public void onClientTick(ClientPlayerEntity player) {
        super.onClientTick(player);
        int exposure = this.sunTicks;
        float targetVolume = (exposure / (float) MAX_EXPOSURE) * MAX_VOLUME;

        VampireClientTrait.tick(player, targetVolume);
    }

    private void handleDamage(ServerPlayerEntity player) {
        if (sunTicks < MAX_EXPOSURE) {
            sunDamageTicks = 0;
            return;
        }

        sunDamageTicks++;

        if (sunDamageTicks >= DAMAGE_INTERVAL) {
            sunDamageTicks = 0;
            player.damage(
                    player.getDamageSources().magic(),
                    1.0f
            );
            player.setFireTicks(10);
        }
    }


    @Override
    public Identifier getId() {
        return LightRPG.id("suntan");
    }

    @Override
    public TraitType getType() {
        return TraitType.RACIAL;
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("SunTick", sunTicks);
        tag.putInt("SunDamageTick", sunDamageTicks);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        sunTicks = tag.getInt("SunTick");
        sunDamageTicks = tag.getInt("SunDamageTick");
    }
}
