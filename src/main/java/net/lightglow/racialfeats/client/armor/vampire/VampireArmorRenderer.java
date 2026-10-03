package net.lightglow.racialfeats.client.armor.vampire;

import net.lightglow.racialfeats.component.entity.PlayerAppearanceComponent;
import net.lightglow.racialfeats.item.armor.CrusaderArmorItem;
import net.lightglow.racialfeats.item.armor.VampireArmorItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class VampireArmorRenderer extends GeoArmorRenderer<VampireArmorItem> {

    private static final VampireMaleArmorModel MALE_MODEL = new VampireMaleArmorModel();
    private static final VampireFemaleArmorModel FEMALE_MODEL = new VampireFemaleArmorModel();

    public <I extends CrusaderArmorItem> VampireArmorRenderer() {
        super(MALE_MODEL);
    }

    @Override
    public GeoModel<VampireArmorItem> getGeoModel() {
        LivingEntity entity = (LivingEntity) this.currentEntity;

        if (entity instanceof PlayerEntity player) {
            if (PlayerAppearanceComponent.KEY.get(player).isMale()) {
                return MALE_MODEL;
            } else {
                return FEMALE_MODEL;
            }
        }

        return MALE_MODEL; // fallback
    }

}
