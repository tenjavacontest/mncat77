package me.mncat77.fictionworld.generator.biome.layer;

import me.mncat77.fictionworld.generator.biome.BiomeBase;

public class BiomeLayerLakes extends BiomeLayer {

    public BiomeLayerLakes(long seed, BiomeLayer parent) {
        super(seed);
        this.parent = parent;
    }

    public byte[] getValues(int realX, int realZ, int width, int length) {
        byte[] parentValues = this.parent.getValues(realX - 1, realZ - 1, width + 2, length + 2);
        byte[] values = new byte[width * length];

        for (int z = 0; z < length; z++) {
            for (int x = 0; x < width; x++) {
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                byte parentValue = parentValues[x + 1 + (z + 1) * (width + 2)];

                if ((parentValue != BiomeBase.SWAMPLAND.id || this.nextByte(6) != 0) && (parentValue != BiomeBase.JUNGLE.id && parentValue != BiomeBase.JUNGLE_HILLS.id || this.nextByte(8) != 0)) {
                    values[x + z * width] = parentValue;
                } else {
                    values[x + z * width] = BiomeBase.RIVER.id;
                }
            }
        }

        return values;
    }
}
