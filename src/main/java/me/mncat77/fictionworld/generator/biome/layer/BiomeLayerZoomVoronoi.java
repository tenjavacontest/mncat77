package me.mncat77.fictionworld.generator.biome.layer;

public class BiomeLayerZoomVoronoi extends BiomeLayer {
    
    public BiomeLayerZoomVoronoi(long seed, BiomeLayer parent) {
        super(seed);
        super.parent = parent;
    }
    
    public byte[] getValues(int realX, int realZ, int width, int length) {
        realX -= 2;
        realZ -= 2;
        byte shifter = 2;
        int oneShift2 = 1 << shifter;
        int realXShifted = realX >> shifter;
        int realZShifted = realZ >> shifter;
        int widthShifted = (width >> shifter) + 3;
        int lengthShifted = (length >> shifter) + 3;
        byte[] parentValues = this.parent.getValues(realXShifted, realZShifted, widthShifted, lengthShifted);
        int widthShifted2 = widthShifted << shifter;
        int lengthShifted2 = lengthShifted << shifter;
        byte[] values = new byte[widthShifted2 * lengthShifted2];
        
        byte parentValueSelf;
        
        for (int z = 0; z < lengthShifted - 1; z++) {
            parentValueSelf = parentValues[z * widthShifted];
            byte parentValueRight = parentValues[z * widthShifted];
            
            for (int x = 0; x < widthShifted - 1; x++) {
                double portion = (double) oneShift2 * 0.9D;
                
                this.initChunkSeed((long) (x + realXShifted << shifter), (long) (z + realZShifted << shifter));
                double rand1 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion;
                double rand2 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion;
                
                this.initChunkSeed((long) (x + realXShifted + 1 << shifter), (long) (z + realZShifted << shifter));
                double rand3 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion + (double) oneShift2;
                double rand4 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion;
                
                this.initChunkSeed((long) (x + realXShifted << shifter), (long) (z + realZShifted + 1 << shifter));
                double rand5 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion;
                double rand6 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion + (double) oneShift2;
                
                this.initChunkSeed((long) (x + realXShifted + 1 << shifter), (long) (z + realZShifted + 1 << shifter));
                double rand7 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion + (double) oneShift2;
                double rand8 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion + (double) oneShift2;
                byte parentValueLower = parentValues[x + 1 + z * widthShifted];
                byte parentValueLowerRight = parentValues[x + 1 + (z + 1) * widthShifted];
                
                for (int i1 = 0; i1 < oneShift2; i1++) {
                    int a = ((z << shifter) + i1) * widthShifted2 + (x << shifter);
                    
                    for (int i2 = 0; i2 < oneShift2; i2++) {
                        double combRand12 = ((double) i1 - rand2) * ((double) i1 - rand2) + ((double) i2 - rand1) * ((double) i2 - rand1);
                        double combRand34 = ((double) i1 - rand4) * ((double) i1 - rand4) + ((double) i2 - rand3) * ((double) i2 - rand3);
                        double combRand56 = ((double) i1 - rand6) * ((double) i1 - rand6) + ((double) i2 - rand5) * ((double) i2 - rand5);
                        double combRand78 = ((double) i1 - rand8) * ((double) i1 - rand8) + ((double) i2 - rand7) * ((double) i2 - rand7);
                        
                        if (combRand12 < combRand34 && combRand12 < combRand56 && combRand12 < combRand78) {
                            values[a++] = parentValueSelf;
                        } else if (combRand34 < combRand12 && combRand34 < combRand56 && combRand34 < combRand78) {
                            values[a++] = parentValueLower;
                        } else if (combRand56 < combRand12 && combRand56 < combRand34 && combRand56 < combRand78) {
                            values[a++] = parentValueRight;
                        } else {
                            values[a++] = parentValueLowerRight;
                        }
                    }
                }
                
                parentValueSelf = parentValueLower;
                parentValueRight = parentValueLowerRight;
            }
        }
        
        byte[] finalValues = new byte[width * length];
        
        for (parentValueSelf = 0; parentValueSelf < length; ++parentValueSelf) {
            System.arraycopy(values, (parentValueSelf + (realZ & oneShift2 - 1)) * (widthShifted << shifter) + (realX & oneShift2 - 1), finalValues, parentValueSelf * width, width);
        }
        
        return finalValues;
    }
    
    private int nextInt(int max) {
        int var2 = (int)((this.chunkSeed >> 24) % (long)max);
        
        if (var2 < 0) {
            var2 += max;
        }
        
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.seed;
        return var2;
    }
}