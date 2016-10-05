package net.mutinies.ships.gui;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemData
{
	private ItemStack stack;
	private int slot;

	public ItemData(ConfigurationSection itemsSection, String subsection)
	{
		ConfigurationSection itemSection = itemsSection.getConfigurationSection(subsection);
		String name = itemSection.getString("name");
		int row = itemSection.getInt("row");
		int col = itemSection.getInt("col");
		String item = itemSection.getString("item");
		int count = itemSection.getInt("count", 1);
		byte data = (byte)itemSection.getInt("data", 0);
		List<String> lore = itemSection.getStringList("lore");

		int pos = row * 9 + col;
		ItemStack itemStack = GUI.applyData(new ItemStack(Material.getMaterial(item), count, data), name, lore);
		slot = pos;
		stack = itemStack;
	}

	public ItemStack getStack()
	{
		return stack;
	}
	public int getSlot()
	{
		return slot;
	}
}