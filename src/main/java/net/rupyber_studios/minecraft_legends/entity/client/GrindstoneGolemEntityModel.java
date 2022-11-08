package net.rupyber_studios.minecraft_legends.entity.client;

import net.minecraft.util.Identifier;
import net.rupyber_studios.minecraft_legends.MinecraftLegends;
import net.rupyber_studios.minecraft_legends.entity.custom.GrindstoneGolemEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class GrindstoneGolemEntityModel extends AnimatedGeoModel<GrindstoneGolemEntity> {
    private static final Identifier MODEL_RESOURCE = new Identifier(MinecraftLegends.MOD_ID, "geo/grindstone_golem.geo.json");
    private static final Identifier ANIMATION_RESOURCE = new Identifier(MinecraftLegends.MOD_ID ,"animations/grindstone_golem.animations.json");

    @Override
    public Identifier getModelResource(GrindstoneGolemEntity object) {
        return MODEL_RESOURCE;
    }

    @Override
    public Identifier getTextureResource(GrindstoneGolemEntity object) {
        return object.getTextureResource();
    }

    @Override
    public Identifier getAnimationResource(GrindstoneGolemEntity animatable) {
        return ANIMATION_RESOURCE;
    }

    @Override
    public void setLivingAnimations(GrindstoneGolemEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
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