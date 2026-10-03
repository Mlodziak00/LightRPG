package net.lightglow.racialfeats.tag;

import net.lightglow.racialfeats.RacialFeats;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModItemTags {
    public static final TagKey<Item> HIDES_BODY_CLOTHES = TagKey.of(RegistryKeys.ITEM, RacialFeats.id("hides_body_clothes"));
    public static final TagKey<Item> HIDES_HEAD_CLOTHES = TagKey.of(RegistryKeys.ITEM, RacialFeats.id("hides_head_clothes"));
    public static final TagKey<Item> HIDES_LEFTARM_CLOTHES = TagKey.of(RegistryKeys.ITEM, RacialFeats.id("hides_leftarm_clothes"));
    public static final TagKey<Item> HIDES_RIGHTARM_CLOTHES = TagKey.of(RegistryKeys.ITEM, RacialFeats.id("hides_rightarm_clothes"));
    public static final TagKey<Item> HIDES_LEFTLEG_CLOTHES = TagKey.of(RegistryKeys.ITEM, RacialFeats.id("hides_leftleg_clothes"));
    public static final TagKey<Item> HIDES_RIGHTLEG_CLOTHES = TagKey.of(RegistryKeys.ITEM, RacialFeats.id("hides_rightleg_clothes"));
    public static final TagKey<Item> HIDES_HEAD_HAIR = TagKey.of(RegistryKeys.ITEM, RacialFeats.id("hides_head_hair"));
    public static final TagKey<Item> HIDES_HEAD_EYES = TagKey.of(RegistryKeys.ITEM, RacialFeats.id("hides_head_eyes"));

    public static final TagKey<Item> LACTOSE_ITEMS = TagKey.of(RegistryKeys.ITEM, RacialFeats.id("lactose_items"));

}
