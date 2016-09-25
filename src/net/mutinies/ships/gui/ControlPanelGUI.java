package net.mutinies.ships.gui;

import net.mutinies.ships.MutiniesShips;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ControlPanelGUI extends GUI
{
	private static ConfigurationSection itemDataSection;

	public static void setItemDataSection(ConfigurationSection itemDataSection)
	{
		ControlPanelGUI.itemDataSection = itemDataSection;
	}

	private enum BindType { NONE, ROTATE, TOGGLEAUTO, MOVE, CHANGESPEED }

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
			for (ItemStack itemStack : player.getInventory())
			{
				List<String> lore = itemStack.getItemMeta().getLore();
				if (lore.equals(MutiniesShips.getInstance().getActionItemManager().getRotateItem().getLore()) ||
						lore.equals(MutiniesShips.getInstance().getActionItemManager().getRotateItem().getLore()) ||
						lore.equals(MutiniesShips.getInstance().getActionItemManager().getRotateItem().getLore()) ||
						lore.equals(MutiniesShips.getInstance().getActionItemManager().getRotateItem().getLore()))
					itemStack.getItemMeta().setLore(null);
			}

		});
		setItem(itemDataSection, "autoPilotItem", e ->
		{
			expectedBind = BindType.TOGGLEAUTO;
		});
		setItem(itemDataSection, "abandonShipItem", e ->
		{

		});
		setItem(itemDataSection, "moveItem", e ->
		{
			expectedBind = BindType.MOVE;
		});
		setItem(itemDataSection, "changeSpeedItem", e ->
		{
			expectedBind = BindType.CHANGESPEED;
		});
		setItem(itemDataSection, "transferOwnItem", e ->
		{
			new OwnerTransferGUI(player);
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
					event.getCurrentItem().getItemMeta().setLore(
							MutiniesShips.getInstance().getActionItemManager().getRotateItem().getLore());
					break;
				case TOGGLEAUTO:
					event.getCurrentItem().getItemMeta().setLore(
							MutiniesShips.getInstance().getActionItemManager().getToggleAutoPilotItem().getLore());
					break;
				case MOVE:
					event.getCurrentItem().getItemMeta().setLore(
							MutiniesShips.getInstance().getActionItemManager().getMoveItem().getLore());
					break;
				case CHANGESPEED:
					event.getCurrentItem().getItemMeta().setLore(
							MutiniesShips.getInstance().getActionItemManager().getChangeSpeedItem().getLore());
					break;
			}

			expectedBind = BindType.NONE;
		}
	}
}