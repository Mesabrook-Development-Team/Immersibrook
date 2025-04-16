package com.mesabrook.ib.entity;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.BlockDiscGolfBasket;
import com.mesabrook.ib.blocks.te.TileEntityDiscGolfBasket;
import com.mesabrook.ib.items.misc.ItemDiscGolf;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;
import com.mojang.authlib.GameProfile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@SuppressWarnings("deprecated")
public class EntityDiscGolf extends EntityThrowable
{
    private int damage;
    public static final DataParameter<String> ITEM_NAME = EntityDataManager.createKey(EntityDiscGolf.class, DataSerializers.STRING);

    public EntityDiscGolf(World worldIn)
    {
        super(worldIn);
        setSize(width, 0.1F);
    }

    public EntityDiscGolf(World worldIn, EntityLivingBase throwerIn, int damage)
    {
        super(worldIn, throwerIn);
        this.damage = damage;
        setSize(width, 0.1F);
    }

    public EntityDiscGolf(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        setSize(width, 0.1F);
    }
    
    @Override
    protected void entityInit() {
    	super.entityInit();
    	
    	dataManager.register(ITEM_NAME, "");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setString("itemName", itemName);
        compound.setInteger("mesarangDamage", damage);
        compound.setFloat("speed", speed);
        compound.setFloat("glide", glide);
        compound.setFloat("turn", turn);
        compound.setFloat("fade", fade);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        itemName = compound.getString("itemName");
        damage = compound.getInteger("mesarangDamage");
        speed = compound.getFloat("speed");
        glide = compound.getFloat("glide");
        turn = compound.getFloat("turn");
        fade = compound.getFloat("fade");
    }

    private boolean impacted = false;
    @Override
    @SuppressWarnings("deprecated")
    protected void onImpact(RayTraceResult result)
    {
    	impacted = true;
        if(result.entityHit != null)
        {
            boolean hit = false;
            damage++;

            if(!world.isRemote)
            {
                if(result.entityHit instanceof EntityPlayerMP)
                {
                    GameProfile profile = ((EntityPlayerMP) result.entityHit).getGameProfile();
                    if(profile != null && Reference.RZ_UUID.equals(profile.getId()) || Reference.CSX_UUID.equals(profile.getId()) || Reference.SLOOSE_UUID.equals(profile.getId()) || Reference.ZOE_UUID.equals(profile.getId()))
                    {
                        hit = true;
                        EntityLightningBolt lightningBolt = new EntityLightningBolt(world, getThrower().posX, getThrower().posY, getThrower().posZ, true);
                        world.addWeatherEffect(lightningBolt);
                        world.spawnEntity(lightningBolt);
                    }
                }
            }

            if(!hit)
            {
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.1f);
            }
        }
        
        if(!this.world.isRemote && result.typeOfHit == Type.BLOCK)
        {
            damage++;
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Reference.MODID, itemName));
            ItemStack stackToSpawn = new ItemStack(item, 1, damage);
            if (damage >= item.getMaxDamage(stackToSpawn))
            {
            	ServerSoundBroadcastPacket sound = new ServerSoundBroadcastPacket();
            	sound.modID = "minecraft";
        		sound.soundName = "entity.item.break";
            	sound.pos = result.getBlockPos();
            	PacketHandler.INSTANCE.sendToAllAround(sound, new TargetPoint(world.provider.getDimension(), result.getBlockPos().getX(), result.getBlockPos().getY(), result.getBlockPos().getZ(), 150));
            	
            	stackToSpawn = null;
            }
            
            if (stackToSpawn != null && result.getBlockPos() != null && world.getBlockState(result.getBlockPos()).getBlock() instanceof BlockDiscGolfBasket)
            {   
            	boolean missed = result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN || result.hitVec.y % 1 < 0.375;
            	
            	ServerSoundBroadcastPacket sound = new ServerSoundBroadcastPacket();
        		sound.soundName = missed ? "discgolf_miss" : "discgolf_in";
            	sound.pos = result.getBlockPos();
            	sound.rapidSounds = true;
            	PacketHandler.INSTANCE.sendToAllAround(sound, new TargetPoint(world.provider.getDimension(), result.getBlockPos().getX(), result.getBlockPos().getY(), result.getBlockPos().getZ(), 150));
            	
            	if (!missed)
            	{
            		TileEntityDiscGolfBasket basket = (TileEntityDiscGolfBasket)world.getTileEntity(result.getBlockPos());
            		if (basket != null)
            		{
            			
            			for(int i = 0; i < basket.getDiscInventory().getSlots(); i++)
            			{
            				ItemStack returnedStack = basket.getDiscInventory().insertItem(i, stackToSpawn, false);
            				if (returnedStack.isEmpty())
            				{
            					stackToSpawn = null;
            					world.notifyBlockUpdate(result.getBlockPos(), world.getBlockState(result.getBlockPos()), world.getBlockState(result.getBlockPos()), 3);
            					break;
            				}            				
            			}
            		}
            	}
            }

            if(stackToSpawn != null)
            {
            	EntityItem itemEntity = new EntityItem(this.world, this.posX, this.posY, this.posZ, stackToSpawn);
            	itemEntity.setGlowing(true);
            	itemEntity.lifespan = Integer.MAX_VALUE;
                this.world.spawnEntity(itemEntity);
            }
            
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }
    
    float speed;
    float glide;
    float turn;
    float fade;
    float initialYaw;
    String itemName;
    
    public void shootDisc(ItemDiscGolf item, Entity entityThrower, float rotationPitchIn, float rotationYawIn, double throwPower)
    {
    	this.itemName = item.getRegistryName().getResourcePath();
    	this.speed = item.getSpeed();
    	this.glide = item.getGlide();
    	this.turn = item.getTurn();
    	this.fade = item.getFade();
    	this.initialYaw = rotationYawIn;
    	
    	this.getDataManager().set(ITEM_NAME, this.itemName);
    	
    	this.shoot(entityThrower, rotationPitchIn, rotationYawIn, 0F, 1.5F * (float)throwPower, 0F);
    }
    
    @Override
    public void onUpdate() {
    	onEntityUpdate();
//    	super.onUpdate();
        // === Glide Simulation ===
        // Glide affects how much gravity pulls on the disc; more glide = less gravity
        double glideGravity = 0.04D - (0.005D * this.glide); // ranges ~0.005 to 0.035
        this.motionY -= glideGravity;

        // === Turn Simulation (early flight) ===
        if (this.ticksExisted < 20) {
            // Turn is usually negative for understable discs (go right on RHBH)
            double turnInfluence = 0.0025 * this.turn; // e.g., turn -3 = -0.0075
            applyLateralTurn(turnInfluence);
        }

        // === Fade Simulation (late flight) ===
        if (this.ticksExisted > 40) {
            // Fade is positive, pulling the disc left (for RHBH)
            double fadeInfluence = 0.0025 * this.fade;
            applyLateralTurn(-fadeInfluence);
        }

        // === Air Drag ===
        this.motionX *= 0.985;
        this.motionZ *= 0.985;

        // === Move the Entity ===
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        
        Vec3d currentPos = this.getPositionVector();
        Vec3d nextPos = currentPos.addVector(this.motionX, this.motionY, this.motionZ);
        RayTraceResult result = this.world.rayTraceBlocks(currentPos, nextPos, false, true, false);
        
        if (result != null)
        {
        	this.onImpact(result);
        }
        
        this.ticksExisted++;
    }


    private void applyLateralTurn(double influence) {
        // Apply sideways curve perpendicular to direction of travel
        double horizontalMag = Math.sqrt(motionX * motionX + motionZ * motionZ);
        if (horizontalMag == 0) return;

        // Normalize horizontal direction
        double normX = motionX / horizontalMag;
        double normZ = motionZ / horizontalMag;

        // Perpendicular vector (rotate 90° right-hand)
        double perpX = -normZ;
        double perpZ = normX;

        // Apply influence
        this.motionX += perpX * influence;
        this.motionZ += perpZ * influence;
    }

    public String getItemName()
    {
    	return itemName;
    }
}