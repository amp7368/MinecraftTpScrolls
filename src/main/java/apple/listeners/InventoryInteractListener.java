package apple.listeners;

import apple.ScrollInventories;
import apple.finals.YMLNavigate;
import apple.guiTypes.*;
import apple.finals.GUIActionsFinal;
import apple.finals.MessageFinals;
import apple.utils.Teleport;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class InventoryInteractListener implements Listener {
    public InventoryInteractListener(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void inventoryEvent(InventoryClickEvent event) {
        InventoryHolder currentHolder = event.getInventory().getHolder();
        if (!(currentHolder instanceof GUI))
            return;


        // deal with buttons on the inventory
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof GUI) {
            // Don't let the player use shift click
            if (event.isShiftClick()) {
                event.setCancelled(true);
                return;
            }
            // Don't let the player use numbers to get stuff.
            if (event.getClick().equals(ClickType.CONTROL_DROP) || event.getClick().equals(ClickType.DOUBLE_CLICK) || event.getClick().equals(ClickType.DROP)) {
                event.setCancelled(true);
                return;
            }

            int rawSlot = event.getRawSlot();
            if (rawSlot >= event.getInventory().getSize() || rawSlot < 0) {
                // ignore out of bounds
                return;
            }

            // get the item in the cursor's hand
            // if it's paper, then place it
            ItemStack currentItem = event.getCursor();
            if (((GUI) holder).getSpace(event.getRawSlot()).editable) {
                if (currentItem != null && currentItem.getType() != Material.AIR) {
                    if (currentItem.getType() == Material.PAPER) {
                        event.setCancelled(false);
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                }
            } else
                event.setCancelled(true);

            currentItem = event.getCurrentItem();
            if (currentItem != null) {
                ItemMeta im = currentItem.getItemMeta();
                if (im != null) {
                    String localName = im.getLocalizedName();
                    if (GUIActionsFinal.dealWith(localName, event)) {
                        return;
                    }
                }
            }

            // associate the click with the right type of inventory
            if (currentHolder instanceof GUIPublic) {
                dealWithScrollInv(event);
            } else if (currentHolder instanceof GUIPrivate) {
                dealWithScrollInv(event);
            } else if (currentHolder instanceof GUIPublicEdit) {
                dealWithEditInv(event);
            } else if (currentHolder instanceof GUIPrivateEdit) {
                dealWithEditInv(event);
            } else if (currentHolder instanceof GUIMain) {
                dealWithMainGUI(event);
            } else if (currentHolder instanceof GUIQuickEdit) {
                dealWithEditInv(event);
            } else if (currentHolder instanceof GUIQuick) {
                dealWithQuickInv(event);
            }
        }


    }

    private void dealWithQuickInv(InventoryClickEvent event) {
        HumanEntity who = event.getWhoClicked();
        // if this wasn't interacted with a player, wtf happened? best to ignore it..
        if (!(who instanceof Player)) {
            event.setCancelled(true);
            return;
        }
        Player player = (Player) who;

        // don't treat this as a normal inv
        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR)
            return;

        // get info about the clicked item
        ItemMeta clickedMeta = clickedItem.getItemMeta();
        if (clickedMeta == null) {
            return;
        }
        List<String> clickedLore = clickedMeta.getLore();
        if (clickedLore != null)
            Teleport.teleportScroll(clickedLore, player);
    }

    private void dealWithEditInv(InventoryClickEvent event) {
    }


    private void dealWithMainGUI(InventoryClickEvent event) {
        HumanEntity who = event.getWhoClicked();
        // if this wasn't interacted with a player, wtf happened? best to ignore it..
        if (!(who instanceof Player)) {
            return;
        }
        Player player = (Player) who;

        // don't treat this as a normal inv
        event.setCancelled(true);

        // Don't let the player use numbers to get stuff.
        if (event.getClick().equals(ClickType.NUMBER_KEY)) {
            return;
        }
        ItemStack clickedItem = event.getCurrentItem();
        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR)
            return;
        switch (clickedItem.getType()) {
            case RED_TERRACOTTA: {
                try {
                    Inventory inv = ScrollInventories.openPersonalInv(YMLNavigate.INVENTORY_PRIVATE, player, false);
                    player.openInventory(inv);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case RED_GLAZED_TERRACOTTA: {
                try {
                    Inventory inv = ScrollInventories.openPersonalInv(YMLNavigate.INVENTORY_PRIVATE, player, true);
                    player.openInventory(inv);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case BLUE_TERRACOTTA: {
                try {
                    Inventory inv = ScrollInventories.openPersonalInv(YMLNavigate.INVENTORY_QUICK, player, false);
                    player.openInventory(inv);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }

            case BLUE_GLAZED_TERRACOTTA: {
                try {
                    Inventory inv = ScrollInventories.openPersonalInv(YMLNavigate.INVENTORY_QUICK, player, true);
                    player.openInventory(inv);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case GREEN_TERRACOTTA: {
                player.openInventory(ScrollInventories.scrollInvAll);
                break;
            }
            case GREEN_GLAZED_TERRACOTTA: {
                // change based on perms
                if (player.isOp())
                    player.openInventory(ScrollInventories.scrollInvAllEdit);

                break;
            }
        }
    }

    private void dealWithScrollInv(InventoryClickEvent event) {
        HumanEntity who = event.getWhoClicked();
        // if this wasn't interacted with a player, wtf happened? best to ignore it..
        if (!(who instanceof Player)) {
            event.setCancelled(true);
            return;
        }
        Player player = (Player) who;

        // don't treat this as a normal inv
        event.setCancelled(true);


        ItemStack clickedItem = event.getCurrentItem();
        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR)
            return;

        // get info about the clicked item
        ItemMeta clickedMeta = clickedItem.getItemMeta();
        if (clickedMeta == null) {
            return;
        }
        List<String> clickedLore = clickedMeta.getLore();
        Material clickedType = clickedItem.getType();

        // get the player inv and contents to see if it's empty
        PlayerInventory playerInv = player.getInventory();
        ItemStack[] contents = playerInv.getStorageContents();
        boolean canAddItem = false;
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item == null || item.getAmount() == 0 || item.getType().equals(Material.AIR)) {
                canAddItem = true;
            } else {
                // get info about item
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta == null)
                    continue;
                List<String> itemLore = itemMeta.getLore();

                // if we already have the item in our inv
                if (item.getType() == clickedType && itemLore != null && itemLore.equals(clickedLore)) {
                    if (item.getAmount() != 64) {
                        item.setAmount(64);
                        playerInv.setItem(i, item);
                        return;
                    }
                    player.sendMessage(MessageFinals.GET_ALREADY_HAVE);
                    canAddItem = false;
                    break;
                }
            }
        }

        // if the player inv has an empty slot and we don't already have it add the item
        if (canAddItem) {
            ItemStack newItem = new ItemStack(clickedItem);
            if (event.getAction().compareTo(InventoryAction.PICKUP_HALF) == 0) {
                newItem.setAmount(1);
            } else {
                newItem.setAmount(64);
            }
            player.getInventory().addItem(newItem);
        }
    }
}
