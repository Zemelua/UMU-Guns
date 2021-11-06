package io.github.zemelua.umu_guns.item;

import io.github.zemelua.umu_guns.entity.BulletEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class LargeBulletItem extends Item implements IBulletItem {
	public LargeBulletItem(Properties properties) {
		super(properties);
	}

	@Override
	public BulletEntity createBullet(Level world, LivingEntity owner) {
		return new BulletEntity(world, owner).setPower(2.0F);
	}
}
