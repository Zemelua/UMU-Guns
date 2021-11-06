package io.github.zemelua.umu_guns.entity;

import io.github.zemelua.umu_guns.entity.damage.ModDamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class BulletEntity extends Projectile {
	private static final int LIFESPAN = 200;

	private float power = 1.0F;

	protected BulletEntity(EntityType<? extends Projectile> type, Level world) {
		super(type, world);
	}

	public BulletEntity(Level world, Entity owner) {
		this(ModEntities.BULLET.get(), world);

		this.setPos(owner.getX(), owner.getEyeY() - (double)0.1F, owner.getZ());
		this.setOwner(owner);
	}

	@Override
	public void tick() {
		if (this.tickCount > LIFESPAN) {
			this.discard();
			return;
		}

		Vec3 moveVec = this.getDeltaMovement();
		this.setPos(this.getX() + moveVec.x(), this.getY() + moveVec.y(), this.getZ() + moveVec.z());
		if (this.isInWater()) {
			this.setDeltaMovement(moveVec.subtract(0.0D, 0.05F, 0.0F));
		}

		HitResult hitResult = ProjectileUtil.getHitResult(this, this::canHitEntity);
		this.onHit(hitResult);

		super.tick();
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		Entity target = hitResult.getEntity();
		Entity owner = this.getOwner();
		target.hurt(ModDamageSources.bullet(this, owner == null ? this : owner), this.power);
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		super.onHitBlock(hitResult);

		if (this.level.isClientSide()) {
			// TODO: set bullet mark
		}
	}

	@Override
	protected void defineSynchedData() {}

	public BulletEntity setPower(float power) {
		this.power = power;

		return this;
	}

	protected static void onLivingAttack(final LivingAttackEvent event) {
		if (event.getSource().getMsgId().equals("bullet")) {
			event.getEntityLiving().invulnerableTime = 0;
		}
	}
}
