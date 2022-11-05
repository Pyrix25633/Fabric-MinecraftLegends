package net.rupyber_studios.minecraft_legends.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.rupyber_studios.minecraft_legends.entity.custom.GrindstoneGolemEntity;
import net.rupyber_studios.minecraft_legends.entity.custom.PlankGolemEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GrindstoneGolemEntityRenderer extends GeoEntityRenderer<GrindstoneGolemEntity> {
    public GrindstoneGolemEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GrindstoneGolemEntityModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public Identifier getTextureResource(GrindstoneGolemEntity instance) {
        return instance.getTextureResource();
    }
}