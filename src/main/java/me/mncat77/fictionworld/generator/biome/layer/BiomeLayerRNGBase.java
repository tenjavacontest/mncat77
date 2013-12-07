package me.mncat77.fictionworld.generator.biome.layer;

public class BiomeLayerRNGBase extends BiomeLayer {
    
    public BiomeLayerRNGBase(long seed) {
        super(seed);
    }
    
    public byte[] getValues(int realX, int realZ, int width, int length) {
        byte[] values = new byte[width * length];
        
        for (int x = 0; x < length; x++) {
            for (int z = 0; z < width; z++) {
                this.initChunkSeed((long) (realX + z), (long) (realZ + x));
                values[z + x * width] = this.nextByte(10) == 0 ? (byte)1 : 0;
            }
        }
        
        if (realX > -width && realX <= 0 && realZ > -length && realZ <= 0) {
            values[-realX + -realZ * width] = 1;
        }
        
        return values;
    }

}