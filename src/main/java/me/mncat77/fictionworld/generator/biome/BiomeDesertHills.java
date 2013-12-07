package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeDesertHills extends BiomeBase {
    
    public BiomeDesertHills(byte id) {
        super(id);
        this.topBlockId = 12;
        this.fillerBlockId = 12;
        this.baseHeight = 19;
        this.temperature = .8D;
        this.rainfall = .1D;
        this.biome = Biome.DESERT_HILLS;
    }
    
}
