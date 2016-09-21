package net.mutinies.ships.items;

import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public abstract class ActionItem
{
	private final List<String> lore;

	ActionItem(List<String> lore)
	{
		this.lore = lore;
	}

	public abstract void leftClick(PlayerInteractEvent e);
	public abstract void rightClick(PlayerInteractEvent e);
	public List<String> getLore() { return lore; }
}