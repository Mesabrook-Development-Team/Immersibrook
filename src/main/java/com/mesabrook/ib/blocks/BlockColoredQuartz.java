package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockColoredQuartz extends Block implements IHasModel
{
    public static PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);

    public BlockColoredQuartz()
    {
        super(Material.ROCK);
        setUnlocalizedName("colored_quartz");
        setRegistryName("colored_quartz");
        setHardness(1.5F);
        setResistance(8.0F);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setHarvestLevel("pickaxe", 0);
        setLightLevel(0);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this)
        {
            public int getMetadata(int damage) {
                return damage;
            }

            public String getUnlocalizedName(ItemStack stack)
            {
                return "tile.colored_quartz." + EnumDyeColor.byMetadata(stack.getMetadata()).getName();
            }
        }.setRegistryName(this.getRegistryName()).setMaxStackSize(64).setHasSubtypes(true));
    }

    @Override
    public void registerModels()
    {
        for (int i = 0; i < 16; i++)
        {
            Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), i,
                    "color=" + EnumDyeColor.byMetadata(i).getName());
        }
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (int i = 0; i < 16; i++)
        {
            items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, COLOR);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(COLOR).getMetadata();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1, getMetaFromState(state));
    }
}
