package net.lightglow.racialfeats.race;

import net.lightglow.racialfeats.trait.imp.RaceTrait;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Race {
    private final Identifier id;
    private final Text name;
    private final Text description;
    private final Map<RegistryEntry<EntityAttribute>, EntityAttributeModifier> attributes;
    private final Float heightScale;
    private final Float widthScale;
    private final Float heightHitboxScale;
    private final Float widthHitboxScale;
    private final boolean glowingEyes;
    private final Map<Identifier, String> hairs;
    private final Map<Identifier, String> eyes;
    private final Map<Identifier, String> clothes;
    private final List<Supplier<RaceTrait>> traits;
    private final SoundEvent damageSound;
    private final boolean hidden;

    public Race(Identifier id, Text name, Text description, Map<RegistryEntry<EntityAttribute>, EntityAttributeModifier> attributes, Float heightScale, Float widthScale, Float heightHitboxScale, Float widthHitboxScale, boolean glowingEyes, Map<Identifier, String> hair, Map<Identifier, String> eyes, Map<Identifier, String> clothes, List<Supplier<RaceTrait>> traits, SoundEvent damageSound, boolean hidden) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.attributes = attributes;
        this.heightScale = heightScale;
        this.widthScale = widthScale;
        this.heightHitboxScale = heightHitboxScale;
        this.widthHitboxScale = widthHitboxScale;
        this.glowingEyes = glowingEyes;
        this.hairs = hair;
        this.eyes = eyes;
        this.clothes = clothes;
        this.traits = traits;
        this.damageSound = damageSound;
        this.hidden = hidden;
    }


    public Identifier getId() {
        return id;
    }

    public Text getName() {
        return name;
    }

    public Text getDescription() {
        return description;
    }

    public float getWidth(){
        return widthScale;
    }

    public float getHeight(){
        return heightScale;
    }
    public float getWidthHitbox(){
        return widthHitboxScale;
    }

    public float getHeightHitbox(){
        return heightHitboxScale;
    }

    public boolean hasGlowingEyes(){
        return glowingEyes;
    }

    public boolean isHidden(){
        return hidden;
    }

    public Map<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getAttributes() {
        return attributes;
    }

    public List<RaceTrait> createTraits() {
        return traits.stream()
                .map(Supplier::get)
                .toList();
    }

    public List<Supplier<RaceTrait>> getTraits() {
        return traits;
    }

    public Map<Identifier, String> getClothes() {
        return clothes;
    }
    public Map<Identifier, String> getHairs() {
        return hairs;
    }
    public Map<Identifier, String> getEyes() {
        return eyes;
    }

    public SoundEvent getDamageSound(){
        return damageSound;
    }

    public static class RaceBuilder {

        private Text name = Text.literal("null");
        private Text description = Text.literal("");
        private final Map<RegistryEntry<EntityAttribute>, EntityAttributeModifier> attributes = new LinkedHashMap<>();
        private final List<Supplier<RaceTrait>> traits = new ArrayList<>();
        private final Map<Identifier, String> hairs = new LinkedHashMap<>();
        private final Map<Identifier, String> clothes = new LinkedHashMap<>();
        private final Map<Identifier, String> eyes = new LinkedHashMap<>();

        private Float heightScale = 1.0f;
        private Float widthScale = 1.0f;
        private Float heightHitboxScale = 1.0f;
        private Float widthHitboxScale = 1.0f;
        private SoundEvent damageSound = SoundEvents.ENTITY_PLAYER_HURT;
        private boolean hidden = false;

        private boolean glowingEyes = false;

        public static RaceBuilder create() {
            return new RaceBuilder();
        }

        public RaceBuilder name(Text name) {
            this.name = name;
            return this;
        }

        public RaceBuilder description(Text description) {
            this.description = description;
            return this;
        }

        public RaceBuilder attribute(
                RegistryEntry<EntityAttribute> attribute,
                EntityAttributeModifier modifier
        ) {
            this.attributes.put(attribute, modifier);
            return this;
        }

        public RaceBuilder dimensions(float height, float width) {
            this.heightScale = height;
            this.widthScale = width;
            return this;
        }
        public RaceBuilder dimensionsHitbox(float heightHitbox, float widthHitbox) {
            this.heightHitboxScale = heightHitbox;
            this.widthHitboxScale = widthHitbox;
            return this;
        }

        public RaceBuilder glowingEyes() {
            this.glowingEyes = true;
            return this;
        }

        public RaceBuilder trait(Supplier<RaceTrait> trait) {
            this.traits.add(trait);
            return this;
        }

        public RaceBuilder damageSound(SoundEvent damageSound){
            this.damageSound = damageSound;
            return this;
        }

        public RaceBuilder hidden(boolean hidden){
            this.hidden = hidden;
            return this;
        }

        public RaceBuilder hair(Identifier id, String name){
            this.hairs.put(id, name);
            return this;
        }
        public RaceBuilder clothing(Identifier id, String name){
            this.clothes.put(id, name);
            return this;
        }
        public RaceBuilder eye(Identifier id, String name){
            this.eyes.put(id, name);
            return this;
        }

        public Race build(Identifier id) {
            return new Race(
                    id,
                    name,
                    description,
                    attributes,
                    heightScale,
                    widthScale,
                    heightHitboxScale,
                    widthHitboxScale,
                    glowingEyes,
                    hairs,
                    eyes,
                    clothes,
                    traits,
                    damageSound,
                    hidden
            );
        }
    }
}
