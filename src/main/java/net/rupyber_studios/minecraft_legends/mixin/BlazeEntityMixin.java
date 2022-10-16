package net.rupyber_studios.minecraft_legends.mixin;

import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.BlazeEntity;
import net.rupyber_studios.minecraft_legends.entity.customs.PlankGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlazeEntity.class)
public abstract class BlazeEntityMixin {
    @Inject(method = "initGoals()V", at = @At("TAIL"))
    private void injected(CallbackInfo ci) {
        ((MobEntityAccessor)this).getTargetSelector().add(3,
                new ActiveTargetGoal<>((BlazeEntity)(Object)this, PlankGolemEntity.class, true));
    }
}