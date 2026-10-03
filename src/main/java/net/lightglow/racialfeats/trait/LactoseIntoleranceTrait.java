package net.lightglow.racialfeats.trait;

import net.lightglow.racialfeats.RacialFeats;
import net.lightglow.racialfeats.trait.imp.RaceTrait;
import net.minecraft.util.Identifier;

public class LactoseIntoleranceTrait extends RaceTrait {

    @Override
    public Identifier getId() {
        return RacialFeats.id("lactose_intolerance");
    }

    @Override
    public TraitType getType() {
        return TraitType.PASSIVE;
    }

}
