package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeTaiga extends BiomeBase {
    
    public BiomeTaiga(byte id) {
        super(id);
        this.temperature = .2;
        this.rainfall = .4;
        this.baseHeight = 10;
        this.biome = Biome.TAIGA;
    }
    
}
