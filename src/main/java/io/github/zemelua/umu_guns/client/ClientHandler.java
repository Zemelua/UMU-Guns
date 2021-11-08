package io.github.zemelua.umu_guns.client;

import io.github.zemelua.umu_guns.client.model.ModModelLayers;
import io.github.zemelua.umu_guns.client.model.entity.BulletModel;
import io.github.zemelua.umu_guns.client.particle.BallisticParticle;
import io.github.zemelua.umu_guns.client.particle.ModParticles;
import io.github.zemelua.umu_guns.client.renderer.entity.BulletRenderer;
import io.github.zemelua.umu_guns.entity.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ClientHandler {
	private final IEventBus forgeBus;
	private final IEventBus modBus;

	private boolean initialized;

	public ClientHandler(IEventBus forgeBus, IEventBus modBus) {
		this.forgeBus = forgeBus;
		this.modBus = modBus;

		this.initialized = false;
	}

	public void initialize() {
		if (initialized) throw new IllegalStateException("Client is already initialized!");

		modBus.addListener(ClientHandler::onRegisterLayerDefinitions);
		modBus.addListener(ClientHandler::onRegisterEntityRenderers);
		modBus.addListener(ClientHandler::onParticleFactoryRegister);

		this.initialized = true;
	}

	private static void onRegisterLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.BULLET, BulletModel::createLayer);
	}

	private static void onRegisterEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.BULLET.get(), BulletRenderer::new);
	}

	private static void onParticleFactoryRegister(final ParticleFactoryRegisterEvent event) {
		ParticleEngine factories = Minecraft.getInstance().particleEngine;

		factories.register(ModParticles.BALLISTIC.get(), BallisticParticle.Provider::new);
	}
}
