package net.lightglow.racialfeats.race;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class RacialFeatureInstance {
    private Identifier featureId;
    private int color;
    private boolean enabled;

    public RacialFeatureInstance(Identifier featureId, int color, boolean enabled) {
        this.featureId = featureId;
        this.color = color;
        this.enabled = enabled;
    }


    public Identifier getFeatureId() {
        return featureId;
    }

    public int getColor() {
        return color;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public NbtCompound writeNbt() {
        NbtCompound tag = new NbtCompound();
        tag.putString("Id", featureId.toString());
        tag.putInt("Color", color);
        tag.putBoolean("Enabled", enabled);
        return tag;
    }

    public static RacialFeatureInstance fromNbt(NbtCompound tag) {
        return new RacialFeatureInstance(
                Identifier.of(String.valueOf(tag.getString("Id"))),
                tag.getInt("Color"),
                tag.getBoolean("Enabled")
        );
    }
}
