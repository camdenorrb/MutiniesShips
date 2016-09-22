package net.mutinies.ships.gui;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ControlPanelGUI extends GUI
{
	private static ConfigurationSection itemDataSection;

	public static void setItemDataSection(ConfigurationSection itemDataSection)
	{
		ControlPanelGUI.itemDataSection = itemDataSection;
	}

	private enum BindType
	{
		NONE, ROTATE, TOGGLEAUTO, MOVE, CHANGESPEED
	}

	private BindType expectedBind = BindType.NONE;

	public ControlPanelGUI(Player player)
	{
		super(player, 3, "Control Panel");
	}

	@Override
	protected void addItems()
	{
		setItem(itemDataSection, "rotateItem", e ->
		{
			expectedBind = BindType.ROTATE;
		});
		setItem(itemDataSection, "clearBindedToolsItem", e ->
		{

		});
		setItem(itemDataSection, "autoPilotItem", e ->
		{

		});
		setItem(itemDataSection, "abandonShipItem", e ->
		{

		});
		setItem(itemDataSection, "moveItem", e ->
		{

		});
		setItem(itemDataSection, "changeSpeedItem", e ->
		{

		});
		setItem(itemDataSection, "transferOwnItem", e ->
		{

		});
		if (player.hasPermission("mutinies.ships.manageLeaders"))
		{
			setItem(itemDataSection, "manageLeadersItem", e ->
			{

			});
		}
	}

	@Override
	public void process(int slot, InventoryClickEvent event)
	{
		if (slot < event.getView().getTopInventory().getSize())
			super.process(slot, event);
		else
		{
			switch (expectedBind)
			{
				case ROTATE:

					break;
				case TOGGLEAUTO:
					break;
				case MOVE:
					break;
				case CHANGESPEED:
					break;
			}

			expectedBind = BindType.NONE;
		}
	}
}