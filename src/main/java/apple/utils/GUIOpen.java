package apple.utils;

import apple.ScrollInventories;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class GUIOpen {
    private static JavaPlugin plugin;

    public GUIOpen(JavaPlugin pl) {
        plugin = pl;
    }

    public static boolean openMainGUI(Player player) {
        File file = new File(plugin.getDataFolder() + File.separator + "scrollInv" + File.separator + "admins.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configAdmins = config.getConfigurationSection("admins");
        int i = 0;
        String uuid = player.getUniqueId().toString();
        if (configAdmins == null) {
            System.err.println("Something went wrong with reading the admins.");
            player.openInventory(ScrollInventories.MainGUI);
            return false;
        }
        String configAdmin = configAdmins.getString("admin" + i++);
        while (configAdmin != null) {
            if (configAdmin.equals(uuid)) {
                // open the MainOp inventory
                player.openInventory(ScrollInventories.MainGUIOp);
                return false;
            }
            configAdmin = configAdmins.getString("admin" + i++);
        }
        player.openInventory(ScrollInventories.MainGUI);
        return false;
    }

}
