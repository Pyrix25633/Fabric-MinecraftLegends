package net.rupyber_studios.minecraft_legends.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.world.World;
import net.rupyber_studios.minecraft_legends.entity.customs.PlankGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin extends HostileEntity {
    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initCustomGoals()V", at = @At("TAIL"))
    private void injected(CallbackInfo ci) {
        this.targetSelector.add(5, new ActiveTargetGoal(this, PlankGolemEntity.class,
                10, true, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
    }
}