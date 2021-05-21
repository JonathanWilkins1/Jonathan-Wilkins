import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A class for the graphical user interface of a Falling Sand game.
 * 
 * Note to Millersville University students: you do not need to understand or
 * modify any part of this class.
 * 
 * @author Dave Feinberg, Chad Hogg
 */
public class SandDisplay extends JComponent
		implements MouseListener, MouseMotionListener, ActionListener, ChangeListener {

	/** A version number that would be used if this object were serialized. */
	private static final long serialVersionUID = 1L;
	
	/** The area in which the simulation actually takes place. */
	private Image image;
	/** The size that each particle takes up on the screen. */
	private int cellSize;
	/** The window onto which all parts of the GUI will be drawn. */
	private JFrame frame;
	/** Which paintbrush the user has selected. */
	private int selectedTool;
	/** The height of the simulation area. */
	private int numRows;
	/** The width of the simulation area. */
	private int numCols;
	/** The current location of the mouse cursor. */
	private int[] mouseLoc;
	/** The buttons by which the user can select different tools. */
	private JButton[] buttons;
	/** The UI component that allows the user to control the speed. */
	private JSlider slider;
	/** The current speed. */
	private int speed;
	/** A scaling factor for speed. */
	private double speedFactor;
	/** A file chooser dialog for saving / loading. */
	private JFileChooser fileChooser;
	/** The next file to load. */
	private File nextLoad;
	/** The next file to save. */
	private File nextSave;
	

	/**
	 * Constructs a new SandDisplay.
	 * 
	 * @param title The title of the window.
	 * @param numRows The height of the simulation.
	 * @param numCols The width of the simulation.
	 * @param buttonNames The names of all of the buttons.
	 * @param speedFactor A scaling factor for speed.
	 */
	public SandDisplay(String title, int numRows, int numCols, String[] buttonNames, double speedFactor) {
		// Copy sizes.
		this.numRows = numRows;
		this.numCols = numCols;
		// Assume that the first button has been selected.
		selectedTool = 1;
		// The mouse has not yet been used.
		mouseLoc = null;
		// Choose the middle speed.
		this.speedFactor = speedFactor;
		speed = computeSpeed(50);

		// Determine cell size and create the image.
		cellSize = Math.max(1, 600 / Math.max(numRows, numCols));
		image = new BufferedImage(numCols * cellSize, numRows * cellSize, BufferedImage.TYPE_INT_RGB);

		// Create the frame.
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

		// Create the main content pane.
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		frame.getContentPane().add(topPanel);

		// Be notified of events.
		setPreferredSize(new Dimension(numCols * cellSize, numRows * cellSize));
		addMouseListener(this);
		addMouseMotionListener(this);
		topPanel.add(this);

		// Create the sub-pane that will contain the buttons.
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		topPanel.add(buttonPanel);

		// Create all of the buttons.
		buttons = new JButton[buttonNames.length + 2];
		buttons[0] = new JButton("Save");
		buttons[0].setActionCommand("save");
		buttons[0].addActionListener(this);
		buttonPanel.add(buttons[0]);
		buttons[1] = new JButton("Load");
		buttons[1].setActionCommand("load");
		buttons[1].addActionListener(this);
		buttonPanel.add(buttons[1]);
		for (int i = 0; i < buttonNames.length; i++) {
			buttons[i + 2] = new JButton(buttonNames[i]);
			buttons[i + 2].setActionCommand("" + i);
			buttons[i + 2].addActionListener(this);
			buttonPanel.add(buttons[i + 2]);
		}
		buttons[selectedTool + 2].setSelected(true);

		// Create the speed slider.
		slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		slider.addChangeListener(this);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer(0), new JLabel("Slow"));
		labelTable.put(new Integer(100), new JLabel("Fast"));
		slider.setLabelTable(labelTable);
		slider.setPaintLabels(true);
		frame.getContentPane().add(slider);
		
		// Create the file chooser.
		fileChooser = new JFileChooser();

		// Prepare everything to be seen.
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}

	/**
	 * Pauses the simulation for a time.
	 * 
	 * @param milliseconds The length of time to pause, in milliseconds.
	 */
	public void pause(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the mouse location.
	 * 
	 * @return The location of the mouse.
	 */
	public int[] getMouseLocation() {
		return mouseLoc;
	}

	/**
	 * Gets which tool is currently selected.
	 * 
	 * @return Which tool is currently selected.
	 */
	public int getSelectedTool() {
		return selectedTool;
	}

	/**
	 * Sets the color of a specific location in the image.
	 * 
	 * @param row The row number to change.
	 * @param col The column number to change.
	 * @param color The new color of that location.
	 */
	public void setColor(int row, int col, Color color) {
		Graphics g = image.getGraphics();
		g.setColor(color);
		g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Intentionally does nothing, because we do not care about click events.
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseLoc = toLocation(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseLoc = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Intentionally does nothing, because we do not care about enter events.
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Intentionally does nothing, because we do not care about exit events.
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// Intentionally does nothing, because we do not care about move events.
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseLoc = toLocation(e);
	}

	/**
	 * Finds the cell the mouse is on.
	 * 
	 * @param e The event.
	 * @return The final location.
	 */
	private int[] toLocation(MouseEvent e) {
		int row = e.getY() / cellSize;
		int col = e.getX() / cellSize;
		if (row < 0 || row >= numRows || col < 0 || col >= numCols)
			return null;
		int[] loc = new int[2];
		loc[0] = row;
		loc[1] = col;
		return loc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("save")) {
			int result = fileChooser.showSaveDialog(frame);
			if(result == JFileChooser.APPROVE_OPTION) {
				nextSave = fileChooser.getSelectedFile();
			}
			else {
				nextSave = null;
			}
		}
		else if(e.getActionCommand().equals("load")) {
			int result = fileChooser.showOpenDialog(frame);
			if(result == JFileChooser.APPROVE_OPTION) {
				nextLoad = fileChooser.getSelectedFile();
			}
			else {
				nextLoad = null;
			}
		}
		else {
			selectedTool = Integer.parseInt(e.getActionCommand());
			for (JButton button : buttons) {
				button.setSelected(false);
			}
			((JButton) e.getSource()).setSelected(true);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		speed = computeSpeed(slider.getValue());
	}

	/**
	 * Gets the speed of the simulation.
	 * 
	 * @return The number of times to step between repainting and processing mouse input.
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Gets the file to load, if any.
	 * 
	 * @return The file to load.
	 */
	public File getNextLoad() {
		return nextLoad;
	}
	
	/**
	 * Finishes loading a file.
	 * 
	 * @param newHeight The new height.
	 * @param newWidth The new width;
	 */
	public void finishLoading(int newHeight, int newWidth) {
		nextLoad = null;
		numRows = newHeight;
		numCols = newWidth;
		cellSize = Math.max(1, 600 / Math.max(numRows, numCols));
		image = new BufferedImage(numCols * cellSize, numRows * cellSize, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(numCols * cellSize, numRows * cellSize));
		frame.pack();
		repaint();
		frame.repaint();
	}

	/**
	 * Gets the file to save, if any.
	 * 
	 * @return The file to save.
	 */
	public File getNextSave() {
		return nextSave;
	}
	
	/**
	 * Finishes saving a file.
	 */
	public void finishSaving() {
		nextSave = null;
	}

	/**
	 * Gets the current speed based on exponential interpolation of the slider location between 10^speedFactor and 10^(speedFactor + 3).
	 * 
	 * @param sliderValue The current location of the slider.
	 * @return The desired speed.
	 */
	private int computeSpeed(int sliderValue) {
		return (int) Math.pow(10, 0.03 * sliderValue + speedFactor);
	}
}