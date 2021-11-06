package io.github.zemelua.umu_guns.client;

import io.github.zemelua.umu_guns.entity.ModEntities;
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

		ModEntities.initializeClient(forgeBus, modBus);

		this.initialized = true;
	}
}
