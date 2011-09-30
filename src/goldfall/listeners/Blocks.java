package goldfall.listeners;

import goldfall.GoldFall;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.inventory.ItemStack;

public class Blocks extends BlockListener {
    public GoldFall plugin;
    
    public Blocks(GoldFall plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(org.bukkit.Material.LEAVES)) {
            Random random = new Random();
            int chance = random.nextInt(plugin.denom);
            if (chance <= plugin.use) {
                ItemStack item = new ItemStack(322);
                item.setAmount(1);
                event.getBlock().getLocation().getWorld().dropItem(event.getBlock().getLocation(), item);
                Location l = event.getPlayer().getLocation();
                event.getPlayer().getServer().broadcastMessage(plugin.message.replace("+name", event.getPlayer().getDisplayName()));
                System.out.println(plugin.message.replace("+name", event.getPlayer().getDisplayName()));
            }
        }
    }
}
