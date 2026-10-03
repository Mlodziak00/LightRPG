package net.lightglow.racialfeats.client.player.renderer;

import net.lightglow.racialfeats.client.GeoPlayerTransformUtil;
import net.lightglow.racialfeats.client.player.animatibles.EyesPlayerVisualAnimatable;
import net.lightglow.racialfeats.client.IMojangModelPart;
import net.lightglow.racialfeats.client.player.model.EyesPlayerGeoModel;
import net.lightglow.racialfeats.component.entity.PlayerAppearanceComponent;
import net.lightglow.racialfeats.component.entity.PlayerImpactfulComponent;
import net.lightglow.racialfeats.reg.RaceRegistry;
import net.lightglow.racialfeats.tag.ModItemTags;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
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

public class EyesPlayerGeoRenderer<T extends LivingEntity, M extends PlayerEntityModel<T>> extends FeatureRenderer<T, M> {

    public static final EyesPlayerVisualAnimatable ANIMATABLE =
            EyesPlayerVisualAnimatable.INSTANCE;

    private static final EyesPlayerGeoRendererImpl GEO_RENDERER =
            new EyesPlayerGeoRendererImpl();

    private static final EyesPlayerGeoModel MODEL =
            new EyesPlayerGeoModel();

    public EyesPlayerGeoRenderer(FeatureRendererContext<T, M> context) {
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
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        bodyBone.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(body, bone, 0, 0, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        rightArmBone.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(rightArm, bone, 5, 2, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        leftArmBone.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(leftArm, bone, -5, 2, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        rightLegBone.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(rightLeg, bone, 2, 12, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        leftLegBone.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(leftLeg, bone, -2, 12, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        int argb = PlayerAppearanceComponent.KEY.get(player).getEyeColor();
        argb |= 0xFF000000;

        float r = ColorHelper.Argb.getRed(argb)   / 255f;
        float g = ColorHelper.Argb.getGreen(argb) / 255f;
        float b = ColorHelper.Argb.getBlue(argb)  / 255f;

        GEO_RENDERER.setColor(Color.ofRGB(r, g, b));

        GEO_RENDERER.render(
                matrices,
                ANIMATABLE,
                consumers,
                RaceRegistry.get(PlayerImpactfulComponent.KEY.get(player).getRace()).hasGlowingEyes() ?
                        RenderLayer.getEyes(
                                GEO_RENDERER.getTintedTextureResource(ANIMATABLE)
                        )
    :
                        RenderLayer.getEntityTranslucent(
                                GEO_RENDERER.getTintedTextureResource(ANIMATABLE)
                        ),
                null,
                RaceRegistry.get(PlayerImpactfulComponent.KEY.get(player).getRace()).hasGlowingEyes() ? 15728640 : light,
                tickDelta
        );

        GEO_RENDERER.setColor(Color.WHITE);
        matrices.pop();
        matrices.push();
        matrices.multiply(new Quaternionf().rotateY(180 * MathHelper.RADIANS_PER_DEGREE));
        matrices.multiply(new Quaternionf().rotateX(180 * MathHelper.RADIANS_PER_DEGREE));
        matrices.translate(0, -1.5f, 0);
        matrices.translate(-0.5f, -0.5f, -0.5f);

        ANIMATABLE.setPlayer(player);

        ANIMATABLE.setPlayer(player);

        int argb2 = PlayerAppearanceComponent.KEY.get(player).getSkinColor();
        argb2 |= 0xFF000000;

        float r2 = ColorHelper.Argb.getRed(argb2)   / 255f;
        float g2 = ColorHelper.Argb.getGreen(argb2) / 255f;
        float b2 = ColorHelper.Argb.getBlue(argb2)  / 255f;
        BakedGeoModel bakedModel3 =
                GEO_RENDERER.getGeoModel()
                        .getBakedModel(MODEL.getModelResource(ANIMATABLE));

        var headBone3 = bakedModel3.getBone("bipedHead");
        var bodyBone3 = bakedModel3.getBone("bipedBody");
        var rightArmBone3 = bakedModel3.getBone("bipedRightArm");
        var leftArmBone3 = bakedModel3.getBone("bipedLeftArm");
        var rightLegBone3 = bakedModel3.getBone("bipedRightLeg");
        var leftLegBone3 = bakedModel3.getBone("bipedLeftLeg");

        headBone3.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(head, bone, 0, 0, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        bodyBone3.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(body, bone, 0, 0, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        rightArmBone3.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(rightArm, bone, 5, 2, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        leftArmBone3.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(leftArm, bone, -5, 2, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        rightLegBone3.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(rightLeg, bone, 2, 12, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        leftLegBone3.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(leftLeg, bone, -2, 12, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        GEO_RENDERER.setColor(Color.ofRGB(r2, g2, b2));
        GEO_RENDERER.render(
                matrices,
                ANIMATABLE,
                consumers,
                RenderLayer.getEntityTranslucent(
                        GEO_RENDERER.getSkinTintedTextureResource(ANIMATABLE)
                ),
                null,
                light,
                tickDelta
        );
        GEO_RENDERER.setColor(Color.WHITE);
        matrices.pop();
        matrices.push();
        matrices.multiply(new Quaternionf().rotateY(180 * MathHelper.RADIANS_PER_DEGREE));
        matrices.multiply(new Quaternionf().rotateX(180 * MathHelper.RADIANS_PER_DEGREE));
        matrices.translate(0, -1.5f, 0);
        matrices.translate(-0.5f, -0.5f, -0.5f);

        ANIMATABLE.setPlayer(player);

        ANIMATABLE.setPlayer(player);
        BakedGeoModel bakedModel2 =
                GEO_RENDERER.getGeoModel()
                        .getBakedModel(MODEL.getModelResource(ANIMATABLE));

        var headBone2 = bakedModel2.getBone("bipedHead");
        var bodyBone2 = bakedModel2.getBone("bipedBody");
        var rightArmBone2 = bakedModel2.getBone("bipedRightArm");
        var leftArmBone2 = bakedModel2.getBone("bipedLeftArm");
        var rightLegBone2 = bakedModel2.getBone("bipedRightLeg");
        var leftLegBone2 = bakedModel2.getBone("bipedLeftLeg");

        headBone2.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(head, bone, 0, 0, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        bodyBone2.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(body, bone, 0, 0, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        rightArmBone2.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(rightArm, bone, 5, 2, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        leftArmBone2.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(leftArm, bone, -5, 2, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        rightLegBone2.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(rightLeg, bone, 2, 12, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

        leftLegBone2.ifPresent(bone -> {
                    GeoPlayerTransformUtil.applyLimb(leftLeg, bone, -2, 12, 0);
                    bone.setHidden(player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.HIDES_HEAD_EYES));
                }
        );

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
        matrices.pop();
    }


}
