package me.mncat77.fictionworld.generator.biome.layer;

public class BiomeLayerZoomFuzzy extends BiomeLayer {
    
    public BiomeLayerZoomFuzzy(long i, BiomeLayer parent) {
        super(i);
        this.parent = parent;
    }
    
    public byte[] getValues(int realX, int realZ, int width, int length) {
        int realXOffset = realX >> 1;
        int realZOffset = realZ >> 1;
        int widthOffset = (width >> 1) + 3;
        int lengthOffset = (length >> 1) + 3;
        byte[] parentValues = this.parent.getValues(realXOffset, realZOffset, widthOffset, lengthOffset);
        byte[] values = new byte[widthOffset * 2 * lengthOffset * 2];
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
                
                values[mOffset] = parentValueSelf;
                values[mOffset++ + widthOffset2] = this.choose(parentValueSelf, parentValueRight);
                values[mOffset] = this.choose(parentValueSelf, parentValueLower);
                values[mOffset++ + widthOffset2] = this.choose(parentValueSelf, parentValueLower, parentValueRight, parentValueLowerRight);
                parentValueSelf = parentValueLower;
                parentValueRight = parentValueLowerRight;
            }
        }
        
        byte[] finalValues = new byte[width * length];
        
        for (int zIterator = 0; zIterator < length; zIterator++) {
            System.arraycopy(values, (zIterator + (realZ & 1)) * (widthOffset << 1) + (realX & 1), finalValues, zIterator * width, width);
        }
        
        return finalValues;
    }
    
    protected byte choose(byte option1, byte option2) {
        return this.nextByte(2) == 0 ? option1 : option2;
    }
    
    protected byte choose(byte option1, byte option2, byte option3, byte option4) {
        byte r = this.nextByte(4);
        
        return r == 0 ? option1 : (r == 1 ? option2 : (r == 2 ? option3 : option4));
    }
}