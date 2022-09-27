package net.rupyber_studios.minecraft_legends.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.rupyber_studios.minecraft_legends.MinecraftLegends;

public class ModItemGroup {
    public static final ItemGroup MINECRAFT_LEGENDS = FabricItemGroupBuilder.build(new Identifier(MinecraftLegends.MOD_ID,
            "minecraft_legends"), () -> new ItemStack(ModItems.QUIVER_SPAWN_EGG));
}