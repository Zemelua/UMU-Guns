package io.github.zemelua.umu_guns.item;

import io.github.zemelua.umu_guns.entity.BulletEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public interface IBulletItem {
	BulletEntity createBullet(Level world, LivingEntity owner);
}
