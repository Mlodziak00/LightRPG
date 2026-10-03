package net.lightglow.racialfeats.mixin;

import net.lightglow.racialfeats.client.player.animatibles.RaceHandsPlayerVisualAnimatable;
import net.lightglow.racialfeats.client.player.renderer.*;
import net.lightglow.racialfeats.component.entity.PlayerAppearanceComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.util.Color;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }
    @Unique
    private static final RaceHandsPlayerVisualAnimatable ANIMATABLE =
            RaceHandsPlayerVisualAnimatable.INSTANCE;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addFeatureso(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci){
        this.addFeature(new RacePlayerGeoRenderer<>(this));
        this.addFeature(new HairPlayerGeoRenderer<>(this));
        this.addFeature(new EyesPlayerGeoRenderer<>(this));
        this.addFeature(new ClothesPlayerGeoRenderer<>(this));
    }

    @Unique
    private static final RaceHandsPlayerGeoRendererImpl GEO_RENDERER =
            new RaceHandsPlayerGeoRendererImpl();
    @Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    private void hideVanillaModel(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci){
        this.model.setVisible(false);
    }
    @Inject(method = "renderArm", at =@At("HEAD"), cancellable = true)
    private void new$addFeatures(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve, CallbackInfo ci){

        ci.cancel();

        PlayerEntityRenderer renderer = (PlayerEntityRenderer)(Object)this;
        PlayerEntityModel<AbstractClientPlayerEntity> model = renderer.getModel();

        boolean rightArm = arm == model.rightArm;

        ANIMATABLE.setPlayer(player);

        BakedGeoModel bakedModel =
                GEO_RENDERER.getGeoModel()
                        .getBakedModel(
                                GEO_RENDERER.getGeoModel()
                                        .getModelResource(ANIMATABLE)
                        );

        String boneName = rightArm ? "bipedRightArm" : "bipedLeftArm";
        bakedModel.getBone("bipedRightArm").ifPresent(b -> b.setHidden(true));
        bakedModel.getBone("bipedLeftArm").ifPresent(b -> b.setHidden(true));

        bakedModel.getBone(rightArm ? "bipedRightArm" : "bipedLeftArm")
                .ifPresent(b -> b.setHidden(false));

        matrices.push();
        matrices.translate(rightArm ? -0.5 : 0.0, -2.0, 0.0);

        matrices.scale(2.0f, 2.0f, 2.0f);


        int argb = PlayerAppearanceComponent.KEY.get(player).getSkinColor();
        argb |= 0xFF000000;

        float r = ColorHelper.Argb.getRed(argb)   / 255f;
        float g = ColorHelper.Argb.getGreen(argb) / 255f;
        float b = ColorHelper.Argb.getBlue(argb)  / 255f;
        float a = ColorHelper.Argb.getAlpha(argb) / 255f;

        GEO_RENDERER.setColor(Color.ofRGB(r, g, b));
        GEO_RENDERER.render(
                matrices,
                ANIMATABLE,
                vertexConsumers,
                RenderLayer.getEntityTranslucent(
                        GEO_RENDERER.getTextureLocation(ANIMATABLE)
                ),
                null,
                light,
                MinecraftClient.getInstance().getRenderTime()
        );
        GEO_RENDERER.setColor(Color.WHITE);

        matrices.pop();
        ANIMATABLE.clearPlayer();

    }
}
