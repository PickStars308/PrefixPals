package top.xinstudio.prefixpals;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageDecoratorEvent;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrefixPals implements ModInitializer {
	public static final String MOD_ID = "prefixpals";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private volatile MinecraftServerAudiences adventure;

	public static PrefixPals INSTANCE; // ← 添加这一行

	public MinecraftServerAudiences adventure() {
		if (this.adventure == null) {
			throw new IllegalStateException("Tried to access Adventure without a running server!");
		}
		return this.adventure;
	}

	@Override
	public void onInitialize() {

		INSTANCE = this; // ← 在初始化时赋值

		ServerLifecycleEvents.SERVER_STARTING.register(server ->
				this.adventure = MinecraftServerAudiences.of(server)
		);

		LOGGER.info("Hello PrefixPals!");

		ServerLifecycleEvents.SERVER_STOPPED.register(server ->
				this.adventure = null
		);

		ServerLifecycleEvents.SERVER_STARTED.register(PrefixPalsManager::init);

		PrefixPalsCommands.register();

		ServerMessageDecoratorEvent.EVENT.register((sender, message) -> {

			if (!(sender instanceof ServerPlayerEntity player)) {
				return message;
			}

			PrefixPalsData data = PrefixPalsManager.get(player.getUuid());

			if (data == null) return message;

			String prefixRaw = data.getPrefix();
			String suffixRaw = data.getSuffix();

			if (prefixRaw.isEmpty() && suffixRaw.isEmpty()) {
				return message;
			}

			MiniMessage mini = MiniMessage.miniMessage();

			// 把当前 message 转成 Adventure
			Component original =
					PrefixPals.INSTANCE.adventure().asAdventure(message);

			Component result = Component.empty();

			// prefix
			if (!prefixRaw.isEmpty()) {
				result = result.append(
						mini.deserialize(preserveTrailingSpaces(prefixRaw))
				);
			}

			// 原消息
			result = result.append(original);

			// suffix
			if (!suffixRaw.isEmpty()) {
				result = result.append(
						mini.deserialize(preserveTrailingSpaces(suffixRaw))
				);
			}

			// 转回 Minecraft Text
			return PrefixPals.INSTANCE.adventure().asNative(result);
		});
	}

	private static String preserveTrailingSpaces(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}

		int length = input.length();
		int trailingSpaces = 0;

		for (int i = length - 1; i >= 0; i--) {
			if (input.charAt(i) == ' ') {
				trailingSpaces++;
			} else {
				break;
			}
		}

		if (trailingSpaces == 0) return input;

		StringBuilder builder = new StringBuilder();
		builder.append(input, 0, length - trailingSpaces);

		for (int i = 0; i < trailingSpaces; i++) {
			builder.append('\u00A0'); // NBSP
		}

		return builder.toString();
	}
}