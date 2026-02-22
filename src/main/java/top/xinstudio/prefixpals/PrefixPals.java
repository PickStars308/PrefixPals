package top.xinstudio.prefixpals;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
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

		ServerMessageEvents.ALLOW_CHAT_MESSAGE.register((message, sender, params) ->
				!PrefixPalsManager.handleChat(sender, message.getContent())
		);
	}
}