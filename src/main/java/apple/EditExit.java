package apple;

import apple.finals.GUIFinals;
import apple.finals.MessageFinals;
import apple.finals.YMLNavigate;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EditExit {
    private static JavaPlugin plugin;

    public EditExit(JavaPlugin pl) {
        plugin = pl;
    }

    public static void editAll(Inventory inventory, String invName, String playerName) {
        // get the yml for the contents of the inventory
        File file = new File(plugin.getDataFolder() + File.separator + "scrollInv" + File.separator + "scrollInv.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configInv = config.getConfigurationSection(YMLNavigate.INVENTORY);
        // make sure configInv is not null
        if (configInv == null) {
            config.createSection(YMLNavigate.INVENTORY);
            configInv = config.getConfigurationSection(YMLNavigate.INVENTORY);
        }
        if (configInv == null) {
            System.err.println(MessageFinals.ERROR_CREATE_INV);
            return;
        }
        // remove the inventory___Section
        configInv.set(invName, null);

        // create the configInv___
        ConfigurationSection configInvName = configInv.createSection(invName);
        configInvName.set(YMLNavigate.PLAYER_NAME, playerName);
        int size = inventory.getSize();
        for (int itemI = 0; itemI < size; itemI++) {
            ItemStack item = inventory.getItem(itemI);
            if (item == null || item.getType() == Material.AIR || GUIFinals.containsItemInSideGUI(item))
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
    }
}
