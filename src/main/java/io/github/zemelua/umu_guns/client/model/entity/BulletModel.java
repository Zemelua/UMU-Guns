package io.github.zemelua.umu_guns.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class BulletModel extends Model {
	private final ModelPart root;

	public BulletModel(ModelPart root) {
		super(RenderType::entitySolid);

		this.root = root;
	}

	public static LayerDefinition createLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		partDefinition.addOrReplaceChild("ballet", CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F),
				PartPose.ZERO
		);

		return LayerDefinition.create(meshDefinition, 64, 32);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer consumer, int light, int overlay, float red, float green, float blue, float alpha) {
		root.render(matrixStack, consumer, light, overlay);
	}
}
