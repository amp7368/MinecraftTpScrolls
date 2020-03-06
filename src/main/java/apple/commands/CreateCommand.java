package apple.commands;

import apple.finals.MessageFinals;
import apple.ScrollMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CreateCommand implements CommandExecutor {
    JavaPlugin plugin;

    public CreateCommand(ScrollMain plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand("scroll_create");
        if (command == null) {
            System.out.println("ScrollsTp could not get the scroll_create command");
            return;
        }
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            return true;
        }
        if (args.length != 1) {
            commandSender.sendMessage(MessageFinals.CREATE_CORRECT_USAGE);
            return true;
        }
        String itemName = args[0];
        ItemStack mainHand = player.getInventory().getItemInMainHand();

        if (mainHand.getAmount() == 0 || mainHand.getType().equals(Material.AIR) ) {
            // create a scroll
            ItemStack scroll = new ItemStack(Material.PAPER, 64);
            ItemMeta meta = scroll.getItemMeta();
            if(meta == null)
                return true;
            meta.setCustomModelData(0);
            // get the player location
            Location loc = player.getLocation();
            List<String> coords = new ArrayList<String>(4);
            coords.add("x:" + loc.getBlockX());
            coords.add("y:" + loc.getBlockY());
            coords.add("z:" + loc.getBlockZ());
            World w = loc.getWorld();
            if (w == null) {
                commandSender.sendMessage(MessageFinals.SOMETHING_WRONG);
                return true;
            }
            coords.add("world:" + w.getName());

            // set the meta data
            meta.setLore(coords);
            meta.setDisplayName(itemName);
            scroll.setItemMeta(meta);

            player.getInventory().setItemInMainHand(scroll);
        }else{
            commandSender.sendMessage(MessageFinals.CREATE_FULL_HANDS);
        }
        return false;
    }
}
