package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeIceMountains extends BiomeBase {

    public BiomeIceMountains(byte id) {
        super(id);
        this.baseHeight = 16;
        this.temperature = .16;
        this.rainfall = .38;
        this.biome = Biome.ICE_MOUNTAINS;
    }
    
}
