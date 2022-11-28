package net.rupyber_studios.minecraft_legends.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.rupyber_studios.minecraft_legends.entity.ModEntities;
import net.rupyber_studios.minecraft_legends.entity.custom.GrindstoneGolemEntity;
import net.rupyber_studios.minecraft_legends.entity.custom.MossyGolemEntity;
import net.rupyber_studios.minecraft_legends.entity.custom.PlankGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Iterator;

@Mixin(CarvedPumpkinBlock.class)
public abstract class CarvedPumpkinBlockMixin {
    @Nullable
    private BlockPattern plankGolemDispenserPattern, grindstoneGolemDispenserPattern, mossyGolemDispenserPattern;
    @Nullable
    private BlockPattern plankGolemPattern, grindstoneGolemPattern, mossyGolemPattern;

    @Inject(method = "trySpawnEntity", at = @At("HEAD"), cancellable = true)
    private void trySpawnEntity(World world, BlockPos pos, CallbackInfo ci) {
        BlockPattern.Result result = this.getPlankGolemPattern().searchAround(world, pos);
        Iterator<ServerPlayerEntity> var6;
        ServerPlayerEntity serverPlayerEntity;
        if(result != null) { //Plank Golem
            for(int i = 0; i < this.getPlankGolemPattern().getHeight(); ++i) {
                CachedBlockPosition cachedBlockPosition = result.translate(0, i, 0);
                world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
                world.syncWorldEvent(2001, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
            }

            PlankGolemEntity plankGolemEntity = ModEntities.PLANK_GOLEM.create(world);
            BlockPos blockPos = result.translate(0, 2, 0).getBlockPos();
            assert plankGolemEntity != null;
            plankGolemEntity.refreshPositionAndAngles((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 1.05, (double)blockPos.getZ() + 0.5, 0.0F, 0.0F);
            world.spawnEntity(plankGolemEntity);
            var6 = world.getNonSpectatingEntities(ServerPlayerEntity.class, plankGolemEntity.getBoundingBox().expand(5.0)).iterator();

            while(var6.hasNext()) {
                serverPlayerEntity = var6.next();
                Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, plankGolemEntity);
            }

            for(int j = 0; j < this.getPlankGolemPattern().getHeight(); ++j) {
                CachedBlockPosition cachedBlockPosition2 = result.translate(0, j, 0);
                world.updateNeighbors(cachedBlockPosition2.getBlockPos(), Blocks.AIR);
            }
            ci.cancel();
        }
        result = this.getGrindstoneGolemPattern().searchAround(world, pos);
        if(result != null) { //Grindstone Golem
            for(int i = 0; i < this.getGrindstoneGolemPattern().getHeight(); ++i) {
                CachedBlockPosition cachedBlockPosition = result.translate(0, i, 0);
                world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
                world.syncWorldEvent(2001, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
            }

            GrindstoneGolemEntity grindstoneGolemEntity = ModEntities.GRINDSTONE_GOLEM.create(world);
            BlockPos blockPos = result.translate(0, 2, 0).getBlockPos();
            assert grindstoneGolemEntity != null;
            grindstoneGolemEntity.refreshPositionAndAngles((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 1.05, (double)blockPos.getZ() + 0.5, 0.0F, 0.0F);
            world.spawnEntity(grindstoneGolemEntity);
            var6 = world.getNonSpectatingEntities(ServerPlayerEntity.class, grindstoneGolemEntity.getBoundingBox().expand(5.0)).iterator();

            while(var6.hasNext()) {
                serverPlayerEntity = var6.next();
                Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, grindstoneGolemEntity);
            }

            for(int j = 0; j < this.getGrindstoneGolemPattern().getHeight(); ++j) {
                CachedBlockPosition cachedBlockPosition2 = result.translate(0, j, 0);
                world.updateNeighbors(cachedBlockPosition2.getBlockPos(), Blocks.AIR);
            }
            ci.cancel();
        }
        result = this.getMossyGolemPattern().searchAround(world, pos);
        if(result != null) { //Mossy Golem
            for(int i = 0; i < this.getGrindstoneGolemPattern().getHeight(); ++i) {
                CachedBlockPosition cachedBlockPosition = result.translate(0, i, 0);
                world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
                world.syncWorldEvent(2001, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
            }

            MossyGolemEntity mossyGolemEntity = ModEntities.MOSSY_GOLEM.create(world);
            BlockPos blockPos = result.translate(0, 2, 0).getBlockPos();
            assert mossyGolemEntity != null;
            mossyGolemEntity.refreshPositionAndAngles((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 1.05, (double)blockPos.getZ() + 0.5, 0.0F, 0.0F);
            world.spawnEntity(mossyGolemEntity);
            var6 = world.getNonSpectatingEntities(ServerPlayerEntity.class, mossyGolemEntity.getBoundingBox().expand(5.0)).iterator();

            while(var6.hasNext()) {
                serverPlayerEntity = var6.next();
                Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, mossyGolemEntity);
            }

            for(int j = 0; j < this.getGrindstoneGolemPattern().getHeight(); ++j) {
                CachedBlockPosition cachedBlockPosition2 = result.translate(0, j, 0);
                world.updateNeighbors(cachedBlockPosition2.getBlockPos(), Blocks.AIR);
            }
            ci.cancel();
        }
    }

    @Inject(method = "canDispense", at = @At("HEAD"), cancellable = true)
    private void canDispense(WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(getPlankGolemDispenserPattern().searchAround(world, pos) != null ||
                getGrindstoneGolemDispenserPattern().searchAround(world, pos) != null ||
                getMossyGolemDispenserPattern().searchAround(world, pos) != null) {
            cir.setReturnValue(true); cir.cancel();
        }
    }

    private BlockPattern getPlankGolemDispenserPattern() {
        if(this.plankGolemDispenserPattern == null) {
            this.plankGolemDispenserPattern = BlockPatternBuilder.start().aisle(" ", "#").where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.OAK_PLANKS))).build();
        }
        return this.plankGolemDispenserPattern;
    }

    private BlockPattern getPlankGolemPattern() {
        if(this.plankGolemPattern == null) {
            this.plankGolemPattern = BlockPatternBuilder.start().aisle("^", "#").where('^', CachedBlockPosition.matchesBlockState(CarvedPumpkinBlockAccessor.getIS_GOLEM_HEAD_PREDICATE())).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.OAK_PLANKS))).build();
        }
        return this.plankGolemPattern;
    }

    private BlockPattern getGrindstoneGolemDispenserPattern() {
        if(this.grindstoneGolemDispenserPattern == null) {
            this.grindstoneGolemDispenserPattern = BlockPatternBuilder.start().aisle(" ", "#").where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.GRINDSTONE))).build();
        }
        return this.grindstoneGolemDispenserPattern;
    }

    private BlockPattern getGrindstoneGolemPattern() {
        if(this.grindstoneGolemPattern == null) {
            this.grindstoneGolemPattern = BlockPatternBuilder.start().aisle("^", "#").where('^', CachedBlockPosition.matchesBlockState(CarvedPumpkinBlockAccessor.getIS_GOLEM_HEAD_PREDICATE())).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.GRINDSTONE))).build();
        }
        return this.grindstoneGolemPattern;
    }

    private BlockPattern getMossyGolemDispenserPattern() {
        if(this.mossyGolemDispenserPattern == null) {
            this.mossyGolemDispenserPattern = BlockPatternBuilder.start().aisle(" ", "#").where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.MOSS_BLOCK))).build();
        }
        return this.mossyGolemDispenserPattern;
    }

    private BlockPattern getMossyGolemPattern() {
        if(this.mossyGolemPattern == null) {
            this.mossyGolemPattern = BlockPatternBuilder.start().aisle("^", "#").where('^', CachedBlockPosition.matchesBlockState(CarvedPumpkinBlockAccessor.getIS_GOLEM_HEAD_PREDICATE())).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.MOSS_BLOCK))).build();
        }
        return this.mossyGolemPattern;
    }
}