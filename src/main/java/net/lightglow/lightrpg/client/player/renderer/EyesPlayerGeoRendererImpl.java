package net.lightglow.lightrpg.client.player.renderer;

import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.client.player.animatibles.EyesPlayerVisualAnimatable;
import net.lightglow.lightrpg.client.player.model.EyesPlayerGeoModel;
import net.lightglow.lightrpg.component.entity.PlayerAppearanceComponent;
import net.lightglow.lightrpg.component.entity.PlayerImpactfulComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;
import software.bernie.geckolib.util.Color;

public class EyesPlayerGeoRendererImpl extends GeoObjectRenderer<EyesPlayerVisualAnimatable> {

    private Color color = Color.WHITE;

    public EyesPlayerGeoRendererImpl() {
        super(new EyesPlayerGeoModel());
    }

    public void setColor(Color color){
        this.color = color;
    }

    @Override
    public GeoModel<EyesPlayerVisualAnimatable> getGeoModel() {
        return super.getGeoModel();
    }

    public Identifier getTintedTextureResource(EyesPlayerVisualAnimatable animatable) {
        AbstractClientPlayerEntity player = animatable.getPlayer();
        Identifier eyeType = PlayerAppearanceComponent.KEY.get(player).getEyeType();
        Identifier eye = Identifier.of(eyeType.getNamespace(),"textures/eyes/"+eyeType.getPath()+"t.png");

        return resourceExists(eye) ? eye : LightRPG.id("textures/eyes/empty.png");
    }
    public Identifier getSkinTintedTextureResource(EyesPlayerVisualAnimatable animatable) {
        AbstractClientPlayerEntity player = animatable.getPlayer();
        Identifier eyeType = PlayerAppearanceComponent.KEY.get(player).getEyeType();
        Identifier eye = Identifier.of(eyeType.getNamespace(),"textures/eyes/"+eyeType.getPath()+"s.png");

        return resourceExists(eye) ? eye : LightRPG.id("textures/eyes/empty.png");
    }

    private boolean resourceExists(Identifier id) {
        return MinecraftClient.getInstance()
                .getResourceManager()
                .getResource(id)
                .isPresent();
    }

    @Override
    public Color getRenderColor(EyesPlayerVisualAnimatable animatable, float partialTick, int packedLight) {
        return color;
    }
}
