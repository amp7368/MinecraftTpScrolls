package apple.guiTypes;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GUI implements InventoryHolder {
    ArrayList<Space> spaces;
    private final Inventory inventory;

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

    public GUI(int size, String name) {
        spaces = new ArrayList<Space>(size);
        for (int i = 0; i < size; i++) {
            spaces.add(new Space(false));
        }
        inventory = Bukkit.createInventory(this, size, name);
    }

    public Space getSpcae(int i) {
        return spaces.get(i);
    }

    protected void set(int space, boolean editable) {
        spaces.get(space).editable = true;
    }

    protected int getSize() {
        return spaces.size();
    }
}