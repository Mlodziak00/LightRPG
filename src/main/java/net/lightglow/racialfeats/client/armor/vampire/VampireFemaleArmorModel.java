package net.lightglow.racialfeats.client.armor.vampire;

import net.lightglow.racialfeats.RacialFeats;
import net.lightglow.racialfeats.item.armor.VampireArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class VampireFemaleArmorModel extends GeoModel<VampireArmorItem> {

    @Override
    public Identifier getModelResource(VampireArmorItem crusaderArmorItem) {
        return RacialFeats.id("geo/item/armor/base_armor_female.geo.json");
    }

    @Override
    public Identifier getTextureResource(VampireArmorItem crusaderArmorItem) {
        return RacialFeats.id("textures/item/armor/vampire_armor.png");
    }

    @Override
    public Identifier getAnimationResource(VampireArmorItem crusaderArmorItem) {
        return RacialFeats.id("animations/item/armor/base_armor.animation.json");
    }
}
