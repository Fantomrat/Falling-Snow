package ru.cobaltmc.falling_snow;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("falling_snow")
public class ForgeEntrypoint {
    public ForgeEntrypoint(FMLJavaModLoadingContext context) {
        var modBus = context.getModBusGroup();
    }
}