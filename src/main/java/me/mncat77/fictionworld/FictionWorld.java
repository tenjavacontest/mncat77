package me.mncat77.fictionworld;

import me.mncat77.fictionworld.generator.FictionWorldChunkGenerator;
import me.mncat77.fictionworld.listener.PlayerInteractListener;
import me.mncat77.fictionworld.world.FictionWorldAttributes;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FictionWorld extends JavaPlugin {
    
    @Override
    public void onEnable() {
        PluginManager pM = getServer().getPluginManager();
        pM.registerEvents(new PlayerInteractListener(this), this);
        this.saveDefaultConfig();
    }
    
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new FictionWorldChunkGenerator(this.getConfig().getLong("world." + worldName + ".seed"),
                FictionWorldAttributes.fromString(this.getConfig().getString("world." + worldName + ".attributes")));
        
    }
    
}

