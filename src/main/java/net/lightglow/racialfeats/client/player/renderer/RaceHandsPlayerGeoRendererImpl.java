package net.lightglow.racialfeats.client.player.renderer;

import net.lightglow.racialfeats.client.player.animatibles.RaceHandsPlayerVisualAnimatable;
import net.lightglow.racialfeats.client.player.model.RaceHandsPlayerGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;
import software.bernie.geckolib.util.Color;

public class RaceHandsPlayerGeoRendererImpl extends GeoObjectRenderer<RaceHandsPlayerVisualAnimatable> {

    private Color color = Color.WHITE;

    public RaceHandsPlayerGeoRendererImpl() {
        super(new RaceHandsPlayerGeoModel());
    }

    public void setColor(Color color){
        this.color = color;
    }

    @Override
    public GeoModel<RaceHandsPlayerVisualAnimatable> getGeoModel() {
        return super.getGeoModel();
    }

    @Override
    public Color getRenderColor(RaceHandsPlayerVisualAnimatable animatable, float partialTick, int packedLight) {
        return color;
    }
}
