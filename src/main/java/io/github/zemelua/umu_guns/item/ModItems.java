package io.github.zemelua.umu_guns.item;

import io.github.zemelua.umu_guns.UMUGuns;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModItems {
	private static final DeferredRegister<Item> REGISTRY = UMUGuns.registry(ForgeRegistries.ITEMS);

	public static final RegistryObject<Item> LARGE_BULLET = REGISTRY.register("large_bullet", ()
			-> new LargeBulletItem(new Item.Properties().stacksTo(128).tab(CreativeModeTab.TAB_COMBAT))
	);
	public static final RegistryObject<Item> HEAVY_MACHINE_GUN = REGISTRY.register("heavy_machine_gun", ()
			-> new HeavyMachineGunItem(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT))
	);

	private ModItems() {}

	public static void initialize(IEventBus forgeBus, IEventBus modBus) {
		REGISTRY.register(modBus);
	}
}
