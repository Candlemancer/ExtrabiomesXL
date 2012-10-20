/**
 * This work is licensed under the Creative Commons
 * Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.module.cautia.block;

import java.util.Locale;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.Property;

import com.google.common.base.Optional;

import extrabiomes.Extrabiomes;
import extrabiomes.ExtrabiomesLog;
import extrabiomes.api.Stuff;
import extrabiomes.configuration.ExtrabiomesConfig;
import extrabiomes.module.cautia.worldgen.QuicksandGenerator;
import extrabiomes.proxy.CommonProxy;

public enum BlockManager {
	QUICKSAND {
		@Override
		protected void create() {
			Stuff.quickSand = Optional.of(new BlockQuicksand(blockID));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.quickSand.get();

			thisBlock.setBlockName("extrabiomes.quicksand");
			proxy.setBlockHarvestLevel(thisBlock, "shovel", 0);
			proxy.registerBlock(thisBlock);

			proxy.addName(thisBlock, "Quicksand");

			proxy.registerOre("sandQuick", thisBlock);

			proxy.registerWorldGenerator(new QuicksandGenerator(
					thisBlock.blockID));
		}
	},
	GRIT {
		@Override
		protected void create() {
			Stuff.grit = Optional.of(new BlockGrit(blockID));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.grit.get();

			thisBlock.setBlockName("extrabiomes.grit");
			proxy.setBlockHarvestLevel(thisBlock, "shovel", 0);
			proxy.registerBlock(thisBlock,
					extrabiomes.utility.MultiItemBlock.class);
			for (final BlockGrit.BlockType blockType : BlockGrit.BlockType
					.values())
				proxy.addName(
						new ItemStack(thisBlock, 1, blockType
								.metadata()), blockType.itemName());

			proxy.registerOre("dustSand", new ItemStack(thisBlock, 1,
					BlockGrit.BlockType.GRIT.metadata()));
			proxy.registerOre("dustDirt", new ItemStack(thisBlock, 1,
					BlockGrit.BlockType.DUST.metadata()));
		}
	};

	private static boolean	settingsLoaded	= false;

	private static void createBlocks() throws InstantiationException,
			IllegalAccessException
	{
		for (final BlockManager block : BlockManager.values())
			if (block.blockID > 0) {
				block.create();
				block.blockCreated = true;
			}
	}

	public static void init() throws InstantiationException,
			IllegalAccessException
	{
		for (final BlockManager block : values())
			if (block.blockCreated) block.prepare();
	}

	private static void loadSettings(ExtrabiomesConfig config) {
		settingsLoaded = true;

		ExtrabiomesLog.info("== Cautia Block ID List ==");
		ExtrabiomesLog
				.info("  (may be changed by ID Resolver, if installed.)");

		// Load config settings
		for (final BlockManager cube : BlockManager.values()) {
			final Property property = config.getBlock(cube.idKey(),
					Extrabiomes.getNextDefaultBlockID());
			cube.blockID = property.getInt(0);

			ExtrabiomesLog.info("  %s: %d", cube.toString(),
					cube.blockID);
		}

		ExtrabiomesLog.info("=== End Block ID List ===");
	}

	public static void preInit(ExtrabiomesConfig config)
			throws InstantiationException, IllegalAccessException
	{
		if (settingsLoaded) return;

		loadSettings(config);
		createBlocks();
	}

	protected int	blockID			= 0;
	private boolean	blockCreated	= false;

	protected abstract void create();

	private String idKey() {
		return toString() + ".id";
	}

	protected abstract void prepare();

	@Override
	public String toString() {
		return super.toString().toLowerCase(Locale.US);
	}
}
