package io.github.zemelua.umu_guns.client.particle;

import io.github.zemelua.umu_guns.UMUGuns;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModParticles {
	private static final DeferredRegister<ParticleType<?>> REGISTRY = UMUGuns.registry(ForgeRegistries.PARTICLE_TYPES);

	public static final RegistryObject<SimpleParticleType> BALLISTIC = register("ballistic", false);

	private ModParticles() {}

	public static void initialize(IEventBus forgeBus, IEventBus modBus) {
		REGISTRY.register(modBus);
	}

	@SuppressWarnings("SameParameterValue")
	private static RegistryObject<SimpleParticleType> register(String name, boolean overrideLimiter) {
		return REGISTRY.register(name, () -> new SimpleParticleType(overrideLimiter));
	}
}
