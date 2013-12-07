package me.mncat77.fictionworld.generator.biome.layer;

import me.mncat77.fictionworld.world.FictionWorldHostility;

//BiomeLayer classes are partially based on NMS (GenLayer classes)
public abstract class BiomeLayer {
    
    public static BiomeLayer getLayer(double averageRainfall, double averageTemperature, FictionWorldHostility hostility, long seed) {
        BiomeLayer layer;
        if (hostility != FictionWorldHostility.APOCALYPSE) {
            layer = new BiomeLayerRNGBase(seed);
            layer = new BiomeLayerZoomFuzzy(seed, layer);
            layer = new BiomeLayerAddBlocksToIslands(seed, layer);
            layer = new BiomeLayerZoom(seed, layer);
            layer = new BiomeLayerAddBlocksToIslands(seed + 1, layer);
            layer = new BiomeLayerIcePlains(seed, layer);
            layer = new BiomeLayerZoom(seed + 1, layer);
            layer = new BiomeLayerAddBlocksToIslands(seed + 2, layer);
            layer = new BiomeLayerZoom(seed + 2, layer);
            layer = new BiomeLayerAddBlocksToIslands(seed + 3, layer);
            layer = BiomeLayerZoom.magnify(seed, layer, 0);
            
            BiomeLayer layer2 = new BiomeLayerRiverInit(seed, layer);
            layer2 = BiomeLayerZoom.magnify(seed , layer2, 6);
            layer2 = new BiomeLayerRiver(seed, layer2);
            layer2 = new BiomeLayerSmooth(seed, layer2);
            
            layer = new BiomeLayerBiome(seed, layer, averageRainfall, averageTemperature);
            layer = BiomeLayerZoom.magnify(seed, layer, 2);
            layer = new BiomeLayerRegionHills(seed, layer);
            for (int size = 0; size < 4; ++size) {
                layer = new BiomeLayerZoom((long) (seed + size), layer);
                if (size == 0) {
                    layer = new BiomeLayerAddBlocksToIslands(seed + 4, layer);
                } else if (size == 1) {
                    layer = new BiomeLayerLakes(1000L, layer);
                }
            }
            layer = new BiomeLayerSmooth(seed, layer);
            layer = new BiomeLayerRiverMix(seed, layer, layer2);
            layer = new BiomeLayerZoomVoronoi(seed, layer);
            
        } else {
            layer = new BiomeLayerApocalypse(seed, averageRainfall, averageTemperature);
            layer = new BiomeLayerZoomFuzzy(seed, layer);
            layer = BiomeLayerZoom.magnify(seed, layer, 5);
            layer = new BiomeLayerZoomVoronoi(seed, layer);
        }
        return layer;
    }
    
    protected long seed;
    protected BiomeLayer parent;
    
    protected long chunkSeed;
    
    
    public BiomeLayer(long seed) {
        this.seed = seed;
    }
    
    public void initChunkSeed(long realX, long realZ) {
        this.chunkSeed = this.seed;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += realX;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += realZ;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += realX;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += realZ;
    }
    
    protected byte nextByte(int max) {
        byte rand = (byte)((this.chunkSeed >> 24) % (long)max);
        
        if (rand < 0) {
            rand += max;
        }
        
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.seed;
        return rand;
    }
    
    public abstract byte[] getValues(int x, int z, int width, int length);
    
}
