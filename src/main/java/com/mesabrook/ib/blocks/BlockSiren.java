/**
 * USING THE SIREN CLASS 101 WITH RAVENHOLMZOMBIE.
 * 
 * When registering in ModBlocks, you will need to provide the following parameters in your function.
 * 
 * @param String name - The unlocalized name of the block.
 * @param SoundEvent sirenSoundIn - The ModSounds SoundEvent object that'll be played in-game.
 * @param int soundDurationIn - The duration of the above SoundEvent in ticks. To prevent sound overlap, be sure to calculate
 * how long your desired SoundEvent is by playing its OGG file in a media player and counting how long it lasts in seconds.
 * To convert to ticks, simply do 1 second×20 ticks/second=20 ticks. For example: A 10-second sound converts to 200 ticks.
 * @param int sirenSoundRadius - The physical in-game radius of the SoundEvent. Any players within this radius will hear the sound.
*/


package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.te.TileEntitySiren;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.init.ModSounds;
import com.mesabrook.ib.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSiren extends Block implements IHasModel
{
	private SoundEvent sirenSound = null;
	private int duration;
	private int radius;
	
	public BlockSiren(String name, SoundEvent sirenSoundIn, int soundDurationIn, int sirenSoundRadiusIn) 
	{
        super(Material.IRON);
        setUnlocalizedName(name);
        setRegistryName(name);
        setSoundType(SoundType.METAL);
        setHardness(2.0F);
        setResistance(10.0F);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        
        this.sirenSound = sirenSoundIn;
        this.duration = soundDurationIn;
        this.radius = sirenSoundRadiusIn;
        
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
	}
	
	@Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
		boolean powered = world.isBlockPowered(pos);
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TileEntitySiren) 
        {
            TileEntitySiren siren = (TileEntitySiren) tileEntity;
            siren.setSirenSound(this.sirenSound);
            siren.setDuration(this.duration);
            siren.setRadius(this.radius);
            siren.setPowered(powered);
        }
    }
	
	@Override
    public boolean hasTileEntity(IBlockState state) 
	{
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) 
    {
        return new TileEntitySiren();
    }
}
