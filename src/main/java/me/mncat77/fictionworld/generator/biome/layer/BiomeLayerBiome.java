package me.mncat77.fictionworld.generator.biome.layer;

import me.mncat77.fictionworld.generator.biome.BiomeBase;

public class BiomeLayerBiome extends BiomeLayer {
    
    private final BiomeBase[] biomes;
    
    private final double rainfall;
    private final double temperature;
    
    public BiomeLayerBiome(long seed, BiomeLayer parent, double rainfall, double temperature) {
        super(seed);
        if(temperature < .3) {
            this.biomes = new BiomeBase[] { BiomeBase.TAIGA};
        } else if(temperature > .7) {
            this.biomes = new BiomeBase[] { BiomeBase.DESERT};
        } else {
            this.biomes = new BiomeBase[] { BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.PLAINS, BiomeBase.TAIGA, BiomeBase.JUNGLE};
        }
        this.rainfall = rainfall;
        this.temperature = temperature;
        this.parent = parent;
    }
    
    public byte[] getValues(int realX, int realZ, int width, int length) {
        byte[] parentValues = this.parent.getValues(realX, realZ, width, length);
        byte[] values = new byte[width * length];
        
        for (int z = 0; z < length; z++) {
            for (int x = 0; x < width; x++) {
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                byte parentValue = parentValues[x + z * width];
                
                if (parentValue == 0) {
                    values[x + z * width] = 0;
                } else if (parentValue == 1) {
                    BiomeBase b = this.biomes[this.nextByte(this.biomes.length)];
                    while((Math.abs(b.rainfall - rainfall) > .3) || (Math.abs(b.temperature - temperature) > .3)) {
                        b = this.biomes[this.nextByte(this.biomes.length)];
                    }
                    values[x + z * width] = this.biomes[this.nextByte(this.biomes.length)].id;
                } else {
                    BiomeBase b = this.biomes[this.nextByte(this.biomes.length)];
                    while((Math.abs(b.rainfall - rainfall) > .3) || (Math.abs(b.temperature - temperature) > .3)) {
                        b = this.biomes[this.nextByte(this.biomes.length)];
                    }
                    byte randId = b.id;
                    
                    if (randId == BiomeBase.TAIGA.id) {
                        values[x + z * width] = randId;
                    } else {
                        values[x + z * width] = BiomeBase.ICE_PLAINS.id;
                    }
                }
            }
        }
        
        return values;
    }
}