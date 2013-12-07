package me.mncat77.fictionworld.generator.biome.layer;

import me.mncat77.fictionworld.generator.biome.BiomeBase;

public class BiomeLayerBiome extends BiomeLayer {

    private final BiomeBase[] biomes;

    public BiomeLayerBiome(long seed, BiomeLayer parent) {
        super(seed);
        this.biomes = new BiomeBase[] { BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.PLAINS, BiomeBase.TAIGA, BiomeBase.JUNGLE};
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
                    values[x + z * width] = this.biomes[this.nextByte(this.biomes.length)].id;
                } else {
                    byte randId = this.biomes[this.nextByte(this.biomes.length)].id;

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