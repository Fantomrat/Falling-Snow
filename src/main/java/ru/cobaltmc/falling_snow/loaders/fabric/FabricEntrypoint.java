//? if fabric {
package ru.cobaltmc.falling_snow.loaders.fabric;

import ru.cobaltmc.falling_snow.FallingSnow;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;

public class FabricEntrypoint implements ModInitializer {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info("Hello from FabricEntrypoint!");
        FallingSnow.initialize();
    }
}
//?}
