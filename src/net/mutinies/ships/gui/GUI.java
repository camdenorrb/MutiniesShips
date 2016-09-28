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

public abstract class GUI
{
	protected Player player;
	protected Inventory inventory;

	protected Map<Integer, Consumer<InventoryClickEvent>> actionMap = new HashMap<>();

	public GUI(Player player, int rows, String title)
	{
		inventory = Bukkit.createInventory(null, rows * 9, title);
		this.player = player;
		addItems();
		player.openInventory(inventory);
		GUIManager.getInventoryGUIMap().put(player.getOpenInventory(), this);
	}

	public static ItemStack applyData(ItemStack stack, String name, String... lore)
	{
		ItemMeta itemMeta = stack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(Arrays.asList(lore));
		stack.setItemMeta(itemMeta);
		return stack;
	}

	public static ItemStack applyData(ItemStack stack, String name, List<String> lore)
	{
		ItemMeta itemMeta = stack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		stack.setItemMeta(itemMeta);
		return stack;
	}

	protected abstract void addItems();

	protected void setItem(ConfigurationSection parent, String itemSection, Consumer<InventoryClickEvent> consumer)
	{
		setItem(parent.getConfigurationSection(itemSection), consumer);
	}

	protected void setItem(ConfigurationSection itemSection, Consumer<InventoryClickEvent> consumer)
	{
		String name = itemSection.getString("name");
		int row = itemSection.getInt("row");
		int col = itemSection.getInt("col");
		String item = itemSection.getString("item");
		int count = itemSection.getInt("count", 1);
		byte data = (byte)itemSection.getInt("data", 0);
		List<String> lore = itemSection.getStringList("lore");

		int pos = row * 9 + col;
		ItemStack itemStack = applyData(new ItemStack(Material.getMaterial(item), count, data), name, lore);
		inventory.setItem(pos, itemStack);
		actionMap.put(pos, consumer);
	}

	protected void setItem(int r, int c, ItemStack item, Consumer<InventoryClickEvent> consumer)
	{
		int pos = r * 9 + c;
		inventory.setItem(pos, item);
		actionMap.put(pos, consumer);
	}

	public Player getPlayer()
	{
		return player;
	}

	public void process(int slot, InventoryClickEvent event)
	{
		Consumer<InventoryClickEvent> consumer = actionMap.get(slot);
		if (consumer != null)
			consumer.accept(event);
	}
}
