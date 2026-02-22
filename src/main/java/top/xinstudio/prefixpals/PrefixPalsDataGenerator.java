package top.xinstudio.prefixpals;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import top.xinstudio.prefixpals.datagen.PrefixpalsENLanguageProvider;
import top.xinstudio.prefixpals.datagen.PrefixpalsZHLanguageProvider;

public class PrefixPalsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(PrefixpalsENLanguageProvider::new);
		pack.addProvider(PrefixpalsZHLanguageProvider::new);
	}
}
