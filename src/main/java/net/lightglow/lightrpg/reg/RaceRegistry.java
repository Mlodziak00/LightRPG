package net.lightglow.lightrpg.reg;

import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.race.Race;
import net.lightglow.lightrpg.trait.FeralSurgeTrait;
import net.lightglow.lightrpg.trait.VampriteTrait;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.puffish.attributesmod.api.PuffishAttributes;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class RaceRegistry {

    private static final Map<Identifier, Race> RACES = new LinkedHashMap<>();

    //HUMAN
    public static final Race HUMAN = register(LightRPG.id("human"),
            Race.RaceBuilder.create()
                    .name(Text.literal("Human"))
                    .description(Text.literal("Adaptable and resilient, humans thrive in any environment."))
                    .attribute(EntityAttributes.GENERIC_MAX_HEALTH,
                            new EntityAttributeModifier(LightRPG.id("human_health"),
                                    2.0,
                                    EntityAttributeModifier.Operation.ADD_VALUE))
                    .attribute(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            new EntityAttributeModifier(LightRPG.id("human_speed"),
                                    0.05,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.MELEE_DAMAGE,
                            new EntityAttributeModifier(LightRPG.id("human_melee_attack"),
                                    0.05,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.RANGED_DAMAGE,
                            new EntityAttributeModifier(LightRPG.id("human_ranged_attack"),
                                    0.05,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.MAGIC_DAMAGE,
                            new EntityAttributeModifier(LightRPG.id("human_magic_attack"),
                                    0.05,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.EXPERIENCE,
                            new EntityAttributeModifier(LightRPG.id("human_experience"),
                                    -0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .hair(LightRPG.id("male_short"), "Short Male")
                    .hair(LightRPG.id("female_short"), "Short Female")
                    .eye(LightRPG.id("small"), "Small")
                    .eye(LightRPG.id("wide"), "Wide")
                    .eye(LightRPG.id("rain"), "Rain")
                    .clothing(LightRPG.id("fancy"), "Fancy")
                    .clothing(LightRPG.id("basics"), "Shirt with Shorts")
                    .clothing(LightRPG.id("basicl"), "Shirt with Pants"));

    //ELF
    public static final Race ELF = register(LightRPG.id("elf"),
            Race.RaceBuilder.create()
                    .name(Text.literal("Elf"))
                    .description(Text.literal("Wow, Pointy ears and stuff"))
                    .attribute(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            new EntityAttributeModifier(LightRPG.id("elf_speed"),
                                    0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.EXPERIENCE,
                            new EntityAttributeModifier(LightRPG.id("elf_experience"),
                                    0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.MAGIC_RESISTANCE,
                            new EntityAttributeModifier(LightRPG.id("elf_magic_res"),
                                    0.05,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.MAGIC_DAMAGE,
                            new EntityAttributeModifier(LightRPG.id("elf_magic_damage"),
                                    0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.RANGED_DAMAGE,
                            new EntityAttributeModifier(LightRPG.id("elf_ranged_damage"),
                                    0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.MELEE_DAMAGE,
                            new EntityAttributeModifier(LightRPG.id("elf_melee_damage"),
                                    -0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .dimensions(1.2f, 1.0f)
                    .hair(LightRPG.id("male_short"), "Short Male")
                    .hair(LightRPG.id("female_short"), "Short Female")
                    .eye(LightRPG.id("small"), "Small")
                    .eye(LightRPG.id("wide"), "Wide")
                    .eye(LightRPG.id("rain"), "Rain")
                    .clothing(LightRPG.id("fancy"), "Fancy")
                    .clothing(LightRPG.id("basics"), "Shirt with Shorts")
                    .clothing(LightRPG.id("basicl"), "Shirt with Pants")
    );

    //BEASTFOLK
    public static final Race BEASTFOLK = register(LightRPG.id("beastfolk"),
            Race.RaceBuilder.create()
                    .name(Text.literal("Beastfolk"))
                    .description(Text.literal("Personal note, DO NOT CALL FLUFFY"))
                    .attribute(EntityAttributes.GENERIC_MAX_HEALTH,
                            new EntityAttributeModifier(LightRPG.id("beastfolk_health"),
                                    4.0,
                                    EntityAttributeModifier.Operation.ADD_VALUE))
                    .attribute(PuffishAttributes.MELEE_DAMAGE,
                            new EntityAttributeModifier(LightRPG.id("beastfolk_melee_damage"),
                                    0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.SPRINTING_SPEED,
                            new EntityAttributeModifier(LightRPG.id("beastfolk_sprint_speed"),
                                    0.2,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.RANGED_DAMAGE,
                            new EntityAttributeModifier(LightRPG.id("beastfolk_ranged_damage"),
                                    -0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.MAGIC_DAMAGE,
                            new EntityAttributeModifier(LightRPG.id("beastfolk_magic_damage"),
                                    -0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .trait(FeralSurgeTrait::new)
                    .hair(LightRPG.id("male_short"), "Short Male")
                    .hair(LightRPG.id("female_short"), "Short Female")
                    .eye(LightRPG.id("small"), "Small")
                    .eye(LightRPG.id("wide"), "Wide")
                    .eye(LightRPG.id("rain"), "Rain")
                    .clothing(LightRPG.id("fancy"), "Fancy")
                    .clothing(LightRPG.id("basics"), "Shirt with Shorts")
                    .clothing(LightRPG.id("basicl"), "Shirt with Pants")
    );

    //BIRDFOLK
    public static final Race BIRDFOLK = register(LightRPG.id("birdfolk"),
            Race.RaceBuilder.create()
                    .name(Text.literal("Birdfolk"))
                    .description(Text.literal("Caw caw or something idk."))
                    .attribute(EntityAttributes.GENERIC_MAX_HEALTH,
                            new EntityAttributeModifier(LightRPG.id("birdfolk_health"),
                                    -2.0,
                                    EntityAttributeModifier.Operation.ADD_VALUE))
                    .attribute(PuffishAttributes.BOW_PROJECTILE_SPEED,
                            new EntityAttributeModifier(LightRPG.id("birdfolk_bow_draw_speed"),
                                    0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))
                    .attribute(PuffishAttributes.CROSSBOW_PROJECTILE_SPEED,
                            new EntityAttributeModifier(LightRPG.id("birdfolk_crossbow_draw_speed"),
                                    0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))
                    .attribute(PuffishAttributes.FALL_REDUCTION,
                            new EntityAttributeModifier(LightRPG.id("birdfolk_fall_reduction"),
                                    0.25,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.JUMP,
                            new EntityAttributeModifier(LightRPG.id("birdfolk_jump"),
                                    0.5,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.MELEE_RESISTANCE,
                            new EntityAttributeModifier(LightRPG.id("birdfolk_melee_res"),
                                    -0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.RANGED_RESISTANCE,
                            new EntityAttributeModifier(LightRPG.id("birdfolk_range_res"),
                                    -0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))

                    .hair(LightRPG.id("male_short"), "Short Male")
                    .hair(LightRPG.id("female_short"), "Short Female")
                    .eye(LightRPG.id("small"), "Small")
                    .eye(LightRPG.id("wide"), "Wide")
                    .eye(LightRPG.id("rain"), "Rain")
                    .clothing(LightRPG.id("fancy"), "Fancy")
                    .clothing(LightRPG.id("basics"), "Shirt with Shorts")
                    .clothing(LightRPG.id("basicl"), "Shirt with Pants")
    );

    //CONSTRUCT
    public static final Race CONSTRUCT = register(LightRPG.id("construct"),
            Race.RaceBuilder.create()
                    .name(Text.literal("Construct"))
                    .description(Text.literal("I would say something here, but I'd sound bad..."))
                    .attribute(EntityAttributes.GENERIC_MAX_HEALTH,
                            new EntityAttributeModifier(LightRPG.id("construct_health"),
                                    8.0,
                                    EntityAttributeModifier.Operation.ADD_VALUE))
                    .attribute(EntityAttributes.GENERIC_ARMOR,
                            new EntityAttributeModifier(LightRPG.id("construct_armor"),
                                    6.0,
                                    EntityAttributeModifier.Operation.ADD_VALUE))
                    .attribute(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
                            new EntityAttributeModifier(LightRPG.id("construct_knockback_res"),
                                    1.0,
                                    EntityAttributeModifier.Operation.ADD_VALUE))
                    .attribute(PuffishAttributes.MELEE_DAMAGE,
                            new EntityAttributeModifier(LightRPG.id("construct_melee_damage"),
                                    0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            new EntityAttributeModifier(LightRPG.id("construct_speed"),
                                    -0.15,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.NATURAL_REGENERATION,
                            new EntityAttributeModifier(LightRPG.id("construct_nat_regen"),
                                    -0.25,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .dimensions(1.25f, 1.2f)
                    .hair(LightRPG.id("bald"), "Bald")
                    .hair(LightRPG.id("male_short"), "Short Male")
                    .hair(LightRPG.id("female_short"), "Short Female")
                    .eye(LightRPG.id("construct/small"), "Small")
                    .eye(LightRPG.id("construct/wide"), "Wide")
                    .eye(LightRPG.id("construct/peace"), "Peace")
                    .eye(LightRPG.id("construct/war"), "War")
                    .clothing(LightRPG.id("fancy"), "Fancy")
                    .clothing(LightRPG.id("basics"), "Shirt with Shorts")
                    .clothing(LightRPG.id("basicl"), "Shirt with Pants")
    );

    //INSECTOID
    public static final Race INSECTOID = register(LightRPG.id("insectoid"),
            Race.RaceBuilder.create()
                    .name(Text.literal("Insectoid"))
                    .description(Text.literal("Yeah, dunno about this one"))
                    .attribute(EntityAttributes.GENERIC_ARMOR,
                            new EntityAttributeModifier(LightRPG.id("insectoid_armor"),
                                    1.0,
                                    EntityAttributeModifier.Operation.ADD_VALUE))
                    .attribute(PuffishAttributes.ARMOR_SHRED,
                            new EntityAttributeModifier(LightRPG.id("insectoid_armor_shred"),
                                    0.1,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.BREAKING_SPEED,
                            new EntityAttributeModifier(LightRPG.id("insectoid_break"),
                                    0.15,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(PuffishAttributes.MELEE_DAMAGE,
                            new EntityAttributeModifier(LightRPG.id("insectoid_melee_damage"),
                                    0.05,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            new EntityAttributeModifier(LightRPG.id("insectoid_speed"),
                                    -0.05,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                    .attribute(EntityAttributes.GENERIC_MAX_HEALTH,
                            new EntityAttributeModifier(LightRPG.id("insectoid_health"),
                                    -2.0,
                                    EntityAttributeModifier.Operation.ADD_VALUE))
                    .attribute(PuffishAttributes.HEALING,
                            new EntityAttributeModifier(LightRPG.id("insectoid_healing"),
                                    -0.2,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE))

                    .hair(LightRPG.id("male_short"), "Short Male")
                    .hair(LightRPG.id("female_short"), "Short Female")
                    .eye(LightRPG.id("small"), "Small")
                    .eye(LightRPG.id("wide"), "Wide")
                    .eye(LightRPG.id("rain"), "Rain")
                    .clothing(LightRPG.id("fancy"), "Fancy")
                    .clothing(LightRPG.id("basics"), "Shirt with Shorts")
                    .clothing(LightRPG.id("basicl"), "Shirt with Pants")
    );

    //UNDEAD

    public static final Race UNDEAD = register(LightRPG.id("undead"),
            Race.RaceBuilder.create()
                    .name(Text.literal("Undead"))
                    .description(Text.literal("Wonder who rattled their bones..."))
                    .attribute(EntityAttributes.GENERIC_MAX_HEALTH,
                            new EntityAttributeModifier(LightRPG.id("undead_health"),
                                    -2.0,
                                    EntityAttributeModifier.Operation.ADD_VALUE))
                    .attribute(PuffishAttributes.NATURAL_REGENERATION,
                            new EntityAttributeModifier(LightRPG.id("undead_nat_regen"),
                                    -10.0,
                                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))
                    .damageSound(SoundEvents.ENTITY_SKELETON_HURT)
                    .dimensionsHitbox(1.0f, 0.9f)
                    .eye(LightRPG.id("undead/notglowing"), "Not Glowing")
                    .eye(LightRPG.id("undead/glowingall"), "Glowing")
                    .eye(LightRPG.id("undead/glowingeyes"), "Glowing Eyes")
                    .eye(LightRPG.id("undead/glowingleft"), "Glowing Left Eye")
                    .eye(LightRPG.id("undead/glowingright"), "Glowing Right Eye")
                    .clothing(LightRPG.id("fancy"), "Fancy")
                    .clothing(LightRPG.id("basics"), "Shirt with Shorts")
                    .clothing(LightRPG.id("basicl"), "Shirt with Pants")
                    .glowingEyes());

    public static final Race VAMPIRE = register(LightRPG.id("vampire"),
            Race.RaceBuilder.create()
                    .name(Text.literal("Vampire"))
                    .description(Text.literal("Raaaaaah, I will suck yur di- I mean blood."))
                    .attribute(EntityAttributes.GENERIC_MAX_HEALTH,
                            new EntityAttributeModifier(LightRPG.id("vampire_max_health"),
                                    5.0,
                                    EntityAttributeModifier.Operation.ADD_VALUE))
                    .dimensions(0.9f, 0.9f)
                    .hidden(true)
                    .trait(VampriteTrait::new)
                    .hair(LightRPG.id("male_short"), "Short Male")
                    .hair(LightRPG.id("female_short"), "Short Female")
                    .eye(LightRPG.id("small"), "Small")
                    .eye(LightRPG.id("wide"), "Wide")
                    .eye(LightRPG.id("rain"), "Rain")
                    .clothing(LightRPG.id("fancy"), "Fancy")
                    .clothing(LightRPG.id("basics"), "Shirt with Shorts")
                    .clothing(LightRPG.id("basicl"), "Shirt with Pants")
                    .glowingEyes());


    public static final Race CHICKEN = register(LightRPG.id("chicken"),
            Race.RaceBuilder.create()
                    .name(Text.literal("Chicken"))
                    .description(Text.literal("Cluck Cluck."))
                    .attribute(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE,
                            new EntityAttributeModifier(LightRPG.id("chicken_no_fall_damage"),
                                    100.0,
                                    EntityAttributeModifier.Operation.ADD_VALUE))
                    .dimensionsHitbox(0.5f, 1.0f)
                    .hidden(true));



    private static Race register(Identifier id, Race.RaceBuilder builder) {
        Race race = builder.build(id);
        RACES.put(id, race);
        return race;
    }


    public static Race get(Identifier id) {
        return RACES.get(id);
    }

    public static Collection<Race> getAll() {
        return RACES.values();
    }


}
