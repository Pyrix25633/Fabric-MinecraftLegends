package net.rupyber_studios.minecraft_legends.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CarvedPumpkinBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Predicate;

@Mixin(CarvedPumpkinBlock.class)
public interface CarvedPumpkinBlockAccessor {
    @Accessor("IS_GOLEM_HEAD_PREDICATE")
    static Predicate<BlockState> getIS_GOLEM_HEAD_PREDICATE() {
        throw new AssertionError();
    }
}