package apple.commands;

import apple.ScrollInventories;
import apple.ScrollMain;
import apple.utils.MessageFinals;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class ScrollPrivateCommand implements CommandExecutor {
    private JavaPlugin plugin;

    public ScrollPrivateCommand(ScrollMain plugin) {
        this.plugin = plugin;
        // set the scroll command to execute here
        PluginCommand command = plugin.getCommand("scroll_private");
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

        // open the scroll inventory
        Inventory inv;
        try {
            inv = ScrollInventories.open(player);
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("Oof! Something's wrong.. Give apple this: " + e.getMessage());
            return true;
        }
        player.openInventory(inv);
        return false;
    }
}
