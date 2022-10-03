package net.rupyber_studios.minecraft_legends.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.rupyber_studios.minecraft_legends.entity.customs.QuiverEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class QuiverRenderer extends GeoEntityRenderer<QuiverEntity> {
    public QuiverRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new QuiverModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public Identifier getTextureResource(QuiverEntity instance) {
        return instance.getTextureResource();
    }
}