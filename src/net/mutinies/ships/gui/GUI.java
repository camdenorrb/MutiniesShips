package net.mutinies.ships.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class GUI {
	protected Player player;
	protected Inventory inventory;

	protected Map<Integer, Consumer<InventoryClickEvent>> actionMap = new HashMap<>();

	public GUI(Player player, int rows, String title) {
		inventory = Bukkit.createInventory(null, rows * 9, title);
		this.player = player;
	}

	public static ItemStack applyData(ItemStack stack, String name, String... lore) {
		ItemMeta itemMeta = stack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(Arrays.asList(lore));
		stack.setItemMeta(itemMeta);
		return stack;
	}

	public static ItemStack applyData(ItemStack stack, String name, List<String> lore) {
		ItemMeta itemMeta = stack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		stack.setItemMeta(itemMeta);
		return stack;
	}

	protected abstract void addItems();

	protected void setItem(ItemData data, Consumer<InventoryClickEvent> consumer) {
		inventory.setItem(data.getSlot(), data.getStack());
		actionMap.put(data.getSlot(), consumer);
	}

	protected void setItem(int r, int c, ItemStack item, Consumer<InventoryClickEvent> consumer) {
		int pos = r * 9 + c;
		inventory.setItem(pos, item);
		actionMap.put(pos, consumer);
	}

	public Player getPlayer() {
		return player;
	}

	public void process(int slot, InventoryClickEvent event) {
		Consumer<InventoryClickEvent> consumer = actionMap.get(slot);
		if (consumer != null) consumer.accept(event);
	}
}