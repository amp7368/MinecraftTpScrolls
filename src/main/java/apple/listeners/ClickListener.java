package apple.listeners;

import apple.finals.MessageFinals;
import apple.ScrollMain;
import apple.utils.Teleport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public class ClickListener implements Listener {
    JavaPlugin plugin;
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
                    event.getPlayer().sendMessage(MessageFinals.WRONG_SCROLL);
                    return;
                }
                Teleport.teleportScroll(lore, event.getPlayer());
                item.setAmount(item.getAmount() - 1);

            }
        }
    }
}
