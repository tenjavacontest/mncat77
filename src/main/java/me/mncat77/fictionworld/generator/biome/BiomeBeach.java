package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeBeach extends BiomeBase {

    public BiomeBeach(byte id) {
        super(id);
        this.topBlockId = 12;
        this.fillerBlockId = 12;
        this.baseHeight = 3;
        this.temperature = .64;
        this.rainfall = .63;
        this.biome = Biome.BEACH;
    }
    
}
