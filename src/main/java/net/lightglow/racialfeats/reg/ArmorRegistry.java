package net.lightglow.racialfeats.reg;

import net.lightglow.racialfeats.RacialFeats;
import net.lightglow.racialfeats.item.armor.CrusaderArmorItem;
import net.lightglow.racialfeats.item.armor.VampireArmorItem;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ArmorRegistry {

    public static final Item CRUSADER_ARMOR_HELMET = register(new CrusaderArmorItem(ArmorMaterials.IRON, ArmorItem.Type.HELMET,
            new Item.Settings().maxCount(1)), "crusader_helmet");
    public static final Item CRUSADER_ARMOR_CHESTPLATE = register(new CrusaderArmorItem(ArmorMaterials.IRON, ArmorItem.Type.CHESTPLATE,
            new Item.Settings().maxCount(1)), "crusader_chestplate");
    public static final Item CRUSADER_ARMOR_LEGGINGS = register(new CrusaderArmorItem(ArmorMaterials.IRON, ArmorItem.Type.LEGGINGS,
            new Item.Settings().maxCount(1)), "crusader_leggings");
    public static final Item CRUSADER_ARMOR_BOOTS = register(new CrusaderArmorItem(ArmorMaterials.IRON, ArmorItem.Type.BOOTS,
            new Item.Settings().maxCount(1)), "crusader_boots");

    public static final Item VAMPIRE_ARMOR_HELMET = register(new VampireArmorItem(ArmorMaterials.IRON, ArmorItem.Type.HELMET,
            new Item.Settings().maxCount(1)), "vampire_helmet");
    public static final Item VAMPIRE_ARMOR_CHESTPLATE = register(new VampireArmorItem(ArmorMaterials.IRON, ArmorItem.Type.CHESTPLATE,
            new Item.Settings().maxCount(1)), "vampire_chestplate");
    public static final Item VAMPIRE_ARMOR_LEGGINGS = register(new VampireArmorItem(ArmorMaterials.IRON, ArmorItem.Type.LEGGINGS,
            new Item.Settings().maxCount(1)), "vampire_leggings");
    public static final Item VAMPIRE_ARMOR_BOOTS = register(new VampireArmorItem(ArmorMaterials.IRON, ArmorItem.Type.BOOTS,
            new Item.Settings().maxCount(1)), "vampire_boots");

    public static Item register(Item item, String id) {
        Identifier itemID = RacialFeats.id( id);
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        return registeredItem;
    }

    public static void register(){}
}
