package net.mutinies.ships.items;

import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class RotateItem extends ActionItem
{
	public RotateItem(List<String> lore)
	{
		super(lore);
	}

	@Override
	public void leftClick(PlayerInteractEvent e){}

	@Override
	public void rightClick(PlayerInteractEvent e)
	{

	}
}