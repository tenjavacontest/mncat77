package me.mncat77.fictionworld.generator.biome.layer;

public class BiomeLayerRiverInit extends BiomeLayer {

    public BiomeLayerRiverInit(long seed, BiomeLayer layer) {
        super(seed);
        this.parent = layer;
    }

    public byte[] getValues(int realX, int realZ, int width, int length) {
        byte[] parentValues = this.parent.getValues(realX, realZ, width, length);
        byte[] values = new byte[width * length];

        for (int z = 0; z < length; z++) {
            for (int x = 0; x < width; x++) {
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                values[x + z * width] = parentValues[x + z * width] > 0 ? (byte)(this.nextByte(2) + 2) : (byte)0;
            }
        }

        return values;
    }
}