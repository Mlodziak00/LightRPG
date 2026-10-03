package net.lightglow.racialfeats.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.lightglow.racialfeats.reg.ArmorRegistry;
import net.lightglow.racialfeats.tag.ModItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RacialFeatsTagGen extends FabricTagProvider<Item> {
    public RacialFeatsTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModItemTags.HIDES_HEAD_CLOTHES)
                .add(ArmorRegistry.CRUSADER_ARMOR_HELMET);

        getOrCreateTagBuilder(ModItemTags.HIDES_BODY_CLOTHES)
                .add(ArmorRegistry.CRUSADER_ARMOR_CHESTPLATE)
                .add(ArmorRegistry.VAMPIRE_ARMOR_CHESTPLATE);

        getOrCreateTagBuilder(ModItemTags.HIDES_LEFTARM_CLOTHES)
                .add(ArmorRegistry.CRUSADER_ARMOR_CHESTPLATE)
                .add(ArmorRegistry.VAMPIRE_ARMOR_CHESTPLATE);

        getOrCreateTagBuilder(ModItemTags.HIDES_RIGHTARM_CLOTHES)
                .add(ArmorRegistry.CRUSADER_ARMOR_CHESTPLATE)
                .add(ArmorRegistry.VAMPIRE_ARMOR_CHESTPLATE);

        getOrCreateTagBuilder(ModItemTags.HIDES_LEFTLEG_CLOTHES)
                .add(ArmorRegistry.CRUSADER_ARMOR_LEGGINGS)
                .add(ArmorRegistry.VAMPIRE_ARMOR_LEGGINGS);

        getOrCreateTagBuilder(ModItemTags.HIDES_RIGHTLEG_CLOTHES)
                .add(ArmorRegistry.CRUSADER_ARMOR_LEGGINGS)
                .add(ArmorRegistry.VAMPIRE_ARMOR_LEGGINGS);



        getOrCreateTagBuilder(ModItemTags.HIDES_HEAD_HAIR)
                .add(ArmorRegistry.CRUSADER_ARMOR_HELMET);


        getOrCreateTagBuilder(ModItemTags.HIDES_HEAD_EYES)
                .add(ArmorRegistry.CRUSADER_ARMOR_HELMET);

        getOrCreateTagBuilder(ModItemTags.LACTOSE_ITEMS)
                .add(Items.MILK_BUCKET);
    }
}
