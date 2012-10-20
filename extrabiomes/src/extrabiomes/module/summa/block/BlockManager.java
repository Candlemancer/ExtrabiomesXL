/**
 * This work is licensed under the Creative Commons
 * Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.module.summa.block;

import static com.google.common.base.Preconditions.checkElementIndex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.WorldGenTallGrass;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.base.Optional;

import extrabiomes.Extrabiomes;
import extrabiomes.ExtrabiomesLog;
import extrabiomes.api.BiomeManager;
import extrabiomes.api.Stuff;
import extrabiomes.configuration.ExtrabiomesConfig;
import extrabiomes.module.summa.biome.BiomeManagerImpl;
import extrabiomes.module.summa.worldgen.CatTailGenerator;
import extrabiomes.module.summa.worldgen.FlowerGenerator;
import extrabiomes.module.summa.worldgen.LeafPileGenerator;
import extrabiomes.module.summa.worldgen.WorldGenAcacia;
import extrabiomes.module.summa.worldgen.WorldGenAutumnTree;
import extrabiomes.module.summa.worldgen.WorldGenFirTree;
import extrabiomes.module.summa.worldgen.WorldGenFirTreeHuge;
import extrabiomes.module.summa.worldgen.WorldGenRedwood;
import extrabiomes.module.summa.worldgen.WorldGenWastelandGrass;
import extrabiomes.proxy.CommonProxy;

public enum BlockManager {
	AUTUMNLEAVES {
		@Override
		protected void create() {
			Stuff.autumnLeaves = Optional.of(new BlockAutumnLeaves(
					blockID));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.autumnLeaves.get();

			thisBlock.setBlockName("extrabiomes.autumnleaves");
			proxy.registerBlock(
					thisBlock,
					extrabiomes.module.summa.block.ItemCustomLeaves.class);

			for (final BlockAutumnLeaves.BlockType type : BlockAutumnLeaves.BlockType
					.values())
			{
				final ItemStack itemstack = new ItemStack(thisBlock, 1,
						type.metadata());
				proxy.addName(itemstack, type.itemName());
				proxy.registerOre("leaves", itemstack);
				proxy.registerOre("leavesAutumn", itemstack);
				proxy.registerOre("leaves" + type.toString(), itemstack);
			}

			WorldGenAutumnTree.setLeavesBlock(thisBlock,
					BlockAutumnLeaves.BlockType.BROWN.metadata(),
					BlockAutumnLeaves.BlockType.ORANGE.metadata(),
					BlockAutumnLeaves.BlockType.PURPLE.metadata(),
					BlockAutumnLeaves.BlockType.YELLOW.metadata());

		}
	},
	CATTAIL {
		@Override
		protected void create() {
			Stuff.catTail = Optional.of(new BlockCatTail(blockID));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.catTail.get();

			thisBlock.setBlockName("extrabiomes.cattail");
			proxy.registerBlock(thisBlock,
					extrabiomes.module.summa.block.ItemCatTail.class);

			proxy.addName(thisBlock, "Cat Tail");

			proxy.registerOre("reedCatTail", thisBlock);

			proxy.registerWorldGenerator(new CatTailGenerator(
					thisBlock.blockID));
		}
	},
	CRACKEDSAND(true) {
		@Override
		protected void create() {
			Stuff.crackedSand = Optional.of(new BlockCrackedSand(
					blockID));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.crackedSand.get();

			thisBlock.setBlockName("extrabiomes.crackedsand");
			proxy.setBlockHarvestLevel(thisBlock, "pickaxe", 0);
			proxy.registerBlock(thisBlock);
			proxy.addName(thisBlock, "Cracked Sand");

			proxy.registerOre("sandCracked", thisBlock);
			addCrackedSandToWasteland(thisBlock.blockID);
		}
	},
	FLOWER {
		@Override
		protected void create() {
			Stuff.flower = Optional.of(new BlockCustomFlower(blockID));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.flower.get();

			thisBlock.setBlockName("extrabiomes.flower");
			proxy.registerBlock(thisBlock,
					extrabiomes.utility.MultiItemBlock.class);

			for (final BlockCustomFlower.BlockType type : BlockCustomFlower.BlockType
					.values())
			{
				final ItemStack itemstack = new ItemStack(thisBlock, 1,
						type.metadata());
				proxy.addName(itemstack, type.itemName());
				proxy.registerOre("flower", itemstack);
				proxy.registerOre("flower" + type.toString(), itemstack);
			}

			proxy.registerWorldGenerator(new FlowerGenerator(
					thisBlock.blockID));
		}
	},
	GRASS {
		@Override
		protected void create() {
			Stuff.grass = Optional
					.of(new BlockCustomTallGrass(blockID));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.grass.get();

			thisBlock.setBlockName("extrabiomes.grass");
			proxy.registerBlock(thisBlock,
					extrabiomes.utility.MultiItemBlock.class);

			for (final BlockCustomTallGrass.BlockType type : BlockCustomTallGrass.BlockType
					.values())
			{
				final ItemStack itemstack = new ItemStack(thisBlock, 1,
						type.metadata());
				proxy.addName(itemstack, type.itemName());
				proxy.registerOre("grass", itemstack);
				proxy.registerOre("grass" + type.toString(), itemstack);
			}

			if (BiomeManager.mountainridge.isPresent()) {
				BiomeManager.addWeightedGrassGenForBiome(
						BiomeManager.mountainridge.get(),
						new WorldGenTallGrass(thisBlock.blockID,
								BlockCustomTallGrass.BlockType.BROWN
										.metadata()), 100);
				BiomeManager
						.addWeightedGrassGenForBiome(
								BiomeManager.mountainridge.get(),
								new WorldGenTallGrass(
										thisBlock.blockID,
										BlockCustomTallGrass.BlockType.SHORT_BROWN
												.metadata()), 100);
			}

			if (BiomeManager.wasteland.isPresent()) {
				BiomeManager.addWeightedGrassGenForBiome(
						BiomeManager.wasteland.get(),
						new WorldGenWastelandGrass(thisBlock.blockID,
								BlockCustomTallGrass.BlockType.DEAD
										.metadata()), 90);
				BiomeManager
						.addWeightedGrassGenForBiome(
								BiomeManager.wasteland.get(),
								new WorldGenWastelandGrass(
										thisBlock.blockID,
										BlockCustomTallGrass.BlockType.DEAD_YELLOW
												.metadata()), 90);
				BiomeManager
						.addWeightedGrassGenForBiome(
								BiomeManager.wasteland.get(),
								new WorldGenWastelandGrass(
										thisBlock.blockID,
										BlockCustomTallGrass.BlockType.DEAD_TALL
												.metadata()), 35);
			}
		}
	},
	GREENLEAVES {
		@Override
		protected void create() {
			Stuff.greenLeaves = Optional.of(new BlockGreenLeaves(
					blockID));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.greenLeaves.get();

			thisBlock.setBlockName("extrabiomes.greenleaves");
			proxy.registerBlock(
					thisBlock,
					extrabiomes.module.summa.block.ItemCustomLeaves.class);

			for (final BlockGreenLeaves.BlockType type : BlockGreenLeaves.BlockType
					.values())
			{
				final ItemStack itemstack = new ItemStack(thisBlock, 1,
						type.metadata());
				proxy.addName(itemstack, type.itemName());
				proxy.registerOre("leaves", itemstack);
				proxy.registerOre("leavesGreen", itemstack);
				proxy.registerOre("leaves" + type.toString(), itemstack);
			}

			WorldGenAcacia.setLeavesBlock(thisBlock,
					BlockGreenLeaves.BlockType.ACACIA.metadata());
			WorldGenFirTree.setLeavesBlock(thisBlock,
					BlockGreenLeaves.BlockType.FIR.metadata());
			WorldGenFirTreeHuge.setLeavesBlock(thisBlock,
					BlockGreenLeaves.BlockType.FIR.metadata());
			WorldGenRedwood.setLeavesBlock(thisBlock,
					BlockGreenLeaves.BlockType.REDWOOD.metadata());

		}
	},
	LEAFPILE {
		@Override
		protected void create() {
			Stuff.leafPile = Optional.of(new BlockLeafPile(blockID));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.leafPile.get();

			thisBlock.setBlockName("extrabiomes.leafpile");
			proxy.setBlockHarvestLevel(thisBlock, "pickaxe", 0);
			proxy.registerBlock(thisBlock);
			proxy.addName(thisBlock, "Leaf Pile");

			proxy.registerOre("pileLeaf", thisBlock);
			proxy.registerWorldGenerator(new LeafPileGenerator(
					thisBlock.blockID));
		}
	},
	REDROCK(true) {
		@Override
		protected void create() {
			Stuff.redRock = Optional.of(new BlockRedRock(blockID));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.redRock.get();

			thisBlock.setBlockName("extrabiomes.redrock");
			proxy.setBlockHarvestLevel(thisBlock, "pickaxe", 0);
			proxy.registerBlock(thisBlock,
					extrabiomes.utility.MultiItemBlock.class);
			for (final BlockRedRock.BlockType blockType : BlockRedRock.BlockType
					.values())
				proxy.addName(
						new ItemStack(thisBlock, 1, blockType
								.metadata()), blockType.itemName());

			final ItemStack redRockItem = new ItemStack(thisBlock, 1,
					BlockRedRock.BlockType.RED_ROCK.metadata());
			final ItemStack redCobbleItem = new ItemStack(thisBlock, 1,
					BlockRedRock.BlockType.RED_COBBLE.metadata());
			final ItemStack redRockBrickItem = new ItemStack(thisBlock,
					1, BlockRedRock.BlockType.RED_ROCK_BRICK.metadata());

			OreDictionary.registerOre("rockRed", redRockItem);
			OreDictionary.registerOre("cobbleRed", redCobbleItem);
			OreDictionary.registerOre("brickRedRock", redRockBrickItem);
			addRedRockToMountainRidge(thisBlock.blockID);
		}
	},
	SAPLING {
		@Override
		protected void create() {
			Stuff.sapling = Optional
					.of(new BlockCustomSapling(blockID));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.sapling.get();

			thisBlock.setBlockName("extrabiomes.sapling");
			proxy.registerBlock(thisBlock,
					extrabiomes.utility.MultiItemBlock.class);

			for (final BlockCustomSapling.BlockType type : BlockCustomSapling.BlockType
					.values())
			{
				final ItemStack itemstack = new ItemStack(thisBlock, 1,
						type.metadata());
				proxy.addName(itemstack, type.itemName());
				proxy.registerOre("sapling", itemstack);
				proxy.registerOre("sapling" + type.toString(),
						itemstack);
			}

			proxy.registerEventHandler(new SaplingBonemealEventhandler(
					(BlockCustomSapling) thisBlock));
			proxy.registerFuelHandler(new FuelHandlerSapling(
					thisBlock.blockID));
		}
	},
	CUSTOMLOG {
		@Override
		protected void create() {
			Stuff.log = Optional.of(new BlockCustomLog(blockID));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.log.get();

			thisBlock.setBlockName("extrabiomes.customlog");
			proxy.registerBlock(thisBlock,
					extrabiomes.utility.MultiItemBlock.class);
			proxy.setBlockHarvestLevel(thisBlock, "axe", 0);

			for (final BlockCustomLog.BlockType type : BlockCustomLog.BlockType
					.values())
			{
				final ItemStack itemstack = new ItemStack(thisBlock, 1,
						type.metadata());
				proxy.addName(itemstack, type.itemName());
				proxy.registerOre("log", itemstack);
				proxy.registerOre("log" + type.toString(), itemstack);
			}

			WorldGenAcacia.setTrunkBlock(thisBlock,
					BlockCustomLog.BlockType.ACACIA.metadata());
			WorldGenFirTree.setTrunkBlock(thisBlock,
					BlockCustomLog.BlockType.FIR.metadata());

			Extrabiomes.proxy.registerEventHandler(thisBlock);
		}
	},
	QUARTERLOG0 {
		@Override
		protected void create() {
			Stuff.quarterLogNW = Optional.of(new BlockQuarterLog(
					blockID, BlockQuarterLog.BarkOn.NW));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.quarterLogNW.get();

			thisBlock.setBlockName("extrabiomes.quarterlog.NW");
			proxy.registerBlock(thisBlock,
					extrabiomes.utility.MultiItemBlock.class);
			proxy.setBlockHarvestLevel(thisBlock, "axe", 0);

			for (final BlockQuarterLog.BlockType type : BlockQuarterLog.BlockType
					.values())
			{
				final ItemStack itemstack = new ItemStack(thisBlock, 1,
						type.metadata());
				proxy.addName(itemstack, type.itemName());
				proxy.registerOre("log", itemstack);
				proxy.registerOre("log" + type.toString(), itemstack);
			}

			Extrabiomes.proxy.registerEventHandler(thisBlock);
		}
	},
	QUARTERLOG1 {
		@Override
		protected void create() {
			Stuff.quarterLogNE = Optional.of(new BlockQuarterLog(
					blockID, BlockQuarterLog.BarkOn.NE));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.quarterLogNE.get();

			thisBlock.setBlockName("extrabiomes.quarterlog.NE");
			proxy.registerBlock(thisBlock,
					extrabiomes.utility.MultiItemBlock.class);
			proxy.setBlockHarvestLevel(thisBlock, "axe", 0);

			for (final BlockQuarterLog.BlockType type : BlockQuarterLog.BlockType
					.values())
			{
				final ItemStack itemstack = new ItemStack(thisBlock, 1,
						type.metadata());
				proxy.addName(itemstack, type.itemName());
				proxy.registerOre("log", itemstack);
				proxy.registerOre("log" + type.toString(), itemstack);
			}

			Extrabiomes.proxy.registerEventHandler(thisBlock);
		}
	},
	QUARTERLOG2 {
		@Override
		protected void create() {
			Stuff.quarterLogSW = Optional.of(new BlockQuarterLog(
					blockID, BlockQuarterLog.BarkOn.SW));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.quarterLogSW.get();

			thisBlock.setBlockName("extrabiomes.quarterlog.SW");
			proxy.registerBlock(thisBlock,
					extrabiomes.utility.MultiItemBlock.class);
			proxy.setBlockHarvestLevel(thisBlock, "axe", 0);

			for (final BlockQuarterLog.BlockType type : BlockQuarterLog.BlockType
					.values())
			{
				final ItemStack itemstack = new ItemStack(thisBlock, 1,
						type.metadata());
				proxy.addName(itemstack, type.itemName());
				proxy.registerOre("log", itemstack);
				proxy.registerOre("log" + type.toString(), itemstack);
			}

			Extrabiomes.proxy.registerEventHandler(thisBlock);
		}
	},
	QUARTERLOG3 {
		@Override
		protected void create() {
			Stuff.quarterLogSE = Optional.of(new BlockQuarterLog(
					blockID, BlockQuarterLog.BarkOn.SE));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.quarterLogSE.get();

			thisBlock.setBlockName("extrabiomes.quarterlog.SE");
			proxy.registerBlock(thisBlock,
					extrabiomes.utility.MultiItemBlock.class);
			proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
			BlockQuarterLog.setDropID(thisBlock.blockID);

			for (final BlockQuarterLog.BlockType type : BlockQuarterLog.BlockType
					.values())
			{
				final ItemStack itemstack = new ItemStack(thisBlock, 1,
						type.metadata());
				proxy.addName(itemstack, type.itemName());
				proxy.registerOre("log", itemstack);
				proxy.registerOre("log" + type.toString(), itemstack);
			}

			Extrabiomes.proxy.registerEventHandler(thisBlock);
		}
	},
	PARCHEDEARTH(true) {
		@Override
		protected void create() {
			Stuff.earthParched = Optional.of(new BlockParchedEarth(
					blockID));
		}

		@Override
		protected void prepare() {
			final CommonProxy proxy = Extrabiomes.proxy;
			final Block thisBlock = Stuff.earthParched.get();

			thisBlock.setBlockName("extrabiomes.parchedearth");
			proxy.setBlockHarvestLevel(thisBlock, "shovel", 0);
			proxy.registerBlock(thisBlock);
			proxy.addName(thisBlock, "Parched Earth");

			proxy.registerOre("dirtParched", thisBlock);
			addParchedEarthToWasteland(thisBlock.blockID);
		}
	};

	private static boolean		settingsLoaded	= false;

	private static final String	TERRAIN_COMMENT	= "%s is used in terrain generation. Its id must be less than 256.";

	private static void addCrackedSandToWasteland(int crackedsandID) {
		if (!BiomeManager.wasteland.isPresent()) return;

		final BiomeGenBase wasteland = BiomeManager.wasteland.get();
		wasteland.topBlock = (byte) crackedsandID;
		ExtrabiomesLog.info("Added cracked sand to wasteland biome.");
	}

	private static void addParchedEarthToWasteland(int crackedsandID) {
		if (!BiomeManager.wasteland.isPresent()) return;

		final BiomeGenBase wasteland = BiomeManager.wasteland.get();
		wasteland.fillerBlock = (byte) crackedsandID;
		ExtrabiomesLog.info("Added parched to wasteland biome.");
	}

	private static void addRedRockToMountainRidge(int redrockID) {
		if (!BiomeManager.mountainridge.isPresent()) return;

		final BiomeGenBase mountainridge = BiomeManager.mountainridge
				.get();
		mountainridge.topBlock = (byte) redrockID;
		mountainridge.fillerBlock = (byte) redrockID;
		ExtrabiomesLog.info("Added red rock to mountain ridge biome.");
	}

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

		if (Stuff.quarterLogNE.isPresent()
				|| Stuff.quarterLogNW.isPresent()
				|| Stuff.quarterLogSE.isPresent()
				|| Stuff.quarterLogSW.isPresent())
			BlockQuarterLog.setRenderId(Extrabiomes.proxy
					.registerBlockHandler(new RenderQuarterLog()));

		if (Stuff.quarterLogNE.isPresent()
				&& Stuff.quarterLogNW.isPresent()
				&& Stuff.quarterLogSE.isPresent()
				&& Stuff.quarterLogSW.isPresent())
		{
			WorldGenFirTreeHuge.setTrunkBlock(Stuff.quarterLogNW.get(),
					Stuff.quarterLogNE.get(), Stuff.quarterLogSW.get(),
					Stuff.quarterLogSE.get(),
					BlockQuarterLog.BlockType.FIR.metadata());
			WorldGenRedwood.setTrunkBlock(Stuff.quarterLogNW.get(),
					Stuff.quarterLogNE.get(), Stuff.quarterLogSW.get(),
					Stuff.quarterLogSE.get(),
					BlockQuarterLog.BlockType.REDWOOD.metadata());
			BlockQuarterLog.setQuarterLogs(Stuff.quarterLogNW.get(),
					Stuff.quarterLogNE.get(), Stuff.quarterLogSW.get(),
					Stuff.quarterLogSE.get());
		}
	}

	private static void loadSettings(ExtrabiomesConfig config) {
		settingsLoaded = true;

		ExtrabiomesLog.info("== Summa Block ID List ==");
		ExtrabiomesLog
				.info("  (may be changed by ID Resolver, if installed.)");

		// Load config settings
		for (final BlockManager cube : BlockManager.values()) {
			final Property property = cube.restrictTo256 ? config
					.getRestrictedBlock(cube.idKey(),
							Extrabiomes.getNextDefaultBlockID())
					: config.getBlock(cube.idKey(),
							Extrabiomes.getNextDefaultBlockID());
			cube.blockID = property.getInt(0);
			if (cube.restrictTo256) {
				final String comment = String.format(TERRAIN_COMMENT,
						cube.toString());
				property.comment = comment;
				checkElementIndex(cube.blockID, 256, comment);
			}

			ExtrabiomesLog.info("  %s: %d", cube.toString(),
					cube.blockID);
		}

		ExtrabiomesLog.info("=== End Block ID List ===");

		if (QUARTERLOG0.blockID == 0 || QUARTERLOG1.blockID == 0
				|| QUARTERLOG2.blockID == 0 || QUARTERLOG3.blockID == 0)
		{
			QUARTERLOG0.blockID = 0;
			QUARTERLOG1.blockID = 0;
			QUARTERLOG2.blockID = 0;
			QUARTERLOG3.blockID = 0;
			ExtrabiomesLog
					.info("At least one quarterlog was disabled, so all have been.");
		}
	}

	public static void preInit(ExtrabiomesConfig config)
			throws InstantiationException, IllegalAccessException
	{
		if (settingsLoaded) return;

		loadSettings(config);
		createBlocks();

		final Collection<BiomeGenBase> biomes = new ArrayList();
		if (BiomeManager.mountainridge.isPresent())
			biomes.add(BiomeManager.mountainridge.get());
		if (BiomeManager.wasteland.isPresent())
			biomes.add(BiomeManager.wasteland.get());
		BiomeManagerImpl.disableDefaultGrassforBiomes(biomes);
	}

	private final boolean	restrictTo256;
	protected int			blockID			= 0;
	private boolean			blockCreated	= false;

	BlockManager() {
		this(false);
	}

	BlockManager(boolean restrictTo256) {
		this.restrictTo256 = restrictTo256;
	}

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
