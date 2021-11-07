package io.github.zemelua.umu_guns.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import io.github.zemelua.umu_guns.UMUGuns;
import io.github.zemelua.umu_guns.client.model.ModModelLayers;
import io.github.zemelua.umu_guns.client.model.entity.BulletModel;
import io.github.zemelua.umu_guns.entity.BulletEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BulletRenderer extends EntityRenderer<BulletEntity> {
	private static final ResourceLocation BULLET_TEXTURE = UMUGuns.location("textures/entity/bullet.png");

	private final BulletModel model;

	public BulletRenderer(EntityRendererProvider.Context renderer) {
		super(renderer);

		this.model = new BulletModel(renderer.bakeLayer(ModModelLayers.BULLET));
	}

	@Override
	public void render(BulletEntity entity, float yaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int light) {
		matrixStack.pushPose();
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
		matrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) + 90.0F));
		VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(this.getTextureLocation(entity)), false, false);
		this.model.renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.popPose();

		super.render(entity, yaw, partialTicks, matrixStack, buffer, light);
	}

	@Override
	public ResourceLocation getTextureLocation(BulletEntity entity) {
		return BULLET_TEXTURE;
	}
}
