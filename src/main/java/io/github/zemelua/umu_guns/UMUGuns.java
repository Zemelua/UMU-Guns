package io.github.zemelua.umu_guns;

import io.github.zemelua.umu_guns.client.ClientHandler;
import io.github.zemelua.umu_guns.entity.ModEntities;
import io.github.zemelua.umu_guns.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(UMUGuns.MOD_ID)
public class UMUGuns {
	public static final String MOD_ID = "umu_guns";
	public static final Logger LOGGER = LogManager.getLogger();

	public UMUGuns() {
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModItems.initializeCommon(forgeBus, modBus);
		ModEntities.initializeCommon(forgeBus, modBus);

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new ClientHandler(forgeBus, modBus)::initialize);
	}

	public static ResourceLocation location(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> registry(IForgeRegistry<T> type) {
		return DeferredRegister.create(type, MOD_ID);
	}
}
