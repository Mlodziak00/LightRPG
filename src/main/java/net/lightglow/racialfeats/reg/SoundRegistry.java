package net.lightglow.racialfeats.reg;

import net.lightglow.racialfeats.RacialFeats;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundRegistry {
    public static final SoundEvent VAMPIRE_SIZZLE = registerSound("vampire_sizzle");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = RacialFeats.id(id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }
    public static void register(){}
}
