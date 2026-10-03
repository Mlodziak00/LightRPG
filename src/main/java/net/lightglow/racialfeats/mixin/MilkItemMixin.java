package net.lightglow.racialfeats.mixin;

import net.lightglow.racialfeats.RacialFeats;
import net.lightglow.racialfeats.component.entity.PlayerImpactfulComponent;
import net.lightglow.racialfeats.reg.TraitRegistry;
import net.lightglow.racialfeats.tag.ModItemTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public class MilkItemMixin {

    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void LactoseCheck(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir){
        if (stack.isIn(ModItemTags.LACTOSE_ITEMS) && !PlayerImpactfulComponent.KEY.get(user).hasTrait(TraitRegistry.LACTOSE_INTOLERANCE)){
            var comp = PlayerImpactfulComponent.KEY.get(user);
            Random random = world.getRandom();
            if (random.nextBetween(0, 100) < 10){
                comp.addPassiveTrait(TraitRegistry.LACTOSE_INTOLERANCE);
            } else {
                RacialFeats.LOGGER.info("No Lactose");
            }
        }
        if (PlayerImpactfulComponent.KEY.get(user).hasTrait(TraitRegistry.LACTOSE_INTOLERANCE)){
            RacialFeats.LOGGER.info("ow lactose");
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 120, 0, false, false));
        }
    }
}
