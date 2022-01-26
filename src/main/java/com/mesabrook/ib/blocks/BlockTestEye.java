package com.mesabrook.ib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;

public class BlockTestEye extends Block
{
    public BlockTestEye(String name)
    {
        super(Material.ROCK);
        setUnlocalizedName(name);
        setRegistryName(name);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
}
