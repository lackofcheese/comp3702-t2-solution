package tutorial2;

import search.State;
import search.heuristics.Heuristic;

public class TotalAngleDeltaHeuristic implements Heuristic{
	/** The goal state */
	private RobotArmState goalState;
	
	/** 
	 * Constructs a total angle delta heuristic with the given goal state.
	 * @param goalState the goal state.
	 */
	public TotalAngleDeltaHeuristic(RobotArmState goalState) {
		this.goalState = goalState;
	}
	
	@Override
	public double estimate(State s) {
		RobotArmState ras = (RobotArmState)s;
		return StateTools.totalAngleDelta(ras, goalState);
	}

}
