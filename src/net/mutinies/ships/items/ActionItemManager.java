package net.mutinies.ships.items;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionItemManager
{
	private Map<List<String>, ActionItem> actionItemMap = new HashMap<>();

	public ActionItemManager(ConfigurationSection itemSection)
	{
		ActionItem[] actionItems =
				{
						new MoveItem(itemSection.getStringList("moveItem.lore")),
						new RotateItem(itemSection.getStringList("rotateItem.lore")),
						new ChangeSpeedItem(itemSection.getStringList("changeSpeedItem.lore")),
						new ToggleAutoPilotItem(itemSection.getStringList("toggleAutoPilotItem.lore"))
				};

		for (ActionItem actionItem : actionItems)
			actionItemMap.put(actionItem.getLore(), actionItem);
	}

	public ActionItem getActionItem(ItemStack stack)
	{
		return actionItemMap.get(stack.getItemMeta().getLore());
	}
}