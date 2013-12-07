package me.mncat77.fictionworld.generator.biome.layer;

import me.mncat77.fictionworld.generator.biome.BiomeBase;

public class BiomeLayerRegionHills extends BiomeLayer {
    
    public BiomeLayerRegionHills(long seed, BiomeLayer parent) {
        super(seed);
        this.parent = parent;
    }
    
    public byte[] getValues(int realX, int realZ, int width, int length) {
        byte[] parentValues = this.parent.getValues(realX - 1, realZ - 1, width + 2, length + 2);
        byte[] values = new byte[width * length];
        
        for (int z = 0; z < length; z++) {
            for (int x = 0; x < width; x++) {
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                byte baseParentValue = parentValues[x + 1 + (z + 1) * (width + 2)];
                
                if (this.nextByte(3) == 0) {
                    byte l1 = baseParentValue;
                    
                    if (baseParentValue == BiomeBase.DESERT.id) {
                        l1 = BiomeBase.DESERT_HILLS.id;
                    } else if (baseParentValue == BiomeBase.FOREST.id) {
                        l1 = BiomeBase.FOREST_HILLS.id;
                    } else if (baseParentValue == BiomeBase.TAIGA.id) {
                        l1 = BiomeBase.TAIGA_HILLS.id;
                    } else if (baseParentValue == BiomeBase.PLAINS.id) {
                        l1 = BiomeBase.FOREST.id;
                    } else if (baseParentValue == BiomeBase.ICE_PLAINS.id) {
                        l1 = BiomeBase.ICE_MOUNTAINS.id;
                    } else if (baseParentValue == BiomeBase.JUNGLE.id) {
                        l1 = BiomeBase.JUNGLE_HILLS.id;
                    }
                    
                    if (l1 == baseParentValue) {
                        values[x + z * width] = baseParentValue;
                    } else {
                        int parentValue2 = parentValues[x + 1 + (z + 1 - 1) * (width + 2)];
                        int parentValue3 = parentValues[x + 1 + 1 + (z + 1) * (width + 2)];
                        int parentValue4 = parentValues[x + 1 - 1 + (z + 1) * (width + 2)];
                        int parentValue5 = parentValues[x + 1 + (z + 1 + 1) * (width + 2)];
                        
                        if (parentValue2 == baseParentValue && parentValue3 == baseParentValue && parentValue4 == baseParentValue && parentValue5 == baseParentValue) {
                            values[x + z * width] = l1;
                        } else {
                            values[x + z * width] = baseParentValue;
                        }
                    }
                } else {
                    values[x + z * width] = baseParentValue;
                }
            }
        }
        
        return values;
    }
}