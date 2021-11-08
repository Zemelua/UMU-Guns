package io.github.zemelua.umu_guns.item;

import io.github.zemelua.umu_guns.client.sound.ModSounds;
import io.github.zemelua.umu_guns.entity.BulletEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class HeavyMachineGunItem extends ProjectileWeaponItem {
	private static final int MAX_FIRE_INTERVAL = 2;
	private static final int BOOTING_FIRE_INTERVAL = 15;

	public HeavyMachineGunItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack heavyMachineGun = player.getItemInHand(hand);
		ItemStack bullet = player.getProjectile(heavyMachineGun);

		if (player.getAbilities().instabuild || bullet.is(ModItems.LARGE_BULLET.get())) {
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(heavyMachineGun);
		}

		return InteractionResultHolder.fail(heavyMachineGun);
	}

	@Override
	public void onUseTick(Level world, LivingEntity living, ItemStack itemStack, int ticks) {
		if (world.isClientSide()) return;

		if (living instanceof Player player) {
			final int passedTicks = this.getUseDuration(itemStack) - ticks;

			float recoil = 0.0F;
			int bootingTicks = 0;
			int fireCount = 0;
			boolean canFire = false;
			for (int i = BOOTING_FIRE_INTERVAL; i > MAX_FIRE_INTERVAL; i -= MAX_FIRE_INTERVAL) {
				if (passedTicks == bootingTicks && !canFire) {
					canFire = true;
					recoil = Math.min(fireCount + 0.4F, 8.0F);
				}
				fireCount++;
				bootingTicks += i;
			}

			if (canFire || (passedTicks >= bootingTicks + MAX_FIRE_INTERVAL && passedTicks % MAX_FIRE_INTERVAL == 0)) {
				if (!canFire) recoil = Math.min((passedTicks - bootingTicks) / 2.0F, 8.0F);

				player.level.playSound(null, player.blockPosition(), ModSounds.HEAVY_MACHINE_GUN_SHOOT.get(), SoundSource.PLAYERS, 0.23F, 0.3F);

				ItemStack projectile = player.getProjectile(itemStack);
				if (player.getAbilities().instabuild && projectile.is(Items.ARROW)) {
					projectile = new ItemStack(ModItems.LARGE_BULLET.get());
				}

				if (projectile.getItem() instanceof IBulletItem bullet) {
					BulletEntity bulletEntity = bullet.createBullet(world, player);

					Vec3 towardsVec = player.getLookAngle().scale(4.0D);
					bulletEntity.shoot(towardsVec.x(), towardsVec.y(), towardsVec.z(), 1.0F, recoil);

					world.addFreshEntity(bulletEntity);
				}
			}
		}
	}

	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return (itemStack -> itemStack.is(ModItems.LARGE_BULLET.get()));
	}

	@Override
	public int getDefaultProjectileRange() {
		return 11;
	}
}
