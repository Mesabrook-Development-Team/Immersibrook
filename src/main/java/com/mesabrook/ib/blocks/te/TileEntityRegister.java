package com.mesabrook.ib.blocks.te;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.mesabrook.ib.apimodels.company.Register;
import com.mesabrook.ib.apimodels.company.RegisterStatus.Statuses;
import com.mesabrook.ib.net.sco.POSInitializeRegisterResponsePacket;
import com.mesabrook.ib.util.apiaccess.DataAccess;
import com.mesabrook.ib.util.apiaccess.DataRequestQueue;
import com.mesabrook.ib.util.apiaccess.DataRequestTask;
import com.mesabrook.ib.util.apiaccess.DataRequestTaskStatus;
import com.mesabrook.ib.util.apiaccess.GetData;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.apiaccess.DataAccess.AuthenticationStatus;
import com.mesabrook.ib.util.apiaccess.DataAccess.GenericErrorResponse;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TileEntityRegister extends TileEntity implements ITickable {
	private String name = "";
	private UUID identifier = new UUID(0L, 0L);
	RegisterStatuses registerStatus = RegisterStatuses.Uninitialized;
	
	// Runtime data
	DataRequestTask statusUpdateTask = null;
	DataRequestTask initializeTask = null;
	Calendar nextUpdateTime = Calendar.getInstance();
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		identifier = compound.getUniqueId("identifier");
		name = compound.getString("name");
		registerStatus = RegisterStatuses.values()[compound.getInteger("registerStatus")];
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setUniqueId("identifier", identifier);
		compound.setString("name", name);
		compound.setInteger("registerStatus", registerStatus.ordinal());
		return super.writeToNBT(compound);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = super.getUpdateTag();
		compound.setInteger("registerStatus", registerStatus.ordinal());
		compound.setString("name", name);
		return compound;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		name = tag.getString("name");
		registerStatus = RegisterStatuses.values()[tag.getInteger("registerStatus")];
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	public RegisterStatuses getRegisterStatus() { return registerStatus; }
	
	private void setRegisterStatus(RegisterStatuses registerStatus)
	{
		if (this.registerStatus == registerStatus)
		{
			return;
		}
		
		this.registerStatus = registerStatus;
		markDirty();
		
		getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
	}
	
	// Called from BlockRegister during random ticks
	public void onRandomTick()
	{
		if (statusUpdateTask != null || identifier == null)
		{
			return;
		}		
	}

	@Override
	public void update() {	
		if (getWorld().isRemote)
		{
			return;
		}
		
		if (DataAccess.getAuthenticationStatus() == AuthenticationStatus.LoggedOut)
		{
			if (registerStatus != RegisterStatuses.WaitingForNetwork)
			{
				setRegisterStatus(RegisterStatuses.WaitingForNetwork);
			}
			return;
		}
		
		if (registerStatus == RegisterStatuses.Uninitialized)
		{
			if (initializeTask != null && initializeTask.getStatus() == DataRequestTaskStatus.Complete)
			{
				POSInitializeRegisterResponsePacket response = new POSInitializeRegisterResponsePacket();
				response.pos = getPos();
				
				UUID playerID = (UUID)initializeTask.getData().get("initializer");
				Register successResult = initializeTask.getTask().getResult(Register.class);
				GenericErrorResponse failedResult = initializeTask.getTask().getResult(GenericErrorResponse.class);
				if (successResult != null)
				{
					identifier = successResult.Identifier;
					setRegisterStatus(RegisterStatuses.WaitingForNetwork); // marks dirty automatically
					
					response.wasSuccessful = true;
				}
				else if (failedResult != null)
				{
					setRegisterStatus(RegisterStatuses.Uninitialized);
					
					response.wasSuccessful = false;
					response.error = failedResult.message;
				}
				else
				{
					setRegisterStatus(RegisterStatuses.Uninitialized);
					
					response.wasSuccessful = false;
					response.error = "An unknown error occurred";
				}
				
				initializeTask = null;
				PacketHandler.INSTANCE.sendTo(response, FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerID));
			}
			
			return;
		}
		
		if ((identifier == null || identifier.equals(new UUID(0L, 0L))) && registerStatus != RegisterStatuses.Uninitialized)
		{
			setRegisterStatus(RegisterStatuses.Uninitialized);
			return;
		}
		
		if (nextUpdateTime.before(Calendar.getInstance()) && statusUpdateTask == null)
		{
			createStatusUpdateTask();
		}
		
		if (statusUpdateTask != null && statusUpdateTask.getStatus() == DataRequestTaskStatus.Complete)
		{
			nextUpdateTime = Calendar.getInstance();
			nextUpdateTime.add(Calendar.SECOND, 10);
			
			if (!statusUpdateTask.getTask().getRequestSuccessful())
			{
				setRegisterStatus(RegisterStatuses.WaitingForNetwork);
				statusUpdateTask = null;
				return;
			}
			
			Register result = statusUpdateTask.getTask().getResult(Register.class);
			if (result != null && result.CurrentStatus != null && result.CurrentStatus.Status != null)
			{
				switch(result.CurrentStatus.Status)
				{
					case Offline:
						setRegisterStatus(RegisterStatuses.Offline);
						break;
					case InternalStorageFull:
						setRegisterStatus(RegisterStatuses.InternalStorageFull);
						break;
					case Online:
						setRegisterStatus(RegisterStatuses.Online);
						break;
				}
			}
			else
			{
				setRegisterStatus(RegisterStatuses.WaitingForNetwork);
			}
			statusUpdateTask = null;
		}
	}
	
	private void createStatusUpdateTask()
	{
		GetData get = new GetData(API.Company, "RegisterAccess/Get", Register.class);
		get.getHeaderOverrides().put("RegisterIdentifier", identifier.toString());
		statusUpdateTask = new DataRequestTask(get);
		DataRequestQueue.INSTANCE.addTask(statusUpdateTask);
	}
	
	public void initialize(String identifier, UUID initializer, String playerName)
	{
		setRegisterStatus(RegisterStatuses.Initializing);
		
		GetData get = new GetData(API.Company, "RegisterAccess/GetByIdentifier/" + identifier, Register.class);
		get.getHeaderOverrides().put("PlayerName", playerName);
		initializeTask = new DataRequestTask(get);
		initializeTask.getData().put("initializer", initializer);
		DataRequestQueue.INSTANCE.addTask(initializeTask);
	}
	
	public enum RegisterStatuses
	{
		WaitingForNetwork,
		Uninitialized,
		Initializing,
		Offline,
		InternalStorageFull,
		Online;
	}
}
