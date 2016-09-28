package net.mutinies.ships.gui;

import net.mutinies.ships.MutiniesShips;
import net.mutinies.ships.items.ActionItemManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

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
				if (itemStack == null
						|| itemStack.getItemMeta() == null
						|| itemStack.getItemMeta().getLore() == null) continue;
				ItemMeta itemMeta = itemStack.getItemMeta();
				List<String> lore = itemMeta.getLore();
				if (lore.equals(MutiniesShips.getInstance().getActionItemManager().getRotateItem().getLore()) ||
						lore.equals(MutiniesShips.getInstance().getActionItemManager().getMoveItem().getLore()) ||
						lore.equals(MutiniesShips.getInstance().getActionItemManager().getChangeSpeedItem().getLore()) ||
						lore.equals(MutiniesShips.getInstance().getActionItemManager().getToggleAutoPilotItem().getLore()))
				{
					itemMeta.setLore(null);
					itemStack.setItemMeta(itemMeta);
				}
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
			player.sendMessage("Move clicked");
		});
		setItem(itemDataSection, "changeSpeedItem", e ->
		{
			expectedBind = BindType.CHANGESPEED;
		});
		setItem(itemDataSection, "transferOwnItem", e ->
		{
			new OwnerTransferGUI(player);
		});
		if (player.hasPermission("mutinies.ship.manageLeaders"))
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
			ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
			switch (expectedBind)
			{
				case ROTATE:
					itemMeta.setLore(MutiniesShips.getInstance().getActionItemManager().getRotateItem().getLore());
					event.getCurrentItem().setItemMeta(itemMeta);
					break;
				case TOGGLEAUTO:
					itemMeta.setLore(MutiniesShips.getInstance().getActionItemManager().getToggleAutoPilotItem().getLore());
					event.getCurrentItem().setItemMeta(itemMeta);
					break;
				case MOVE:
					itemMeta.setLore(MutiniesShips.getInstance().getActionItemManager().getMoveItem().getLore());
					event.getCurrentItem().setItemMeta(itemMeta);
					break;
				case CHANGESPEED:
					itemMeta.setLore(MutiniesShips.getInstance().getActionItemManager().getChangeSpeedItem().getLore());
					event.getCurrentItem().setItemMeta(itemMeta);
					break;
			}

			expectedBind = BindType.NONE;
		}
	}
}