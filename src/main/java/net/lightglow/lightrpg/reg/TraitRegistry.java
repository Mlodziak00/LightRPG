package net.lightglow.lightrpg.reg;

import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.trait.LactoseIntoleranceTrait;
import net.lightglow.lightrpg.trait.TraitRge;
import net.lightglow.lightrpg.trait.imp.RaceTrait;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TraitRegistry {

    public static final Identifier LACTOSE_INTOLERANCE =
            TraitRge.register(
                    LightRPG.id("lactose_intolerance"),
                    LactoseIntoleranceTrait::new
            );




    public static void register(){}
}
