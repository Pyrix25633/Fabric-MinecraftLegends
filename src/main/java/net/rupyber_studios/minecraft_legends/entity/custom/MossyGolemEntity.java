package net.rupyber_studios.minecraft_legends.entity.custom;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.rupyber_studios.minecraft_legends.MinecraftLegends;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class MossyGolemEntity extends ModAbstractGolemEntity implements IAnimatable {
    private static final Identifier CRACK_NONE = getTextureResource(Crack.NONE);
    private static final Identifier CRACK_LOW = getTextureResource(Crack.LOW);
    private static final Identifier CRACK_HIGH = getTextureResource(Crack.HIGH);

    public MossyGolemEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, true));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return GolemEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 60D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.5D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.8D);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mossy_golem.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mossy_golem.idle", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if(this.isAttacking() && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mossy_golem.attack", false));
        }
        return PlayState.CONTINUE;
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if(itemStack.isOf(Items.COBBLESTONE) || itemStack.isOf(Items.STONE)) {
            float f = this.getHealth();
            this.heal(15.0F);
            if(this.getHealth() == f) {
                return ActionResult.PASS;
            } else {
                float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.BLOCK_WOOD_PLACE, 1.0F, g);
                if(!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                return ActionResult.success(this.world.isClient);
            }
        }
        else return ActionResult.PASS;
    }

    public Identifier getTextureResource() {
        Crack crack = Crack.from(this.getHealth());
        return switch(crack) {
            case NONE -> CRACK_NONE;
            case LOW -> CRACK_LOW;
            default -> CRACK_HIGH;
        };
    }

    public static Identifier getTextureResource(Crack crack) {
        return new Identifier(MinecraftLegends.MOD_ID, "textures/entity/mossy_golem/mossy_golem_crack_" +
                crack.toString() + ".png");
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
        animationData.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.8F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_MOSS_FALL;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_MOSS_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_MOSS_BREAK;
    }
}