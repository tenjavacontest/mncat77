package me.mncat77.fictionworld.generator;

import java.util.Random;
import me.mncat77.fictionworld.generator.biome.BiomeBase;
import me.mncat77.fictionworld.generator.biome.layer.BiomeLayer;
import me.mncat77.fictionworld.world.FictionWorldAttributes;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class FictionWorldChunkGenerator extends ChunkGenerator {
    
    private final double[] distanceFactorGrid = new double[289];
    
    private final long seed;
    
    private final BiomeLayer layer;
    
    private final SimplexOctaveGenerator noise;
    private final double noiseAmplitude;
    private final double noiseFrequency;
    
    private final int waterLevel;
    
    
    public FictionWorldChunkGenerator(long seed, FictionWorldAttributes attributes) {
        //Create distance altitude multiplication matrix
        double g = 0;
        double distanceGridAddition = attributes.getDistanceGridAddition();
        for(int x = -8; x < 9; x++) {
            for(int z = -8; z < 9; z++) {
                g += 1.0 / Math.sqrt(x * x + z * z + distanceGridAddition);
            }
        }
        int i = 0;
        for(int x = -8; x < 9; x++) {
            for(int z = -8; z < 9; z++) {
                this.distanceFactorGrid[i] = (1.0 / Math.sqrt( x * x + z * z + distanceGridAddition)) / g;
                i++;
            }
        }
        
        this.seed = seed;
        
        this.noise = new SimplexOctaveGenerator(this.seed, 8);
        this.noise.setScale(1.0D / 64.0D);
        this.noiseAmplitude = attributes.getNoiseAmplitude();
        this.noiseFrequency = attributes.getNoiseFrequency();
        
        this.layer = BiomeLayer.getLayer(attributes.getAverageRainfall(), attributes.getAverageTemperature(), attributes.getHostility(), this.seed);
        
        this.waterLevel = attributes.getWaterLevel();
    }
    
    //This method is partially based on NMS code
    @Override
    public byte[][] generateBlockSections(World world, Random rand, int chunkX, int chunkZ, BiomeGrid biome) {
        int chunkRealX = chunkX * 16;
        int chunkRealZ = chunkZ * 16;
        byte[] values = layer.getValues(chunkRealX, chunkRealZ, 16, 16);
        byte[][] chunk = new byte[16][];
        byte[] altitudes = new byte[289];
        int index2D = 0;
        byte[] biomes = this.layer.getValues(chunkRealX - 8, chunkRealZ - 8, 34, 34);
        for (int x = 0; x < 17; ++x) {
            int realX = chunkRealX + x;
            for (int z = 0; z < 17; ++z) {
                double height = 0;
                for (int neighbourOffsetX = 0; neighbourOffsetX < 17; neighbourOffsetX++) {
                    for (int neighbourOffsetZ = 0; neighbourOffsetZ < 17; neighbourOffsetZ++) {
                        height += distanceFactorGrid[neighbourOffsetX * 17 + neighbourOffsetZ] *
                                (waterLevel + BiomeBase.byId[biomes[(z + neighbourOffsetZ) * 34 + x + neighbourOffsetX]].baseHeight);
                    }
                }
                
                double n = this.noise.noise(2 * realX, (chunkRealZ + z) * 2, this.noiseFrequency, this.noiseAmplitude);
                
                //NMS start
                if (n < 0.0D) {
                    n = -n * 0.3D;
                }
                
                n = n * 3.0D - 2.0D;
                if (n < 0.0D) {
                    n /= 2.0D;
                    if (n < -1.0D) {
                        n = -1.0D;
                    }
                    
                    n /= 1.4D;
                    n /= 2.0D;
                } else {
                    if (n > 1.0D) {
                        n = 1.0D;
                    }
                    
                    n /= 8.0D;
                }
                n++;
                altitudes[index2D] = (byte)Math.round(height + 12.0D * n);
                index2D++;
                //NMS stop
            }
        }
        for (int x = 0; x < 16; x++) {
            int xIndex = x * 17;
            for (int z = 0; z < 16; z++) {
                BiomeBase biomeBase = BiomeBase.byId[values[z * 16 + x]];
                byte top = biomeBase.topBlockId;
                byte filler = biomeBase.fillerBlockId;
                biome.setBiome(x, z, biomeBase.getBukkitBiome());
                int m = altitudes[xIndex + z];
                int y = 0;
                for (; y < rand.nextInt(4) + 2; y++) {
                    setBlock(x, y, z, chunk, (byte)7);
                }
                for (; y < m - rand.nextInt(4) - 2; y++) {
                    setBlock(x, y, z, chunk, (byte)1);
                }
                for (; y < m; y++) {
                    setBlock(x, y, z, chunk, filler);
                }
                if (m > waterLevel) {
                    setBlock(x, m, z, chunk, top);
                } else {
                    setBlock(x, m, z, chunk, filler);
                    for(y = m + 1; y < waterLevel; y++) {
                        setBlock(x, y, z, chunk,  (byte)8);
                    }
                    setBlock(x, waterLevel, z, chunk,  biomeBase.temperature <= .3 ? (byte)79 : (byte)8);
                }
                
            }
        }
        
        return chunk;
    }
    
    //These are common methods, I did not create them
    public static void setBlock(int x, int y, int z, byte[][] chunk, byte b) {
        //if the Block section the block is in hasn't been used yet, allocate it
        if (chunk[y >> 4] == null) {
            chunk[y >> 4] = new byte[16 * 16 * 16];
        }
        if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0)) {
            return;
        }
        try {
            chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = b;
        } catch (Exception e) {
            // do nothing
        }
    }
    
    public static byte getBlock(int x, int y, int z, byte[][] chunk) {
        //if the Block section the block is in hasn't been used yet, allocate it
        if (chunk[y >> 4] == null) {
            return 0;
        } //block is air as it hasnt been allocated
        if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0)) {
            return 0;
        }
        try {
            return chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
}
