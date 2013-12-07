package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeForestHills extends BiomeBase {

    public BiomeForestHills(byte id) {
        super(id);
        this.rainfall = .6;
        this.temperature = .44;
        this.baseHeight = 16;
        this.biome = Biome.FOREST_HILLS;
    }
    
}
