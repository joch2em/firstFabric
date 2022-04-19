package net.fabricmc.firstFabric;

import com.mojang.datafixers.types.templates.Tag;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.firstFabric.world.dimension.ModDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.texture.TextureStitcher;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.datafixer.fix.ItemNameFix;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.*;

import java.util.List;

public class FirstFabric implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
			new Identifier("firstfabric", "general"),
			() -> new ItemStack(Blocks.COBBLESTONE));

	public static final TestItem FABRIC_ITEM =
			new TestItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(16).group(FirstFabric.ITEM_GROUP));

	//public static final Block FABRIC_BLOCK = registerBlock(
	//	"Teleporter",
	//		new Teleporter(FabricBlockSettings.of(Material.STONE).strength(6f)
	//				.breakByTool(FabricToolTags.PICKAXES, 2).requiresTool()
	//		)
	//);



	public static final Logger LOGGER = LoggerFactory.getLogger("firstfabric");
	public static final String MOD_ID = "firstFabric";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.ITEM, new Identifier("firstfabric", "test_item"), FABRIC_ITEM);
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

	static class Teleporter extends Block{

		public Teleporter(Settings settings) {
			super(settings);
		}
	}
}