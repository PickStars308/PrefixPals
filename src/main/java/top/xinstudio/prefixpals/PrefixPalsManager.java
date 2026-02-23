package top.xinstudio.prefixpals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.server.MinecraftServer;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PrefixPalsManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Map<UUID, PrefixPalsData> DATA = new HashMap<>();
    private static final Object DATA_LOCK = new Object();
    private static Path CONFIG_PATH;

    public static void init(MinecraftServer server) {
        Object runDir = server.getRunDirectory();
        if (runDir instanceof Path) {
            CONFIG_PATH = ((Path) runDir).resolve("config").resolve("chatpals.json");
        } else if (runDir instanceof File) {
            CONFIG_PATH = ((File) runDir).toPath().resolve("config").resolve("chatpals.json");
        } else {
            throw new IllegalStateException("Unsupported run directory type: " + runDir.getClass());
        }
        load();
    }

    public static void load() {
        synchronized (DATA_LOCK) {
            try {
                File file = CONFIG_PATH.toFile();

                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    save();
                }

                try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                    Type type = new TypeToken<Map<UUID, PrefixPalsData>>() {
                    }.getType();
                    Map<UUID, PrefixPalsData> loaded = GSON.fromJson(reader, type);

                    if (loaded != null) {
                        DATA.clear();
                        DATA.putAll(loaded);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        synchronized (DATA_LOCK) {
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(DATA, writer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static PrefixPalsData get(UUID uuid) {
        synchronized (DATA_LOCK) {
            return DATA.computeIfAbsent(uuid, u -> new PrefixPalsData());
        }
    }

    public static void clear(UUID uuid) {
        synchronized (DATA_LOCK) {
            DATA.remove(uuid);
            save();
        }
    }

}