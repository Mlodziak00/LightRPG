package net.lightglow.racialfeats.client;

import software.bernie.geckolib.cache.object.GeoBone;

public final class GeoPlayerTransformUtil {

    private GeoPlayerTransformUtil() {}

    public static void apply(
            IMojangModelPart part,
            GeoBone bone,
            float xOffset,
            float yOffset,
            float zOffset
    ) {
        bone.setRotX((float) -part.lightrpg$getRotation().x);
        bone.setRotY((float) -part.lightrpg$getRotation().y);
        bone.setRotZ((float)  part.lightrpg$getRotation().z);

        bone.updatePosition(
                (float) part.lightrpg$getPosition().x + xOffset,
                (float) -part.lightrpg$getPosition().y + yOffset,
                (float) part.lightrpg$getPosition().z + zOffset
        );
    }
    public static void applyLimb(
            IMojangModelPart part,
            GeoBone bone,
            float xOffset,
            float yOffset,
            float zOffset
    ) {
        bone.setRotX((float) -part.lightrpg$getRotation().x);
        bone.setRotY((float) -part.lightrpg$getRotation().y);
        bone.setRotZ((float)  part.lightrpg$getRotation().z);

        bone.updatePosition(
                (float) -part.lightrpg$getPosition().x + xOffset,
                (float) part.lightrpg$getPosition().y + yOffset,
                (float) -part.lightrpg$getPosition().z + zOffset
        );
    }
}
