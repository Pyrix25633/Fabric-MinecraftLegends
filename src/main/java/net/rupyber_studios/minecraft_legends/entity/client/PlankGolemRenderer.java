package net.rupyber_studios.minecraft_legends.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.rupyber_studios.minecraft_legends.entity.customs.PlankGolemEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PlankGolemRenderer extends GeoEntityRenderer<PlankGolemEntity> {
    public PlankGolemRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PlankGolemModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public Identifier getTextureResource(PlankGolemEntity instance) {
        return instance.getTextureResource();
    }
}