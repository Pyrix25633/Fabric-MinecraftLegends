package net.rupyber_studios.minecraft_legends.util;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.rupyber_studios.minecraft_legends.entity.ModEntities;
import net.rupyber_studios.minecraft_legends.entity.client.GrindstoneGolemEntityRenderer;
import net.rupyber_studios.minecraft_legends.entity.client.PlankGolemEntityRenderer;
import net.rupyber_studios.minecraft_legends.entity.custom.GrindstoneGolemEntity;
import net.rupyber_studios.minecraft_legends.entity.custom.PlankGolemEntity;

public class ModRegistries {
    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.PLANK_GOLEM, PlankGolemEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GRINDSTONE_GOLEM, GrindstoneGolemEntity.setAttributes());
    }

    public static void registerRenderers() {
        EntityRendererRegistry.register(ModEntities.PLANK_GOLEM, PlankGolemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.GRINDSTONE_GOLEM, GrindstoneGolemEntityRenderer::new);
    }
}