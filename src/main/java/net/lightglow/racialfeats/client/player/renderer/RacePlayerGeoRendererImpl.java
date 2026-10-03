package net.lightglow.racialfeats.client.player.renderer;

import net.lightglow.racialfeats.client.player.animatibles.RacePlayerVisualAnimatable;
import net.lightglow.racialfeats.client.player.model.RacePlayerGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;
import software.bernie.geckolib.util.Color;

public class RacePlayerGeoRendererImpl extends GeoObjectRenderer<RacePlayerVisualAnimatable> {

    private Color color = Color.WHITE;

    public RacePlayerGeoRendererImpl() {
        super(new RacePlayerGeoModel());
    }

    public void setColor(Color color){
        this.color = color;
    }

    @Override
    public GeoModel<RacePlayerVisualAnimatable> getGeoModel() {
        return super.getGeoModel();
    }

    @Override
    public Color getRenderColor(RacePlayerVisualAnimatable animatable, float partialTick, int packedLight) {
        return color;
    }
}
