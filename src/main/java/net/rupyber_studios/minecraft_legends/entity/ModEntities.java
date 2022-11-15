package net.rupyber_studios.minecraft_legends.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.rupyber_studios.minecraft_legends.MinecraftLegends;
import net.rupyber_studios.minecraft_legends.entity.custom.GrindstoneGolemEntity;
import net.rupyber_studios.minecraft_legends.entity.custom.MossyGolemEntity;
import net.rupyber_studios.minecraft_legends.entity.custom.PlankGolemEntity;

public class ModEntities {
    public static final EntityType<PlankGolemEntity> PLANK_GOLEM = Registry.register(Registry.ENTITY_TYPE,
            new Identifier(MinecraftLegends.MOD_ID, "plank_golem"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PlankGolemEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9F,1.2F)).build());
    public static final EntityType<GrindstoneGolemEntity> GRINDSTONE_GOLEM = Registry.register(Registry.ENTITY_TYPE,
            new Identifier(MinecraftLegends.MOD_ID, "grindstone_golem"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GrindstoneGolemEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9F,1.3F)).build());
    public static final EntityType<MossyGolemEntity> MOSSY_GOLEM = Registry.register(Registry.ENTITY_TYPE,
            new Identifier(MinecraftLegends.MOD_ID, "mossy_golem"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MossyGolemEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9F,1.3F)).build());
}