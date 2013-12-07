package me.mncat77.fictionworld.generator.blockpopulator;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

public class FirePopulator extends BlockPopulator {
    
    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int centerX = (chunk.getX() << 4) + 7;
        int centerZ = (chunk.getZ() << 4) + 7;
        for(int x = 0; x <= 2 * 8; x++) {
            int rX = centerX + x - 8;
            for(int z = 0; z <= 2 * 8; z++) {
                int rZ = centerZ + z - 8;
                Random rand = getLocationRandom(world.getSeed(), centerX + x, centerZ + z);
                if(rand.nextInt(100) < 3)  {
                    Block start = new Location(world, rX, world.getHighestBlockYAt(rX, rZ), rZ).getBlock();
                    if(!start.getRelative(BlockFace.DOWN).isLiquid()) {
                        return;
                    }
                    start.setType(Material.FIRE);
                }
            }
            
        }
    }
    
    private static Random getLocationRandom(long seed, int realX, int realZ) {
        seed *= seed * 6364136223846793005L + 1442695040888963407L;
        seed += realX;
        seed *= seed * 6364136223846793005L + 1442695040888963407L;
        seed += realZ;
        seed *= seed * 6364136223846793005L + 1442695040888963407L;
        seed += realX;
        seed *= seed * 6364136223846793005L + 1442695040888963407L;
        seed += realZ;
        return new Random(seed);
    }

}
