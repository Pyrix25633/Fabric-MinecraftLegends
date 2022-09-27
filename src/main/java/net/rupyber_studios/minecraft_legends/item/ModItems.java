package net.rupyber_studios.minecraft_legends.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.rupyber_studios.minecraft_legends.MinecraftLegends;
import net.rupyber_studios.minecraft_legends.entity.ModEntities;

public class ModItems {
    public static final Item QUIVER_SPAWN_EGG = registerItem("quiver_spawn_egg",
            new SpawnEggItem(ModEntities.QUIVER, 0x5E4627, 0x5E4627,
                    new FabricItemSettings().group(ModItemGroup.MINECRAFT_LEGENDS).maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(MinecraftLegends.MOD_ID, name), item);
    }

    public static void registerModItems() {
        System.out.println("Registering ModItems for " + MinecraftLegends.MOD_ID);
    }
}