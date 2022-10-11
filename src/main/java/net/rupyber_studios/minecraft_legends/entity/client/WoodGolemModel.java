package net.rupyber_studios.minecraft_legends.entity.client;

import net.minecraft.util.Identifier;
import net.rupyber_studios.minecraft_legends.MinecraftLegends;
import net.rupyber_studios.minecraft_legends.entity.customs.WoodGolemEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WoodGolemModel extends AnimatedGeoModel<WoodGolemEntity> {
    @Override
    public Identifier getModelResource(WoodGolemEntity object) {
        return new Identifier(MinecraftLegends.MOD_ID, "geo/wood_golem.geo.json");
    }

    @Override
    public Identifier getTextureResource(WoodGolemEntity object) {
        return object.getTextureResource();
    }

    @Override
    public Identifier getAnimationResource(WoodGolemEntity animatable) {
        return new Identifier(MinecraftLegends.MOD_ID ,"animations/wood_golem.animations.json");
    }

    @Override
    public void setLivingAnimations(WoodGolemEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone body = this.getAnimationProcessor().getBone("body");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (body != null) {
            body.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            body.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}