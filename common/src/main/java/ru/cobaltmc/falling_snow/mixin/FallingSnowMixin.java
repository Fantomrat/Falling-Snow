package ru.cobaltmc.falling_snow.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowLayerBlock.class)
public class FallingSnowMixin extends Block {

    public FallingSnowMixin(Properties properties) {
        super(properties);
    }

    private boolean isFree(BlockState blockState) {
        return blockState.isAir() || blockState.liquid() || blockState.canBeReplaced();
    }

    @Inject(method = "updateShape", at = @At("HEAD"), cancellable = true)
    protected void snowLayerUpdateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor world, BlockPos pos, BlockPos pos2, CallbackInfoReturnable<BlockState> cir) {
        if (!(world instanceof Level level)) return;

        BlockPos below = pos.below();
        if (isFree(level.getBlockState(below)) && pos.getY() >= level.getMinBuildHeight()) {
            level.scheduleTick(pos, (SnowLayerBlock)(Object)this, 2);

            cir.setReturnValue(state);
            cir.cancel();
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (isFree(world.getBlockState(pos.below()))) {
            FallingBlockEntity fall = FallingBlockEntity.fall(world, pos, state);
            fall.dropItem = false;
            world.addFreshEntity(fall);
            world.removeBlock(pos, false);
        }
    }


}
