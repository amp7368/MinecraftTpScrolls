package apple.commands;

import apple.ScrollInventories;
import apple.ScrollMain;
import apple.utils.GUIOpen;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ScrollCommand implements CommandExecutor {
    private JavaPlugin plugin;

    public ScrollCommand(ScrollMain plugin) {
        this.plugin = plugin;
        // set the scroll command to execute here
        PluginCommand command = plugin.getCommand("scroll");
        if (command == null) {
            System.err.println("ScrollsTp could not get the scroll command");
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

        return GUIOpen.openMainGUI(player);
    }
}
