package apple.commands;

import apple.ScrollMain;
import apple.YMLNavigate;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ScrollAddAllCommand implements CommandExecutor {
    private JavaPlugin plugin;
    private ScrollCommand scrollToUpdate;

    public ScrollAddAllCommand(ScrollMain plugin, ScrollCommand scrollCommand) {
        this.scrollToUpdate = scrollCommand;
        this.plugin = plugin;
        Bukkit.getPluginCommand("scroll_add_all").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        // get the yml for the contents of the inventory
        File file = new File(plugin.getDataFolder() + File.separator + "scrollInv" + File.separator + "scrollInv.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configInv = config.getConfigurationSection(YMLNavigate.INVENTORY);

        // make sure configInv is not null
        if (configInv == null)
            return true;

        // get the configInv for the global library
        ConfigurationSection configInvAll = configInv.getConfigurationSection(YMLNavigate.INVENTORY_ALL);

        // figure out what number I should put the item in
        int itemI = 1;
        ConfigurationSection item = configInv.getConfigurationSection(String.format(YMLNavigate.ITEM + "%d", itemI++));
        while (item != null) {
            item = configInv.getConfigurationSection(String.format(YMLNavigate.ITEM + "%d", itemI++));
        }
        if (configInvAll == null)
            return true;

        // get whatever is in the player's main hand
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null)
            return true;
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // create the item section in the yml
        configInvAll.createSection(String.format(YMLNavigate.ITEM + "%d", itemI));
        ConfigurationSection configInvAllItem = configInvAll.getConfigurationSection(String.format(YMLNavigate.ITEM + "%d", itemI));
        configInvAll.createSection(YMLNavigate.MATERIAL);
        if (configInvAllItem == null)
            return true;
        configInvAllItem.set(YMLNavigate.MATERIAL, itemInHand.getType().toString());
        configInvAllItem.createSection(YMLNavigate.LORE);
        ConfigurationSection configInvAllItemLore = configInvAllItem.getConfigurationSection(YMLNavigate.LORE);
        ItemMeta itemMeta = itemInHand.getItemMeta();
        if (itemMeta == null)
            return true;
        List<String> itemLore = itemMeta.getLore();
        if (itemLore == null)
            return true;
        if (configInvAllItemLore == null)
            return true;

        // create the lore section in the yml
        int i = 1;
        for (String l : itemLore) {
            configInvAllItemLore.createSection(String.format(YMLNavigate.LINE + "%d", i));
            configInvAllItemLore.set(String.format(YMLNavigate.LINE + "%d", i++), l);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scrollToUpdate.update();
        return false;
    }
}
