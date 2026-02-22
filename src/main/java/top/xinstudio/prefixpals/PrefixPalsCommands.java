package top.xinstudio.prefixpals;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PrefixPalsCommands {

    public static void register() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            dispatcher.register(
                    CommandManager.literal("pp")
                            .then(CommandManager.literal("prefix")
                                            .then(CommandManager.argument("text", StringArgumentType.greedyString())
                                                            .executes(ctx -> {
                                                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                                                String text = StringArgumentType.getString(ctx, "text");
//
//                                                if (!LuckPermsHook.hasPermission(player, "chatpal.prefix")) {
//                                                    player.sendMessage(Text.literal("你还没有权限呢喵~"), false);
//                                                    return 0;
//                                                }

                                                                PrefixPalsManager.get(player.getUuid()).setPrefix(text);
                                                                PrefixPalsManager.save();

                                                                player.sendMessage(Text.literal("Success~"), false);
                                                                return 1;
                                                            })
                                            )
                            )
                            .then(CommandManager.literal("suffix")
                                            .then(CommandManager.argument("text", StringArgumentType.greedyString())
                                                            .executes(ctx -> {
                                                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                                                String text = StringArgumentType.getString(ctx, "text");

//                                                if (!LuckPermsHook.hasPermission(player, "chatpal.suffix")) {
//                                                    player.sendMessage(Text.literal("你还没有权限呢喵~"), false);
//                                                    return 0;
//                                                }

                                                                PrefixPalsManager.get(player.getUuid()).setSuffix(text);
                                                                PrefixPalsManager.save();

                                                                player.sendMessage(Text.literal("Success~"), false);
                                                                return 1;
                                                            })
                                            )
                            )
                            .then(CommandManager.literal("clear")
                                    .executes(ctx -> {
                                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                                        PrefixPalsManager.clear(player.getUuid());
                                        player.sendMessage(Text.literal("Cleared Success~"), false);
                                        return 1;
                                    })
                            ).then(
                                    CommandManager.literal("info")
                                            .executes(ctx -> {
                                                ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();
                                                PrefixPalsData data = PrefixPalsManager.get(player.getUuid());
                                                String prefix = data.getPrefix().isEmpty() ? "None" : data.getPrefix();
                                                String suffix = data.getSuffix().isEmpty() ? "None" : data.getSuffix();
                                                player.sendMessage(Text.literal(prefix + " Example Message " + suffix), false);
                                                return 1;
                                            }))
                            .then(CommandManager.literal("help")
                                    .executes(ctx -> {
                                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                                        if (player != null) {
                                            player.sendMessage(Text.literal("PrefixPals Help~"), false);
                                            player.sendMessage(Text.literal("/pp prefix <text>"), false);
                                            player.sendMessage(Text.literal("/pp suffix <text>"), false);
                                            player.sendMessage(Text.literal("/pp clear"), false);
                                            player.sendMessage(Text.literal("/pp info"), false);
                                            player.sendMessage(Text.literal("/pp help"), false);
                                        }
                                        return 1;
                                    }))
            );
        });
    }
}