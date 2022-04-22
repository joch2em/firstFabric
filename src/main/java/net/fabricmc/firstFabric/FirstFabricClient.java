package net.fabricmc.firstFabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.firstFabric.FirstFabric;
import net.fabricmc.firstFabric.entity.hostilemob.HoundEntityModel;
import net.fabricmc.firstFabric.entity.hostilemob.HoundEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FirstFabricClient implements ClientModInitializer {

    public static final EntityModelLayer MODEL_HOUND_LAYER = new EntityModelLayer(new Identifier("firstfabric", "hound"), "main");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(FirstFabric.HOUND, (context) -> {
            return new HoundEntityRenderer(context);
        });

        EntityModelLayerRegistry.registerModelLayer(MODEL_HOUND_LAYER, HoundEntityModel::getTexturedModelData);
    }
}