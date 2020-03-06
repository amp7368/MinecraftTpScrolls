package apple.commands;

import apple.ScrollInventories;
import apple.ScrollMain;
import apple.utils.YMLNavigate;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ScrollCommand implements CommandExecutor {
    private JavaPlugin plugin;

    public ScrollCommand(ScrollMain plugin) {
        this.plugin = plugin;
        // set the scroll command to execute here
        PluginCommand command = plugin.getCommand("scroll");
        if (command == null) {
            return;
        }
        command.setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // get the player
        Player player = Bukkit.getServer().getPlayer(commandSender.getName());

        // return if player is non existent
        if (player == null)
            return false;

        if (!player.isOp()) {
            player.openInventory(ScrollInventories.MainGUI);
            return false;
        }

        boolean isAdmin = false;
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
                // open the scroll inventory
                player.openInventory(ScrollInventories.MainGUIOp);
                return false;
            }
            configAdmin = configAdmins.getString("admin" + i++);
        }
        player.openInventory(ScrollInventories.MainGUI);
        return false;
    }
}
