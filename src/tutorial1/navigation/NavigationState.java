package tutorial1.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import search.State;

/**
 * A simple implementation of a State, using a single name string
 * as a unique identifier, and with all of its successor data stored in a Map.
 * 
 * Successors are added via the addSuccessor() method.
 * 
 * @author lackofcheese
 */
public class NavigationState implements State {
	/** The name string. */
	String name;
	Map<NavigationState, Double> succMap = new HashMap<NavigationState, Double>();
	
	/**
	 * Constructor for the named state.
	 * @param name the name of the state
	 */
	public NavigationState(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the state.
	 * @return the name of the state.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the name of the state.
	 * @return the name of the state.
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * Adds the given state as a successor of the current state, with the given cost.
	 * @param succ the successor state.
	 * @param cost the edge cost to the successor state.
	 */
	public void addSuccessor(NavigationState succ, double cost) {
		this.succMap.put(succ, cost);
	}

	@Override
	public List<State> getSuccessors() {
		return new ArrayList<State>(succMap.keySet());
	}

	@Override
	public double getCost(State successor) {
		return succMap.get(successor);
	}
}
