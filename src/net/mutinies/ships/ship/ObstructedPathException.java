package net.mutinies.ships.ship;

public class ObstructedPathException extends Exception {
	public ObstructedPathException() {
		super("Path is obstructed");
	}
}
