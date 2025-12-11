package ru.cobaltmc.falling_snow.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.cobaltmc.falling_snow.ModConfig;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin {

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;canBeReplaced(Lnet/minecraft/world/item/context/BlockPlaceContext;)Z"
            )
    )
    private boolean replaceCanBeReplaced(BlockState state, BlockPlaceContext ctx) {
        if (state.is(Blocks.SNOW)) {
            return true;
        }

        return state.canBeReplaced(ctx);
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"
            ),
            cancellable = true
    )
    private void onTryPlaceBlock(CallbackInfo ci) {
        if (!ModConfig.getInstance().snowStacking) return;

        FallingBlockEntity self = (FallingBlockEntity)(Object)this;
        Level level = self.level();
        BlockPos blockPos = self.blockPosition();
        BlockState blockStateAbove = level.getBlockState(blockPos.above());
        BlockState blockStateBelow= level.getBlockState(blockPos.below());
        BlockState blockStateMiddle = level.getBlockState(blockPos);

        if (blockStateMiddle.is(Blocks.AIR) && blockStateBelow.is(Blocks.SNOW)) {
            blockPos = blockPos.below();
        } else if (blockStateAbove.is(Blocks.SNOW) && !blockStateMiddle.is(Blocks.AIR)) {
            blockPos = blockPos.above();
        }

        BlockState blockState = level.getBlockState(blockPos);
        Block selfBlock = self.getBlockState().getBlock();

        if (self.getBlockState().is(Blocks.SNOW) && blockState.is(Blocks.SNOW)) {
            self.discard();

            int layers = self.getBlockState().getValue(BlockStateProperties.LAYERS) + blockState.getValue(BlockStateProperties.LAYERS);
            BlockState blockState2 = blockState;

            if (layers <= 8) {
                level.setBlock(blockPos, blockState2.setValue(BlockStateProperties.LAYERS, layers), 3);
                if (selfBlock instanceof Fallable) {
                    Fallable fallable = (Fallable)selfBlock;
                    fallable.onLand(level, blockPos, self.getBlockState(), blockState, self);
                }

            } else {
                level.setBlock(blockPos, blockState2.setValue(BlockStateProperties.LAYERS, 8), 3);
                level.setBlock(blockPos.above(), blockState2.setValue(BlockStateProperties.LAYERS, layers - 8), 3);
                if (selfBlock instanceof Fallable) {
                    Fallable fallable = (Fallable)selfBlock;
                    fallable.onLand(level, blockPos, self.getBlockState(), blockState, self);
                }
            }

            ci.cancel();
        }
    }


}

