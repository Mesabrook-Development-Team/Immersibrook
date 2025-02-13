package com.mesabrook.ib.blocks.te;

import java.util.HashSet;
import java.util.Set;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.net.PacketPlayRecord;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.block.BlockJukebox.TileEntityJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TileEntityCustomJukebox extends TileEntityJukebox implements ITickable
{
	private static final Set<TileEntityCustomJukebox> activeJukeboxes = new HashSet<>();
	private ForgeChunkManager.Ticket chunkTicket;
	
	private int playStartTick = -1; // Tracks the tick when the record started playing
    private int recordDuration = 0;
	
	@Override
    public void onLoad() {
        if (!world.isRemote) {
            // Register this jukebox to the active set
            activeJukeboxes.add(this);

            // Request a chunk loading ticket
            chunkTicket = ForgeChunkManager.requestTicket(Main.instance, this.world, ForgeChunkManager.Type.NORMAL);

            if (chunkTicket != null) {
                ForgeChunkManager.forceChunk(chunkTicket, new ChunkPos(pos));
            }
        }
    }
	
	@Override
    public void invalidate() 
	{
        super.invalidate();
        activeJukeboxes.remove(this);
        if (chunkTicket != null) 
        {
            ForgeChunkManager.releaseTicket(chunkTicket);
        }
    }
	
	@Override
    public void update()
    {
		if (!world.isRemote) 
		{
			if (isPlaying()) {
                // If the record has finished playing, restart it
                if (world.getTotalWorldTime() >= playStartTick + recordDuration) {
                    playRecord();
                }
            }
        }
    }
	
	private boolean isPlaying() {
        return !this.getRecord().isEmpty();
    }

	private void playRecord() {
        ItemStack record = this.getRecord();
        if (!record.isEmpty()) {
            // Play the record and calculate its duration
            world.playEvent(null, 1010, pos, Item.getIdFromItem(record.getItem()));
            playStartTick = (int) world.getTotalWorldTime(); // Record the starting tick
            recordDuration = getRecordDuration(record); // Get the duration of the record
            sendPlayRecordPacket(record); // Send the packet to nearby players
        }
    }
	
	private int getRecordDuration(ItemStack record) {
        // Custom logic to determine the duration of the record in ticks
        // For simplicity, let's assume all records are 180 seconds (3 minutes) long
        return 180 * 20; // 180 seconds * 20 ticks per second
    }
    
    private void sendPlayRecordPacket(ItemStack record) {
        // Radius in chunks (7 chunks radius)
        int radiusInChunks = 7;
        ChunkPos chunkPos = new ChunkPos(pos);

        // Iterate over all players in the world and send the packet if they are within the radius
        for (EntityPlayer player : world.playerEntities) {
            if (player instanceof EntityPlayerMP) {  // Ensure we're dealing with server-side players
                EntityPlayerMP playerMP = (EntityPlayerMP) player;
                ChunkPos playerChunkPos = new ChunkPos(new BlockPos(playerMP.posX, playerMP.posY, playerMP.posZ));
                if (Math.abs(playerChunkPos.x - chunkPos.x) <= radiusInChunks && Math.abs(playerChunkPos.z - chunkPos.z) <= radiusInChunks) {
                    PacketHandler.INSTANCE.sendTo(new PacketPlayRecord(pos, record), playerMP);
                }
            }
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public boolean canRenderBreaking() {
        return true;
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }
    
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        if (event.getWorld().isRemote) return;

        // Re-force the chunk loading for all active jukeboxes in this world
        for (TileEntityCustomJukebox jukebox : activeJukeboxes) {
            if (jukebox.chunkTicket != null) {
                ForgeChunkManager.forceChunk(jukebox.chunkTicket, new ChunkPos(jukebox.getPos()));
            }
        }
    }
}
