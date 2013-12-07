package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeJungle extends BiomeBase {
    
    public BiomeJungle(byte id) {
        super(id);
        this.baseHeight = 11;
        this.rainfall = .8;
        this.temperature = .8;
        this.biome = Biome.JUNGLE;
    }
    
}
