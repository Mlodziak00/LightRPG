package net.lightglow.lightrpg.client.player.model;

import com.google.common.hash.Hashing;
import it.unimi.dsi.fastutil.longs.Long2ReferenceLinkedOpenHashMap;
import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.client.player.animatibles.ClothesPlayerVisualAnimatable;
import net.lightglow.lightrpg.component.entity.PlayerAppearanceComponent;
import net.lightglow.lightrpg.component.entity.PlayerImpactfulComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4f;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

import java.nio.charset.StandardCharsets;

public class ClothesPlayerGeoModel extends GeoModel<ClothesPlayerVisualAnimatable> {
    public Long2ReferenceLinkedOpenHashMap<GeoBone> boneCache = new Long2ReferenceLinkedOpenHashMap<>();

    private static final Identifier DEFAULT_MODEL =
            LightRPG.id("geo/baseclothing/default.geo.json");
    private static final Identifier DEFAULT_MODEL_F =
            LightRPG.id("geo/baseclothing/defaultfemale.geo.json");

    private static final Identifier EMPTY_TEXTURE =
            LightRPG.id("textures/baseclothing/empty.png");

    private static final Identifier DEFAULT_ANIMATION =
            LightRPG.id("animations/default/default.animation.json");
    @Override
    public Identifier getModelResource(ClothesPlayerVisualAnimatable animatable) {
        AbstractClientPlayerEntity player = animatable.getPlayer();
        boolean isMale = PlayerAppearanceComponent.KEY.get(player).isMale();
        Identifier race = PlayerImpactfulComponent.KEY.get(player).getRace();
        Identifier clothStyle = PlayerAppearanceComponent.KEY.get(player).getClothingType();
        Identifier cloth = Identifier.of(clothStyle.getNamespace(), "geo/baseclothing/"+clothStyle.getPath()+ isMale(player) +".geo.json");
        Identifier clothRace = Identifier.of(clothStyle.getNamespace(), "geo/baseclothing/"+race.getPath()+ isMale(player) +".geo.json");

        return resourceExists(cloth) ? cloth : resourceExists(clothRace) ? clothRace : isMale ? DEFAULT_MODEL : DEFAULT_MODEL_F;
    }

    @Override
    public Identifier getTextureResource(ClothesPlayerVisualAnimatable animatable) {
        AbstractClientPlayerEntity player = animatable.getPlayer();
        Identifier clothStyle = PlayerAppearanceComponent.KEY.get(player).getClothingType();
        Identifier texture = Identifier.of(clothStyle.getNamespace(),"textures/baseclothing/"+clothStyle.getPath()+".png");

        return resourceExists(texture) ? texture : EMPTY_TEXTURE;
    }


    @Override
    public Identifier getAnimationResource(ClothesPlayerVisualAnimatable animatable) {
        AbstractClientPlayerEntity player = animatable.getPlayer();
        Identifier clothStyle = PlayerAppearanceComponent.KEY.get(player).getClothingType();
        Identifier cloth = Identifier.of(clothStyle.getNamespace(), "animations/baseclothing/"+clothStyle.getPath()+".animation.json");
        return resourceExists(cloth) ? cloth : DEFAULT_ANIMATION;
    }

    public String isMale(PlayerEntity player){
        boolean isMale = PlayerAppearanceComponent.KEY.get(player).isMale();
        return isMale ? "" : "female";
    }

    private boolean resourceExists(Identifier id) {
        return MinecraftClient.getInstance()
                .getResourceManager()
                .getResource(id)
                .isPresent();
    }



    public static long getHash64(String input) {
        return Hashing.murmur3_128().hashString(input, StandardCharsets.UTF_8).asLong();
    }

    public final GeoBone getCachedGeoBone(String identifier) {
        long hash = getHash64(identifier);
        if (boneCache.containsKey(hash)) {
            return boneCache.get(hash);
        } else {
            GeoBone bone = this.getBone(identifier).orElse(null);
            if (bone != null) {
                this.boneCache.putAndMoveToFirst(hash, bone);
                return bone;
            } else return null;
        }
    }

    private Vector3f vector3dToVector3f(Vector3d vec3d) {
        return new Vector3f((float)vec3d.x(), (float)vec3d.y(), (float)vec3d.z());
    }

    public GeoBone setPositionForBone(String identifier, Vector3d pos) {
        return setPositionForBone(identifier, vector3dToVector3f(pos));
    }

    public GeoBone setPositionForBone(String identifier, Vector3f pos) {
        GeoBone bone = this.getCachedGeoBone(identifier);
        if (bone == null) {
            return null;
        } else {
            bone.setPosX(pos.x());
            bone.setPosY(pos.y());
            bone.setPosZ(pos.z());
            return bone;
        }
    }

    // No ussage
    public GeoBone setRotationForBone(String identifier, Vector3d rot) {
        return setRotationForBone(identifier, vector3dToVector3f(rot));
    }
    public final GeoBone setRotationForBone(String identifier, Vector3f rot) {
        GeoBone bone = this.getCachedGeoBone(identifier);
        if (bone == null) {
            return null;
        } else {
            bone.setRotX(rot.x());
            bone.setRotY(rot.y());
            bone.setRotZ(rot.z());
            return bone;
        }
    }

    public GeoBone translatePositionForBone(String identifier, Vector3d pos) {
        return translatePositionForBone(identifier, vector3dToVector3f(pos));
    }

    public final GeoBone translatePositionForBone(String identifier, Vector3f pos) {
        GeoBone bone = this.getCachedGeoBone(identifier);
        if (bone == null) {
            return null;
        }
        Vector3d newPos = new Vector3d(pos.x() + bone.getPosX(), pos.y() + bone.getPosY(),pos.z() + bone.getPosZ());
        return this.setPositionForBone(identifier, newPos);
    }

    public GeoBone setModelPositionForBone(String identifier, Vector3d pos) {
        return setModelPositionForBone(identifier, vector3dToVector3f(pos));
    }

    public final GeoBone setModelPositionForBone(String identifier, Vector3f pos) {
        GeoBone bone = this.getCachedGeoBone(identifier);
        if (bone == null) {
            return null;
        }
        bone.setModelPosition(new Vector3d(pos.x(), pos.y(), pos.z()));
        return bone;
    }

    public final GeoBone setScaleForBone(String identifier, Vector3d scale) {
        return setScaleForBone(identifier, vector3dToVector3f(scale));
    }

    public final GeoBone setScaleForBone(String identifier, Vector3f scale) {
        GeoBone bone = this.getCachedGeoBone(identifier);
        if (bone == null) {
            return null;
        }
        bone.setScaleX(scale.x());
        bone.setScaleY(scale.y());
        bone.setScaleZ(scale.z());
        return bone;
    }

    public final GeoBone invertRotForPart(String identifier, boolean x, boolean y, boolean z) {
        GeoBone bone = this.getCachedGeoBone(identifier);
        if (bone == null) {
            return null;
        }
        Vector3d rot = bone.getRotationVector().mul(x ? -1 : 1, y ? -1 : 1, z ? -1 : 1);
        bone.setRotX((float)rot.x());
        bone.setRotY((float) rot.y());
        bone.setRotZ((float) rot.z());
        return bone;
    }

    public GeoBone setWorldPositionForBone(String identifier, Vector3d pos) {
        return setWorldPositionForBone(identifier, vector3dToVector3f(pos));
    }

    public GeoBone setWorldPositionForBone(String identifier, Vector3f pos) {
        GeoBone bone = this.getCachedGeoBone(identifier);
        if (bone != null) {
            return null;
        } else {
            Vector4f v = bone.getWorldSpaceMatrix().transform(new Vector4f(pos.x(), pos.y(), pos.z(), 1.0f));
            bone.setPosX(v.x());
            bone.setPosY(v.y());
            bone.setPosZ(v.z());
            return bone;
        }
    }
}
