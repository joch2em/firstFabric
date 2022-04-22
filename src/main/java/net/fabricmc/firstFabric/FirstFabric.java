package net.fabricmc.firstFabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.firstFabric.entity.hostilemob.HoundEntity;
import net.fabricmc.firstFabric.entity.hostilemob.HoundEntityRenderer;
import net.fabricmc.firstFabric.world.dimension.ModDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FirstFabric implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	//ITEM GROUPS

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
			new Identifier("firstfabric", "general"),
			() -> new ItemStack(Blocks.COBBLESTONE));

	//ITEMS

	public static final TestItem FABRIC_ITEM =
			new TestItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(16).group(FirstFabric.ITEM_GROUP));

	//BLOCKS

	public static final Block BACKROOMS_WALL =
			new Backrooms_wall(FabricBlockSettings.of(Material.STONE).strength(10000.0f));
	public static final Block BACKROOMS_LIGHT =
			new Backrooms_light(FabricBlockSettings.of(Material.REDSTONE_LAMP).strength(10000.0f).luminance(45));
	public static final Block BACKROOMS_CARPET =
			new Backrooms_carpet(FabricBlockSettings.of(Material.CARPET).strength(10000.0f));
	public static final Block BACKROOMS_CEILING =
			new Backrooms_ceiling(FabricBlockSettings.of(Material.STONE).strength(10000.0f));

	//SPECIAL BLOCKS

	public static final Block TELEPORTER =
			new Teleporter(FabricBlockSettings.of(Material.STONE).strength(1f).luminance(10).noCollision());

	//HOSTILE ENTITIES

	public static final EntityType<HoundEntity> HOUND =
			Registry.register(Registry.ENTITY_TYPE, new Identifier("firstfabric", "hound"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HoundEntity::new).dimensions(EntityDimensions.fixed(2f, 1)).build()
	);

	public static final Logger LOGGER = LoggerFactory.getLogger("firstfabric");
	public static final String MOD_ID = "firstfabric";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		//	ITEMS
		Registry.register(Registry.ITEM, new Identifier("firstfabric", "test_item"), FABRIC_ITEM);
		Registry.register(Registry.ITEM, new Identifier("firstfabric", "backrooms_wall"), new BlockItem(BACKROOMS_WALL, new FabricItemSettings().group(FirstFabric.ITEM_GROUP)));
		Registry.register(Registry.ITEM, new Identifier("firstfabric", "backrooms_carpet"), new BlockItem(BACKROOMS_CARPET, new FabricItemSettings().group(FirstFabric.ITEM_GROUP)));
		Registry.register(Registry.ITEM, new Identifier("firstfabric", "backrooms_light"), new BlockItem(BACKROOMS_LIGHT, new FabricItemSettings().group(FirstFabric.ITEM_GROUP)));
		Registry.register(Registry.ITEM, new Identifier("firstfabric", "backrooms_ceiling"), new BlockItem(BACKROOMS_CEILING, new FabricItemSettings().group(FirstFabric.ITEM_GROUP)));
		Registry.register(Registry.ITEM, new Identifier("firstfabric", "teleporter"), new BlockItem(TELEPORTER, new FabricItemSettings().group(FirstFabric.ITEM_GROUP)));

		//	BLOCKS
		Registry.register(Registry.BLOCK, new Identifier("firstfabric", "teleporter"), TELEPORTER);
		Registry.register(Registry.BLOCK, new Identifier("firstfabric", "backrooms_wall"), BACKROOMS_WALL);
		Registry.register(Registry.BLOCK, new Identifier("firstfabric", "backrooms_carpet"), BACKROOMS_CARPET);
		Registry.register(Registry.BLOCK, new Identifier("firstfabric", "backrooms_light"), BACKROOMS_LIGHT);
		Registry.register(Registry.BLOCK, new Identifier("firstfabric", "backrooms_ceiling"), BACKROOMS_CEILING);


		// HOSTILE ENTITIES
		FabricDefaultAttributeRegistry.register(HOUND, HoundEntity.createMobAttributes());

		ModDimensions.register();



		LOGGER.info("The greatest mod ever has just loaded (firstFabric from joch2em:D)!");
	}

	static class TestItem extends Item {

		public TestItem(Settings settings) {
			super(settings);
		}

		@Override
		public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
			tooltip.add( new TranslatableText("item.firstFabric.test_item.tooltip") );
			tooltip.add( new TranslatableText("item.firstFabric.test_item.tooltip_red").formatted(Formatting.RED) );
		}

		@Override
		public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
		{
			var stack = user.getStackInHand(hand);

			user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

			stack.decrement(1);
			return TypedActionResult.success(stack);
		}
	}

	//	BLOCKS

	static class Backrooms_wall extends Block{
		public Backrooms_wall(Settings settings) {
			super(settings);
		}
	}

	static class Backrooms_carpet extends Block{
		public Backrooms_carpet(Settings settings){
			super(settings);
		}
	}

	static class Backrooms_light extends Block{
		public Backrooms_light(Settings settings){
			super(settings);
		}
	}

	static class Backrooms_ceiling extends Block{
		public Backrooms_ceiling(Settings settings){
			super(settings);
		}
	}

	static class Teleporter extends Block {

		public Teleporter(Settings settings) {
			super(settings);
		}

		@Override
		public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
			if (!world.isClient) {
				if (!player.isSneaking()) {
					MinecraftServer server = world.getServer();
					if (server != null) {
						if (player instanceof ServerPlayerEntity) {
							ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
							if (world.getRegistryKey() == ModDimensions.backrooms_KEY) {
								ServerWorld overWorld = server.getWorld(World.OVERWORLD);
								if (overWorld != null) {
									BlockPos destPos = getDest(player.getBlockPos(), overWorld, false);
									serverPlayer.teleport(overWorld, destPos.getX(), destPos.getY(), destPos.getZ(),
											serverPlayer.bodyYaw, serverPlayer.prevPitch);
								}
							} else {
								ServerWorld backrooms = server.getWorld(ModDimensions.backrooms_KEY);
								if (backrooms != null) {
									BlockPos destPos = getDest(serverPlayer.getBlockPos(), backrooms, true);
									boolean doSetBlock = true;
									for (BlockPos checkPos : BlockPos.iterate(destPos.down(10).west(10).south(10), destPos.up(10).east(10).north(10))) {
										if (backrooms.getBlockState(checkPos).getBlock() == TELEPORTER) {
											doSetBlock = false;
											break;
										}
									}
									if (doSetBlock) {
										backrooms.setBlockState(destPos, TELEPORTER.getDefaultState());
									}
									serverPlayer.teleport(backrooms, destPos.getX(), destPos.getY(), destPos.getZ(),
											serverPlayer.bodyYaw, serverPlayer.prevPitch);
								}
							}
							return ActionResult.SUCCESS;
						}
					}
				}
			}
			return super.onUse(state, world, pos, player, hand, hit);
		}
		public static BlockPos getDest(BlockPos pos, World destWorld, boolean isInDimension) {
			double y = 61;

			if (!isInDimension) {
				y = pos.getY();
			}

			BlockPos destPos = new BlockPos(pos.getX(), y, pos.getZ());
			int tries = 0;
			while ((!destWorld.getBlockState(destPos).isAir() && !destWorld.getBlockState(destPos)
					.canBucketPlace(Fluids.WATER)) &&
					(!destWorld.getBlockState(destPos.up()).isAir() && !destWorld.getBlockState(destPos.up())
							.canBucketPlace(Fluids.WATER)) && tries < 25) {
				destPos = destPos.up(2);
				tries++;
			}

			return destPos;
		}
	}
}