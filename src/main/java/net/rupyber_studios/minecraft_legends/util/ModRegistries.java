package net.rupyber_studios.minecraft_legends.util;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.rupyber_studios.minecraft_legends.entity.ModEntities;
import net.rupyber_studios.minecraft_legends.entity.client.PlankGolemRenderer;
import net.rupyber_studios.minecraft_legends.entity.customs.PlankGolemEntity;

public class ModRegistries {
    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.PLANK_GOLEM, PlankGolemEntity.setAttributes());
    }

    public static void registerRenderers() {
        EntityRendererRegistry.register(ModEntities.PLANK_GOLEM, PlankGolemRenderer::new);
    }
}