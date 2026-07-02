package net.lightglow.lightrpg.mixin;

import net.lightglow.lightrpg.component.entity.PlayerImpactfulComponent;
import net.lightglow.lightrpg.reg.RaceRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    private void setHurstSound(DamageSource source, CallbackInfoReturnable<SoundEvent> cir){
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (RaceRegistry.get(PlayerImpactfulComponent.KEY.get(player).getRace()).getDamageSound() != SoundEvents.ENTITY_PLAYER_HURT) {
            cir.setReturnValue(RaceRegistry.get(PlayerImpactfulComponent.KEY.get(player).getRace()).getDamageSound());
        }
    }
}
