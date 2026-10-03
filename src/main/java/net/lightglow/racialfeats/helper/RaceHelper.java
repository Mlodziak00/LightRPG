package net.lightglow.racialfeats.helper;

import net.lightglow.racialfeats.race.Race;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.Map;

public class RaceHelper {
    public static void applyRaceDimensions(PlayerEntity player, Race race) {
        ScaleData height = ScaleTypes.HEIGHT.getScaleData(player);
        ScaleData width  = ScaleTypes.WIDTH.getScaleData(player);
        ScaleData heightHitbox = ScaleTypes.HITBOX_HEIGHT.getScaleData(player);
        ScaleData widthHitbox  = ScaleTypes.HITBOX_WIDTH.getScaleData(player);
        ScaleData eye    = ScaleTypes.EYE_HEIGHT.getScaleData(player);

        height.resetScale();
        width.resetScale();
        heightHitbox.resetScale();
        widthHitbox.resetScale();
        eye.resetScale();
        height.setPersistence(false);
        width.setPersistence(false);
        heightHitbox.setPersistence(false);
        widthHitbox.setPersistence(false);
        eye.setPersistence(false);

        if (race.getHeight() != 1.0f || race.getWidth() != 1.0f) {
            height.setScale(race.getHeight());
            width.setScale(race.getWidth());
            eye.setScale(race.getHeight());
            height.setPersistence(true);
            width.setPersistence(true);
            eye.setPersistence(true);
        }
        if (race.getHeightHitbox() != 1.0f || race.getHeightHitbox() != 1.0f){
            heightHitbox.setScale(race.getHeightHitbox());
            widthHitbox.setScale(race.getWidthHitbox());
            eye.setScale(race.getHeightHitbox());
            heightHitbox.setPersistence(true);
            widthHitbox.setPersistence(true);
            eye.setPersistence(true);
        }


    }

    public static void applyRaceAttributes(PlayerEntity player, Race race) {
        for (Map.Entry<RegistryEntry<EntityAttribute>, EntityAttributeModifier> entry
                : race.getAttributes().entrySet()) {

            RegistryEntry<EntityAttribute> attributeEntry = entry.getKey();
            EntityAttributeModifier modifier = entry.getValue();

            EntityAttributeInstance instance =
                    player.getAttributeInstance(attributeEntry);

            if (instance == null) continue;

            instance.removeModifier(modifier.id());
            instance.addPersistentModifier(modifier);
        }
    }
    public static void clearRaceAttributes(PlayerEntity player, Race race) {
        for (Map.Entry<RegistryEntry<EntityAttribute>, EntityAttributeModifier> entry
                : race.getAttributes().entrySet()) {

            EntityAttributeInstance instance =
                    player.getAttributeInstance(entry.getKey());

            if (instance != null) {
                instance.removeModifier(entry.getValue().id());
            }
        }
    }
    public static void applyRace(PlayerEntity player, Race race) {
        applyRaceDimensions(player, race);
        applyRaceAttributes(player, race);
    }
}
