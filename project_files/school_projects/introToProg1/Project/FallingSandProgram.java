import java.io.File;
import java.io.FileNotFoundException;

/**
 * A program in which the player can simulate sand and other particle types interacting with each other.
 * 
 * @author Dave Feinberg, Chad Hogg
 */
public class FallingSandProgram {
	
	/**
	 * Starts the program.
	 * 
	 * @param args Not used.
	 * @throws FileNotFoundException If there is a problem saving or loading a file.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		int height = 120;
		int width = 80;
		double speedFactor = 1.5;
		String[] buttonLabels = Simulation.NAMES;
		SandDisplay display = new SandDisplay("Falling Sand Game", height, width, buttonLabels, speedFactor);
		Simulation simulation = new Simulation(height, width);
		run(simulation, display);
	}
	
	/**
	 * Actually runs the simulation.
	 * 
	 * @param simulation The simulated particle locations.
	 * @param display The GUI that will show the simulation and let the user click on things.
	 * @throws FileNotFoundException If a requested file cannot be saved / loaded.
	 */
	public static void run(Simulation simulation, SandDisplay display) throws FileNotFoundException {
		// This loop intentionally runs forever (until you close the window, which kills the program).
		while(true) {
			// Make a number of updates (more if the speed is higher).
			for(int i = 0; i < display.getSpeed(); i++) {
				simulation.doOneUpdate();
			}
			// Have the simulation tell the display what is now at each location.
			simulation.updateDisplay(display);
			// Redraw the screen to show the new picture.
			display.repaint();
			// Gets the current location of the mouse.
			int[] mouseLoc = display.getMouseLocation();
			// Updates the simulation if the user clicked on a cell.
			if(mouseLoc != null) {
				simulation.fillLocation(mouseLoc[0], mouseLoc[1], display.getSelectedTool());
			}
			// Checks for the user pressing the Save button.
			File nextSave = display.getNextSave();
			if(nextSave != null) {
				simulation.save(nextSave);
				display.finishSaving();
			}
			// Checks for the user pressing the Load button.
			File nextLoad = display.getNextLoad();
			if(nextLoad != null) {
				simulation.load(nextLoad);
				if(simulation.getHeight() > 0 && simulation.getWidth() > 0) {
					display.finishLoading(simulation.getHeight(), simulation.getWidth());
				}
			}
		}
	}
}
