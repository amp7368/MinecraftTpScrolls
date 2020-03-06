package apple.listeners;

import apple.ScrollInventories;
import apple.utils.YMLNavigate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
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
        System.out.println("initialized exit listener");
    }


    @EventHandler
    public void inventoryExit(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() == ScrollInventories.scrollInvAllEdit.getHolder()) {
            editAll(event.getInventory());
        }
    }

    public void editAll(Inventory inventory) {
        // get the yml for the contents of the inventory
        File file = new File(plugin.getDataFolder() + File.separator + "scrollInv" + File.separator + "scrollInv.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configInv = config.getConfigurationSection(YMLNavigate.INVENTORY);
        // make sure configInv is not null
        if (configInv == null)
            return;
        // remove the inventoryAllSection
        configInv.set(YMLNavigate.INVENTORY_ALL, null);

        // create the configInvAll
        ConfigurationSection configInvAll = configInv.createSection(YMLNavigate.INVENTORY_ALL);

        int size = inventory.getSize();
        for (int itemI = 0; itemI < size; itemI++) {
            ItemStack item = inventory.getItem(itemI);
            // create the item section in the yml
            configInvAll.createSection(String.format(YMLNavigate.ITEM + "%d", itemI));
            ConfigurationSection configInvAllItem = configInvAll.getConfigurationSection(String.format(YMLNavigate.ITEM + "%d", itemI));
            if (configInvAllItem == null)
                continue;

            // if the item is nothing, set it to such
            if (item == null) {
                configInvAllItem.set(YMLNavigate.MATERIAL, Material.AIR.toString());
                continue;
            } else {
                configInvAllItem.set(YMLNavigate.MATERIAL, item.getType().toString());
                ItemMeta im = item.getItemMeta();
                if (im != null)
                    configInvAllItem.set(YMLNavigate.NAME, im.getDisplayName());
            }
            configInvAllItem.createSection(YMLNavigate.LORE);
            ConfigurationSection configInvAllItemLore = configInvAllItem.getConfigurationSection(YMLNavigate.LORE);
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta == null)
                continue;
            List<String> itemLore = itemMeta.getLore();
            if (itemLore == null)
                continue;
            if (configInvAllItemLore == null)
                continue;

            // create the lore section in the yml
            int i = 1;
            for (String l : itemLore) {
                configInvAllItemLore.createSection(String.format(YMLNavigate.LINE + "%d", i));
                configInvAllItemLore.set(String.format(YMLNavigate.LINE + "%d", i++), l);
            }
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ScrollInventories.update();
    }
}
