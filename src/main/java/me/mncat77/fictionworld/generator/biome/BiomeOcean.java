package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeOcean extends BiomeBase {
    
    public BiomeOcean(byte id) {
        super(id);
        this.biome = Biome.OCEAN;
        this.baseHeight = -42;
        this.topBlockId = 3;
        this.rainfall = 1.0;
    }
    
}
