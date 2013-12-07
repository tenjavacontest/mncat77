package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeFrozenOcean extends BiomeBase {
    
    public BiomeFrozenOcean(byte id) {
        super(id);
        this.topBlockId = 3;
        this.baseHeight = -42;
        this.temperature = .1;
        this.rainfall = .9;
        this.biome = Biome.FROZEN_OCEAN;
    }
    
}
