package net.lightglow.racialfeats.component.entity;

import net.lightglow.racialfeats.RacialFeats;
import net.lightglow.racialfeats.race.RacialFeatureInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import java.util.ArrayList;
import java.util.List;

public class PlayerAppearanceComponent implements AutoSyncedComponent {

    public static final ComponentKey<PlayerAppearanceComponent> KEY =
            ComponentRegistry.getOrCreate(RacialFeats.id("appearance"), PlayerAppearanceComponent.class);

    private final PlayerEntity player;

    private String characterName = "";
    private boolean male = true;

    private Identifier eyeType = RacialFeats.id("small");
    private Identifier hairStyle = RacialFeats.id("male_short");
    private Identifier clothingStyle = RacialFeats.id("fancy");

    private int skinColor = 15313547;
    private int hairColor = 4340530;
    private int eyeColor = 6853803;
    private int clothingColor = 6853803;

    private final List<RacialFeatureInstance> racialFeatures = new ArrayList<>();

    public PlayerAppearanceComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync(){
        KEY.sync(this.player);
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String name) {
        this.characterName = name;
        sync();
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
        sync();
    }

    public int getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(int skinColor) {
        this.skinColor = skinColor;
        sync();
    }

    public Identifier getHairStyle() {
        return hairStyle;
    }

    public void setHairStyle(Identifier hairStyle) {
        this.hairStyle = hairStyle;
        sync();
    }

    public int getHairColor() {
        return hairColor;
    }

    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
        sync();
    }

    public Identifier getEyeType() {
        return eyeType;
    }

    public void setEyeType(Identifier eyeType) {
        this.eyeType = eyeType;

    }

    public int getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(int eyeColor) {
        this.eyeColor = eyeColor;
        sync();
    }
    public Identifier getClothingType() {
        return clothingStyle;
    }

    public void setClothingType(Identifier clothingType) {
        this.clothingStyle = clothingType;

    }

    public int getClothingColor() {
        return clothingColor;
    }

    public void setClothingColor(int clothingColor) {
        this.clothingColor = clothingColor;
        sync();
    }

    public List<RacialFeatureInstance> getRacialFeatures() {
        return racialFeatures;
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup lookup) {
        characterName = tag.getString("CharacterName");
        male = tag.getBoolean("Male");

        eyeType = Identifier.of(tag.getString("EyeType"));
        hairStyle = Identifier.of(tag.getString("HairStyle"));
        clothingStyle = Identifier.of(tag.getString("ClothingStyle"));
        skinColor = tag.getInt("SkinColor");
        hairColor = tag.getInt("HairColor");
        eyeColor = tag.getInt("EyeColor");
        clothingColor = tag.getInt("ClothingColor");

        racialFeatures.clear();
        if (tag.contains("RacialFeatures")) {
            NbtList list = tag.getList("RacialFeatures", NbtElement.COMPOUND_TYPE);
            for (NbtElement el : list) {
                racialFeatures.add(RacialFeatureInstance.fromNbt((NbtCompound) el));
            }
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup lookup) {
        tag.putString("CharacterName", characterName);
        tag.putBoolean("Male", male);

        tag.putInt("SkinColor", skinColor);
        tag.putString("HairStyle", hairStyle.toString());
        tag.putString("ClothingStyle", clothingStyle.toString());
        tag.putInt("HairColor", hairColor);
        tag.putString("EyeType", eyeType.toString());
        tag.putInt("EyeColor", eyeColor);
        tag.putInt("ClothingColor", clothingColor);

        NbtList featuresTag = new NbtList();
        for (RacialFeatureInstance feature : racialFeatures) {
            featuresTag.add(feature.writeNbt());
        }
        tag.put("RacialFeatures", featuresTag);

    }
}
