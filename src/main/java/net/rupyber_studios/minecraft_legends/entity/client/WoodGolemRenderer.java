package net.rupyber_studios.minecraft_legends.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.rupyber_studios.minecraft_legends.entity.customs.WoodGolemEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WoodGolemRenderer extends GeoEntityRenderer<WoodGolemEntity> {
    public WoodGolemRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WoodGolemModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public Identifier getTextureResource(WoodGolemEntity instance) {
        return instance.getTextureResource();
    }
}