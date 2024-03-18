package com.mesabrook.ib.init;

import com.mesabrook.ib.blocks.te.TileEntityFluidMeter;
import com.mesabrook.ib.blocks.te.TileEntitySoundEmitter;
import com.mesabrook.ib.blocks.te.TileEntityWirelessChargingPad;

import mcjty.xnet.XNet;
import mcjty.xnet.api.channels.IConnectable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class XNetAPI {
	public static void registerConnectables()
	{
		XNet.xNetApi.registerConnectable(new WirelessChargingPadConnectable());
		XNet.xNetApi.registerConnectable(new FluidMeterConnectable());
		XNet.xNetApi.registerConnectable(new SoundEmitterConnectable());
	}
	
	public static class WirelessChargingPadConnectable implements IConnectable
	{
		@Override
		public ConnectResult canConnect(IBlockAccess world, BlockPos connectorPos, BlockPos blockPos, TileEntity te,
				EnumFacing facing) {
			if (te instanceof TileEntityWirelessChargingPad)
			{
				return facing.getOpposite() == EnumFacing.DOWN ? ConnectResult.YES : ConnectResult.NO;
			}
			
			return ConnectResult.DEFAULT;
		}
	}
	
	public static class FluidMeterConnectable implements IConnectable
	{
		@Override
		public ConnectResult canConnect(IBlockAccess world, BlockPos connectorPos, BlockPos blockPos, TileEntity te,
				EnumFacing facing) {
			if (te instanceof TileEntityFluidMeter)
			{
				return (facing == EnumFacing.UP || facing == EnumFacing.DOWN) ? ConnectResult.YES : ConnectResult.NO;
			}
			
			return ConnectResult.DEFAULT;
		}
	}
	
	public static class SoundEmitterConnectable implements IConnectable
	{
		@Override
		public ConnectResult canConnect(IBlockAccess world, BlockPos connectorPos, BlockPos blockPos, TileEntity te, EnumFacing facing)
		{
			if (te instanceof TileEntitySoundEmitter)
			{
				return facing != null ? ConnectResult.YES : ConnectResult.NO;
			}
			return ConnectResult.DEFAULT;
		}
	}
}
