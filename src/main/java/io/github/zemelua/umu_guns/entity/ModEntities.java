package io.github.zemelua.umu_guns.entity;

import io.github.zemelua.umu_guns.UMUGuns;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
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

	public static void initialize(IEventBus forgeBus, IEventBus modBus) {
		REGISTRY.register(modBus);

		forgeBus.addListener(BulletEntity::onLivingAttack);
	}
}
