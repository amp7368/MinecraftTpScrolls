package apple.listeners;

import apple.EditExit;
import apple.ScrollInventories;
import apple.finals.MessageFinals;
import apple.guiTypes.GUIPrivate;
import apple.guiTypes.GUIPrivateEdit;
import apple.guiTypes.GUIPublicEdit;
import apple.utils.YMLNavigate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
            EditExit.editAll(event.getInventory(), YMLNavigate.INVENTORY_PRIVATE + "." + uuid, event.getPlayer().getName());
            // remove the inventory from ScrollInventories to save space
            ScrollInventories.scrollInvEditIndividual.popKey(uuid);
        } else if (holder instanceof GUIPublicEdit) {
            EditExit.editAll(event.getInventory(), YMLNavigate.INVENTORY_ALL, "server");
        } else if (holder instanceof GUIPrivate) {
            String uuid = event.getPlayer().getUniqueId().toString();
            // remove the inventory from ScrollInventories to save space
            ScrollInventories.scrollInvIndividual.popKey(uuid);
        }
    }
}
