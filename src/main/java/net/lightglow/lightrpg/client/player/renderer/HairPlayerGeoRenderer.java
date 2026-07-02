package net.lightglow.lightrpg.client.player.renderer;

import net.lightglow.lightrpg.client.GeoPlayerTransformUtil;
import net.lightglow.lightrpg.client.player.animatibles.HairPlayerVisualAnimatable;
import net.lightglow.lightrpg.client.IMojangModelPart;
import net.lightglow.lightrpg.client.player.model.HairPlayerGeoModel;
import net.lightglow.lightrpg.component.entity.PlayerAppearanceComponent;
import net.lightglow.lightrpg.tag.ModItemTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.joml.Quaternionf;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.util.Color;

public class HairPlayerGeoRenderer<T extends LivingEntity, M extends PlayerEntityModel<T>> extends FeatureRenderer<T, M> {

    private static final HairPlayerVisualAnimatable ANIMATABLE =
            HairPlayerVisualAnimatable.INSTANCE;

    private static final HairPlayerGeoRendererImpl GEO_RENDERER =
            new HairPlayerGeoRendererImpl();

    private static final HairPlayerGeoModel MODEL =
            new HairPlayerGeoModel();

    public HairPlayerGeoRenderer(FeatureRendererContext<T, M> context) {
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
        matrices.multiply(new Quaternionf().rotateY(180 * MathHelper.RADIANS_PER_DEGREE));
        matrices.multiply(new Quaternionf().rotateX(180 * MathHelper.RADIANS_PER_DEGREE));
        matrices.translate(0, -1.5f, 0);
        matrices.translate(-0.5f, -0.5f, -0.5f);
        M model = this.getContextModel();

        IMojangModelPart head = (IMojangModelPart) (Object) model.head;
        IMojangModelPart body = (IMojangModelPart) (Object) model.body;
        IMojangModelPart rightArm = (IMojangModelPart) (Object) model.rightArm;
        IMojangModelPart leftArm = (IMojangModelPart) (Object) model.leftArm;
        IMojangModelPart rightLeg = (IMojangModelPart) (Object) model.rightLeg;
        IMojangModelPart leftLeg = (IMojangModelPart) (Object) model.leftLeg;


        ANIMATABLE.setPlayer(player);
        BakedGeoModel bakedModel =
                GEO_RENDERER.getGeoModel()
                        .getBakedModel(MODEL.getModelResource(ANIMATABLE));

        var headBone = bakedModel.getBone("bipedHead");
        var bodyBone = bakedModel.getBone("bipedBody");
        var rightArmBone = bakedModel.getBone("bipedRightArm");
        var leftArmBone = bakedModel.getBone("bipedLeftArm");
        var rightLegBone = bakedModel.getBone("bipedRightLeg");
        var leftLegBone = bakedModel.getBone("bipedLeftLeg");


        headBone.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(head, bone, 0, 0, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_HAIR));
                }
        );

        bodyBone.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(body, bone, 0, 0, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_HAIR));
                }
        );

        rightArmBone.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(rightArm, bone, 5, 2, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_HAIR));
                }
        );

        leftArmBone.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(leftArm, bone, -5, 2, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_HAIR));
                }
        );

        rightLegBone.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(rightLeg, bone, 2, 12, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_HAIR));
                }
        );

        leftLegBone.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(leftLeg, bone, -2, 12, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_HAIR));
                }
        );

        int argb = PlayerAppearanceComponent.KEY.get(player).getHairColor();
        argb |= 0xFF000000;

        float r = ColorHelper.Argb.getRed(argb)   / 255f;
        float g = ColorHelper.Argb.getGreen(argb) / 255f;
        float b = ColorHelper.Argb.getBlue(argb)  / 255f;
        float a = ColorHelper.Argb.getAlpha(argb) / 255f;

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
