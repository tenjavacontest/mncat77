package me.mncat77.fictionworld.listener;

import java.util.List;
import java.util.Random;
import me.mncat77.fictionworld.FictionWorld;
import me.mncat77.fictionworld.generator.FictionWorldChunkGenerator;
import me.mncat77.fictionworld.world.FictionWorldAttributes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.BookMeta;

public class PlayerInteractListener implements Listener {
    
    private final FictionWorld plugin;
    
    
    public PlayerInteractListener(FictionWorld plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if((event.getMaterial() == Material.WRITTEN_BOOK) && (event.getAction() == Action.LEFT_CLICK_AIR)) {
            Player player = event.getPlayer();
            
            BookMeta meta = (BookMeta)event.getItem().getItemMeta();
            String title = meta.getTitle();
            for(World world : plugin.getServer().getWorlds()) {
                if(world.getName().equalsIgnoreCase(title)) {
                    //World already exsists, teleport player to dimension
                    player.teleport(world.getSpawnLocation());
                    return;
                }
            }
            
            
            
            if(player.isOp()) {
                //Create new word with context and teleport player to it
                List<String> describingWords = meta.getPages();
                long seed = new Random().nextLong();
                FictionWorldAttributes attributes = new FictionWorldAttributes(describingWords);
                event.getPlayer().teleport(new WorldCreator(title).generator(new FictionWorldChunkGenerator(
                        seed, attributes)).createWorld().getSpawnLocation());
                plugin.getConfig().set("world." + title + ".attributes", attributes.toString());
                plugin.getConfig().set("world." + title + ".seed", seed);
                plugin.saveConfig();
            } else {
                player.sendMessage(ChatColor.RED + "You don't have permission to create a new world!");
            }
        }
    }
    
}
