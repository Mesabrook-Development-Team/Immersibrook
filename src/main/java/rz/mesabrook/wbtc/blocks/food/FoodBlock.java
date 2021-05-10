package rz.mesabrook.wbtc.blocks.food;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.init.SoundInit;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.TooltipRandomizer;
import rz.mesabrook.wbtc.util.config.ModConfig;

public class FoodBlock extends Block implements IHasModel
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	protected static final AxisAlignedBB PIE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	private int containerItems = 1;
	private int mainTier = 1;
	private float tierPitch = 1.0F;
	
	public FoodBlock(String name, MapColor color, SoundType snd, CreativeTabs tab)
	{
		super(Material.CLAY, color);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(snd);
		setHardness(1.0F);
		setResistance(3.0F);
		setCreativeTab(tab);
		setHarvestLevel("sword", 0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		
		TooltipRandomizer.ChosenTooltip();
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) 
	{
		return Item.getItemFromBlock(this);
	}
	
	@Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		if(this.getUnlocalizedName().contains("cube_pumpkin_pie"))
		{
			return PIE_AABB;
		}
		else
		{
			return FULL_BLOCK_AABB;
		}
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
		if(ModConfig.funnyTooltips)
		{
			// Randomizer
			if(this.getUnlocalizedName().contains("cube_apples"))
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + "How is this even possible?");
			}
			else if(this.getUnlocalizedName().contains("cube_carrot"))
			{
				tooltip.add(TextFormatting.YELLOW + "What's up, doc.");
			}
			else if(this.getUnlocalizedName().contains("cube_potato"))
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + "Product of Idaho");
			}
			else if(this.getUnlocalizedName().contains("cube_pumpkin_pie"))
			{
				tooltip.add(TextFormatting.GOLD + "Taste of Autumn Valley");
			}
			else if(this.getUnlocalizedName().contains("cube_beetroot"))
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + "b e e t");
			}
			else if(this.getUnlocalizedName().contains("cube_cookie"))
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + "Don't eat this cube.");
			}
			else if(this.getUnlocalizedName().contains("cube_gapple"))
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + "The God of Cubes");
			}
			else if(this.getUnlocalizedName().contains("cube_bread"))
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + "Crunchy");
			}
			else if(this.getUnlocalizedName().contains("cube_fish"))
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + "Smelly");
			}
			else if(this.getUnlocalizedName().contains("cube_salmon"))
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + "Smelly");
			}
			else if(this.getUnlocalizedName().contains("cube_nemo"))
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + "This is what happened to Nemo.");
			}
			else if(this.getUnlocalizedName().contains("cube_pufferfish"))
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + "This is what happened to Mr. Puff.");
			}
			else 
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + TooltipRandomizer.result);
			}
		}
		
		if(this.getUnlocalizedName().contains("cube_pork") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.GREEN + " Raw Porkchops");
		}
		else if(this.getUnlocalizedName().contains("cube_beef") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.GREEN + " Raw Steaks");
		}
		else if(this.getUnlocalizedName().contains("cube_chicken") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9"+ TextFormatting.GREEN + " Raw Chickens");
		}
		else if(this.getUnlocalizedName().contains("cube_rabbit") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.GREEN + " Raw Rabbits");
		}
		else if(this.getUnlocalizedName().contains("cube_mutton") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.GREEN + " Raw Mutton");
		}
		else if(this.getUnlocalizedName().contains("cube_apples"))
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.RED + " Apples");
		}
		else if(this.getUnlocalizedName().contains("cube_cheese"))
		{
			tooltip.add(TextFormatting.AQUA + "Contains Milk");
		}
		else if(this.getUnlocalizedName().contains("cube_carrot") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.YELLOW + " Carrots");
		}
		else if(this.getUnlocalizedName().contains("cube_potato") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.GREEN + " Potatoes");
		}
		else if(this.getUnlocalizedName().contains("cube_pumpkin_pie") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.GREEN + " Pumpkin Pies");
		}
		else if(this.getUnlocalizedName().contains("cube_beetroot") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.GREEN + " Beetroots");
		}
		else if(this.getUnlocalizedName().contains("cube_cookie") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.GREEN + " Cookies");
		}
		else if(this.getUnlocalizedName().contains("cube_gapple") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.GOLD + " Golden Apples");
		}
		else if(this.getUnlocalizedName().contains("cube_bread") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.GREEN + " Bread");
		}
		else if(this.getUnlocalizedName().contains("cube_fish") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.GREEN + " Raw Fish");
		}
		else if(this.getUnlocalizedName().contains("cube_nemo") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.GREEN + " Raw Clownfish");
		}
		else if(this.getUnlocalizedName().contains("cube_pufferfish") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.GREEN + " Raw Pufferfish");
		}
		else if(this.getUnlocalizedName().contains("cube_salmon") && mainTier == 1)
		{
			tooltip.add(TextFormatting.AQUA + "Contains 9" + TextFormatting.GREEN + " Raw Salmon");
		}
		
		super.addInformation(stack, world, tooltip, flag);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(ModConfig.foodCubeSounds && !world.isRemote)
		{
			try
			{
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = pos;
				
				if(this.getUnlocalizedName().contains("cube_pork"))
				{
					world.playSound(player, pos, SoundEvents.ENTITY_PIG_HURT, SoundCategory.BLOCKS, 1.0F, tierPitch);
				}
				else if(this.getUnlocalizedName().contains("cube_beef"))
				{
					world.playSound(player, pos, SoundEvents.ENTITY_COW_HURT, SoundCategory.BLOCKS, 1.0F, tierPitch);
				}
				else if(this.getUnlocalizedName().contains("cube_chicken"))
				{
					world.playSound(player, pos, SoundEvents.ENTITY_CHICKEN_HURT, SoundCategory.BLOCKS, 1.0F, tierPitch);
				}
				else if(this.getUnlocalizedName().contains("cube_mutton"))
				{
					world.playSound(player, pos, SoundEvents.ENTITY_SHEEP_HURT, SoundCategory.BLOCKS, 1.0F, tierPitch);
				}
				else if(this.getUnlocalizedName().contains("cube_rabbit"))
				{
					world.playSound(player, pos, SoundEvents.ENTITY_RABBIT_HURT, SoundCategory.BLOCKS, 1.0F, tierPitch);
				}
				else if(this.getUnlocalizedName().contains("cube_cheese"))
				{
					packet.soundName = "cheese_click";
				}
				else if(this.getUnlocalizedName().contains("cube_pumpkin_pie"))
				{
					packet.soundName = "pie";
				}
				else if(this.getUnlocalizedName().contains("cube_gapple"))
				{
					world.playSound(player, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
					
					if(ModConfig.goldenAppleCubeGivesPotionEffects)
					{
						if(player instanceof EntityPlayer)
						{
							int duration = 1000;
							
							Random rand = new Random();
							int effects;
							int level;
							effects = rand.nextInt(6);
							level = rand.nextInt(8);
							
							switch(effects)
							{
							case 1:
								player.addPotionEffect(new PotionEffect(MobEffects.SPEED, duration, level, true, false));
								break;
							case 2:
								player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, duration, level, true, false));
								break;
							case 3:
								player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, duration, level, true, false));
								break;
							case 4:
								player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, duration, level, true, false));
								break;
							case 5:
								player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, duration, level, true, false));
								break;
							case 6:
								player.clearActivePotions();
								break;
							}
						}
					}
				}
				else if(this.getUnlocalizedName().contains("fish") || this.getUnlocalizedName().contains("pufferfish") || this.getUnlocalizedName().contains("salmon") || this.getUnlocalizedName().contains("nemo"))
				{
					packet.soundName = "fish";
				}
				else if(this.getUnlocalizedName().contains("cube_beetroot") || this.getUnlocalizedName().contains("cube_apples") || this.getUnlocalizedName().contains("cube_carrot"))
				{
					world.playSound(player, pos, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				else
				{
					world.playSound(player, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				
				TooltipRandomizer.ChosenTooltip();
				return true;
			}
			catch(Exception er)
			{
				TooltipRandomizer.ChosenTooltip();
				return true;
			}
			
			
		}
		else
		{
			TooltipRandomizer.ChosenTooltip();
			return false;
		}
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
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
