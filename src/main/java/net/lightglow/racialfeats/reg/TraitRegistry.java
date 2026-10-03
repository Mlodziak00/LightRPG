package net.lightglow.racialfeats.reg;

import net.lightglow.racialfeats.RacialFeats;
import net.lightglow.racialfeats.trait.LactoseIntoleranceTrait;
import net.lightglow.racialfeats.trait.TraitRge;
import net.minecraft.util.Identifier;

public class TraitRegistry {

    public static final Identifier LACTOSE_INTOLERANCE =
            TraitRge.register(
                    RacialFeats.id("lactose_intolerance"),
                    LactoseIntoleranceTrait::new
            );




    public static void register(){}
}
