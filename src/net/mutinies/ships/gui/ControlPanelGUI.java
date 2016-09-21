package net.mutinies.ships.gui;

import org.bukkit.entity.Player;

/*
TODO add functionality to itemstacks (inventory buttons)
Move
Rotate
Change Speed
Clear binded
 */
public class ControlPanelGUI extends GUI
{
	public ControlPanelGUI(Player player)
	{
		super(player, 3, "Control Panel");
	}

	@Override
	protected void addItems()
	{


		if (player.hasPermission("mutinies.ships.manageLeaders"))
		{

		}
	}
}