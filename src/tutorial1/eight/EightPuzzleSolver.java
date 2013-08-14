package tutorial1.eight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import search.algorithms.*;
import search.heuristics.*;

/**
 * An implementation of the 8-puzzle problem from Tutorial 1.
 * @author lackofcheese
 */
public class EightPuzzleSolver {
	/** The default file to read input from. */
	public static final String DEFAULT_INPUT = "src/eight.in";
	
	/** The initial state for the search. */
	private static EightPuzzleState initialState;
	/** The goal state for the search */
	private static EightPuzzleState goalState;
	/**
	 * Reads the search parameters from the given file name.
	 * @param inputFileName the file to read.
	 * @throws IOException if there are issues reading the file.
	 */
	private static void readFile(String inputFileName) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(inputFileName));
		initialState = new EightPuzzleState(input.readLine());
		goalState = new EightPuzzleState(input.readLine());
		input.close();
	}
	
	/**
	 * @param args the command-line arguments. If any are given, the first
	 * will be taken as the file to read from.
	 */
	public static void main(String args[]) {
		try {
			if (args.length > 0) {
				readFile(args[0]);
			} else {
				readFile(DEFAULT_INPUT);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		@SuppressWarnings("unused")
		Heuristic heuristic;
		heuristic = new TotalManhattanDistance(goalState);
		//heuristic = new ZeroHeuristic();
		
		AbstractSearchAlgorithm algo;
		//algo = new DepthFirstSearch(initialState, goalState);
		//algo = new DepthLimitedSearch(26, initialState, goalState);
		//algo = new IterativeDeepeningSearch(initialState, goalState);
		
		algo = new BreadthFirstSearch(initialState, goalState);
		//algo = new AStarSearch(initialState, goalState, heuristic);
		
		algo.verboseSearch();
	}
}