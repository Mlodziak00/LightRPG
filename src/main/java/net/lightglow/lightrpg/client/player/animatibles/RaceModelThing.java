package net.lightglow.lightrpg.client.player.animatibles;

import net.lightglow.lightrpg.client.player.model.RacePlayerGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;
import software.bernie.geckolib.util.Color;

public class RaceModelThing extends GeoObjectRenderer<RacePlayerVisualAnimatable> {
    private Color color;
    public RaceModelThing(GeoModel<RacePlayerVisualAnimatable> model) {
        super(model);
    }

    @Override
    public RacePlayerGeoModel getGeoModel() {
        return (RacePlayerGeoModel) super.getGeoModel();
    }

    @Override
    public Color getRenderColor(RacePlayerVisualAnimatable animatable, float partialTick, int packedLight) {
        return this.color;
    }

}
