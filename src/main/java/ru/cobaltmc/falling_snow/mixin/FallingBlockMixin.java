package ru.cobaltmc.falling_snow.mixin;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FallingBlock.class)
public class FallingBlockMixin {
    @Inject(method = "isFree", at = @At("HEAD"), cancellable = true)
    private static void isFreeMixin(BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
        if (blockState.is(Blocks.SNOW)) {cir.setReturnValue(false);}
    }
}
