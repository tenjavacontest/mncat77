package me.mncat77.fictionworld.generator.biome;

import org.bukkit.block.Biome;

public abstract class BiomeBase {
    
    public static final BiomeBase OCEAN = new BiomeOcean((byte)0);
    public static final BiomeBase PLAINS = new BiomePlains((byte)1);
    public static final BiomeBase DESERT = new BiomeDesert((byte)2);
    public static final BiomeBase EXTREME_HILLS = new BiomeExtremeHills((byte)3);
    public static final BiomeBase FOREST = new BiomeForest((byte)4);
    public static final BiomeBase TAIGA = new BiomeTaiga((byte)5);
    public static final BiomeBase SWAMPLAND = new BiomeSwamp((byte)6);
    public static final BiomeBase RIVER = new BiomeRiver((byte)7);
    
    public static final BiomeBase FROZEN_OCEAN = new BiomeFrozenOcean((byte)10);
    public static final BiomeBase FROZEN_RIVER = new BiomeFrozenRiver((byte)11);
    public static final BiomeBase ICE_PLAINS = new BiomeIcePlains((byte)12);
    public static final BiomeBase ICE_MOUNTAINS = new BiomeIceMountains((byte)13);
    
    public static final BiomeBase BEACH = new BiomeBeach((byte)16);
    public static final BiomeBase DESERT_HILLS = new BiomeDesertHills((byte)17);
    public static final BiomeBase FOREST_HILLS = new BiomeForestHills((byte)18);
    public static final BiomeBase TAIGA_HILLS = new BiomeTaigaHills((byte)19);
    public static final BiomeBase SMALL_MOUNTAINS = new BiomeExtremeHillsEdge((byte)20);
    public static final BiomeBase JUNGLE = new BiomeJungle((byte)21);
    public static final BiomeBase JUNGLE_HILLS = new BiomeJungleHills((byte)22);
    
    public static final BiomeBase[] byId = {OCEAN, PLAINS, DESERT,
        EXTREME_HILLS, FOREST, TAIGA, SWAMPLAND, RIVER, OCEAN, OCEAN,
        FROZEN_OCEAN, FROZEN_RIVER,ICE_PLAINS, ICE_MOUNTAINS, OCEAN,
        OCEAN, BEACH,DESERT_HILLS, FOREST_HILLS, TAIGA_HILLS,
        SMALL_MOUNTAINS, JUNGLE, JUNGLE_HILLS};
    
    
    public final byte id;
    
    public double rainfall = .5;
    public double temperature = .5;
    
    protected Biome biome = Biome.PLAINS;
    
    public int baseHeight = 7;
    
    public byte topBlockId = 2;
    public byte fillerBlockId = 3;
    
    
    public BiomeBase(byte id) {
        this.id = id;
    }
    
    public Biome getBukkitBiome() {
        return this.biome;
    }
    
}
