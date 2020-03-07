package apple.listeners;

import apple.EditExit;
import apple.ScrollInventories;
import apple.finals.GUIFinals;
import apple.guiTypes.*;
import apple.finals.YMLNavigate;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryExitListener implements Listener {
    private JavaPlugin plugin;

    public InventoryExitListener(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void inventoryExit(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        // edit if necessary when you exit the inventory
        if (holder instanceof GUIPrivateEdit) {
            String uuid = event.getPlayer().getUniqueId().toString();
            EditExit.updateConfig(event.getInventory(), YMLNavigate.INVENTORY_PRIVATE + "." + uuid, event.getPlayer().getName());
            // remove the inventory from ScrollInventories to save space
            ScrollInventories.scrollInvEditIndividual.popKey(uuid);
        } else if (holder instanceof GUIQuickEdit) {
            String uuid = event.getPlayer().getUniqueId().toString();
            EditExit.updateConfig(event.getInventory(), YMLNavigate.INVENTORY_QUICK + "." + uuid, event.getPlayer().getName());
            // remove the inventory from ScrollInventories to save space
            ScrollInventories.scrollInvEditQuick.popKey(uuid);
        } else if (holder instanceof GUIPublicEdit) {
            EditExit.updateConfig(event.getInventory(), YMLNavigate.INVENTORY_ALL, GUIFinals.SERVER_NAME);
        } else if (holder instanceof GUIPrivate) {
            String uuid = event.getPlayer().getUniqueId().toString();
            // remove the inventory from ScrollInventories to save space
            ScrollInventories.scrollInvIndividual.popKey(uuid);
        } else if (holder instanceof GUIQuick) {
            String uuid = event.getPlayer().getUniqueId().toString();
            // remove the inventory from ScrollInventories to save space
            ScrollInventories.scrollInvQuick.popKey(uuid);
        }
    }
}
