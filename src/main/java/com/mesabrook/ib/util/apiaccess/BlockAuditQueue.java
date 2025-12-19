package com.mesabrook.ib.util.apiaccess;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.apimodels.mesasys.BlockAudit;
import com.mesabrook.ib.apimodels.mesasys.BlockAudit.AuditTypes;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.config.ModConfig;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAuditQueue {
	private volatile boolean running = false;
	private volatile boolean cancel = false;
	static
	{
		INSTANCE = new BlockAuditQueue();
	}
	
	private BlockAuditQueue() {}
	public static BlockAuditQueue INSTANCE;
	
	public boolean start()
	{
		if (running)
		{
			return false;
		}
		
		Thread thread = new Thread(() -> run(), "IB Block Audit Queue");
		thread.start();
		return true;
	}
	
	public boolean stop()
	{
		if (!running)
		{
			return false;
		}
		
		cancel = true;
		return true;
	}
	
	public void add(BlockAudit.AuditTypes auditType, BlockPos pos, World world, String player)
	{
		BlockAudit audit = new BlockAudit();
		audit.AuditTime = new Date();
		audit.PositionX = pos.getX();
		audit.PositionY = pos.getY();
		audit.PositionZ = pos.getZ();
		audit.AuditType = auditType;
		
		IBlockState state = world.getBlockState(pos);
		ItemStack stack = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
		
		if (stack.getItem() == Items.AIR)
		{
			audit.BlockName = state.getBlock().getLocalizedName();
		}
		else
		{
			audit.BlockName = stack.getDisplayName();
		}
		
		audit.PlayerName = player;
		
		synchronized(auditsToSend)
		{
			auditsToSend.add(audit);
		}
	}
	
	public void addToCheckUse(BlockPos pos, World world, String player)
	{
		UseToCheck useToCheck = new UseToCheck();
		useToCheck.auditTime = new Date();
		useToCheck.pos = pos;
		useToCheck.player = player;
		
		
		IBlockState state = world.getBlockState(pos);
		ItemStack stack = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
		
		if (stack.getItem() == Items.AIR)
		{
			useToCheck.blockName = state.getBlock().getLocalizedName();
		}
		else
		{
			useToCheck.blockName = stack.getDisplayName();
		}
		useToCheck.blockClazz = state.getBlock().getClass();
		
		synchronized(usesToCheck)
		{
			usesToCheck.add(useToCheck);
		}
	}
	
	private Queue<BlockAudit> auditsToSend = new LinkedList<>();
	private HashSet<UseToCheck> usesToCheck = new HashSet<>();
	
	private final int MAX_AUDITS = 5000;
	private void run()
	{
		try
		{
			int threadDelay = Math.max(1000, ModConfig.transmitBlockAuditDataFrequency); // Ensures at least a 1 second delay regardless of config
			
			while(!cancel)
			{
				running = true;
				checkUses();
				
				HashSet<BlockAudit> blockAuditsToSend = new HashSet<>();
				synchronized(auditsToSend)
				{
					if (auditsToSend.size() > MAX_AUDITS)
					{
						for(int i = 0; i < MAX_AUDITS; i++)
						{
							blockAuditsToSend.add(auditsToSend.poll());
						}
					}
					else	
					{
						blockAuditsToSend.addAll(auditsToSend);
						auditsToSend.clear();
					}
				}
				
				if (!blockAuditsToSend.isEmpty())
				{
					PostData post = new PostData(API.System, "BlockAuditIB/Post", blockAuditsToSend, new Class<?>[0]);
					post.execute();
					
					if (!post.getRequestSuccessful()) // Re-add this to the original list so that this data doesn't get lost
					{
						auditsToSend.addAll(blockAuditsToSend);
					}
				}
				
				if (cancel)
				{
					return;
				}
				else
				{
					try {
						Thread.sleep(threadDelay);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		}
		catch(Exception ex)
		{
			if (Main.logger != null)
			{
				Main.logger.error("An unexpected error occurred with the Block Audit Queue", ex);
			}
		}
		finally
		{
			auditsToSend.clear();
			running = false;
			
			if (!cancel) // Restart if cancel wasn't requested
			{
				start();
			}
			cancel = false;
		}
	}
	
//	private final Class<?>[] onBlockActivatedSignature = new Class<?>[]
//	{
//		World.class,
//		BlockPos.class,
//		IBlockState.class,
//		EntityPlayer.class,
//		EnumHand.class,
//		EnumFacing.class,
//		float.class,
//		float.class,
//		float.class
//	};
//	
	private void checkUses()
	{
		synchronized(usesToCheck)
		{
			for(UseToCheck useToCheck : usesToCheck)
			{
				try
				{
//					boolean hasActivated = false;
//					Class<?> workingClass = useToCheck.blockClazz;
//					while(!hasActivated && workingClass != Block.class)
//					{
//						Method onBlockActivatedMethod = null;
//						try
//						{
//							onBlockActivatedMethod = workingClass.getDeclaredMethod("onBlockActivated", onBlockActivatedSignature);
//						}
//						catch(Exception ex) {}
//						
//						if (onBlockActivatedMethod == null) // Check obfuscated name
//						{
//							try
//							{
//								onBlockActivatedMethod = workingClass.getDeclaredMethod("func_180639_a", onBlockActivatedSignature);
//							}
//							catch(Exception ex) {}
//						}
//						
//						hasActivated = onBlockActivatedMethod != null;
//						
//						workingClass = workingClass.getSuperclass();
//					}
//					
//					if (hasActivated)
//					{
						BlockAudit audit = new BlockAudit();
						audit.AuditTime = useToCheck.auditTime;
						audit.PositionX = useToCheck.pos.getX();
						audit.PositionY = useToCheck.pos.getY();
						audit.PositionZ = useToCheck.pos.getZ();
						audit.AuditType = AuditTypes.Use;
						audit.BlockName = useToCheck.blockName;
						audit.PlayerName = useToCheck.player;
						auditsToSend.add(audit);
//					}
				}
				catch(Exception ex) {}
			}
			
			usesToCheck.clear();
		}
	}

	private static class UseToCheck
	{
		public Class<?> blockClazz;
		public Date auditTime;
		public BlockPos pos;
		public String blockName;
		public String player;
	}
}
