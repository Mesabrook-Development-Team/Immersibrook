package com.mesabrook.ib.util.handlers;

import java.util.HashSet;

import com.mesabrook.ib.apimodels.mesasys.BlockAudit.AuditTypes;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.apiaccess.BlockAuditQueue;
import com.mesabrook.ib.util.config.ModConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value = Side.SERVER, modid = Reference.MODID)
public class BlockAuditEventHandlers {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void logBlockBreak(BlockEvent.BreakEvent event)
	{
		if (!ModConfig.transmitBlockAuditData && event.getResult() != Result.DENY)
		{
			return;
		}
		
		BlockAuditQueue.INSTANCE.add(AuditTypes.Break, event.getPos(), event.getWorld(), event.getPlayer().getName());
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void logRightClick(PlayerInteractEvent.RightClickBlock evt) {
		if (!ModConfig.transmitBlockAuditData && evt.getUseBlock() != Result.DENY)
		{
			return;
		}
		
		BlockAuditQueue.INSTANCE.addToCheckUse(evt.getPos(), evt.getWorld(), evt.getEntity().getName());
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void logBlockPlace(BlockEvent.EntityPlaceEvent event)
	{
		if (!ModConfig.transmitBlockAuditData && event.getResult() != Result.DENY)
		{
			return;
		}
		
		BlockAuditQueue.INSTANCE.add(AuditTypes.Place, event.getPos(), event.getWorld(), event.getEntity().getName());
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void logBlockPlace(BlockEvent.MultiPlaceEvent event)
	{
		if (!ModConfig.transmitBlockAuditData && event.getResult() != Result.DENY)
		{
			return;
		}
		
		HashSet<Long> handledPositions = new HashSet<>();
		for(BlockSnapshot snapshot : event.getReplacedBlockSnapshots())
		{
			if (handledPositions.add(snapshot.getPos().toLong()))
			{
				BlockAuditQueue.INSTANCE.add(AuditTypes.Place, event.getPos(), event.getWorld(), event.getEntity().getName());				
			}
		}		
	}
}
