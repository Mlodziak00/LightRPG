package net.lightglow.lightrpg.trait;

import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.trait.imp.RaceTrait;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class WeakWings extends RaceTrait {
    private static final Identifier ID = LightRPG.id("weak_wings");
    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public TraitType getType() {
        return TraitType.RACIAL;
    }

}
