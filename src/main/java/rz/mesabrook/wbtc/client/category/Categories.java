package rz.mesabrook.wbtc.client.category;

/**
 * Original Author: MrCrayfish
 * Adapted by RavenholmZombie for use in Immersibrook.
 * 
 * https://github.com/MrCrayfish
 * https://github.com/RavenholmZombie
 */
public class Categories 
{
	public static final AbstractCategory BUILDING_BLOCKS = new CategoryBlocks();
	public static final AbstractCategory CEILING_BLOCKS = new CategoryCeiling();
	public static final AbstractCategory HOUSEHOLD = new CategoryHousehold();
	public static final AbstractCategory TROPHY = new CategoryTrophy();
	public static final AbstractCategory TOOLS = new CategoryTools();
	public static final AbstractCategory MUSIC = new CategoryRecords();
	public static final AbstractCategory RAW = new CategoryResources();
	public static final AbstractCategory FOOD = new CategoryFood();
}
