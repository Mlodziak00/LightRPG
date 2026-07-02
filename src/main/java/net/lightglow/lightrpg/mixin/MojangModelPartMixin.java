package net.lightglow.lightrpg.mixin;

import net.lightglow.lightrpg.client.IMojangModelPart;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelTransform;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ModelPart.class)
public abstract class MojangModelPartMixin implements IMojangModelPart {
    @Shadow public abstract ModelTransform getTransform();

    @Override
    public Vector3d lightrpg$getPosition() {
        ModelTransform t = getTransform();
        return new Vector3d(t.pivotX, t.pivotY, t.pivotZ).negate();
    }

    @Override
    public Vector3d lightrpg$getRotation() {
        ModelTransform t = getTransform();
        return new Vector3d(t.pitch, t.yaw, t.roll);
    }
}
