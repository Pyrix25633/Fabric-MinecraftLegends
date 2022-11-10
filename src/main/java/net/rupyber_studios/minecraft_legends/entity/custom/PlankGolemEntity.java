package net.rupyber_studios.minecraft_legends.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.rupyber_studios.minecraft_legends.MinecraftLegends;
import net.rupyber_studios.minecraft_legends.entity.ai.PlankGolemBowAttackGoal;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class PlankGolemEntity extends ModAbstractGolemEntity implements RangedAttackMob, InventoryOwner {
    private static final TrackedData<Integer> ARROWS = DataTracker.registerData(PlankGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final Identifier ARROWS_EMPTY = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/plank_golem/plank_golem.png");
    private static final Identifier ARROWS_1 = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/plank_golem/plank_golem_arrows_1.png");
    private static final Identifier ARROWS_2 = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/plank_golem/plank_golem_arrows_2.png");
    private static final Identifier ARROWS_3 = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/plank_golem/plank_golem_arrows_3.png");
    private static final Identifier ARROWS_FULL = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/plank_golem/plank_golem_arrows_full.png");
    private final SimpleInventory inventory = new SimpleInventory(1);
    private int pulling;

    public PlankGolemEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
        setArrows(0);
        this.setCanPickUpLoot(true);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ARROWS, 64);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(2, new PlankGolemBowAttackGoal(this, 1.0, 12F));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return GolemEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4D);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.plank_golem.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.plank_golem.idle", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if(this.isAttacking() && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.plank_golem.attack", false));
        }
        return PlayState.CONTINUE;
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if(itemStack.isOf(Items.ARROW)) {
            int a = getArrows();
            if(a < 64) {
                float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                SoundEvent sound = switch(Arrows.from(a)) {
                    case EMPTY, LOW -> SoundEvents.ITEM_CROSSBOW_LOADING_START;
                    case MEDIUM, HIGH -> SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE;
                    case FULL -> SoundEvents.ITEM_CROSSBOW_LOADING_END;
                };
                this.playSound(sound, 1.0F, g);
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                setArrows(a + 1);
                return ActionResult.success(this.world.isClient);
            } else {
                return ActionResult.PASS;
            }
        }
        else if(itemStack.isOf(Items.OAK_PLANKS)) {
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

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Arrows", getArrows());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setArrows(nbt.getInt("Arrows"));
    }

    public Identifier getTextureResource() {
        Arrows arrows = Arrows.from(this.getArrows());
        return switch(arrows) {
            case EMPTY -> ARROWS_EMPTY;
            case LOW -> ARROWS_1;
            case MEDIUM -> ARROWS_2;
            case HIGH -> ARROWS_3;
            default -> ARROWS_FULL;
        };
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
        animationData.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }

    public int getArrows() {
        return this.dataTracker.get(ARROWS);
    }

    public void setArrows(int arrows) {
        this.dataTracker.set(ARROWS, arrows);
    }

    public void decrementArrows() {
        this.dataTracker.set(ARROWS, this.dataTracker.get(ARROWS) - 1);
    }

    public int getPulling() {
        return pulling;
    }

    public void incrementPulling() {
        pulling++;
    }

    public void clearPulling() {
        pulling = 0;
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

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        ItemStack itemStack = new ItemStack(Items.ARROW, 1);
        PersistentProjectileEntity persistentProjectileEntity = this.createArrowProjectile(itemStack, pullProgress);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - persistentProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        persistentProjectileEntity.setVelocity(d, e + g * 0.20000000298023224, f, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(persistentProjectileEntity);
        this.decrementArrows();
    }

    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        return ProjectileUtil.createArrowProjectile(this, arrow, damageModifier);
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        return stack.isOf(Items.ARROW) && this.getArrows() < 64;
    }

    @Override
    public boolean canGather(ItemStack stack) {
        return this.canPickupItem(stack);
    }

    @Override
    public void sendPickup(Entity item, int count) {
        if(!item.isRemoved() && !this.world.isClient) {
            int a = this.getArrows();
            super.sendPickup(item, count);
            ItemStack stack = getStackInHand(Hand.MAIN_HAND);
            this.setArrows(a + stack.getCount());
            stack.decrement(Math.min(count, 64 - a));
            this.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.AIR));
            this.dropStack(stack);
        }
    }

    @Override
    protected void drop(DamageSource source) {
        if(!this.world.isClient) {
            this.dropStack(new ItemStack(Items.ARROW, getArrows()));
        }
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        this.drop(source);
    }

    @Override
    public SimpleInventory getInventory() {
        return inventory;
    }

    private enum Arrows {
        EMPTY,
        LOW,
        MEDIUM,
        HIGH,
        FULL;

        public static Arrows from(int arrowQuantity) {
            if(arrowQuantity == 0) return EMPTY;
            else if(arrowQuantity < 14) return LOW;
            else if(arrowQuantity < 26) return MEDIUM;
            else if(arrowQuantity < 48) return HIGH;
            else return FULL;
        }
    }
}