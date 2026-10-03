package net.lightglow.racialfeats.client.player.renderer;

import net.lightglow.racialfeats.client.IMojangModelPart;
import net.lightglow.racialfeats.client.player.animatibles.RaceHandsPlayerVisualAnimatable;
import net.lightglow.racialfeats.client.player.model.RaceHandsPlayerGeoModel;
import net.lightglow.racialfeats.component.entity.PlayerAppearanceComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.ColorHelper;
import software.bernie.geckolib.util.Color;

public class RaceHandsPlayerGeoRenderer<T extends LivingEntity, M extends PlayerEntityModel<T>> extends FeatureRenderer<T, M> {

    public static final RaceHandsPlayerVisualAnimatable ANIMATABLE =
            RaceHandsPlayerVisualAnimatable.INSTANCE;

    public static final RaceHandsPlayerGeoRendererImpl GEO_RENDERER =
            new RaceHandsPlayerGeoRendererImpl();

    public static final RaceHandsPlayerGeoModel MODEL =
            new RaceHandsPlayerGeoModel();

    public RaceHandsPlayerGeoRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Override
    public void render(
            MatrixStack matrices,
            VertexConsumerProvider consumers,
            int light,
            T entity,
            float limbAngle,
            float limbDistance,
            float tickDelta,
            float animationProgress,
            float headYaw,
            float headPitch) {

        if (!(entity instanceof AbstractClientPlayerEntity player)) return;

        matrices.push();


        // === ABSOLUTE MINIMUM TRANSFORM ===
        //matrices.multiply(new Quaternionf().rotateY(180 * MathHelper.RADIANS_PER_DEGREE));
        //matrices.multiply(new Quaternionf().rotateX(180 * MathHelper.RADIANS_PER_DEGREE));
        matrices.translate(0, -1.5f, 0);
        matrices.translate(-0.5f, -0.5f, -0.5f);
        PlayerEntityRenderer playerRenderer =
                (PlayerEntityRenderer) MinecraftClient.getInstance()
                        .getEntityRenderDispatcher()
                        .getRenderer(player);

        IMojangModelPart head = (IMojangModelPart) (Object) playerRenderer.getModel().head;
        IMojangModelPart body = (IMojangModelPart) (Object) playerRenderer.getModel().body;
        IMojangModelPart rightArm = (IMojangModelPart) (Object) playerRenderer.getModel().rightArm;
        IMojangModelPart leftArm = (IMojangModelPart) (Object) playerRenderer.getModel().leftArm;
        IMojangModelPart rightLeg = (IMojangModelPart) (Object) playerRenderer.getModel().rightLeg;
        IMojangModelPart leftLeg = (IMojangModelPart) (Object) playerRenderer.getModel().leftLeg;


        ANIMATABLE.setPlayer(player);

        int argb = PlayerAppearanceComponent.KEY.get(player).getSkinColor();
        argb |= 0xFF000000;

        float r = ColorHelper.Argb.getRed(argb)   / 255f;
        float g = ColorHelper.Argb.getGreen(argb) / 255f;
        float b = ColorHelper.Argb.getBlue(argb)  / 255f;

        GEO_RENDERER.setColor(Color.ofRGB(r, g, b));
        GEO_RENDERER.render(
                matrices,
                ANIMATABLE,
                consumers,
                RenderLayer.getEntityTranslucent(
                        GEO_RENDERER.getTextureLocation(ANIMATABLE)
                ),
                null,
                light,
                tickDelta
        );

        GEO_RENDERER.setColor(Color.WHITE);
        matrices.pop();
    }


}
