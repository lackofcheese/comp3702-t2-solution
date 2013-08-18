package tutorial2;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class represents the specifications of a given problem and solution;
 * that is, it provides a structured representation of the contents of 
 * a problem text file and associated solution text file, as described
 * in the assignment specifications.
 * 
 * This class doesn't do any validity checking - see the code in tester.Tester for this.
 * @author lackofcheese
 */
public class ProblemSpec {
	/** True iff a problem is currently loaded */
	private boolean problemLoaded = false;
	/** True iff a solution is currently loaded */
	private boolean solutionLoaded = false;
	
	/** The length of the first rod */
	private double length1;
	/** The length of the second rod */
	private double length2;
	/** The initial configuration */
	private RobotArmState initialState;
	/** The goal configuration */
	private RobotArmState goalState;
	/** The obstacles */
	private List<Obstacle> obstacles;
	
	/** The path taken in the solution */
	private List<RobotArmState> path;
	/** The cost of the solution */
	private double solutionCost;
	
	/**
	 * Loads a problem from a problem text file.
	 * @param filename the text file to load.
	 * @throws IOException if the text file doesn't exist or doesn't meet
	 * the assignment specifications.
	 */
	public void loadProblem(String filename) throws IOException {
		problemLoaded = false;
		solutionLoaded = false;
		BufferedReader input = new BufferedReader(new FileReader(filename));
		int lineNo = 0;
		String line;
		Scanner s;
		try {
			lineNo++;
			line = input.readLine();
			s = new Scanner(line);
			length1 = s.nextDouble();
			length2 = s.nextDouble();
			s.close();
			
			lineNo++;
			line = input.readLine();
			s = new Scanner(line);
			double angle1 = Math.toRadians(s.nextDouble());
			double angle2 = Math.toRadians(s.nextDouble());
			s.close();
			initialState = new RobotArmState(length1, length2, angle1, angle2);
			
			
			lineNo++;
			line = input.readLine();
			s = new Scanner(line);
			angle1 = Math.toRadians(s.nextDouble());
			angle2 = Math.toRadians(s.nextDouble());
			s.close();
			goalState = new RobotArmState(length1, length2, angle1, angle2);
			
			obstacles = new ArrayList<Obstacle>();
			while ((line = input.readLine()) != null) {
				lineNo++;
				obstacles.add(new Obstacle(line));
			}
			problemLoaded = true;
		}  catch (InputMismatchException e) {
			throw new IOException(String.format("Invalid number format on line %d: %s", lineNo, e.getMessage()));
		} catch (NoSuchElementException e) {
			throw new IOException(String.format("Not enough tokens on line %d", lineNo));
		} catch (NullPointerException e) {
			throw new IOException(String.format("Line %d expected, but file ended.", lineNo));
		} finally {
			input.close();
		}
	}
	
	
	/**
	 * Loads a solution from a solution text file.
	 * @param filename the text file to load.
	 * @throws IOException if the text file doesn't exist or doesn't meet
	 * the assignment specifications.
	 */
	public void loadSolution(String filename) throws IOException {
		if (!problemLoaded) {
			return;
		}
		solutionLoaded = false;
		BufferedReader input = new BufferedReader(new FileReader(filename));
		String line;
		int lineNo = 0;
		path = new ArrayList<RobotArmState>();
		try {
			while ((line = input.readLine()) != null) {
				lineNo++;
				Scanner s = new Scanner(line);
				double angle1 = Math.toRadians(s.nextDouble());
				double angle2 = Math.toRadians(s.nextDouble());
				s.close();
				RobotArmState ras = new RobotArmState(length1, length2, angle1, angle2);
				path.add(ras);	
			}
			solutionLoaded = true;
		} catch (InputMismatchException e) {
			throw new IOException(String.format("Invalid number format on line %d: %s", lineNo, e.getMessage()));
		} catch (NoSuchElementException e) {
			throw new IOException(String.format("Not enough tokens on line %d - 2 required", lineNo));
		} catch (NullPointerException e) {
			throw new IOException(String.format("Line %d expected, but file ended.", lineNo));
		} finally {
			input.close();
		}
	}
	
	/**
	 * Returns the length of the first rod.
	 * @return the length of the first rod.
	 */
	public double getLength1() {
		return length1;
	}

	/**
	 * Returns the length of the second rod.
	 * @return the length of the second rod.
	 */
	public double getLength2() {
		return length2;
	}

	/**
	 * Returns the initial configuration.
	 * @return the initial configuration.
	 */
	public RobotArmState getInitialState() {
		return initialState;
	}
	
	/**
	 * Returns the goal configuration.
	 * @return the goal configuration.
	 */
	public RobotArmState getGoalState() {
		return goalState;
	}
	
	/**
	 * Returns the solution path.
	 * @return the solution path.
	 */
	public List<RobotArmState> getPath() {
		return new ArrayList<RobotArmState>(path);
	}
	
	/**
	 * Returns the list of obstacles.
	 * @return the list of obstacles.
	 */
	public List<Obstacle> getObstacles() {
		return new ArrayList<Obstacle>(obstacles);
	}
	
	/**
	 * Returns the cost of the solution.
	 * @return the cost of the solution.
	 */
	public double getSolutionCost() {
		return solutionCost;
	}
	
	/**
	 * Returns whether a problem is currently loaded.
	 * @return whether a problem is currently loaded.
	 */
	public boolean problemLoaded() {
		return problemLoaded;
	}
	
	/**
	 * Returns whether a solution is currently loaded.
	 * @return whether a solution is currently loaded.
	 */
	public boolean solutionLoaded() {
		return solutionLoaded;
	}
}
