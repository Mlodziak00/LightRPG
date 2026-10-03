package net.lightglow.racialfeats.client.armor.crusader;

import net.lightglow.racialfeats.RacialFeats;
import net.lightglow.racialfeats.item.armor.CrusaderArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CrusaderFemaleArmorModel extends GeoModel<CrusaderArmorItem> {

    @Override
    public Identifier getModelResource(CrusaderArmorItem crusaderArmorItem) {
        return RacialFeats.id("geo/item/armor/base_armor_female.geo.json");
    }

    @Override
    public Identifier getTextureResource(CrusaderArmorItem crusaderArmorItem) {
        return RacialFeats.id("textures/item/armor/crusader_armor.png");
    }

    @Override
    public Identifier getAnimationResource(CrusaderArmorItem crusaderArmorItem) {
        return RacialFeats.id("animations/item/armor/base_armor.animation.json");
    }
}
