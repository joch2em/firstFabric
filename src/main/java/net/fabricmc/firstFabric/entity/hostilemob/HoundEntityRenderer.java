package net.fabricmc.firstFabric.entity.hostilemob;

import net.fabricmc.firstFabric.FirstFabricClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class HoundEntityRenderer extends MobEntityRenderer<HoundEntity, HoundEntityModel> {

    public HoundEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new HoundEntityModel(context.getPart(FirstFabricClient.MODEL_HOUND_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(HoundEntity entity) {
        return new Identifier("firstfabric", "textures/entity/hound/hound.png");
    }
}
