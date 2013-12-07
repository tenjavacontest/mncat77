package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public class BiomeTaigaHills extends BiomeBase {
    
    public BiomeTaigaHills(byte id) {
        super(id);
        this.temperature = .1;
        this.rainfall = .4;
        this.baseHeight = 21;
        this.biome = Biome.TAIGA_HILLS;
    }
    
}
