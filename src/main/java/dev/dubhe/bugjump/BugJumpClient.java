package dev.dubhe.bugjump;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BugJumpClient implements ClientModInitializer {
    public static BugJumpConfig config = new BugJumpConfig();

    public static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("bugjump.json").toFile();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void onInitializeClient() {
        if (!CONFIG_FILE.isFile()) {
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(config, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                config = GSON.fromJson(reader, BugJumpConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static final class BugJumpConfig {
        @SerializedName("bugjump_title")
        public boolean bugjumpTitle = false;
    }
}
