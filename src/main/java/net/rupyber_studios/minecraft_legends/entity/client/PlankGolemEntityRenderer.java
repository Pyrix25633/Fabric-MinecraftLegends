package net.rupyber_studios.minecraft_legends.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.rupyber_studios.minecraft_legends.entity.custom.PlankGolemEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PlankGolemEntityRenderer extends GeoEntityRenderer<PlankGolemEntity> {
    public PlankGolemEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PlankGolemEntityModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public Identifier getTextureResource(PlankGolemEntity instance) {
        return instance.getTextureResource();
    }
}