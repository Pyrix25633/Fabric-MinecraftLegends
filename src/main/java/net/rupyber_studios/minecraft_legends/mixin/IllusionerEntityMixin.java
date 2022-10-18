package net.rupyber_studios.minecraft_legends.mixin;

import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.IllusionerEntity;
import net.rupyber_studios.minecraft_legends.entity.custom.ModAbstractGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IllusionerEntity.class)
public abstract class IllusionerEntityMixin {
    @Inject(method = "initGoals()V", at = @At("TAIL"))
    private void injected(CallbackInfo ci) {
        ((MobEntityAccessor)this).getTargetSelector().add(3,
                new ActiveTargetGoal<>((IllusionerEntity)(Object)this, ModAbstractGolemEntity.class, true));
    }
}