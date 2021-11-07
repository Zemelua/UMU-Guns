package io.github.zemelua.umu_guns.effect;

import io.github.zemelua.umu_guns.entity.damage.ModDamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

public class BleedEffect extends MobEffect {
	protected BleedEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void applyEffectTick(LivingEntity living, int amplifier) {
		living.hurt(ModDamageSources.bleed(), 0.6F);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		int i = 40 >> amplifier;
		if (i > 0) {
			return duration % i == 0;
		}

		return true;
	}

	protected static void onLivingAttack(final LivingAttackEvent event) {
		if (event.getEntityLiving().hasEffect(ModEffects.BLEED.get())) {
			event.getEntityLiving().invulnerableTime = 0;
		}
	}

	protected static void onLivingKnockBack(final LivingKnockBackEvent event) {
		if (event.getEntityLiving().hasEffect(ModEffects.BLEED.get())) {
			event.setCanceled(true);
		}
	}
}
