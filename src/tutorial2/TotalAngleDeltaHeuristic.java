package tutorial2;

import search.State;
import search.heuristics.Heuristic;

/**
 * A heuristic for the robot arm; the cost to the goal is estimated as the total
 * of the differences in angle1 and angle2 between the given state and the goal
 * state.
 * 
 * @author lackofcheese
 * 
 */
public class TotalAngleDeltaHeuristic implements Heuristic {
	/** The goal state */
	private RobotArmState goalState;

	/**
	 * Constructs a total angle delta heuristic with the given goal state.
	 * 
	 * @param goalState
	 *            the goal state.
	 */
	public TotalAngleDeltaHeuristic(RobotArmState goalState) {
		this.goalState = goalState;
	}

	@Override
	public double estimate(State s) {
		RobotArmState ras = (RobotArmState) s;
		return StateTools.totalAngleDelta(ras, goalState);
	}

}
