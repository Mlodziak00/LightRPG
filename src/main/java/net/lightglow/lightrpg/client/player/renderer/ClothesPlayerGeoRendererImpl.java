package net.lightglow.lightrpg.client.player.renderer;

import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.client.player.animatibles.ClothesPlayerVisualAnimatable;
import net.lightglow.lightrpg.client.player.animatibles.EyesPlayerVisualAnimatable;
import net.lightglow.lightrpg.client.player.model.ClothesPlayerGeoModel;
import net.lightglow.lightrpg.client.player.model.EyesPlayerGeoModel;
import net.lightglow.lightrpg.component.entity.PlayerAppearanceComponent;
import net.lightglow.lightrpg.component.entity.PlayerImpactfulComponent;
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

        return resourceExists(texture) ? texture : LightRPG.id("textures/baseclothing/empty.png");
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
