package io.github.zemelua.umu_guns.entity;

import io.github.zemelua.umu_guns.client.particle.ModParticles;
import io.github.zemelua.umu_guns.effect.ModEffects;
import io.github.zemelua.umu_guns.entity.damage.ModDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BulletEntity extends Projectile {
	private static final int LIFESPAN = 200;

	private float power = 1.0F;

	protected BulletEntity(EntityType<? extends Projectile> type, Level world) {
		super(type, world);
	}

	public BulletEntity(Level world, Entity owner) {
		this(ModEntities.BULLET.get(), world);

		this.setPos(owner.getX(), owner.getEyeY() - (double) 0.1F, owner.getZ());
		this.setOwner(owner);
	}

	@Override
	public void tick() {
		super.tick();

		Vec3 moveVec = this.getDeltaMovement();
		this.setPos(this.getX() + moveVec.x(), this.getY() + moveVec.y(), this.getZ() + moveVec.z());
		if (this.isInWater()) {
			this.setDeltaMovement(moveVec.subtract(0.0D, 0.05F, 0.0F));
		}

		HitResult hitResult = ProjectileUtil.getHitResult(this, this::canHitEntity);
		this.onHit(hitResult);


		if (!this.level.isClientSide()) {
			if (this.level instanceof ServerLevel worldServer) {
				Vec3 oldPos = new Vec3(this.xOld, this.yOld, this.zOld);

				for (double i = 0.0D; i < this.position().distanceTo(oldPos); i += 0.01) {
					Vec3 movedVec = this.position().subtract(oldPos);
					Vec3 particlePos = oldPos.add(movedVec.scale(i));

					worldServer.sendParticles(
							ModParticles.BALLISTIC.get(), particlePos.x(), particlePos.y(), particlePos.z(),
							1, 0.0D, 0.0D, 0.0D, 0.0D
					);
				}
			}

			if (this.tickCount > LIFESPAN) {
				this.discard();
			}
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		Entity target = hitResult.getEntity();
		Entity owner = this.getOwner();

		if (!this.level.isClientSide()) {
			this.playSound(SoundEvents.ARROW_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));

			if (target instanceof LivingEntity targetLiving) {
				int duration = 20 + targetLiving.getRandom().nextInt(10);
				((LivingEntity) target).addEffect(new MobEffectInstance(ModEffects.BLEED.get(), duration, 3));
			}
			target.hurt(ModDamageSources.bullet(this, owner == null ? this : owner), this.power);
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		super.onHitBlock(hitResult);

		BlockPos hitPos = hitResult.getBlockPos();
		BlockState hitBlock = this.level.getBlockState(hitPos);

		if (this.level.isClientSide()) {
			// TODO: set bullet mark
		} else {
			if (!hitBlock.isAir()) {
				if (!this.isSilent()) {
					SoundType sound = hitBlock.getSoundType(level, hitPos, this);
					this.playSound(sound.getHitSound(), sound.getVolume(), sound.getPitch() * 0.75F);
				}

				if (this.level instanceof ServerLevel worldServer) {
					if (!hitBlock.isAir()) {
						worldServer.sendParticles(
								new BlockParticleOption(ParticleTypes.BLOCK, hitBlock).setPos(hitPos), this.getX(), this.getY(), this.getZ(),
								7, 0.0D, 0.0D, 0.0D, 0.15D
						);
						for (int i = 0; i < 2; i++) {
							double randomX = this.random.nextGaussian() * 0.25;
							double randomY = this.random.nextDouble() * 0.2;
							double randomZ = this.random.nextGaussian() * 0.25;
							worldServer.sendParticles(
									ParticleTypes.FLAME, this.getX() + randomX, this.getY() + randomY, this.getZ() + randomZ,
									1, 0.0D, 0.0D, 0.0D, 0.0D
							);
						}
					}
				}

				this.discard();
			}
		}
	}

	@Override
	protected void defineSynchedData() { }

	public BulletEntity setPower(float power) {
		this.power = power;

		return this;
	}
}
