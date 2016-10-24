package net.mutinies.ships.ship;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Cuboid {
	private Location minPos;
	private Location maxPos;

	public Cuboid(World world) {
		this(new Location(world, 0, 0, 0), new Location(world, 0, 0, 0));
	}

	public Cuboid(Location pos1, Location pos2) {
		World world = pos1.getWorld();
		if (!pos2.getWorld().equals(world))
			throw new IllegalArgumentException("Worlds from locations must match");

		double minX = Math.min(pos1.getX(), pos2.getX());
		double minY = Math.min(pos1.getY(), pos2.getY());
		double minZ = Math.min(pos1.getZ(), pos2.getZ());

		double maxX = Math.max(pos1.getX(), pos2.getX());
		double maxY = Math.max(pos1.getY(), pos2.getY());
		double maxZ = Math.max(pos1.getZ(), pos2.getZ());

		minPos = new Location(world, minX, minY, minZ);
		maxPos = new Location(world, maxX, maxY, maxZ);
	}

	public void forEachLocation(Consumer<Location> consumer) {
		for (int x = minPos.getBlockX(); x < maxPos.getBlockX(); x++)
			for (int y = minPos.getBlockY(); y < maxPos.getBlockY(); y++)
				for (int z = minPos.getBlockZ(); z < maxPos.getBlockZ(); z++)
					consumer.accept(new Location(minPos.getWorld(), x, y, z));
	}

	/*
	Untested - if bugs occur, check this
	 */
	public boolean intersects(Cuboid other) {
//		return (other.maxPos.getX() < other.minPos.getX() || other.maxPos.getX() > minPos.getX()) &&
//				(other.maxPos.getY() < other.minPos.getY() || other.maxPos.getY() > minPos.getY()) &&
//				(other.maxPos.getZ() < other.minPos.getZ() || other.maxPos.getZ() > minPos.getZ()) &&
//				(maxPos.getX() < minPos.getX() || maxPos.getX() > other.minPos.getX()) &&
//				(maxPos.getY() < minPos.getY() || maxPos.getY() > other.minPos.getY()) &&
//				(maxPos.getZ() < minPos.getZ() || maxPos.getZ() > other.minPos.getZ());

		return other.maxPos.getX() > minPos.getX() &&       //First case from other return statement shouldn't be allowed to happen
				other.maxPos.getY() > minPos.getY() &&
				other.maxPos.getZ() > minPos.getZ() &&
				maxPos.getX() > other.minPos.getX() &&
				maxPos.getY() > other.minPos.getY() &&
				maxPos.getZ() > other.minPos.getZ();
	}

	public boolean inside(Location location) {
		return location.getX() <= maxPos.getX() &&
				location.getX() >= minPos.getX() &&
				location.getY() <= maxPos.getY() &&
				location.getY() >= minPos.getY() &&
				location.getZ() <= maxPos.getZ() &&
				location.getZ() >= minPos.getZ();
	}

	public boolean edgeBlock(Location location) {
		return location.getX() == maxPos.getX() ||
				location.getX() == minPos.getX() ||
				location.getY() == maxPos.getY() ||
				location.getY() == minPos.getY() ||
				location.getZ() == maxPos.getZ() ||
				location.getZ() == minPos.getZ();
	}

	public void expandToLocation(Location location) {
		if (!location.getWorld().equals(minPos.getWorld()))
			throw new IllegalArgumentException("Must be in the proper world");
		if (location.getBlockX() > maxPos.getBlockX())
			maxPos.setX(location.getX());
		if (location.getBlockY() > maxPos.getBlockY())
			maxPos.setY(location.getY());
		if (location.getBlockZ() > maxPos.getBlockZ())
			maxPos.setZ(location.getZ());
		if (location.getBlockX() < minPos.getBlockX())
			minPos.setX(location.getX());
		if (location.getBlockY() < minPos.getBlockY())
			minPos.setY(location.getY());
		if (location.getBlockZ() < minPos.getBlockZ())
			minPos.setZ(location.getZ());
	}

	public List<Chunk> getChunks(Location offset) {
		List<Chunk> chunks = new ArrayList<>();
		Chunk startChunk = minPos.clone().add(offset).getChunk();
		Chunk endChunk = maxPos.clone().add(offset).getChunk();
		int x1 = startChunk.getX();
		int z1 = startChunk.getZ();
		int x2 = endChunk.getX();
		int z2 = endChunk.getZ();
		World world = startChunk.getWorld();
		for (int i = x1; i <= x2; i++)
			for (int j = z1; j <= z2; j++)
				chunks.add(world.getChunkAt(i, j));
		return chunks;
	}

	public Location getMinPos() {
		return minPos;
	}

	public Location getMaxPos() {
		return maxPos;
	}

	public int getSmallerValues() {
		return minPos.getBlockX() * 31 + minPos.getBlockZ();
	}
}