
package extrabiomes.api;

import net.minecraft.src.Block;
import net.minecraft.src.Item;

import com.google.common.base.Optional;

/**
 * This class contains all of the custom items and blocks.
 * 
 * @author ScottKillen
 * 
 */
public enum Stuff {
	INSTANCE;

	public static Optional<? extends Item>	dirtClod			= Optional.absent();
	public static Optional<? extends Item>	logTurner			= Optional.absent();
	public static Optional<? extends Item>	sandClump			= Optional.absent();
	public static Optional<? extends Item>	scarecrow			= Optional.absent();
	
	public static Optional<? extends Block>	cattail				= Optional.absent();
	public static Optional<? extends Block>	crackedSand			= Optional.absent();
	public static Optional<? extends Block>	earthParched		= Optional.absent();
	public static Optional<? extends Block>	flower				= Optional.absent();
	public static Optional<? extends Block>	grass				= Optional.absent();
	public static Optional<? extends Block>	grit				= Optional.absent();
	public static Optional<? extends Block>	leafPile			= Optional.absent();
	public static Optional<? extends Block>	leavesAutumn		= Optional.absent();
	public static Optional<? extends Block>	leavesGreen			= Optional.absent();
	public static Optional<? extends Block>	log					= Optional.absent();
	public static Optional<? extends Block>	planks				= Optional.absent();
	public static Optional<? extends Block>	quarterLogNE		= Optional.absent();
	public static Optional<? extends Block>	quarterLogNW		= Optional.absent();
	public static Optional<? extends Block>	quarterLogSE		= Optional.absent();
	public static Optional<? extends Block>	quarterLogSW		= Optional.absent();
	public static Optional<? extends Block>	quickSand			= Optional.absent();
	public static Optional<? extends Block>	redRock				= Optional.absent();
	public static Optional<? extends Block>	sapling				= Optional.absent();
	public static Optional<? extends Block>	slabRedRock			= Optional.absent();
	public static Optional<? extends Block>	slabRedRockDouble	= Optional.absent();
	public static Optional<? extends Block>	slabWood			= Optional.absent();
	public static Optional<? extends Block>	slabWoodDouble		= Optional.absent();
	public static Optional<? extends Block>	stairsAcacia		= Optional.absent();
	public static Optional<? extends Block>	stairsFir			= Optional.absent();
	public static Optional<? extends Block>	stairsRedCobble		= Optional.absent();
	public static Optional<? extends Block>	stairsRedRockBrick	= Optional.absent();
	public static Optional<? extends Block>	stairsRedwood		= Optional.absent();

}
