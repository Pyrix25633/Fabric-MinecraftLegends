package net.rupyber_studios.minecraft_legends.mixin;

import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.ZombieEntity;
import net.rupyber_studios.minecraft_legends.entity.custom.ModAbstractGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin {
    @Inject(method = "initGoals()V", at = @At("TAIL"))
    private void injected(CallbackInfo ci) {
        ((MobEntityAccessor)this).getTargetSelector().add(3,
                new ActiveTargetGoal<>((ZombieEntity)(Object)this, ModAbstractGolemEntity.class, true));
    }
}