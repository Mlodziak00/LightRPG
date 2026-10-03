package net.lightglow.racialfeats.client.player.renderer;

import net.lightglow.racialfeats.client.player.animatibles.HairPlayerVisualAnimatable;
import net.lightglow.racialfeats.client.player.model.HairPlayerGeoModel;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;
import software.bernie.geckolib.util.Color;

public class HairPlayerGeoRendererImpl extends GeoObjectRenderer<HairPlayerVisualAnimatable> {

    private Color color = Color.WHITE;

    public HairPlayerGeoRendererImpl() {
        super(new HairPlayerGeoModel());
    }

    public void setColor(Color color){
        this.color = color;
    }

    @Override
    public GeoModel<HairPlayerVisualAnimatable> getGeoModel() {
        return super.getGeoModel();
    }

    @Override
    public Identifier getTextureLocation(HairPlayerVisualAnimatable animatable) {
        return super.getTextureLocation(animatable);
    }

    @Override
    public Color getRenderColor(HairPlayerVisualAnimatable animatable, float partialTick, int packedLight) {
        return color;
    }
}
