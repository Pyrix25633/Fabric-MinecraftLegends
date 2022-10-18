package net.rupyber_studios.minecraft_legends.mixin;

import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.SpiderEntity;
import net.rupyber_studios.minecraft_legends.entity.custom.ModAbstractGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin {
    @Inject(method = "initGoals()V", at = @At("TAIL"))
    private void injected(CallbackInfo ci) {
        ((MobEntityAccessor)this).getTargetSelector().add(3,
                new ActiveTargetGoal<>((SpiderEntity)(Object)this, ModAbstractGolemEntity.class, true));
    }
}