package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeExtremeHillsEdge extends BiomeBase {

    public BiomeExtremeHillsEdge(byte id) {
        super(id);
        this.baseHeight = 16;
        this.rainfall = .44;
        this.temperature = .44;
        this.biome = Biome.EXTREME_HILLS;
    }
    
}
