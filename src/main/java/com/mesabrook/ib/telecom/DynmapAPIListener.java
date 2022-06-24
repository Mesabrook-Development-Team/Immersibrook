package com.mesabrook.ib.telecom;

import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.telecom.WirelessEmergencyAlertManager.WirelessEmergencyAlert;
import com.mesabrook.ib.telecom.WirelessEmergencyAlertManager.WirelessEmergencyAlert.Coordinate;
import com.mesabrook.ib.util.config.ModConfig;

import net.minecraftforge.fml.common.FMLCommonHandler;

public class DynmapAPIListener extends DynmapCommonAPIListener {

	private static DynmapCommonAPI api;
	public static DynmapCommonAPI getDynmapApi() { return api; }
	private static String getWorldName()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance().getWorldName();
	}
	
	@Override
	public void apiEnabled(DynmapCommonAPI api) {
		DynmapAPIListener.api = api;
		
		getWeatherSet().deleteMarkerSet(); // Reset
	}
	
	public static void register()
	{
		DynmapCommonAPIListener.register(new DynmapAPIListener());
	}
	
	private DynmapAPIListener()
	{
		super();
	}
	
	private static  MarkerSet getWeatherSet()
	{
		MarkerAPI markerAPI = api.getMarkerAPI();
		MarkerSet weatherSet = markerAPI.getMarkerSet(ModConfig.wxMarkerSetID);
		if (weatherSet == null)
		{
			weatherSet = markerAPI.createMarkerSet(ModConfig.wxMarkerSetID, "Weather Alerts", null, true);
		}
		
		return weatherSet;
	}
	
	public static void setupAlertArea(WirelessEmergencyAlert alert)
	{
		MarkerSet weatherSet = getWeatherSet();
		
		String label = "<b>" + alert.getName() + "</b><hr /><p>" + alert.getDescription() + "</p>";
		Coordinate[] coords = alert.getCoords().toArray(new Coordinate[] {});
		
		AreaMarker marker = weatherSet.createAreaMarker(alert.getId().toString(), label, true, getWorldName(), new double[] { coords[0].getX(), coords[1].getX(), coords[2].getX(), coords[3].getX() }, new double[] { coords[0].getZ(), coords[1].getZ(), coords[2].getZ(), coords[3].getZ() }, true);
		if (marker == null)
		{
			Main.logger.warn("Couldn't create an alert area on dynmap for " + alert.getId().toString() + ". Does the marker already exist?");
			return;
		}
		boolean successfullySetColor = false;
		for(String colorOption : ModConfig.alertColorOptions)
		{
			String[] options = colorOption.split("\\|");
			if (options.length < 3)
			{
				continue;
			}
			
			if (!alert.getName().toLowerCase().equals(options[0].toLowerCase()))
			{
				continue;
			}
			
			int lineColor;
			try {
				lineColor = Integer.parseInt(options[1], 16);
			} catch (Exception e) {
				continue;
			}
			
			int fillColor = -1;
			if ("transparent".equals(options[2].toLowerCase()))
			{
				marker.setFillStyle(0, 0x000000);
			}
			else
			{
				try {
					fillColor = Integer.parseInt(options[2], 16);
				} catch (Exception e) {
					continue;
				}
			}
			
			marker.setLineStyle(3, 1, lineColor);
			if (fillColor != -1)
			{
				marker.setFillStyle(0.2, fillColor);
			}
			
			successfullySetColor = true;
			break;
		}
		
		if (!successfullySetColor)
		{
			marker.setLineStyle(3, 1, 0x444444);
			marker.setFillStyle(0.2, 0x444444);
		}
	}
	
	public static void takeDownAlertArea(WirelessEmergencyAlert alert)
	{
		MarkerSet weatherSet = getWeatherSet();
		
		for(AreaMarker marker : weatherSet.getAreaMarkers())
		{
			if (marker.getMarkerID().toLowerCase().equals(alert.getId().toString().toLowerCase()))
			{
				marker.deleteMarker();
				return;
			}
		}
	}
}
