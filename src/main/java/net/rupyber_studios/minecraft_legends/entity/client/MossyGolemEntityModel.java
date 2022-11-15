package net.rupyber_studios.minecraft_legends.entity.client;

import net.minecraft.util.Identifier;
import net.rupyber_studios.minecraft_legends.MinecraftLegends;
import net.rupyber_studios.minecraft_legends.entity.custom.MossyGolemEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MossyGolemEntityModel extends AnimatedGeoModel<MossyGolemEntity> {
    private static final Identifier MODEL_RESOURCE = new Identifier(MinecraftLegends.MOD_ID, "geo/mossy_golem.geo.json");
    private static final Identifier ANIMATION_RESOURCE = new Identifier(MinecraftLegends.MOD_ID ,"animations/mossy_golem.animations.json");

    @Override
    public Identifier getModelResource(MossyGolemEntity object) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(MossyGolemEntity object) {
        return object.getTextureResource();
    }

    @Override
    public Identifier getAnimationResource(MossyGolemEntity animatable) {
        return ANIMATION_RESOURCE;
    }

    @Override
    public void setLivingAnimations(MossyGolemEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone body = this.getAnimationProcessor().getBone("body");
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData)customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if(body != null && head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            if(!customPredicate.isMoving())
                body.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}