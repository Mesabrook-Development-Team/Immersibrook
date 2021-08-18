package rz.mesabrook.wbtc.telecom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.server.FMLServerHandler;
import rz.mesabrook.wbtc.util.config.ModConfig;

public class CallManager {
	
	private static CallManager instance = null;
	public static CallManager instance()
	{
		if (instance == null)
		{
			instance = new CallManager();
		}
		
		return instance;
	}
	
	private HashMap<UUID, Call> callsByID = new HashMap<>();
	private HashMap<String, Call> callsByOriginPhone = new HashMap<>();
	private ArrayList<UUID> callDequeue = new ArrayList<>();
	private Object callMapLock = new Object();
	
	private CallManager()
	{
		
	}
	
	public void tick()
	{
		synchronized (callMapLock) {
			for(Call call : callsByID.values())
			{
				call.tick();
			}
			
			for(UUID dequeueCall : callDequeue)
			{
				Call call = callsByID.get(dequeueCall);
				
				if (call != null)
				{
					callsByID.remove(dequeueCall);
					callsByOriginPhone.values().remove(call);
				}
			}
		}
		
		callDequeue.clear();
	}
	
	public void dequeueCall(UUID id)
	{
		callDequeue.add(id);
	}
	
	public class Call
	{
		private UUID id;
		private CallPhases callPhase;
		private String originPhone;
		private String destPhone;
		private int connectingTicks;
		
		public Call(String originPhone, String destPhone)
		{
			id = UUID.randomUUID();
			callPhase = CallPhases.Connecting;
			this.originPhone = originPhone;
			this.destPhone = destPhone;
		}
		
		public UUID getID()
		{
			return id;
		}
		
		public CallPhases getCallPhase()
		{
			return callPhase;
		}
		
		public void tick()
		{
			if (getCallPhase() == CallPhases.Connecting)
			{
				connectingTicks++;
				
				if (connectingTicks > ModConfig.phoneRingTicks)
				{
					sendMessageToPhone(FMLServerHandler.instance().getServer(), originPhone, new TextComponentTranslation("phone.didNotAnswer", destPhone));
					dequeueCall(getID());
				}
			}
		}
		
		private void sendMessageToPhone(MinecraftServer server, String phone, TextComponentTranslation message)
		{
			
		}
	}
}
