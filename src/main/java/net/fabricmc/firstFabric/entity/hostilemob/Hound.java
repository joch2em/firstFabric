package net.fabricmc.firstFabric.entity.hostilemob;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class Hound extends PathAwareEntity {
    public Hound(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}

public class CubeEntityRenderer extends MobEntityRenderer<HoundEntity, HoundEntityModel> {

    public CubeEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new HoundEntityModel(context.getPart(EntityTestingClient.MODEL_CUBE_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(HoundEntity entity) {
        return new Identifier("firstfabric", "textures/entity/hound/hound.png");
    }
}

@Environment(EnvType.CLIENT)
public class EntityTestingClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_CUBE_LAYER = new EntityModelLayer(new Identifier("firstfabric", "hound"), "main");
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(EntityTesting.HOUND, (context) -> {
            return new HoundEntityRenderer(context);
        });

        EntityModelLayerRegistry.registerModelLayer(MODEL_CUBE_LAYER, HoundEntityModel::getTexturedModelData);
    }
}
