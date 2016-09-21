package net.mutinies.ships.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/*
TODO add functionality to itemstacks (inventory buttons)
Move
Rotate
Change Speed
Clear binded
 */
public class ControlPanelGUI
{
	private final static int ROWS = 3;
	private Inventory inventory;

	private Map<Integer, Consumer<InventoryClickEvent>> actionMap = new HashMap<>();

	public ControlPanelGUI(Player player)
	{
		inventory = Bukkit.createInventory(null, ROWS * 9, "Ship Control Panel");
	}

	private void addItems(Player player)
	{
		if (player.hasPermission("mutinies.ships.manageLeaders"))
		{

		}
	}

	private void setItem(int r, int c, ItemStack item, Consumer<InventoryClickEvent> consumer)
	{
		int pos = r * 9 + c;
		inventory.setItem(pos, item);
		actionMap.put(pos, consumer);
	}
}