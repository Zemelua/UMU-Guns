package io.github.zemelua.umu_guns;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(UMUGuns.MOD_ID)
public class UMUGuns {
	public static final String MOD_ID = "umu_guns";
	public static final Logger LOGGER = LogManager.getLogger();

	public UMUGuns() {
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
	}
}
