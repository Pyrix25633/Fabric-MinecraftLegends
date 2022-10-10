package net.rupyber_studios.minecraft_legends.entity.customs;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.rupyber_studios.minecraft_legends.MinecraftLegends;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class QuiverEntity extends GolemEntity implements Angerable, IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);
    private final int arrows;
    private static final UniformIntProvider ANGER_TIME_RANGE;
    private int angerTime;
    @Nullable
    private UUID angryAt;
    private static final Identifier ARROWS_EMPTY = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/quiver.png");
    private static final Identifier ARROWS_1 = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/quiver_arrows_1.png");
    private static final Identifier ARROWS_2 = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/quiver_arrows_2.png");
    private static final Identifier ARROWS_3 = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/quiver_arrows_3.png");
    private static final Identifier ARROWS_FULL = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/quiver_arrows_full.png");

    public QuiverEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.0F;
        arrows = Random.create().nextBetween(0, 64);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.9, 32.0F));
        this.goalSelector.add(2, new WanderAroundPointOfInterestGoal(this, 0.6, false));
        this.goalSelector.add(4, new IronGolemWanderAroundGoal(this, 0.6));
        //this.goalSelector.add(4, bowAttackGoal);
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, false, false, (entity) -> entity instanceof Monster && !(entity instanceof CreeperEntity)));
        this.targetSelector.add(4, new UniversalAngerGoal<>(this, false));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return GolemEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4D);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.quiver.walk"));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.quiver.idle"));
        return PlayState.CONTINUE;
    }

    public Identifier getTextureResource() {
        Arrows arrows = Arrows.from(this.arrows);
        switch(arrows) {
            case EMPTY -> {
                return ARROWS_EMPTY;
            }
            case LOW -> {
                return ARROWS_1;
            }
            case MEDIUM -> {
                return ARROWS_2;
            }
            case HIGH -> {
                return ARROWS_3;
            }
            case FULL -> {
                return ARROWS_FULL;
            }
        }
        return ARROWS_EMPTY;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.7F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_BAMBOO_BREAK;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_BAMBOO_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_BAMBOO_HIT;
    }

    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    public int getAngerTime() {
        return this.angerTime;
    }

    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Nullable
    public UUID getAngryAt() {
        return this.angryAt;
    }

    static {
        ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    }

    public enum Arrows {
        EMPTY(0),
        LOW(1),
        MEDIUM(2),
        HIGH(3),
        FULL(4);

        Arrows(int arrows) {
        }

        public static Arrows from(int arrowQuantity) {
            if(arrowQuantity == 0) return EMPTY;
            else if(arrowQuantity < 14) return LOW;
            else if(arrowQuantity < 26) return MEDIUM;
            else if(arrowQuantity < 48) return HIGH;
            else return FULL;
        }
    }
}