package net.lightglow.lightrpg.client.armor.crusader;

import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.item.armor.CrusaderArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CrusaderFemaleArmorModel extends GeoModel<CrusaderArmorItem> {

    @Override
    public Identifier getModelResource(CrusaderArmorItem crusaderArmorItem) {
        return LightRPG.id("geo/item/armor/base_armor_female.geo.json");
    }

    @Override
    public Identifier getTextureResource(CrusaderArmorItem crusaderArmorItem) {
        return LightRPG.id("textures/item/armor/crusader_armor.png");
    }

    @Override
    public Identifier getAnimationResource(CrusaderArmorItem crusaderArmorItem) {
        return LightRPG.id("animations/item/armor/base_armor.animation.json");
    }
}
