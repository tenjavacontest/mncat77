package me.mncat77.fictionworld.generator.biome.layer;

import me.mncat77.fictionworld.generator.biome.BiomeBase;

public class BiomeLayerAddBlocksToIslands extends BiomeLayer {

    public BiomeLayerAddBlocksToIslands(long seed, BiomeLayer genlayer) {
        super(seed);
        this.parent = genlayer;
    }

    public byte[] getValues(int realX, int realZ, int width, int length) {
        int realXOffset = realX - 1;
        int realZOffset = realZ - 1;
        int widthOffset = width + 2;
        int lengthOffset = length + 2;
        byte[] parentValues = this.parent.getValues(realXOffset, realZOffset, widthOffset, lengthOffset);
        byte[] buffer = new byte[width * length];

        for (int z = 0; z < length; ++z) {
            for (int x = 0; x < width; ++x) {
                byte parentValue1 = parentValues[x + z * widthOffset];
                byte parentValue2 = parentValues[x + 2 + z * widthOffset];
                byte parentValue3 = parentValues[x + (z + 2) * widthOffset];
                byte parentValue4 = parentValues[x + 2 + (z + 2) * widthOffset];
                byte parentValue5 = parentValues[x + 1 + (z + 1) * widthOffset];

                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                if (parentValue5 == 0 && (parentValue1 != 0 || parentValue2 != 0 || parentValue3 != 0 || parentValue4 != 0)) {
                    byte l3 = 1;
                    byte i4 = 1;

                    if (parentValue1 != 0 && this.nextByte(l3++) == 0) {
                        i4 = parentValue1;
                    }

                    if (parentValue2 != 0 && this.nextByte(l3++) == 0) {
                        i4 = parentValue2;
                    }

                    if (parentValue3 != 0 && this.nextByte(l3++) == 0) {
                        i4 = parentValue3;
                    }

                    if (parentValue4 != 0 && this.nextByte(l3++) == 0) {
                        i4 = parentValue4;
                    }

                    if (this.nextByte(3) == 0) {
                        buffer[x + z * width] = i4;
                    } else if (i4 == BiomeBase.ICE_PLAINS.id) {
                        buffer[x + z * width] = BiomeBase.FROZEN_OCEAN.id;
                    } else {
                        buffer[x + z * width] = 0;
                    }
                } else if (parentValue5 > 0 && (parentValue1 == 0 || parentValue2 == 0 || parentValue3 == 0 || parentValue4 == 0)) {
                    if (this.nextByte(5) == 0) {
                        if (parentValue5 == BiomeBase.ICE_PLAINS.id) {
                            buffer[x + z * width] = BiomeBase.FROZEN_OCEAN.id;
                        } else {
                            buffer[x + z * width] = 0;
                        }
                    } else {
                        buffer[x + z * width] = parentValue5;
                    }
                } else {
                    buffer[x + z * width] = parentValue5;
                }
            }
        }

        return buffer;
    }
}
