package io.github.zemelua.umu_guns.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class BallisticParticle extends SimpleAnimatedParticle {
	protected BallisticParticle(ClientLevel world, double xPos, double yPos, double zPos, SpriteSet sprites) {
		super(world, xPos, yPos, zPos, sprites, 0.0F);

		this.quadSize *= 0.3F;
		this.setLifetime(1);
		this.setFadeColor(16772465);
		this.setSpriteFromAge(sprites);
		this.setAlpha(0.45F);
	}

	@SuppressWarnings("ClassCanBeRecord")
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet sprites) {
			this.sprites = sprites;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel world, double xPos, double yPos, double zPos, double xSpeed, double ySpeed, double zSpeed) {
			return new BallisticParticle(world, xPos, yPos, zPos, this.sprites);
		}
	}
}
