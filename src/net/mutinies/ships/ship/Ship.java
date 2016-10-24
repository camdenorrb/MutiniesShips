package net.mutinies.ships.ship;

import net.mutinies.ships.MutiniesShips;
import net.mutinies.ships.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public class Ship {
	private UUID owner;
	private Set<UUID> canOperate = new HashSet<>();

	private String id;

	private Location offset;
	private Cuboid cuboid;
	private Location controlPanel;

	public Ship(UUID owner, Block controlPanel) {
		this.controlPanel = controlPanel.getLocation();
	}

	public void move(Vector direction) {
		Location newPos = offset.clone();
		newPos.add(direction);
		Location change = newPos.subtract(offset);
		Location minPos = cuboid.getMinPos().add(offset);
		Location maxPos = cuboid.getMaxPos().add(offset);
		ShipManager shipManager = MutiniesShips.getInstance().getShipManager();
		int oceanLevel = shipManager.getOceanLevel();

		if (cuboid.inside(minPos.add(change))) {    // Move max first
			for (int x = maxPos.getBlockX(); x > minPos.getBlockX(); x--) {
				int y = maxPos.getBlockY();
				for (; y > oceanLevel; y--)
					for (int z = maxPos.getBlockZ(); z > minPos.getBlockZ(); z--)
						moveBlock(newPos, x, y, z, Material.AIR);
				for (; y > minPos.getBlockY(); y--)
					for (int z = maxPos.getBlockZ(); z > minPos.getBlockZ(); z--)
						moveBlock(newPos, x, y, z, Material.WATER);
			}
		} else {    // Move min first
			for (int x = minPos.getBlockX(); x < maxPos.getBlockX(); x++) {
				int y = minPos.getBlockY();
				for (; y <= oceanLevel; y++)
					for (int z = minPos.getBlockZ(); z <= maxPos.getBlockZ(); z++)
					moveBlock(newPos, x, y, z, Material.WATER);
				for (; y < maxPos.getBlockY(); y++)
					for (int z = minPos.getBlockZ(); z <= maxPos.getBlockZ(); z++)
					moveBlock(newPos, x, y, z, Material.AIR);
			}
		}

		offset = newPos;
	}

	private void moveBlock(Location change, int x, int y, int z, Material replaceType) {
		Block block = new Location(offset.getWorld(), x, y, z).getBlock();
		Block newBlock = new Location(offset.getWorld(), x, y, z).add(change).getBlock();
		newBlock.setType(block.getType());
		block.setType(replaceType);
	}

	private void buildShip() throws ShipTooLargeException {
		Queue<Block> queue= new LinkedList<>();
		queue.add(controlPanel.getBlock());

		Set<Block> done = new HashSet<>();
		int maxShipSize = -1; //todo load from config

		while (!queue.isEmpty()) {
			Block block = queue.remove();
			if (!done.contains(queue)) {
				done.add(block);
				if (done.size() > maxShipSize)
					throw new ShipTooLargeException();
				if (block.getType() != Material.AIR && block.getType() != Material.WATER) {
					cuboid.expandToLocation(block.getLocation());
					queue.add(block.getRelative(BlockFace.NORTH));
					queue.add(block.getRelative(BlockFace.SOUTH));
					queue.add(block.getRelative(BlockFace.EAST));
					queue.add(block.getRelative(BlockFace.WEST));
					queue.add(block.getRelative(BlockFace.UP));
					queue.add(block.getRelative(BlockFace.DOWN));
				}
			}
		}
	}

	public void addBlock(Block block) {
		cuboid.expandToLocation(block.getLocation());
	}

	public void removeBlock(Block block) {
		if (block.getLocation().equals(controlPanel)) {
			canOperate.forEach(p -> PlayerData.getPlayerDataMap().get(p).getOwnedShips().remove(this));
			MutiniesShips.getInstance().getShipManager().getShips().remove(this);
		} else {

		}
	}

	public Cuboid getCuboid() {
		return cuboid;
	}

	public Location getControlPanel() {
		return controlPanel;
	}

	public Location getOffset() {
		return offset;
	}

	public Set<UUID> getOperators() { return canOperate; }
	
	public void addOperator(Player player) {
		canOperate.add(player.getUniqueId());
		PlayerData.getPlayerDataMap().get(player).getOwnedShips().add(this);
	}

	public String getId() { return id; }
}