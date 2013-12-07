package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeForest extends BiomeBase {

    public BiomeForest(byte id) {
        super(id);
        this.rainfall = .6;
        this.temperature = .46;
        this.baseHeight = 8;
        this.biome = Biome.FOREST;
    }
    
}
