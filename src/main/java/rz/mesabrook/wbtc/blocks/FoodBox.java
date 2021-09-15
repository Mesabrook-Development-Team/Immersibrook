package rz.mesabrook.wbtc.blocks;

import com.pam.harvestcraft.item.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.blocks.te.TileEntityFoodBox;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.items.misc.FoodBoxItemBlock;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.ModUtils;
import rz.mesabrook.wbtc.util.Reference;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FoodBox extends Block implements IHasModel
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected final ArrayList<AxisAlignedBB> AABBs;
    private final TextComponentTranslation product = new TextComponentTranslation("im.product");
    private final TextComponentTranslation companyLbl = new TextComponentTranslation("im.company");
    private final TextComponentTranslation empty = new TextComponentTranslation("im.box.empty");

    public FoodBox(String name, Material mat, SoundType sndType, int stackSize, String harvestTool, int harvestLevel, float hardness, float resist, AxisAlignedBB unrotatedAABB)
    {
        super(mat);
        setRegistryName(name);
        setUnlocalizedName(name);
        setSoundType(sndType);
        setHarvestLevel(harvestTool, harvestLevel);
        setHardness(hardness);
        setResistance(resist);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

        AABBs = new ArrayList<AxisAlignedBB>(Arrays.asList(
                ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.DOWN, false),
                ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.UP, false),
                ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.NORTH, false),
                ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.SOUTH, false),
                ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.WEST, false),
                ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.EAST, false),
                unrotatedAABB, unrotatedAABB // Array fill to ensure that the array size covers 4 bit (meta & 0x07).
        ));

        product.getStyle().setColor(TextFormatting.LIGHT_PURPLE);
        companyLbl.getStyle().setColor(TextFormatting.LIGHT_PURPLE);
        empty.getStyle().setColor(TextFormatting.RED);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new FoodBoxItemBlock(this).setRegistryName(this.getRegistryName()).setMaxStackSize(1));
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABBs.get(((EnumFacing)state.getValue(FACING)).getIndex() & 0x7);
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean causesSuffocation(IBlockState state)
    {
        return false;
    }

    @Override
    public float getAmbientOcclusionLightValue(IBlockState state)
    {
        return 1;
    }

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        String boxID = null;
        String company = null;
        int productAmount;
        NBTTagCompound tag = stack.getTagCompound();

        if(tag != null)
        {
            if(tag.hasKey("boxID"))
            {
                boxID = tag.getString("boxID");
            }

            if(tag.hasKey("company"))
            {
                company = tag.getString("company");
            }

            if(tag.hasKey("uses"))
            {
                productAmount = tag.getInteger("uses");
            }
        }

        if(boxID != null)
        {
            tooltip.add(product.getFormattedText() + " " + new TextComponentString(TextFormatting.YELLOW + boxID).getFormattedText());
        }

        if(company != null)
        {
            tooltip.add(companyLbl.getFormattedText() + " " + new TextComponentString(TextFormatting.YELLOW + company).getFormattedText());
        }
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityFoodBox(getUnlocalizedName());
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        World worldIn = (World)world;
        if(!worldIn.isRemote)
        {
            TileEntity te = world.getTileEntity(pos);
            if(te instanceof TileEntityFoodBox)
            {
                TileEntityFoodBox foodBoxTE = (TileEntityFoodBox)te;
                if(foodBoxTE.getUses() > 0)
                {
                    if(state.getBlock() == ModBlocks.SUGAR_BOX)
                    {
                        drops.add(new ItemStack(Items.SUGAR, foodBoxTE.getUses()));
                    }
                    if(state.getBlock() == ModBlocks.FLOUR_BOX)
                    {
                        drops.add(new ItemStack(ItemRegistry.flourItem, foodBoxTE.getUses()));
                    }
                    if(state.getBlock() == ModBlocks.CORNMEAL_BOX)
                    {
                        drops.add(new ItemStack(ItemRegistry.cornmealItem, foodBoxTE.getUses()));
                    }
                    if(state.getBlock() == ModBlocks.COFFEE_BOX)
                    {
                        drops.add(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("harvestcraft", "coffeebeanitem"))));
                    }
                    if(state.getBlock() == ModBlocks.MILK_CHOC_TRUFFLE_BOX)
                    {
                        drops.add(new ItemStack(ModItems.MILK_TRUFFLE, foodBoxTE.getUses()));
                    }
                    if(state.getBlock() == ModBlocks.WHITE_CHOC_TRUFFLE_BOX)
                    {
                        drops.add(new ItemStack(ModItems.WHITE_TRUFFLE, foodBoxTE.getUses()));
                    }
                    if(state.getBlock() == ModBlocks.CHOC_CARAMEL_TRUFFLE_BOX)
                    {
                        drops.add(new ItemStack(ModItems.TRUFFLE_CARAMEL, foodBoxTE.getUses()));
                    }
                    if(state.getBlock() == ModBlocks.CHOC_PB_TRUFFLE_BOX)
                    {
                        drops.add(new ItemStack(ModItems.TRUFFLE_PB, foodBoxTE.getUses()));
                    }
                    if(state.getBlock() == ModBlocks.CHOC_STRAWBERRY_TRUFFLE_BOX)
                    {
                        drops.add(new ItemStack(ModItems.TRUFFLE_STRAWBERRY, foodBoxTE.getUses()));
                    }
                }
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            ItemStack foodboxStack = new ItemStack(this);
            if(!player.isCreative())
            {
                TileEntity te = world.getTileEntity(pos);
                if(te instanceof TileEntityFoodBox)
                {
                    TileEntityFoodBox foodBoxTE = (TileEntityFoodBox)te;
                    if(foodBoxTE.getUses() > 0)
                    {
                        foodBoxTE.setUses(foodBoxTE.getUses() - 1);
                        if(state.getBlock() == ModBlocks.SUGAR_BOX)
                        {
                            player.addItemStackToInventory(new ItemStack(Items.SUGAR, 1));
                            foodBoxTE.markDirty();
                        }
                        if(state.getBlock() == ModBlocks.FLOUR_BOX)
                        {
                            player.addItemStackToInventory(new ItemStack(ItemRegistry.flourItem, 1));
                            foodBoxTE.markDirty();
                        }
                        if(state.getBlock() == ModBlocks.CORNMEAL_BOX)
                        {
                            player.addItemStackToInventory(new ItemStack(ItemRegistry.cornmealItem, 1));
                            foodBoxTE.markDirty();
                        }
                        if(state.getBlock() == ModBlocks.COFFEE_BOX)
                        {
                            player.addItemStackToInventory(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("harvestcraft", "coffeebeanitem"))));
                            foodBoxTE.markDirty();
                        }
                        if(state.getBlock() == ModBlocks.MILK_CHOC_TRUFFLE_BOX)
                        {
                            player.addItemStackToInventory(new ItemStack(ModItems.MILK_TRUFFLE, 1));
                            foodBoxTE.markDirty();
                        }
                        if(state.getBlock() == ModBlocks.WHITE_CHOC_TRUFFLE_BOX)
                        {
                            player.addItemStackToInventory(new ItemStack(ModItems.WHITE_TRUFFLE, 1));
                            foodBoxTE.markDirty();
                        }
                        if(state.getBlock() == ModBlocks.CHOC_CARAMEL_TRUFFLE_BOX)
                        {
                            player.addItemStackToInventory(new ItemStack(ModItems.TRUFFLE_CARAMEL, 1));
                            foodBoxTE.markDirty();
                        }
                        if(state.getBlock() == ModBlocks.CHOC_PB_TRUFFLE_BOX)
                        {
                            player.addItemStackToInventory(new ItemStack(ModItems.TRUFFLE_PB, 1));
                            foodBoxTE.markDirty();
                        }
                        if(state.getBlock() == ModBlocks.CHOC_STRAWBERRY_TRUFFLE_BOX)
                        {
                            player.addItemStackToInventory(new ItemStack(ModItems.TRUFFLE_STRAWBERRY, 1));
                            foodBoxTE.markDirty();
                        }
                    }
                    else
                    {
                        player.sendMessage(new TextComponentString(empty.getFormattedText()));
                    }
                }
                return true;
            }
            else
            {
                if(state.getBlock() == ModBlocks.SUGAR_BOX)
                {
                    player.addItemStackToInventory(new ItemStack(Items.SUGAR, 1));
                }
                if(state.getBlock() == ModBlocks.FLOUR_BOX)
                {
                    player.addItemStackToInventory(new ItemStack(ItemRegistry.flourItem, 1));
                }
                if(state.getBlock() == ModBlocks.CORNMEAL_BOX)
                {
                    player.addItemStackToInventory(new ItemStack(ItemRegistry.cornmealItem, 1));
                }
                if(state.getBlock() == ModBlocks.COFFEE_BOX)
                {
                    player.addItemStackToInventory(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("harvestcraft", "coffeebeanitem"))));
                }
                if(state.getBlock() == ModBlocks.MILK_CHOC_TRUFFLE_BOX)
                {
                    player.addItemStackToInventory(new ItemStack(ModItems.MILK_TRUFFLE, 1));
                }
                if(state.getBlock() == ModBlocks.WHITE_CHOC_TRUFFLE_BOX)
                {
                    player.addItemStackToInventory(new ItemStack(ModItems.WHITE_TRUFFLE, 1));
                }
                if(state.getBlock() == ModBlocks.CHOC_CARAMEL_TRUFFLE_BOX)
                {
                    player.addItemStackToInventory(new ItemStack(ModItems.TRUFFLE_CARAMEL, 1));
                }
                if(state.getBlock() == ModBlocks.CHOC_PB_TRUFFLE_BOX)
                {
                    player.addItemStackToInventory(new ItemStack(ModItems.TRUFFLE_PB, 1));
                }
                if(state.getBlock() == ModBlocks.CHOC_STRAWBERRY_TRUFFLE_BOX)
                {
                    player.addItemStackToInventory(new ItemStack(ModItems.TRUFFLE_STRAWBERRY, 1));
                }
            }
        }
        return true;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        if(willHarvest) return true;
        else return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
    {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }
}
