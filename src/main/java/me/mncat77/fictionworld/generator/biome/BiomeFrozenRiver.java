package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeFrozenRiver extends BiomeBase {
    
    public BiomeFrozenRiver(byte id) {
        super(id);
        this.topBlockId = 3;
        this.baseHeight = -30;
        this.temperature = .14;
        this.rainfall = .83;
        this.biome = Biome.FROZEN_RIVER;
    }
    
}
