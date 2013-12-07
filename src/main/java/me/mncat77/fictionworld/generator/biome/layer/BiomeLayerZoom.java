package me.mncat77.fictionworld.generator.biome.layer;

public class BiomeLayerZoom extends BiomeLayer {
    
    public BiomeLayerZoom(long seed, BiomeLayer layer) {
        super(seed);
        super.parent = layer;
    }
    
    public byte[] getValues(int realX, int realZ, int width, int length) {
        int realXOffset = realX >> 1;
        int realZOffset = realZ >> 1;
        int widthOffset = (width >> 1) + 3;
        int lengthOffset = (length >> 1) + 3;
        byte[] parentValues = this.parent.getValues(realXOffset, realZOffset, widthOffset, lengthOffset);
        byte[] buffer = new byte[widthOffset * 2 * lengthOffset * 2];
        int widthOffset2 = widthOffset << 1;
        
        int zOffset;
        
        for (int z = 0; z < lengthOffset - 1; z++) {
            zOffset = z << 1;
            int mOffset = zOffset * widthOffset2;
            byte parentValueSelf = parentValues[z * widthOffset];
            byte parentValueRight = parentValues[(z + 1) * widthOffset];
            
            for (int x = 0; x < widthOffset - 1; x++) {
                this.initChunkSeed((long) (x + realXOffset << 1), (long) (z + realZOffset << 1));
                byte parentValueLower = parentValues[x + 1 + z * widthOffset];
                byte parentValueLowerRight = parentValues[x + 1 + (z + 1) * widthOffset];
                
                buffer[mOffset] = parentValueSelf;
                buffer[mOffset++ + widthOffset2] = this.choose(parentValueSelf, parentValueRight);
                buffer[mOffset] = this.choose(parentValueSelf, parentValueLower);
                buffer[mOffset++ + widthOffset2] = this.modeOrRandom(parentValueSelf, parentValueLower, parentValueRight, parentValueLowerRight);
                parentValueSelf = parentValueLower;
                parentValueRight = parentValueLowerRight;
            }
        }
        
        byte[] finalValues = new byte[width * length];
        
        for (zOffset = 0; zOffset < length; ++zOffset) {
            System.arraycopy(buffer, (zOffset + (realZ & 1)) * (widthOffset << 1) + (realX & 1), finalValues, zOffset * width, width);
        }
        
        return finalValues;
    }
    
    protected byte choose(byte option1, byte option2) {
        return this.nextByte(2) == 0 ? option1 : option2;
    }
    
    protected byte modeOrRandom(byte option1, byte option2, byte option3, byte option4) {
        if (option2 == option3 && option3 == option4) {
            return option2;
        } else if (option1 == option2 && option1 == option3) {
            return option1;
        } else if (option1 == option2 && option1 == option4) {
            return option1;
        } else if (option1 == option3 && option1 == option4) {
            return option1;
        } else if (option1 == option2 && option3 != option4) {
            return option1;
        } else if (option1 == option3 && option2 != option4) {
            return option1;
        } else if (option1 == option4 && option2 != option3) {
            return option1;
        } else if (option2 == option1 && option3 != option4) {
            return option2;
        } else if (option2 == option3 && option1 != option4) {
            return option2;
        } else if (option2 == option4 && option1 != option3) {
            return option2;
        } else if (option3 == option1 && option2 != option4) {
            return option3;
        } else if (option3 == option2 && option1 != option4) {
            return option3;
        } else if (option3 == option4 && option1 != option2) {
            return option3;
        } else if (option4 == option1 && option2 != option3) {
            return option3;
        } else if (option4 == option2 && option1 != option3) {
            return option3;
        } else if (option4 == option3 && option1 != option2) {
            return option3;
        } else {
            byte b = this.nextByte(4);
            
            return b == 0 ? option1 : (b == 1 ? option2 : (b == 2 ? option3 : option4));
        }
    }
    
    public static BiomeLayer magnify(long seed, BiomeLayer genlayer, int times) {
        BiomeLayer layer = genlayer;
        
        for (int i = 0; i < times; ++i) {
            layer = new BiomeLayerZoom(seed + (long) i, layer);
        }
        
        return layer;
    }
    
}
