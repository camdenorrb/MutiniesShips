package net.mutinies.ships.items;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionItemManager
{
	private Map<List<String>, ActionItem> actionItemMap = new HashMap<>();

	private RotateItem rotateItem;
	private MoveItem moveItem;
	private ChangeSpeedItem changeSpeedItem;
	private ToggleAutoPilotItem toggleAutoPilotItem;
	
	public ActionItemManager(ConfigurationSection itemSection)
	{
		ActionItem[] actionItems =
				{
						new MoveItem(itemSection.getStringList("moveItem.lore")),
						new RotateItem(itemSection.getStringList("rotateItem.lore")),
						new ChangeSpeedItem(itemSection.getStringList("changeSpeedItem.lore")),
						new ToggleAutoPilotItem(itemSection.getStringList("toggleAutoPilotItem.lore"))
				};
				
		rotateItem = new RotateItem(itemSection.getStringList("rotateItem.lore"));
		moveItem = new MoveItem(itemSection.getStringList("moveItem.lore"));
		changeSpeedItem = new ChangeSpeedItem(itemSection.getStringList("changeSpeedItem.lore"));
		toggleAutoPilotItem = new ToggleAutoPilotItem(itemSection.getStringList("toggleAutoPilotItem.lore"));

		actionItemMap.put(rotateItem.getLore(), rotateItem);
		actionItemMap.put(moveItem.getLore(), moveItem);
		actionItemMap.put(changeSpeedItem.getLore(), changeSpeedItem);
		actionItemMap.put(toggleAutoPilotItem.getLore(), toggleAutoPilotItem);
	}

	public ActionItem getActionItem(ItemStack stack)
	{
		if (stack == null || stack.getItemMeta() == null || stack.getItemMeta().getLore() == null) return null;
		return actionItemMap.get(stack.getItemMeta().getLore());
	}
	public RotateItem getRotateItem()
	{
		return rotateItem;
	}
	public MoveItem getMoveItem()
	{
		return moveItem;
	}
	public ChangeSpeedItem getChangeSpeedItem()
	{
		return changeSpeedItem;
	}
	public ToggleAutoPilotItem getToggleAutoPilotItem()
	{
		return toggleAutoPilotItem;
	}
}