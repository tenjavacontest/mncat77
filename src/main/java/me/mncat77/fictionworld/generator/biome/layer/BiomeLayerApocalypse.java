package me.mncat77.fictionworld.generator.biome.layer;

public class BiomeLayerApocalypse extends BiomeLayer {
    
    private final byte apoValue;
    
    
    public BiomeLayerApocalypse(long seed, double rainfall, double temperature) {
        super(seed);
        if(rainfall > .8) {
            if(temperature < .2) {
                apoValue = 3;
            } else {
                apoValue = 0;
            }
        } else if(temperature > .8) {
            apoValue = 6;
        }
        else {
            apoValue = 9;
        }
    }

    @Override
    public byte[] getValues(int x, int z, int width, int length) {
        byte[] values = new byte[width*length];
        for(int i = 0; i < width*length; i++) {
            values[i] = nextByte(2) == 0 ? nextByte(3) : 0;
        }
        return values;
    }
    
}
