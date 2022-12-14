package net.rupyber_studios.minecraft_legends.entity.ai;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.rupyber_studios.minecraft_legends.entity.custom.PlankGolemEntity;

public class PlankGolemBowAttackGoal extends Goal {
    private final PlankGolemEntity actor;
    private final double speed;
    private final float squaredRange;
    private int targetSeeingTicker;
    private boolean movingToLeft;
    private boolean backward;
    private int combatTicks = -1;

    public PlankGolemBowAttackGoal(PlankGolemEntity actor, double speed, float range) {
        this.actor = actor;
        this.speed = speed;
        this.squaredRange = range * range;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    public boolean canStart() {
        return this.actor.getTarget() != null;
    }

    public boolean shouldContinue() {
        return this.canStart() || !this.actor.getNavigation().isIdle();
    }

    public void start() {
        super.start();
        this.actor.setAttacking(true);
    }

    public void stop() {
        super.stop();
        this.actor.setAttacking(false);
        this.targetSeeingTicker = 0;
        this.actor.clearActiveItem();
    }

    public boolean shouldRunEveryTick() {
        return true;
    }

    public void tick() {
        LivingEntity livingEntity = this.actor.getTarget();
        if(livingEntity != null) {
            double d = this.actor.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            boolean bl = this.actor.getVisibilityCache().canSee(livingEntity);
            boolean bl2 = this.targetSeeingTicker > 0;
            if(bl != bl2) this.targetSeeingTicker = 0;

            if(bl) ++this.targetSeeingTicker;
            else --this.targetSeeingTicker;

            if(!(d > (double)this.squaredRange) && this.targetSeeingTicker >= 20) {
                this.actor.getNavigation().stop();
                ++this.combatTicks;
            } else {
                this.actor.getNavigation().startMovingTo(livingEntity, this.speed);
                this.combatTicks = -1;
            }

            if(this.combatTicks >= 20) {
                if((double)this.actor.getRandom().nextFloat() < 0.3) {
                    this.movingToLeft = !this.movingToLeft;
                }
                if((double)this.actor.getRandom().nextFloat() < 0.3) {
                    this.backward = !this.backward;
                }

                this.combatTicks = 0;
            }

            if(this.combatTicks > -1) {
                if(d > (double)(this.squaredRange * 0.75F))
                    this.backward = false;
                else if(d < (double)(this.squaredRange * 0.25F))
                    this.backward = true;

                this.actor.getMoveControl().strafeTo(this.backward ? -0.5F : 0.5F, this.movingToLeft ? 0.5F : -0.5F);
                this.actor.lookAtEntity(livingEntity, 30.0F, 30.0F);
            } else
                this.actor.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);

            if(this.actor.getArrows() > 0) {
                if(bl || this.targetSeeingTicker > -60) {
                    if(this.actor.isMaxPulling()) {
                        this.actor.attack(livingEntity, this.actor.getPulling());
                        this.actor.clearPulling();
                    }
                    else this.actor.incrementPulling();
                }
                else {
                    this.actor.clearPulling();
                }
            }
        }
    }
}