package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeDesert extends BiomeBase {
    
    public BiomeDesert(byte id) {
        super(id);
        this.topBlockId = 12;
        this.fillerBlockId = 12;
        this.baseHeight = 9;
        this.temperature = .8D;
        this.rainfall = .1D;
        this.biome = Biome.DESERT;
    }
    
}
