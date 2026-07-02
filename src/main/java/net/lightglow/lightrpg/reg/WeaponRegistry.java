package net.lightglow.lightrpg.reg;

import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.item.greatshield.GreatShieldItem;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class WeaponRegistry {
    public static final List<GreatShieldItem> SHIELDS = new ArrayList<>();

    public static final Item NETHERITEGREATSHIELD = registerShield(new GreatShieldItem(ToolMaterials.IRON,
            new Item.Settings().maxCount(1).attributeModifiers(GreatShieldItem.createAttributeModifiers(ToolMaterials.IRON, 3, -2.5F, 1.0f))), "netherite_greatshield");

    public static Item registerShield(Item item, String id) {
        Identifier itemID = LightRPG.id( id);
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        SHIELDS.add((GreatShieldItem) item);
        return registeredItem;
    }

    public static Item register(Item item, String id) {
        Identifier itemID = LightRPG.id( id);
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        return registeredItem;
    }
    public static void register(){}
}
