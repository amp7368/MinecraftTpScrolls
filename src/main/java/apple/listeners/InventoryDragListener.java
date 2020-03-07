package apple.listeners;

import apple.guiTypes.GUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryDragListener implements Listener {
    public InventoryDragListener(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        HumanEntity who = event.getWhoClicked();
        if (!(who instanceof Player)) {
            return;
        }
        if (!(event.getInventory().getHolder() instanceof GUI)) {
            return;
        }
        Player player = (Player) who;
        Inventory inventory = player.getOpenInventory().getTopInventory();

        for (int slot : event.getRawSlots()) {
            if (slot < inventory.getSize()) {
                event.setCancelled(true);
                break;
            }
        }
    }
}
