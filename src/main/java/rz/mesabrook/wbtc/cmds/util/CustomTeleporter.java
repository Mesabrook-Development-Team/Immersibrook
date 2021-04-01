package rz.mesabrook.wbtc.cmds.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class CustomTeleporter extends Teleporter
{
	private final WorldServer world;
	private double x, y, z;
	
	public CustomTeleporter(WorldServer world, double x, double y, double z)
	{
		super(world);
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void placeInPortal(Entity entity, float rotationYaw)
	{
		this.world.getBlockState(new BlockPos((int) this.x, (int) this.y, (int) this.z));
		entity.setPosition(this.x, this.y, this.z);
		entity.motionX = 0.0F;
		entity.motionY = 0.0F;
		entity.motionZ = 0.0F;
	}
	
	public static void teleportToDimension(EntityPlayer player, int dim, double x, double y, double z)
	{
		int oldDim = player.getEntityWorld().provider.getDimension();
		EntityPlayerMP emp = (EntityPlayerMP) player;
		MinecraftServer mcserv = player.getEntityWorld().getMinecraftServer();
		WorldServer worldServ = mcserv.getWorld(dim);
		player.addExperienceLevel(0);
		
		x = player.getPosition().getX();
		y = player.getPosition().getY();
		z = player.getPosition().getZ();
		
		if(worldServ == null || worldServ.getMinecraftServer() == null)
		{
			throw new IllegalArgumentException("[WBTC] Error. Dimension " +dim+ " doesn't exist!");
		}
		
		worldServ.getMinecraftServer().getPlayerList().transferPlayerToDimension(emp, dim, new CustomTeleporter(worldServ, x, y, z));
		player.setPositionAndUpdate(x, y, z);
	}
}
