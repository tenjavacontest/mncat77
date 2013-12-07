package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeJungleHills extends BiomeBase {
    
    public BiomeJungleHills(byte id) {
        super(id);
        this.baseHeight = 24;
        this.rainfall = .8;
        this.temperature = .7;
        this.biome = Biome.JUNGLE_HILLS;
    }
    
}
