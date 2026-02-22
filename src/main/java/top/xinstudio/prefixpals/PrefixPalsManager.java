package top.xinstudio.prefixpals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

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
    private static MinecraftServer SERVER;

    public static void init(MinecraftServer server) {
        SERVER = server;
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

    public static boolean handleChat(ServerPlayerEntity player, Text originalMessage) {

        PrefixPalsData data = get(player.getUuid());
        if (data == null) return false;

        String prefixRaw = data.getPrefix();
        String suffixRaw = data.getSuffix();

        if (prefixRaw.isEmpty() && suffixRaw.isEmpty()) return false;

        MiniMessage mini = MiniMessage.miniMessage();

        // ⚠ 保留原聊天组件，不要 getString()
        Component originalComponent =
                PrefixPals.INSTANCE.adventure().asAdventure(originalMessage);

        Component result = Component.empty();

        // ===== prefix =====
        if (!prefixRaw.isEmpty()) {

            String fixedPrefix = preserveTrailingSpaces(prefixRaw);

            Component prefixComponent = mini.deserialize(fixedPrefix);

            result = result.append(prefixComponent);
        }

        // ===== 原消息 =====
        result = result.append(originalComponent);

        // ===== suffix =====
        if (!suffixRaw.isEmpty()) {

            String fixedSuffix = preserveTrailingSpaces(suffixRaw);

            Component suffixComponent = mini.deserialize(fixedSuffix);

            result = result.append(suffixComponent);
        }

        PrefixPals.INSTANCE.adventure()
                .all()
                .sendMessage(result);

        return true;
    }

    private static String preserveTrailingSpaces(String input) {
        int trailing = 0;

        for (int i = input.length() - 1; i >= 0; i--) {
            if (input.charAt(i) == ' ') {
                trailing++;
            } else {
                break;
            }
        }

        if (trailing == 0) return input;

        StringBuilder sb = new StringBuilder(
                input.substring(0, input.length() - trailing)
        );

        for (int i = 0; i < trailing; i++) {
            sb.append('\u00A0'); // 不间断空格
        }

        return sb.toString();
    }

}