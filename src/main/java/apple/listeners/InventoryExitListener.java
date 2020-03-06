package apple.listeners;

import apple.ScrollInventories;
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
            editAll(event.getInventory(), YMLNavigate.INVENTORY_PRIVATE + "." + uuid, event.getPlayer().getName());
            // remove the inventory from ScrollInventories to save space
            ScrollInventories.scrollInvEditIndividual.popKey(uuid);
        } else if (holder instanceof GUIPublicEdit) {
            editAll(event.getInventory(), YMLNavigate.INVENTORY_ALL, "server");
        } else if (holder instanceof GUIPrivate) {
            String uuid = event.getPlayer().getUniqueId().toString();
            // remove the inventory from ScrollInventories to save space
            ScrollInventories.scrollInvIndividual.popKey(uuid);
        }
    }

    public void editAll(Inventory inventory, String invName, String playerName) {
        // get the yml for the contents of the inventory
        File file = new File(plugin.getDataFolder() + File.separator + "scrollInv" + File.separator + "scrollInv.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configInv = config.getConfigurationSection(YMLNavigate.INVENTORY);
        // make sure configInv is not null
        if (configInv == null)
            return;
        System.out.println("[scrolls] the invName is " + invName);
        // remove the inventory___Section
        configInv.set(invName, null);

        // create the configInv___
        ConfigurationSection configInvName = configInv.createSection(invName);
        configInvName.set(YMLNavigate.PLAYER_NAME, playerName);
        int size = inventory.getSize();
        for (int itemI = 0; itemI < size; itemI++) {
            ItemStack item = inventory.getItem(itemI);
            if (item == null || item.getType() == Material.AIR)
                continue;

            // create the item section in the yml
            configInvName.createSection(String.format(YMLNavigate.ITEM + "%d", itemI));
            ConfigurationSection configInvAllItem = configInvName.getConfigurationSection(String.format(YMLNavigate.ITEM + "%d", itemI));
            if (configInvAllItem == null)
                continue;

            // set the item to what it is
            configInvAllItem.set(YMLNavigate.MATERIAL, item.getType().toString());
            ItemMeta im = item.getItemMeta();
            if (im != null)
                configInvAllItem.set(YMLNavigate.NAME, im.getDisplayName());

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
