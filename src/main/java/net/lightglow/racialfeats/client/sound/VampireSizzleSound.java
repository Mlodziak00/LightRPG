package net.lightglow.racialfeats.client.sound;

import net.lightglow.racialfeats.RacialFeats;
import net.lightglow.racialfeats.reg.SoundRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;

public class VampireSizzleSound extends MovingSoundInstance {
    private final ClientPlayerEntity player;
    private float targetVolume = 0.01f;

    public VampireSizzleSound(ClientPlayerEntity player) {
        super(SoundRegistry.VAMPIRE_SIZZLE, SoundCategory.PLAYERS, SoundInstance.createRandom());
        this.player = player;

        this.volume = 0.01f;
        this.repeat = true;
        this.repeatDelay = 0;
        this.pitch = 1.0f;
    }

    public void setTargetVolume(float volume) {
        this.targetVolume = volume;
    }

    @Override
    public void tick() {
        if (player.isRemoved()) {
            this.setDone();
            return;
        }

        this.x = player.getX();
        this.y = player.getEyeY();
        this.z = player.getZ();

        this.volume += (targetVolume - this.volume) * 0.1f;
        RacialFeats.LOGGER.info("vol={} target={}", this.volume, targetVolume);
        if (this.volume < 0.01f && targetVolume <= 0f) {
            this.setDone();
        }

    }
}
