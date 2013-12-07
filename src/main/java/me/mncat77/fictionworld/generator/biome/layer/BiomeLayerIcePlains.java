package me.mncat77.fictionworld.generator.biome.layer;

import me.mncat77.fictionworld.generator.biome.BiomeBase;

public class BiomeLayerIcePlains extends BiomeLayer {
    
    public BiomeLayerIcePlains(long seed, BiomeLayer parent) {
        super(seed);
        this.parent = parent;
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
                byte parentValue = parentValues[x + 1 + (z + 1) * widthOffset];
                
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                if (parentValue == 0) {
                    buffer[x + z * width] = 0;
                } else {
                    byte r5 = this.nextByte(5);
                    
                    if (r5 == 0) {
                        r5 = BiomeBase.ICE_PLAINS.id;
                    } else {
                        r5 = 1;
                    }
                    
                    buffer[x + z * width] = r5;
                }
            }
        }
        
        return buffer;
    }
}