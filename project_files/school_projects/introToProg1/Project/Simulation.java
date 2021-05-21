import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * A class that remembers what type of particle is at each location in a grid.
 * 
 * @author Chad Hogg, Jonathan Wilkins
 */
public class Simulation {
	
	/** A list of the names of the types of particles. */
	public static final String[] NAMES = {"Empty", "Metal", "Sand", "Water", "Acid", "Smoke", "Ice", "Bomb"};

	// Each of these constants represents a different type of particle that can appear in the simulation.
	/** A space that has no particle in it at all. */
	public static final int EMPTY = 0;
	/** The space is filled with metal. */
	public static final int METAL = 1;
	/** The space is filled with sand. */
	public static final int SAND = 2;
	/** The space is filled with water. */
	public static final int WATER = 3;
	/** The space is filled with acid. */
	public static final int ACID = 4;
	/** The space is filled with smoke. */
	public static final int SMOKE = 5;
	/** The space is filled with ice. */
	public static final int ICE = 6;
	/** The space is filled with a bomb. */
	public static final int BOMB_1 = 7;  // Even IDs for orange colored bombs
	/** The space is filled with a bomb. */
	public static final int BOMB_2 = 8;  // Odd IDs for white colored bombs
	/** The space is filled with a bomb. */
	public static final int BOMB_3 = 9;
	/** The space is filled with a bomb. */
	public static final int BOMB_4 = 10;
	/** The space is filled with a bomb. */
	public static final int BOMB_5 = 11;
	/** The space is filled with a bomb. */
	public static final int BOMB_6 = 12;
	/** The space is filled with a bomb. */
	public static final int BOMB_7 = 13;
	/** The space is filled with a bomb. */
	public static final int BOMB_8 = 14;
	/** The space is filled with a bomb. */
	public static final int BOMB_9 = 15;
	/** The space is filled with a bomb. */
	public static final int BOMB_10 = 16;

	// Each of these constants stores the color of a type of particle.
	/** Empty spaces are black. */
	public static final Color EMPTY_COLOR = new Color(0, 0, 0);
	/** Metal is gray. */
	public static final Color METAL_COLOR = new Color(128, 128, 128);
	/** Sand is yellow-brown. */
	public static final Color SAND_COLOR = new Color(252, 226, 122);
	/** Water is blue. */
	public static final Color WATER_COLOR = new Color(31, 132, 246);
	/** Acid is pink. */
	public static final Color ACID_COLOR = new Color(252, 117, 234);
	/** Smoke is light-grey/white. */
	public static final Color SMOKE_COLOR = new Color(195, 195, 195);
	/** The bomb flashes between white and orange. */
	public static final Color BOMB_ORANGE_COLOR = new Color(255, 153, 21);
	/** The bomb flashes between white and orange. */
	public static final Color BOMB_WHITE_COLOR = new Color(255, 255, 255);
	/** Ice is light blue. */
	public static final Color ICE_COLOR = new Color(204, 255, 255);

	/** Initializes a two dimensional array for the grid that contains the type of particle at each location, or lack thereof. */
	private int[][] grid;
	/** Initializes a Random object for choosing particles to update. */
	private Random rand;
	/** Initializes a Random object for determining the horizontal movement of water. */
	private PrintStream writeToFile;
	/** Initializes a Scanner object to load a picture/layout from a file. */
	private Scanner readFile;
	
	/**
	 * Constructs a new Simulation.
	 * 
	 * @param height The number of rows in the simulation.
	 * @param width The number of columns in the simulation.
	 */
	public Simulation(int height, int width) {
		grid = new int[height][width];
		rand = new Random();
	}
	
	/**
	 * Gets the height of the simulation.
	 * 
	 * @return The height of the simulation.
	 */
	public int getHeight() {
		return grid.length;
	}
	
	/**
	 * Gets the width of the simulation.
	 * 
	 * @return The width of the simulation.
	 */
	public int getWidth() {
		return grid[0].length;
	}

	/**
	 * Fills a location in the grid with a type of material.
	 * 
	 * @param row The row to fill.
	 * @param column The column to fill.
	 * @param type The type of material.
	 */
	public void fillLocation(int row, int column, int type) {
		grid[row][column] = type;
	}
	
	/**
	 * Gets the material at a location in the grid.
	 * 
	 * @param row The requested row.
	 * @param column The requested column.
	 * @return The type of material at that location.
	 */
	public int getParticleType(int row, int column) {
		return grid[row][column];
	}

	/**
	 * Sets the colors in a display based on what particles are at each location in the grid.
	 * 
	 * @param display A display to update.
	 */
	public void updateDisplay(SandDisplay display) {
		for (int row = 0; row < getHeight(); row ++) {
			for (int column = 0; column < getWidth(); column ++) {
				if (grid[row][column] == EMPTY) {
					display.setColor(row, column, EMPTY_COLOR);
				}
				else if (grid[row][column] == METAL) {
					display.setColor(row, column, METAL_COLOR);
				}
				else if (grid[row][column] == SAND) {
					display.setColor(row, column, SAND_COLOR);
				}
				else if (grid[row][column] == WATER) {
					display.setColor(row, column, WATER_COLOR);
				}
				else if (grid[row][column] == ACID) {
					display.setColor(row, column, ACID_COLOR);
				}
				else if (grid[row][column] == SMOKE) {
					display.setColor(row, column, SMOKE_COLOR);
				}
				else if (grid[row][column] == BOMB_1 || grid[row][column] == BOMB_3 ||
						 grid[row][column] == BOMB_5 || grid[row][column] == BOMB_7 ||
						 grid[row][column] == BOMB_9) {
					display.setColor(row, column, BOMB_ORANGE_COLOR);
				}
				else if (grid[row][column] == BOMB_2 || grid[row][column] == BOMB_4 ||
						 grid[row][column] == BOMB_6 || grid[row][column] == BOMB_8 ||
						 grid[row][column] == BOMB_10) {
					display.setColor(row, column, BOMB_WHITE_COLOR);
				}
				else if (grid[row][column] == ICE) {
					display.setColor(row, column, ICE_COLOR);
				}
			}
		}
	}

	/**
	 * Causes one particle to take an action.
	 */
	public void doOneUpdate() {
		int chosenRow = rand.nextInt(getHeight());
		int chosenColumn = rand.nextInt(getWidth());
		if (grid[chosenRow][chosenColumn] == SAND) {
			updateSand(chosenRow, chosenColumn);
		}
		else if (grid[chosenRow][chosenColumn] == WATER) {
			updateWater(chosenRow, chosenColumn);
		}
		else if (grid[chosenRow][chosenColumn] == ACID) {
			updateAcid(chosenRow, chosenColumn);
		}
		else if (grid[chosenRow][chosenColumn] == SMOKE) {
			updateSmoke(chosenRow, chosenColumn);
		}
		else if (grid[chosenRow][chosenColumn] == BOMB_1 || grid[chosenRow][chosenColumn] == BOMB_2 ||
				 grid[chosenRow][chosenColumn] == BOMB_3 || grid[chosenRow][chosenColumn] == BOMB_4 ||
				 grid[chosenRow][chosenColumn] == BOMB_5 || grid[chosenRow][chosenColumn] == BOMB_6 ||
				 grid[chosenRow][chosenColumn] == BOMB_7 || grid[chosenRow][chosenColumn] == BOMB_8 ||
				 grid[chosenRow][chosenColumn] == BOMB_9 || grid[chosenRow][chosenColumn] == BOMB_10) {
			updateBomb(chosenRow, chosenColumn);
		}
		else if (grid[chosenRow][chosenColumn] == ICE) {
			updateIce(chosenRow, chosenColumn);
		}
	}
	
	/**
	 * Saves the current picture to a file.
	 * 
	 * @param file The file to save to.
	 * @throws FileNotFoundException If there is a problem printing to the file.
	 */
	public void save(File file) throws FileNotFoundException {
		writeToFile = new PrintStream(file);
		writeToFile.print(toString());
	}
	
	/**
	 * Loads a picture from a file.
	 * 
	 * @param file The file to load.
	 * @throws FileNotFoundException If there is a problem reading from the file.
	 */
	public void load(File file) throws FileNotFoundException {
		readFile = new Scanner(file);
		int rows = readFile.nextInt();
		int columns = readFile.nextInt();
		grid = new int[rows][columns];
		for (int rowIndex = 0; rowIndex < rows; rowIndex ++) {
			for (int columnIndex = 0; columnIndex < columns; columnIndex ++) {
				grid[rowIndex][columnIndex] = readFile.nextInt();
			}
		}
	}
	
	/**
	 * Generates a string of the array of particles on the screen.
	 * 
	 * @return Returns a string of the two dimensional grid of integers that represent the different particle types.
	 */
	public String toString() {
		String output = getHeight() + " " + getWidth() + "\n";
		for (int row = 0; row < getHeight(); row ++) {
			for (int column = 0; column < getWidth(); column ++) {
				output += grid[row][column];
				output += " ";
			}
			if (row != getHeight()) {
				output += "\n";
			}
		}
		return output;
	}
	
	/**
	 * Swaps the locations of two particles to simulate movement.
	 * 
	 * @param p1row Row of the first particle.
	 * @param p1column Column of the first particle.
	 * @param p2row Row of the second particle.
	 * @param p2column Column of the second particle.
	 */
	public void swapLocations(int p1row, int p1column, int p2row, int p2column) {
		int p1type = grid[p1row][p1column];
		int p2type = grid[p2row][p2column];
		grid[p1row][p1column] = p2type;
		grid[p2row][p2column] = p1type;
	}
	
	/**
	 * Updates each sand particle based on its physics.
	 * 
	 * @param row The row of the sand particle.
	 * @param column The column of the sand particle.
	 */
	public void updateSand(int row, int column) {
		if (row == getHeight() - 1) {
			grid[row][column] = EMPTY;
		}
		else if (grid[row + 1][column] == EMPTY || grid[row + 1][column] == WATER || grid[row + 1][column] == ACID) {
			swapLocations(row, column, row + 1, column);
		}
	}
	
	/**
	 * Updates each water particle based on its physics.
	 * Has a 5% chance of turning into an ice particle (works alongside the updateIce method).
	 * 
	 * @param row The row of the water particle.
	 * @param column The column of the water particle.
	 */
	public void updateWater(int row, int column) {
		int freeze = rand.nextInt(5000);
		if (freeze == 2500) {
			grid[row][column] = ICE;
		}
		else if (row == getHeight() - 1) {
			grid[row][column] = EMPTY;
		}
		else if (grid[row + 1][column] == EMPTY) {
			swapLocations(row, column, row + 1, column);
		}
		else if (grid[row + 1][column] != EMPTY) {
			int direction = rand.nextInt(2);
			if (direction == 0 && (column > 0)) {
				if (grid[row][column - 1] == EMPTY) {
					swapLocations(row, column, row, column - 1);
				}
			}
			else if (direction == 0 && (column - 1) < 0) {
				grid[row][column] = EMPTY;
			}
			else if (direction == 1 && (column + 1) < getWidth()) {
				if (grid[row][column + 1] == EMPTY) {
					swapLocations(row, column, row, column + 1);
				}
			}
			else if (direction == 1 && (column + 1) == getWidth()) {
				grid[row][column] = EMPTY;
			}
		}
	}
	
	/**
	 * Updates each acid particle based on its physics.
	 * 
	 * @param row The row of the acid particle.
	 * @param column The column of the acid particle.
	 */
	public void updateAcid(int row, int column) {
		int behavior = rand.nextInt(20);
		if (behavior >= 0 && behavior <= 15) {
			if (row == getHeight() - 1) {
				grid[row][column] = EMPTY;
			}
			else if (grid[row + 1][column] == EMPTY) {
				swapLocations(row, column, row + 1, column);
			}
			else if (grid[row + 1][column] != EMPTY) {
				int direction = rand.nextInt(2);
				if (direction == 0 && (column > 0)) {
					if (grid[row][column - 1] == EMPTY) {
						swapLocations(row, column, row, column - 1);
					}
				}
				else if (direction == 0 && (column - 1) < 0) {
					grid[row][column] = EMPTY;
				}
				else if (direction == 1 && (column + 1) < getWidth()) {
					if (grid[row][column + 1] == EMPTY) {
						swapLocations(row, column, row, column + 1);
					}
				}
				else if (direction == 1 && (column + 1) == getWidth()) {
					grid[row][column] = EMPTY;
				}
			}
		}
		else if (behavior >= 16 && behavior <= 18) {
			int direction = rand.nextInt(4);
			if (direction == 0 && row > 0) { // up row - 1
				if (grid[row - 1][column] == WATER) {
					swapLocations(row, column, row - 1, column);
				}
			}
			else if (direction == 1 && row < (getHeight() - 1)) { // down row + 1
				if (grid[row + 1][column] == WATER) {
					swapLocations(row, column, row + 1, column);
				}
			}
			else if (direction == 2 && column > 0) { // left column - 1
				if (grid[row][column - 1] == WATER) {
					swapLocations(row, column, row, column - 1);
				}
			}
			else if (direction == 3 && column < (getWidth() - 1)) { // right column + 1
				if (grid[row][column + 1] == WATER) {
					swapLocations(row, column, row, column + 1);
				}
			}
		}
		else if (behavior == 19) {
			if (row < (getHeight() - 1)) {
				if (grid[row + 1][column] == SAND || grid[row + 1][column] == METAL || grid[row + 1][column] == ICE) {
					grid[row + 1][column] = EMPTY;
				}
				else if (grid[row + 1][column] == BOMB_1 || grid[row + 1][column] == BOMB_2 ||
				    grid[row + 1][column] == BOMB_3 || grid[row + 1][column] == BOMB_4 ||
				    grid[row + 1][column] == BOMB_5 || grid[row + 1][column] == BOMB_6 ||
				    grid[row + 1][column] == BOMB_7 || grid[row + 1][column] == BOMB_8 ||
				    grid[row + 1][column] == BOMB_9 || grid[row + 1][column] == BOMB_10) {
					grid[row + 1][column] = BOMB_10;
					updateBomb(row + 1, column + 1);
				}
			}
		}
	}
	
	/**
	 * Updates each smoke particle based on its physics.
	 * Smoke floats upward and is destroyed by any gravity affected particle (sand/water/acid),
	 * spreads sideways if metal is above it,
	 * and gets deleted if it goes off the top of the screen.
	 * 
	 * @param row The row of the smoke particle.
	 * @param column The column of the smoke particle.
	 */
	public void updateSmoke(int row, int column) {
		int direction = rand.nextInt(2);
		if (row == 0) {
			grid[row][column] = EMPTY;
		}
		else if (grid[row - 1][column] == EMPTY) {
			swapLocations(row, column, row - 1, column);
		}
		else if (grid[row - 1][column] == SAND || grid[row - 1][column] == WATER || grid[row - 1][column] == ACID) {
			swapLocations(row, column, row - 1, column);
			grid[row - 1][column] = EMPTY;
		}
		else if (grid[row - 1][column] == METAL || grid[row - 1][column] == SMOKE) {
			if (direction == 0 && column > 0) {
				if (grid[row][column - 1] == EMPTY) {
					swapLocations(row, column, row, column - 1);
				}
			}
			else if (direction == 0 && column == 0) {
				grid[row][column] = EMPTY;
			}
			else if (direction == 1 && column < (getWidth() - 1)) {
				if (grid[row][column + 1] == EMPTY) {
					swapLocations(row, column, row, column + 1);
				}
			}
			else if (direction == 1 && column == (getWidth() - 1)){
				grid[row][column] = EMPTY;
			}
		}
		if (row > 0) { // up row - 1
			if (grid[row - 1][column] == ICE) {
				grid[row - 1][column] = WATER;
			}
		}
		if (row < (getHeight() - 1)) { // down row + 1
			if (grid[row + 1][column] == ICE) {
				grid[row + 1][column] = WATER;
			}
		}
		if (column > 0) { // left column - 1
			if (grid[row][column - 1] == ICE) {
				grid[row][column - 1] = WATER;
			}
		}
		if (column < (getWidth() - 1)) { // right column + 1
			if (grid[row][column + 1] == ICE) {
				grid[row][column + 1] = WATER;
			}
		}
	}
	
	/**
	 * Updates each bomb particle based on its physics.
	 * Bombs flash orange and white, blow up the 8 surrounding particles,
	 * and are not affected by acid or gravity. 
	 * 
	 * @param row The row of the bomb particle.
	 * @param column The column of the bomb particle.
	 */
	public void updateBomb(int row, int column) {
		if (grid[row][column] == BOMB_1) {
			grid[row][column] = BOMB_2;
		}
		else if (grid[row][column] == BOMB_2) {
			grid[row][column] = BOMB_3;
		}
		else if (grid[row][column] == BOMB_3) {
			grid[row][column] = BOMB_4;
		}
		else if (grid[row][column] == BOMB_4) {
			grid[row][column] = BOMB_5;
		}
		else if (grid[row][column] == BOMB_5) {
			grid[row][column] = BOMB_6;
		}
		else if (grid[row][column] == BOMB_6) {
			grid[row][column] = BOMB_7;
		}
		else if (grid[row][column] == BOMB_7) {
			grid[row][column] = BOMB_8;
		}
		else if (grid[row][column] == BOMB_8) {
			grid[row][column] = BOMB_9;
		}
		else if (grid[row][column] == BOMB_9) {
			grid[row][column] = BOMB_10;
		}
		else if (grid[row][column] == BOMB_10) {
			if (row > 0 && row < (getHeight() - 1) && column > 0 && column < (getWidth() - 1)) {
				// Bomb does not touch any edge
				grid[row - 1][column - 1] = EMPTY;
				grid[row - 1][column] = EMPTY;
				grid[row - 1][column + 1] = EMPTY;
				grid[row][column - 1] = EMPTY;
				grid[row][column + 1] = EMPTY;
				grid[row + 1][column - 1] = EMPTY;
				grid[row + 1][column] = EMPTY;
				grid[row + 1][column + 1] = EMPTY;
			}
			else if (row == 0 && column == 0) {
				// Bomb touches top and left
				grid[row + 1][column + 2] = EMPTY;
				grid[row + 2][column + 1] = EMPTY;
				grid[row + 2][column + 2] = EMPTY;
			}
			else if (row == 0 && column > 0 && column < (getWidth() - 1)) {
				// Bomb touches top only
				grid[row + 1][column - 1] = EMPTY;
				grid[row + 1][column + 1] = EMPTY;
				grid[row + 2][column - 1] = EMPTY;
				grid[row + 2][column] = EMPTY;
				grid[row + 2][column + 1] = EMPTY;
			}
			else if (row == 0 && column == (getWidth() - 1)) {
				// Bomb touches top and right
				grid[row + 1][column - 2] = EMPTY;
				grid[row + 2][column - 2] = EMPTY;
				grid[row + 2][column - 1] = EMPTY;
			}
			else if (row > 0 && row < (getHeight() - 1) && column == 0) {
				// Bomb touches left only
				grid[row - 1][column + 1] = EMPTY;
				grid[row - 1][column + 2] = EMPTY;
				grid[row ][column + 2] = EMPTY;
				grid[row + 1][column + 1] = EMPTY;
				grid[row + 1][column + 2] = EMPTY;
			}
			else if (row > 0 && row < (getHeight() - 1) && column == (getWidth() - 1)) {
				// Bomb touches right only
				grid[row - 1][column - 2] = EMPTY;
				grid[row - 1][column - 1] = EMPTY;
				grid[row][column - 2] = EMPTY;
				grid[row + 1][column - 2] = EMPTY;
				grid[row + 1][column - 1] = EMPTY;
			}
			else if (row == (getHeight() - 1) && column == 0) {
				// Bomb touches bottom and left
				grid[row - 2][column + 1] = EMPTY;
				grid[row - 2][column + 2] = EMPTY;
				grid[row - 1][column + 2] = EMPTY;
			}
			else if (row == (getHeight() - 1) && column > 0 && column < (getWidth() - 1)) {
				// Bomb touches bottom only
				grid[row - 2][column - 1] = EMPTY;
				grid[row - 2][column] = EMPTY;
				grid[row - 2][column + 1] = EMPTY;
				grid[row - 1][column - 1] = EMPTY;
				grid[row - 1][column + 1] = EMPTY;
			}
			else if (row == (getHeight() - 1) && column == (getWidth() - 1)) {
				// Bomb touches bottom and right
				grid[row - 2][column - 2] = EMPTY;
				grid[row - 2][column - 1] = EMPTY;
				grid[row - 1][column - 2] = EMPTY;
			}
			// Clears the bomb after it blows up the surrounding blocks
			grid[row][column] = EMPTY;
		}
	}
	
	/**
	 * Updates each ice particle based on its physics.
	 * Water has a 5% chance of freezing into ice (within updateWater method)
	 * and each ice particle has a 25% chance of freezing a particle in a random
	 * cardinal direction given that it is a water particle.
	 * 
	 * @param row The row of the ice particle.
	 * @param column The column of the ice particle.
	 */
	public void updateIce(int row, int column) {
		int freezes = rand.nextInt(10);
		if (freezes == 5) {
			int direction = rand.nextInt(4);
			if (direction == 0 && row > 0) { // up row - 1
				if (grid[row - 1][column] == WATER) {
					grid[row - 1][column] = ICE;
				}
			}
			else if (direction == 1 && row < (getHeight() - 1)) { // down row + 1
				if (grid[row + 1][column] == WATER) {
					grid[row + 1][column] = ICE;
				}
			}
			else if (direction == 2 && column > 0) { // left column - 1
				if (grid[row][column - 1] == WATER) {
					grid[row][column - 1] = ICE;
				}
			}
			else if (direction == 3 && column < (getWidth() - 1)) { // right column + 1
				if (grid[row][column + 1] == WATER) {
					grid[row][column + 1] = ICE;
				}
			}
		}
	}
}
