package net.rupyber_studios.minecraft_legends.entity.customs;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
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
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.rupyber_studios.minecraft_legends.MinecraftLegends;
import net.rupyber_studios.minecraft_legends.entity.ai.PlankGolemBowAttackGoal;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class PlankGolemEntity extends GolemEntity implements Angerable, IAnimatable, RangedAttackMob, InventoryOwner {
    private static final TrackedData<Integer> ARROWS = DataTracker.registerData(PlankGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final UniformIntProvider ANGER_TIME_RANGE;
    private static final Identifier ARROWS_EMPTY = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/plank_golem.png");
    private static final Identifier ARROWS_1 = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/plank_golem_arrows_1.png");
    private static final Identifier ARROWS_2 = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/plank_golem_arrows_2.png");
    private static final Identifier ARROWS_3 = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/plank_golem_arrows_3.png");
    private static final Identifier ARROWS_FULL = new Identifier(MinecraftLegends.MOD_ID, "textures/entity/plank_golem_arrows_full.png");
    private final AnimationFactory factory = new AnimationFactory(this);
    private int angerTime;
    @Nullable
    private UUID angryAt;
    private int pulling;
    private final SimpleInventory inventory = new SimpleInventory(1);

    public PlankGolemEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.0F;
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
        this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.9, 32.0F));
        this.goalSelector.add(2, new WanderAroundPointOfInterestGoal(this, 0.6, false));
        this.goalSelector.add(4, new IronGolemWanderAroundGoal(this, 0.6));
        this.goalSelector.add(4, new PlankGolemBowAttackGoal(this, 1.0, 12F));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.5F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this));
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
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
        animationData.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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
            int missing = 64 - a;
            super.sendPickup(item, Math.min(count, missing));
            ItemStack stack = getStackInHand(Hand.MAIN_HAND);
            this.setArrows(a + stack.getCount());
            stack.decrement(Math.min(count, missing));
            this.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.AIR));
            this.dropStack(stack);
        }
    }

    @Override
    public SimpleInventory getInventory() {
        return inventory;
    }

    public enum Arrows {
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