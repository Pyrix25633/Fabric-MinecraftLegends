package net.rupyber_studios.minecraft_legends.util;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.rupyber_studios.minecraft_legends.entity.ModEntities;
import net.rupyber_studios.minecraft_legends.entity.client.QuiverRenderer;
import net.rupyber_studios.minecraft_legends.entity.customs.QuiverEntity;

public class ModRegistries {
    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.QUIVER, QuiverEntity.setAttributes());
    }

    public static void registerRenderers() {
        EntityRendererRegistry.register(ModEntities.QUIVER, QuiverRenderer::new);
    }
}