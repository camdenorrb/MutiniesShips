package net.mutinies.ships.commands;

import net.mutinies.ships.gui.ControlPanelGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandShip implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player pSender = ((Player) sender);
			new ControlPanelGUI(pSender);
		}
		return true;
	}
}
