package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

class BiomeRiver extends BiomeBase {

    public BiomeRiver(byte id) {
        super(id);
        this.topBlockId = 3;
        this.rainfall = .9;
        this.temperature = .44;
        this.baseHeight = -30;
        this.biome = Biome.RIVER;
    }
    
}
