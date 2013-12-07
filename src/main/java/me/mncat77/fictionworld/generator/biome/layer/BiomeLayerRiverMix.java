package me.mncat77.fictionworld.generator.biome.layer;

import me.mncat77.fictionworld.generator.biome.BiomeBase;

public class BiomeLayerRiverMix extends BiomeLayer {

    private final BiomeLayer biomes;
    private final BiomeLayer rivers;

    public BiomeLayerRiverMix(long seed, BiomeLayer biomes, BiomeLayer rivers) {
        super(seed);
        this.biomes = biomes;
        this.rivers = rivers;
    }

    public byte[] getValues(int realX, int realZ, int width, int length) {
        byte[] biomeValues = this.biomes.getValues(realX, realZ, width, length);
        byte[] riverValues = this.rivers.getValues(realX, realZ, width, length);
        byte[] values = new byte[width * length];

        for (int i = 0; i < width * length; i++) {
            if (biomeValues[i] == BiomeBase.OCEAN.id) {
                values[i] = biomeValues[i];
            } else if (riverValues[i] >= 0) {
                if (biomeValues[i] == BiomeBase.ICE_PLAINS.id) {
                    values[i] = BiomeBase.FROZEN_RIVER.id;
                } else {
                    values[i] = riverValues[i];
                }
            } else {
                values[i] = biomeValues[i];
            }
        }

        return values;
    }
}