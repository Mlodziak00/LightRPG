package net.lightglow.racialfeats.trait;

import net.lightglow.racialfeats.trait.imp.RaceTrait;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TraitRge {
    private static final Map<Identifier, Supplier<RaceTrait>> TRAITS =
            new HashMap<>();

    public static Identifier register(
            Identifier id,
            Supplier<RaceTrait> factory
    ) {
        TRAITS.put(id, factory);
        return id;
    }

    public static RaceTrait create(Identifier id) {
        Supplier<RaceTrait> factory = TRAITS.get(id);
        return factory != null ? factory.get() : null;
    }

    public static boolean isRegistered(Identifier id) {
        return TRAITS.containsKey(id);
    }
}
