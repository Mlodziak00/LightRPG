package net.lightglow.lightrpg.client.player.animatibles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;

@Environment(EnvType.CLIENT)
public class RacePlayerVisualAnimatable implements GeoAnimatable {
    AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public static final RacePlayerVisualAnimatable INSTANCE =
            new RacePlayerVisualAnimatable();

    private AbstractClientPlayerEntity currentPlayer;

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController[]{
                new AnimationController(
                        this,
                        "player_race_visual",
                        animationState -> {
                            animationState.setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
                            return PlayState.CONTINUE;
                        }
                )
        });

    }

    public void setPlayer(AbstractClientPlayerEntity player) {
        this.currentPlayer = player;
    }

    public void clearPlayer() {
        this.currentPlayer = null;
    }

    public AbstractClientPlayerEntity getPlayer() {
        return currentPlayer;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return MinecraftClient.getInstance().getRenderTickCounter().getLastFrameDuration();
    }
}
