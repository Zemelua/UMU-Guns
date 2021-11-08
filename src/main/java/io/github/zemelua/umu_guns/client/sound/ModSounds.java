package io.github.zemelua.umu_guns.client.sound;

import io.github.zemelua.umu_guns.UMUGuns;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModSounds {
	private static final DeferredRegister<SoundEvent> REGISTRY = UMUGuns.registry(ForgeRegistries.SOUND_EVENTS);

	public static final RegistryObject<SoundEvent> HEAVY_MACHINE_GUN_SHOOT = register("heavy_machine_gun_shoot");

	private ModSounds() {}

	public static void initialize(IEventBus forgeBus, IEventBus modBus) {
		REGISTRY.register(modBus);
	}

	@SuppressWarnings("SameParameterValue")
	private static RegistryObject<SoundEvent> register(String path) {
		return REGISTRY.register(path, () -> new SoundEvent(UMUGuns.location(path)));
	}
}
