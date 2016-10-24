package net.mutinies.ships.gui;

import net.mutinies.ships.MutiniesShips;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ControlPanelGUI extends GUI {
	private static ConfigurationSection itemDataSection;

	public static void setItemDataSection(ConfigurationSection itemDataSection) {
		ControlPanelGUI.itemDataSection = itemDataSection;
	}

	private enum BindType {NONE, ROTATE, TOGGLE_AUTO, MOVE, CHANGE_SPEED}

	private BindType expectedBind = BindType.NONE;

	private ItemData rotateData;
	private ItemData clearBindedData;
	private ItemData autoPilotData;
	private ItemData abandonShipItem;
	private ItemData moveItem;
	private ItemData changeSpeedItem;
	private ItemData transferOwnItem;
	private ItemData manageLeadersData;

	public ControlPanelGUI(Player player) {
		super(player, 3, "Control Panel");
		addItems();
		player.openInventory(inventory);
		GUIManager.getInventoryGUIMap().put(player.getOpenInventory(), this);
	}

	@Override
	protected void addItems() {
		ItemStack selectedStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData());
		rotateData = new ItemData(itemDataSection, "rotateItem");
		clearBindedData = new ItemData(itemDataSection, "clearBindedToolsItem");
		autoPilotData = new ItemData(itemDataSection, "autoPilotItem");
		abandonShipItem = new ItemData(itemDataSection, "abandonShipItem");
		moveItem = new ItemData(itemDataSection, "moveItem");
		changeSpeedItem = new ItemData(itemDataSection, "changeSpeedItem");
		transferOwnItem = new ItemData(itemDataSection, "transferOwnItem");
		manageLeadersData = new ItemData(itemDataSection, "manageLeadersItem");

		setItem(rotateData, e ->
		{
			expectedBind = BindType.ROTATE;
			ItemMeta rotateMeta = rotateData.getStack().getItemMeta();
			player.getOpenInventory().setItem(rotateData.getSlot(),
					applyData(selectedStack.clone(), rotateMeta.getDisplayName(), rotateMeta.getLore()));
		});
		setItem(clearBindedData, e ->
		{
			for (ItemStack itemStack : player.getInventory()) {
				if (itemStack == null
						|| itemStack.getItemMeta() == null
						|| itemStack.getItemMeta().getLore() == null) continue;
				ItemMeta itemMeta = itemStack.getItemMeta();
				List<String> lore = itemMeta.getLore();
				if (lore.equals(MutiniesShips.getInstance().getActionItemManager().getRotateItem().getLore()) ||
						lore.equals(MutiniesShips.getInstance().getActionItemManager().getMoveItem().getLore()) ||
						lore.equals(MutiniesShips.getInstance().getActionItemManager().getChangeSpeedItem().getLore()) ||
						lore.equals(MutiniesShips.getInstance().getActionItemManager().getToggleAutoPilotItem().getLore())) {
					itemMeta.setLore(null);
					itemStack.setItemMeta(itemMeta);
				}
			}
		});
		setItem(autoPilotData, e ->
		{
			expectedBind = BindType.TOGGLE_AUTO;
			ItemMeta autoPilotMeta = autoPilotData.getStack().getItemMeta();
			player.getOpenInventory().setItem(autoPilotData.getSlot(),
					applyData(selectedStack.clone(), autoPilotMeta.getDisplayName(), autoPilotMeta.getLore()));
		});
		setItem(abandonShipItem, e ->
		{

		});
		setItem(moveItem, e ->
		{
			expectedBind = BindType.MOVE;
			ItemMeta moveMeta = moveItem.getStack().getItemMeta();
			player.getOpenInventory().setItem(moveItem.getSlot(),
					applyData(selectedStack.clone(), moveMeta.getDisplayName(), moveMeta.getLore()));
		});
		setItem(changeSpeedItem, e ->
		{
			expectedBind = BindType.CHANGE_SPEED;
			ItemMeta changeSpeedMeta = changeSpeedItem.getStack().getItemMeta();
			player.getOpenInventory().setItem(changeSpeedItem.getSlot(),
					applyData(selectedStack.clone(), changeSpeedMeta.getDisplayName(), changeSpeedMeta.getLore()));
		});
		setItem(transferOwnItem, e ->
		{
			new OwnerTransferGUI(player);
		});
		if (player.hasPermission("mutinies.ships.manageLeaders")) {
			setItem(manageLeadersData, e ->
			{

			});
		}
	}

	@Override
	public void process(int slot, InventoryClickEvent event) {
		if (slot < event.getView().getTopInventory().getSize()) {
			clearQueued();
			super.process(slot, event);
		} else if (expectedBind != BindType.NONE) {
			ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
			if (itemMeta == null)
				clearQueued();
			else if (itemMeta.getLore() == null) {
				switch (expectedBind) {
					case ROTATE:
						itemMeta.setLore(MutiniesShips.getInstance().getActionItemManager().getRotateItem().getLore());
						inventory.setItem(rotateData.getSlot(), rotateData.getStack());
						break;
					case TOGGLE_AUTO:
						itemMeta.setLore(MutiniesShips.getInstance().getActionItemManager().getToggleAutoPilotItem().getLore());
						inventory.setItem(autoPilotData.getSlot(), autoPilotData.getStack());
						break;
					case MOVE:
						itemMeta.setLore(MutiniesShips.getInstance().getActionItemManager().getMoveItem().getLore());
						inventory.setItem(moveItem.getSlot(), moveItem.getStack());
						break;
					case CHANGE_SPEED:
						itemMeta.setLore(MutiniesShips.getInstance().getActionItemManager().getChangeSpeedItem().getLore());
						inventory.setItem(changeSpeedItem.getSlot(), changeSpeedItem.getStack());
						break;
				}
				event.getCurrentItem().setItemMeta(itemMeta);
				expectedBind = BindType.NONE;
			} else {
				event.getWhoClicked().sendMessage(ChatColor.RED + "You cannot bind an item already in use.");
			}
		}
	}

	public void clearQueued() {
		switch (expectedBind) {
			case ROTATE:
				inventory.setItem(rotateData.getSlot(), rotateData.getStack());
				break;
			case TOGGLE_AUTO:
				inventory.setItem(autoPilotData.getSlot(), autoPilotData.getStack());
				break;
			case MOVE:
				inventory.setItem(moveItem.getSlot(), moveItem.getStack());
				break;
			case CHANGE_SPEED:
				inventory.setItem(changeSpeedItem.getSlot(), changeSpeedItem.getStack());
				break;
		}
		expectedBind = BindType.NONE;
	}
}