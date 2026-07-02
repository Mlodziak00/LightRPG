package net.lightglow.lightrpg.client.player.renderer;

import net.lightglow.lightrpg.client.player.animatibles.RaceHandsPlayerVisualAnimatable;
import net.lightglow.lightrpg.client.player.animatibles.RacePlayerVisualAnimatable;
import net.lightglow.lightrpg.client.player.model.RaceHandsPlayerGeoModel;
import net.lightglow.lightrpg.client.player.model.RacePlayerGeoModel;
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
