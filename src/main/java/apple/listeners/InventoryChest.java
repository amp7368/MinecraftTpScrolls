package apple.listeners;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryChest implements InventoryHolder {
    private final Inventory inventory;

    public InventoryChest(int size, String name) {
        inventory = Bukkit.createInventory(null, size, name);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void put(int index, ItemStack item) {
        inventory.setItem(index, item);
    }

    public void add(ItemStack item) {
        inventory.addItem(item);
    }


}
