package io.github.zemelua.umu_guns.entity.damage;

import io.github.zemelua.umu_guns.entity.BulletEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public final class ModDamageSources {
	public static DamageSource bullet(BulletEntity bullet, @Nullable Entity owner) {
		return new IndirectEntityDamageSource("bullet", bullet, owner).setProjectile();
	}

	public static DamageSource bleed() {
		return new DamageSource("bleed").bypassArmor().bypassMagic();
	}
}
