package com.mesabrook.ib.util.handlers;

import java.util.Random;

import com.mesabrook.ib.init.ModBlocks;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldGenBismuth implements IWorldGenerator
{
	WorldGenMinable bismuth;
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		if (world.provider.getDimension() == 0) 
		{
			generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}
	
	private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		bismuth = new WorldGenMinable(ModBlocks.BISMUTH_ORE.getDefaultState(), 16);
		addOreSpawn(bismuth, world, random, chunkX * 16, chunkZ * 16, 12, 30, 100);
	}
	
	private void addOreSpawn(WorldGenMinable wgm, World world, Random random, int x, int z, int vpc, int minY, int maxY) 
	{
		int rangeY = maxY - minY;
		for (int i = 0; i < vpc; i++) 
		{
			BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(rangeY), z + random.nextInt(16));
			wgm.generate(world, random, pos);
		}
	}
}