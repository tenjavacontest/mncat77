package me.mncat77.fictionworld.generator.biome.layer;

import me.mncat77.fictionworld.generator.biome.BiomeBase;

public class BiomeLayerRiver extends BiomeLayer {

    public BiomeLayerRiver(long seed, BiomeLayer layer) {
        super(seed);
        super.parent = layer;
    }

    public byte[] getValues(int realX, int realZ, int width, int length) {
        int realXOffset = realX - 1;
        int realZOffset = realZ - 1;
        int widthOffset = width + 2;
        int lengthOffset = length + 2;
        byte[] parentValues = this.parent.getValues(realXOffset, realZOffset, widthOffset, lengthOffset);
        byte[] values = new byte[width * length];

        for (int z = 0; z < length; z++) {
            for (int x = 0; x < width; x++) {
                byte parentValue1 = parentValues[x + (z + 1) * widthOffset];
                byte parentValue2 = parentValues[x + 2 + (z + 1) * widthOffset];
                byte parentValue3 = parentValues[x + 1 + z * widthOffset];
                byte parentValue4 = parentValues[x + 1 + (z + 2) * widthOffset];
                byte parentValue5 = parentValues[x + 1 + (z + 1) * widthOffset];

                if (parentValue5 != 0 && parentValue1 != 0 && parentValue2 != 0 && parentValue3 != 0 && parentValue4 != 0 && parentValue5 == parentValue1 && parentValue5 == parentValue3 && parentValue5 == parentValue2 && parentValue5 == parentValue4) {
                    values[x + z * width] = -1;
                } else {
                    values[x + z * width] = BiomeBase.RIVER.id;
                }
            }
        }

        return values;
    }
}