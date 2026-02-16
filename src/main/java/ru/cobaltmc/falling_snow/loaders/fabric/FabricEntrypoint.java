//? if fabric {
package ru.cobaltmc.falling_snow.loaders.fabric;

import ru.cobaltmc.falling_snow.FallingSnow;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;

public class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        FallingSnow.initialize();
    }
}
//?}
