package io.github.zemelua.umu_guns.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import io.github.zemelua.umu_guns.UMUGuns;
import io.github.zemelua.umu_guns.entity.BulletEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BulletRenderer extends EntityRenderer<BulletEntity> {
	private final Object model;

	private final ItemRenderer itemRenderer;

	public BulletRenderer(EntityRendererProvider.Context renderer) {
		super(renderer);
		this.model = null;

		this.itemRenderer = renderer.getItemRenderer();
	}

	@Override
	public void render(BulletEntity entity, float f1, float f2, PoseStack matrixStack, MultiBufferSource buffer, int partialTicks) {
		UMUGuns.LOGGER.info("rend");

		matrixStack.pushPose();
		matrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		this.itemRenderer.renderStatic(new ItemStack(Items.COAL), ItemTransforms.TransformType.GROUND, partialTicks, OverlayTexture.NO_OVERLAY, matrixStack, buffer, entity.getId());
		matrixStack.popPose();
		super.render(entity, f1, f2, matrixStack, buffer, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(BulletEntity entity) {
		return UMUGuns.location("textures/entity/bullet.png");
	}
}
