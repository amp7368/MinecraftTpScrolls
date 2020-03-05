package apple.commands;

import apple.ClickListener;
import apple.MessageFinals;
import apple.ScrollMain;
import org.bukkit.Bukkit;
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

import java.util.List;

public class DuplicateCommand implements CommandExecutor {
    JavaPlugin plugin;

    public DuplicateCommand(ScrollMain plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand("scroll_duplicate");
        if (command == null) {
            System.out.println("ScrollsTp could not get the scrollDuplicate command");
            return;
        }
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null)
            return true;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getAmount() == 0 || item.getType() != Material.PAPER)
            return true;
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return true;
        List<String> lore = meta.getLore();

        if (lore == null || lore.size() != 4) {
            // improper format of lore for a scroll
            player.sendMessage(MessageFinals.IMPROPER_FORMAT_MESSAGE);
            return true;
        }
        String worldString = lore.get(3).substring(6);
        World world;
        world = Bukkit.getWorld(worldString);
        if (world == null) {
            // the world doesn't exist
            player.sendMessage(MessageFinals.IMPROPER_FORMAT_MESSAGE);
        }
        String xString = lore.get(0).substring(2);
        String yString = lore.get(1).substring(2);
        String zString = lore.get(2).substring(2);
        try {
            Integer.parseInt(xString);
            Integer.parseInt(yString);
            Integer.parseInt(zString);
        } catch (NumberFormatException e) {
            // coords aren't numbers
            player.sendMessage(MessageFinals.IMPROPER_FORMAT_MESSAGE);
            return true;
        }
        // we have a valid scroll
        item.setAmount(64);
        player.sendMessage(MessageFinals.DUPLICATE_MESSAGE);
        return false;
    }
}
