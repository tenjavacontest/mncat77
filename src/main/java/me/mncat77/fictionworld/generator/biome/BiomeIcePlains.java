package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeIcePlains extends BiomeBase {
    
    public BiomeIcePlains(byte id) {
        super(id);
        this.baseHeight = 8;
        this.temperature = .23;
        this.rainfall = .4;
        this.biome = Biome.ICE_PLAINS;
    }
    
}
