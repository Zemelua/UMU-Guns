package io.github.zemelua.umu_guns.effect;

import io.github.zemelua.umu_guns.UMUGuns;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModEffects {
	private static final DeferredRegister<MobEffect> REGISTRY = UMUGuns.registry(ForgeRegistries.MOB_EFFECTS);

	public static final RegistryObject<MobEffect> BLEED = REGISTRY.register("bleed", ()
			-> new BleedEffect(MobEffectCategory.HARMFUL, 10492185)
			.addAttributeModifier(Attributes.MOVEMENT_SPEED, "32C10170-D0BF-48EE-85D2-30E9461EEBB8", -0.1D, AttributeModifier.Operation.MULTIPLY_TOTAL)
	);

	private ModEffects() {}

	public static void initialize(IEventBus forgeBus, IEventBus modBus) {
		REGISTRY.register(modBus);

		forgeBus.addListener(BleedEffect::onLivingAttack);
		forgeBus.addListener(BleedEffect::onLivingKnockBack);
	}
}
