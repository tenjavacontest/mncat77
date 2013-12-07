package me.mncat77.fictionworld.generator.biome.layer;

public class BiomeLayerSmooth extends BiomeLayer {

    public BiomeLayerSmooth(long seed, BiomeLayer parent) {
        super(seed);
        super.parent = parent;
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

                if (parentValue1 == parentValue2 && parentValue3 == parentValue4) {
                    this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                    if (this.nextByte(2) == 0) {
                        parentValue5 = parentValue1;
                    } else {
                        parentValue5 = parentValue3;
                    }
                } else {
                    if (parentValue1 == parentValue2) {
                        parentValue5 = parentValue1;
                    }

                    if (parentValue3 == parentValue4) {
                        parentValue5 = parentValue3;
                    }
                }

                values[x + z * width] = parentValue5;
            }
        }

        return values;
    }
}
