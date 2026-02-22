package top.xinstudio.prefixpals.datagen;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PrefixpalsENLanguageProvider extends FabricLanguageProvider {
    public PrefixpalsENLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {

        translationBuilder.add("prefixpals.prefix.set", "The prefix has been set, meow. Hurry up and send a message~");
        translationBuilder.add("prefixpals.suffix.set", "The suffix has been set, meow. Hurry up and send a message~");

        translationBuilder.add("prefixpals.clear", "Cleared successfully, meow~");

        translationBuilder.add("prefixpals.help.title", "PrefixPals Help, meow~");
        translationBuilder.add("prefixpals.help.prefix", "/pp prefix <text> Set your prefix, meow~");
        translationBuilder.add("prefixpals.help.suffix", "/pp suffix <text> Add a suffix, meow~");
        translationBuilder.add("prefixpals.help.clear", "/pp clear Clear your prefix and suffix, meow~");
        translationBuilder.add("prefixpals.help.info", "/pp info Show a preview example, meow~");
        translationBuilder.add("prefixpals.help.help", "/pp help Show help menu, meow~");

        translationBuilder.add("prefixpals.error.001", "Error Code: 001");
    }
}
