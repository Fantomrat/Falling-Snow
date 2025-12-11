package ru.cobaltmc.falling_snow.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
//? if >=1.21.2 && <=1.21.11 {
/*import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
*///?}
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.cobaltmc.falling_snow.ModConfig;

import static net.minecraft.world.level.block.SnowLayerBlock.LAYERS;

@Mixin(SnowLayerBlock.class)
public class SnowLayerBlockMixin extends Block implements Fallable {

    public SnowLayerBlockMixin(Properties properties) {
        super(properties);
    }

    private boolean isFree(BlockState blockState) {
        return blockState.isAir() || blockState.liquid() || blockState.canBeReplaced() && !blockState.is(Blocks.SNOW);
    }

    //? if <=1.21.1 && >=1.20 {
    @Inject(method = "updateShape", at = @At("HEAD"), cancellable = true)
    protected void snowLayerUpdateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor world, BlockPos pos, BlockPos pos2, CallbackInfoReturnable<BlockState> cir) {
        if (!(world instanceof Level level)) return;

        BlockPos below = pos.below();

        if (isFree(level.getBlockState(below)) && pos.getY() >= level.getMinBuildHeight()) {
            level.scheduleTick(pos, (SnowLayerBlock)(Object)this, 2);
            System.out.println("Pos: " + below);

            cir.setReturnValue(state);
            cir.cancel();
        }
    }
    //?} else {
    
    /*@Inject(method = "updateShape", at = @At("HEAD"), cancellable = true)
        protected void snowLayerUpdateShape(BlockState state, LevelReader world, ScheduledTickAccess scheduled, BlockPos pos, Direction direction, BlockPos pos2, BlockState state2, RandomSource randomSource, CallbackInfoReturnable<BlockState> cir) {
        if (!(world instanceof Level level)) return;

        BlockPos below = pos.below();
        if (isFree(level.getBlockState(below)) && pos.getY() >= level.getMinY()) {
            scheduled.scheduleTick(pos, (SnowLayerBlock)(Object)this, 2);

            cir.setReturnValue(state);
            cir.cancel();
        }
    }
    *///?}



    public void onLand(Level level, BlockPos blockPos, BlockState blockState, BlockState blockState2, FallingBlockEntity fallingBlockEntity) {
        if (!(level instanceof ServerLevel world)) return;

        if (ModConfig.getInstance().particles) world.sendParticles(ParticleTypes.SNOWFLAKE, fallingBlockEntity.getX(), fallingBlockEntity.getY() + 0.125 * blockState.getValue(LAYERS), fallingBlockEntity.getZ(), 20, 0.35, 0.1, 0.35, 0.03);
        if (ModConfig.getInstance().playSound) world.playSound(null, blockPos, SoundEvents.SNOW_PLACE, SoundSource.BLOCKS, 1f, 0.9f);
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
