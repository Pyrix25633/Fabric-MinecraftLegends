package net.rupyber_studios.minecraft_legends.entity.client;

import net.minecraft.util.Identifier;
import net.rupyber_studios.minecraft_legends.MinecraftLegends;
import net.rupyber_studios.minecraft_legends.entity.custom.PlankGolemEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PlankGolemEntityModel extends AnimatedGeoModel<PlankGolemEntity> {
    private static final Identifier MODEL_RESOURCE = new Identifier(MinecraftLegends.MOD_ID, "geo/plank_golem.geo.json");
    private static final Identifier ANIMATION_RESOURCE = new Identifier(MinecraftLegends.MOD_ID ,"animations/plank_golem.animations.json");

    @Override
    public Identifier getModelResource(PlankGolemEntity object) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(PlankGolemEntity object) {
        return object.getTextureResource();
    }

    @Override
    public Identifier getAnimationResource(PlankGolemEntity animatable) {
        return ANIMATION_RESOURCE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setCustomAnimations(PlankGolemEntity entity, int uniqueID, AnimationEvent customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        IBone body = this.getAnimationProcessor().getBone("body");

        EntityModelData extraData = (EntityModelData)customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if(body != null) {
            body.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            body.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}