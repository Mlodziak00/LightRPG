package net.lightglow.racialfeats.trait;

import net.lightglow.racialfeats.RacialFeats;
import net.lightglow.racialfeats.trait.imp.RaceTrait;
import net.minecraft.util.Identifier;

public class WeakWings extends RaceTrait {
    private static final Identifier ID = RacialFeats.id("weak_wings");
    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public TraitType getType() {
        return TraitType.RACIAL;
    }

}
