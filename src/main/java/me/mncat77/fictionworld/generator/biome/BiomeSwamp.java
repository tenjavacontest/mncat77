package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeSwamp extends BiomeBase {
    
    public BiomeSwamp(byte id) {
        super(id);
        this.baseHeight = 3;
        this.rainfall = .7;
        this.temperature = .6;
        this.biome = Biome.SWAMPLAND;
    }
    
}
