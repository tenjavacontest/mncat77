package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeExtremeHills extends BiomeBase {

    public BiomeExtremeHills(byte id) {
        super(id);
        this.baseHeight = 32;
        this.rainfall = .4;
        this.temperature = .4;
        this.biome = Biome.EXTREME_HILLS;
    }
    
}
