package net.lightglow.racialfeats.client.player.renderer;

import net.lightglow.racialfeats.RacialFeats;
import net.lightglow.racialfeats.client.player.animatibles.ClothesPlayerVisualAnimatable;
import net.lightglow.racialfeats.client.player.model.ClothesPlayerGeoModel;
import net.lightglow.racialfeats.component.entity.PlayerAppearanceComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;
import software.bernie.geckolib.util.Color;

public class ClothesPlayerGeoRendererImpl extends GeoObjectRenderer<ClothesPlayerVisualAnimatable> {

    private Color color = Color.WHITE;

    public ClothesPlayerGeoRendererImpl() {
        super(new ClothesPlayerGeoModel());
    }

    public void setColor(Color color){
        this.color = color;
    }

    @Override
    public GeoModel<ClothesPlayerVisualAnimatable> getGeoModel() {
        return super.getGeoModel();
    }

    public Identifier getTintedTextureResource(ClothesPlayerVisualAnimatable animatable) {
        AbstractClientPlayerEntity player = animatable.getPlayer();
        Identifier clothStyle = PlayerAppearanceComponent.KEY.get(player).getClothingType();
        Identifier texture = Identifier.of(clothStyle.getNamespace(),"textures/baseclothing/"+clothStyle.getPath()+"t.png");

        return resourceExists(texture) ? texture : RacialFeats.id("textures/baseclothing/empty.png");
    }

    private boolean resourceExists(Identifier id) {
        return MinecraftClient.getInstance()
                .getResourceManager()
                .getResource(id)
                .isPresent();
    }

    @Override
    public Color getRenderColor(ClothesPlayerVisualAnimatable animatable, float partialTick, int packedLight) {
        return color;
    }
}
