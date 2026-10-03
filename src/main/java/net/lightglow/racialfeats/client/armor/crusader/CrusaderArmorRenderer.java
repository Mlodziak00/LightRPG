package net.lightglow.racialfeats.client.armor.crusader;

import net.lightglow.racialfeats.component.entity.PlayerAppearanceComponent;
import net.lightglow.racialfeats.item.armor.CrusaderArmorItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CrusaderArmorRenderer extends GeoArmorRenderer<CrusaderArmorItem> {

    private static final CrusaderMaleArmorModel MALE_MODEL = new CrusaderMaleArmorModel();
    private static final CrusaderFemaleArmorModel FEMALE_MODEL = new CrusaderFemaleArmorModel();

    public <I extends CrusaderArmorItem> CrusaderArmorRenderer() {
        super(MALE_MODEL);
    }

    @Override
    public GeoModel<CrusaderArmorItem> getGeoModel() {
        LivingEntity entity = (LivingEntity) this.currentEntity;

        if (entity instanceof PlayerEntity player) {
            if (PlayerAppearanceComponent.KEY.get(player).isMale()) {
                return MALE_MODEL;
            } else {
                return FEMALE_MODEL;
            }
        }

        return MALE_MODEL;
    }

}
