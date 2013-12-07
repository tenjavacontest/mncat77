package me.mncat77.fictionworld.generator.blockpopulator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class SnowPopulator extends BlockPopulator {
    
    private static final Set<Biome> coldBiomes = new HashSet<Biome>(){{add(Biome.ICE_MOUNTAINS);
    add(Biome.ICE_PLAINS); add(Biome.TAIGA); add(Biome.TAIGA_HILLS); add(Biome.FROZEN_RIVER);
    add(Biome.FROZEN_OCEAN);}};
    
    private final SimplexOctaveGenerator noise;
    
    public SnowPopulator() {
        this.noise = new SimplexOctaveGenerator(1337, 4);
        this.noise.setScale(1/16.0);
    }
    
    @Override
    public void populate(World world, Random random, Chunk source) {
        int startX = source.getX() << 4;
        int startZ = source.getZ() << 4;
        for(int x = 0; x < 16; x++) {
            int realX = startX + x;
            for(int z = 0; z < 16; z++) {
                int realZ = startZ + z;
                if(coldBiomes.contains(world.getBiome(realX, realZ))) {
                    Block block = new Location(world, realX, world.getHighestBlockYAt(realX, realZ), realZ).getBlock();
                    if(block.getRelative(BlockFace.DOWN).getType() != Material.ICE) {
                        block.setType(Material.SNOW);
                        block.setData((byte)(2 * this.noise.noise(realX, realZ, .1, .1) + 2));
                    }
                }
            }
        }
    }

}
