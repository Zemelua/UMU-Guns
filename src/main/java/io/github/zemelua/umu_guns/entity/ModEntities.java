package io.github.zemelua.umu_guns.entity;

import io.github.zemelua.umu_guns.UMUGuns;
import io.github.zemelua.umu_guns.client.renderer.entity.BulletRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModEntities {
	private static final DeferredRegister<EntityType<?>> REGISTRY = UMUGuns.registry(ForgeRegistries.ENTITIES);

	public static final RegistryObject<EntityType<BulletEntity>> BULLET = REGISTRY.register("bullet", ()
			-> EntityType.Builder.<BulletEntity>of(BulletEntity::new, MobCategory.MISC)
			.sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("bullet")
	);

	private ModEntities() {}

	public static void initializeCommon(IEventBus forgeBus, IEventBus modBus) {
		REGISTRY.register(modBus);

		forgeBus.addListener(BulletEntity::onLivingAttack);
	}

	public static void initializeClient(IEventBus forgeBus, IEventBus modBus) {
		modBus.addListener(ModEntities::onRegisterEntityRenderers);
	}

	private static void onRegisterEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(BULLET.get(), BulletRenderer::new);
	}
}
