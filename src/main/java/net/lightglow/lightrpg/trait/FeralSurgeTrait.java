package net.lightglow.lightrpg.trait;

import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.trait.imp.RaceTrait;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ShieldItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class FeralSurgeTrait extends RaceTrait {
    private final double bonus = 0.15f;
    private static final Identifier ID = LightRPG.id("feral_surge_bonus");


    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public TraitType getType() {
        return TraitType.RACIAL;
    }

    @Override
    public void onTick(ServerPlayerEntity player) {
        super.onTick(player);

        EntityAttributeInstance attr =
                player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);

        if (attr == null) return;

        float hpPercent = player.getHealth() / player.getMaxHealth();
        boolean shouldBeActive = hpPercent <= 0.30f;

        boolean hasModifier = attr.getModifier(ID) != null;

        if (shouldBeActive && !hasModifier) {
            attr.addPersistentModifier(new EntityAttributeModifier(
                    ID,
                    bonus,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ));
        }

        if (!shouldBeActive && hasModifier) {
            attr.removeModifier(ID);
        }

        if (shouldBeActive && player.isBlocking()) {
            if (player.getMainHandStack().getItem() instanceof ShieldItem || player.getOffHandStack().getItem() instanceof ShieldItem) {
                player.stopUsingItem();
            }
        }
    }
}
