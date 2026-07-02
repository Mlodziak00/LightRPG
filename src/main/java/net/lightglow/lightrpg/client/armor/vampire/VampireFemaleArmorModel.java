package net.lightglow.lightrpg.client.armor.vampire;

import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.item.armor.CrusaderArmorItem;
import net.lightglow.lightrpg.item.armor.VampireArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class VampireFemaleArmorModel extends GeoModel<VampireArmorItem> {

    @Override
    public Identifier getModelResource(VampireArmorItem crusaderArmorItem) {
        return LightRPG.id("geo/item/armor/base_armor_female.geo.json");
    }

    @Override
    public Identifier getTextureResource(VampireArmorItem crusaderArmorItem) {
        return LightRPG.id("textures/item/armor/vampire_armor.png");
    }

    @Override
    public Identifier getAnimationResource(VampireArmorItem crusaderArmorItem) {
        return LightRPG.id("animations/item/armor/base_armor.animation.json");
    }
}
