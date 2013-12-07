package me.mncat77.fictionworld.generator.blockpopulator;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

public class GrassPopulator extends BlockPopulator {
    
    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int centerX = (chunk.getX() << 4) + 7;
        int centerZ = (chunk.getZ() << 4) + 7;
        for(int x = 0; x <= 2 * 8; x++) {
            int rX = centerX + x - 8;
            for(int z = 0; z <= 2 * 8; z++) {
                int rZ = centerZ + z - 8;
                Random rand = getLocationRandom(world.getSeed(), centerX + x, centerZ + z);
                if(rand.nextInt(100) < 40)  {
                    Block start = new Location(world, rX, world.getHighestBlockYAt(rX, rZ), rZ).getBlock();
                    if(!start.getRelative(BlockFace.DOWN).getType().isSolid()) {
                        return;
                    }
                    int chance2 = 0;
                    int grasstype = 0;
                    switch(start.getBiome()) {
                        case FOREST:
                        case FOREST_HILLS:
                            chance2 = 10;
                            grasstype = 1;
                            break;
                        case ICE_PLAINS:
                        case ICE_MOUNTAINS:
                        case TAIGA:
                        case TAIGA_HILLS:
                            chance2 = 15;
                            grasstype = random.nextBoolean() ? 0 : 1;
                            break;
                        case PLAINS:
                            chance2 = 26;
                            break;
                        case JUNGLE:
                        case JUNGLE_HILLS:
                            chance2 = 32;
                            grasstype = random.nextBoolean() ? 0 : 1;
                            break;
                        case EXTREME_HILLS:
                            chance2 = 6;
                            break;
                        case SWAMPLAND:
                            chance2 = 13;
                            break;
                        case DESERT:
                            chance2 = 2;
                            grasstype = 2;
                    }
                    if(random.nextInt(100) < chance2) {
                        switch(grasstype) {
                            case 0:
                                start.setType(Material.getMaterial(31));
                                start.setData((byte)1);
                                break;
                            case 1:
                                start.setType(Material.getMaterial(31));
                                start.setData((byte)2);
                                break;
                            case 2:
                                start.setType(Material.DEAD_BUSH);
                        }
                        
                        
                    }
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
