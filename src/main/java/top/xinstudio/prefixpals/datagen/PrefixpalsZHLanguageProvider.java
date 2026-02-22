package top.xinstudio.prefixpals.datagen;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PrefixpalsZHLanguageProvider extends FabricLanguageProvider {
    public PrefixpalsZHLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "zh_cn", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {

        translationBuilder.add("prefixpals.prefix.set", "前缀已设置喵，赶紧发个信息吧~");
        translationBuilder.add("prefixpals.suffix.set", "后缀已设置喵，赶紧发个信息吧~");
        translationBuilder.add("prefixpals.clear", "已清理完成喵~");

        translationBuilder.add("prefixpals.help.title", "PrefixPals 帮助喵~");
        translationBuilder.add("prefixpals.help.prefix", "/pp prefix <text> 设置前缀喵~");
        translationBuilder.add("prefixpals.help.suffix", "/pp suffix <text> 添加后缀喵~");
        translationBuilder.add("prefixpals.help.clear", "/pp clear 清理喵~");
        translationBuilder.add("prefixpals.help.info", "/pp info 显示示例喵~");
        translationBuilder.add("prefixpals.help.help", "/pp help 帮助喵~");

        translationBuilder.add("prefixpals.error.001", "错误代码：001");
    }
}
