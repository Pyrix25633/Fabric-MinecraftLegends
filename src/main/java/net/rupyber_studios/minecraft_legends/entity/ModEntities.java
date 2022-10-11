package net.rupyber_studios.minecraft_legends.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.rupyber_studios.minecraft_legends.MinecraftLegends;
import net.rupyber_studios.minecraft_legends.entity.customs.WoodGolemEntity;

public class ModEntities {
    public static final EntityType<WoodGolemEntity> WOOD_GOLEM = Registry.register(Registry.ENTITY_TYPE,
            new Identifier(MinecraftLegends.MOD_ID, "wood_golem"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WoodGolemEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9F,1.2F)).build());
}