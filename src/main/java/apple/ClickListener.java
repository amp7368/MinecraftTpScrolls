package apple;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public class ClickListener implements Listener {
    JavaPlugin plugin;
    private final static int DISTANCE_MARGIN = 5;
    final static String IMPROPER_FORMAT_MESSAGE = "I can't read the language on this scroll..";
    HashMap<String, Long> lastAttemptedScroll = new HashMap<String, Long>();

    public ClickListener(ScrollMain plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void rightClickListener(PlayerInteractEvent event) {
        Action action = event.getAction();
        String playerName = event.getPlayer().getName();
        if (lastAttemptedScroll.containsKey(playerName)) {
            if (lastAttemptedScroll.get(playerName) > System.currentTimeMillis() - 2000) {
                // don't spam!
                return;
            } else {
                lastAttemptedScroll.remove(playerName);
            }
        }
        // if the action is a right click
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = event.getItem();
            if (item == null) {
                return;
            }
            // if the item in hand is paper
            if (item.getType() == Material.PAPER) {
                lastAttemptedScroll.put(playerName, System.currentTimeMillis());

                // get the lore for the item
                ItemMeta meta = item.getItemMeta();
                if (meta == null)
                    return;
                List<String> lore = meta.getLore();

                if (lore == null || lore.size() != 4) {
                    // improper format of lore for a scroll
                    event.getPlayer().sendMessage("I can't read the language on this scroll..");
                    return;
                }
                String worldString = lore.get(3).substring(6);
                World world;
                world = Bukkit.getWorld(worldString);
                if (world == null) {
                    // the world doesn't exist
                    event.getPlayer().sendMessage(IMPROPER_FORMAT_MESSAGE);
                }
                String xString = lore.get(0).substring(2);
                String yString = lore.get(1).substring(2);
                String zString = lore.get(2).substring(2);
                int x, y, z;
                try {
                    x = Integer.parseInt(xString);
                    y = Integer.parseInt(yString);
                    z = Integer.parseInt(zString);
                } catch (NumberFormatException e) {
                    // coords aren't numbers
                    event.getPlayer().sendMessage(IMPROPER_FORMAT_MESSAGE);
                    return;
                }
                Location playerLoc = event.getPlayer().getLocation();
                // if the player is already in the location
                if (x - DISTANCE_MARGIN < playerLoc.getBlockX() && playerLoc.getBlockX() < x + DISTANCE_MARGIN &&
                        y - DISTANCE_MARGIN < playerLoc.getBlockY() && playerLoc.getBlockY() < y + DISTANCE_MARGIN &&
                        z - DISTANCE_MARGIN < playerLoc.getBlockZ() && playerLoc.getBlockZ() < z + DISTANCE_MARGIN) {
                    event.getPlayer().sendMessage("I'm already here! I don't need to use a scroll..");
                    return;
                }

                // teleport the player to x y z in world
                event.getPlayer().teleport(new Location(world, x, y, z));
                item.setAmount(item.getAmount() - 1);


            }
        }
    }
}
