package net.mutinies.ships.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class GUI
{
	protected Player player;
	protected Inventory inventory;

	private Map<Integer, Consumer<InventoryClickEvent>> actionMap = new HashMap<>();

	public GUI(Player player, int rows, String title)
	{
		inventory = Bukkit.createInventory(null, rows * 9, title);
		GUIManager.getInventoryGUIMap().put(inventory, this);
		addItems();
		player.openInventory(inventory);
	}

	protected abstract void addItems();

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
		actionMap.get(slot).accept(event);
	}
}
