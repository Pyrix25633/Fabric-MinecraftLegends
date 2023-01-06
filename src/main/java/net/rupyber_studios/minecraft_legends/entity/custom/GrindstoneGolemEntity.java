package net.rupyber_studios.minecraft_legends.entity.custom;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.rupyber_studios.minecraft_legends.MinecraftLegends;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class GrindstoneGolemEntity extends ModAbstractGolemEntity implements IAnimatable {
    private static final TrackedData<Byte> VARIANT = DataTracker.registerData(GrindstoneGolemEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final Identifier VARIANT_0_CRACK_NONE = getTextureResource(0, Crack.NONE);
    private static final Identifier VARIANT_0_CRACK_LOW = getTextureResource(0, Crack.LOW);
    private static final Identifier VARIANT_0_CRACK_HIGH = getTextureResource(0, Crack.HIGH);
    private static final Identifier VARIANT_1_CRACK_NONE = getTextureResource(1, Crack.NONE);
    private static final Identifier VARIANT_1_CRACK_LOW = getTextureResource(1, Crack.LOW);
    private static final Identifier VARIANT_1_CRACK_HIGH = getTextureResource(1, Crack.HIGH);
    private static final Identifier VARIANT_2_CRACK_NONE = getTextureResource(2, Crack.NONE);
    private static final Identifier VARIANT_2_CRACK_LOW = getTextureResource(2, Crack.LOW);
    private static final Identifier VARIANT_2_CRACK_HIGH = getTextureResource(2, Crack.HIGH);
    private static final Identifier VARIANT_3_CRACK_NONE = getTextureResource(3, Crack.NONE);
    private static final Identifier VARIANT_3_CRACK_LOW = getTextureResource(3, Crack.LOW);
    private static final Identifier VARIANT_3_CRACK_HIGH = getTextureResource(3, Crack.HIGH);

    public GrindstoneGolemEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
        setVariant((byte)(Random.create().nextInt() % 4));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, (byte)0);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, true));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return GolemEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.5D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.7D);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.grindstone_golem.walk"));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.grindstone_golem.idle"));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if(this.isAttacking() && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.grindstone_golem.attack"));
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
                this.playSound(SoundEvents.BLOCK_STONE_PLACE, 1.0F, g);
                if(!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                return ActionResult.success(this.world.isClient);
            }
        }
        else return ActionResult.PASS;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", getVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setVariant(nbt.getByte("Variant"));
    }

    public Identifier getTextureResource() {
        Crack crack = Crack.from(this.getHealth());
        byte variant = getVariant();
        switch(variant) {
            case 0 -> {
                return switch(crack) {
                    case NONE -> VARIANT_0_CRACK_NONE;
                    case LOW -> VARIANT_0_CRACK_LOW;
                    default -> VARIANT_0_CRACK_HIGH;
                };
            }
            case 1 -> {
                return switch(crack) {
                    case NONE -> VARIANT_1_CRACK_NONE;
                    case LOW -> VARIANT_1_CRACK_LOW;
                    default -> VARIANT_1_CRACK_HIGH;
                };
            }
            case 2 -> {
                return switch(crack) {
                    case NONE -> VARIANT_2_CRACK_NONE;
                    case LOW -> VARIANT_2_CRACK_LOW;
                    default -> VARIANT_2_CRACK_HIGH;
                };
            }
            default -> {
                return switch(crack) {
                    case NONE -> VARIANT_3_CRACK_NONE;
                    case LOW -> VARIANT_3_CRACK_LOW;
                    default -> VARIANT_3_CRACK_HIGH;
                };
            }
        }
    }

    public static Identifier getTextureResource(int variant, Crack crack) {
        return new Identifier(MinecraftLegends.MOD_ID, "textures/entity/grindstone_golem/grindstone_golem_" +
                variant + "_crack_" + crack.toString() + ".png");
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
        animationData.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }

    public byte getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    public void setVariant(byte variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.8F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_GRINDSTONE_USE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_STONE_BREAK;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_STONE_BREAK;
    }
}