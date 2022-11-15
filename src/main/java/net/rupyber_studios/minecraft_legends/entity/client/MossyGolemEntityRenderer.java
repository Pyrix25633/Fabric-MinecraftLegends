package net.rupyber_studios.minecraft_legends.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.rupyber_studios.minecraft_legends.entity.custom.MossyGolemEntity;
import net.rupyber_studios.minecraft_legends.entity.custom.PlankGolemEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MossyGolemEntityRenderer extends GeoEntityRenderer<MossyGolemEntity> {
    public MossyGolemEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MossyGolemEntityModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public Identifier getTextureResource(MossyGolemEntity instance) {
        return instance.getTextureResource();
    }
}