package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockRawPlastic extends BlockFalling implements IHasModel
{
    public BlockRawPlastic(String name, float lightLevel)
    {
        super(Material.SAND);
        setRegistryName(name);
        setUnlocalizedName(name);
        setSoundType(SoundType.SAND);
        setHardness(0.3F);
        setResistance(1.0F);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setHarvestLevel("shovel", 0);
        setLightLevel(lightLevel);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
    }
}
