package ru.cobaltmc.falling_snow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
//? fabric {
import net.fabricmc.loader.api.FabricLoader;
 //?} forge {
/*import net.minecraftforge.fml.loading.FMLPaths;
*///?} neoforge {
/*import net.neoforged.fml.loading.FMLPaths;
*///?}


public class ModConfig {
    //? fabric {
    static Path CONFIG_FILE_PATH = FabricLoader.getInstance().getConfigDir().resolve("falling_snow.json");
    //?} forge || neoforge {
    /*static Path CONFIG_FILE_PATH = FMLPaths.CONFIGDIR.get().resolve("falling_snow.json");
    *///?}

    static ModConfig instance;
    static Gson JSON = new GsonBuilder().setPrettyPrinting().create();

    @SerializedName("enable-particles")
    public boolean particles = true;

    @SerializedName("snow-layers-stacking")
    public boolean snowStacking = true;

    @SerializedName("play-sound-on-fall")
    public boolean playSound = true;

    public static ModConfig getInstance() {
        if (instance == null) {
            if (!load())
                save();
        }
        return instance;
    }
    public static boolean load() {
        if (!CONFIG_FILE_PATH.toFile().exists()) {
            instance = new ModConfig();
            try (FileOutputStream stream = new FileOutputStream(CONFIG_FILE_PATH.toFile())) {
                stream.write(JSON.toJson(instance).getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        try {
            ModConfig.instance = JSON.fromJson(new FileReader(ModConfig.CONFIG_FILE_PATH.toFile()), ModConfig.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    private static void save() {
        try (FileOutputStream stream = new FileOutputStream(CONFIG_FILE_PATH.toFile())) {
            stream.write(JSON.toJson(instance).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
