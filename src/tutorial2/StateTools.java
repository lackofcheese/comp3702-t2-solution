package tutorial2;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

public class StateTools {
	/** The random number generator */
	public static final Random random = new Random();
	
	/**
	 * Generates a random RAS with the given rod lengths.
	 * @param length1 length of rod 1
	 * @param length2 length of rod 2.
	 * @return a random RAS.
	 */
	public static RobotArmState createRandomState(double length1, double length2) {
		double angle1 = random.nextDouble() * Math.PI / 2;
		double angle2 = (random.nextDouble() - 0.5) * Math.PI * 2; 
		return new RobotArmState(length1, length2, angle1, angle2);
	}
	
	/**
	 * Returns the total angle delta between the two states.
	 * @param s1 the first state.
	 * @param s2 the second state.
	 * @return the total angle delta between the two states.
	 */
	public static double totalAngleDelta(RobotArmState s1, RobotArmState s2) {
		return Math.toDegrees(Math.abs(s1.getAngle1() - s2.getAngle1()) + Math.abs(s1.getAngle2() - s2.getAngle2()));
	}

	/**
	 * Returns whether the given state is valid.
	 * @param s the state to test.
	 * @param obstacles the obstacles to test against.
	 * @return true if s is a valid state, and false otherwise.
	 */
	public static boolean isValidState(RobotArmState s, List<Obstacle> obstacles) {
		if (s.getAngle1() < 0 || s.getAngle1() > Math.PI/2) {
			return false;
		}
		if (s.getAngle2() < -Math.PI || s.getAngle2() > Math.PI) {
			return false;
		}
		
		for (Point2D p : s.getPoints()) {
			for (double coord : new double[] {p.getX(), p.getY()}) {
				if (coord < 0 || coord > 1) {
					return false;
				}
			}
		}
		for (Obstacle o : obstacles) {
			if (hasCollision(s, o)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns whether there is a valid direct path between the given two robot arm states.
	 * @param s1 the first state.
	 * @param s2 the second state.
	 * @param obstacles the obstacles to test against.
	 * @return true if the direct path is valid, and false otherwise.
	 */
	public static boolean hasDirectPath(RobotArmState s1, RobotArmState s2, List<Obstacle> obstacles) {
		double angle1_0 = s1.getAngle1();
		double angle1_1 = s2.getAngle1();
		double angle2_0 = s2.getAngle2();
		double angle2_1 = s2.getAngle2();
		double maxDelta = Math.max(Math.abs(angle1_1 - angle1_0), Math.abs(angle2_1 - angle2_0));
		int numSteps = (int)Math.ceil(maxDelta * 10);
		for (int i = 1; i < numSteps; i++) {
			double t = (i / (double)numSteps);
			double angle1 = (1-t) * angle1_0 + t*angle1_1;
			double angle2 = (1-t) * angle2_0 + t*angle2_1;
			RobotArmState s = new RobotArmState(s1.getLength1(), s1.getLength2(), angle1, angle2);
			if (!isValidState(s, obstacles)) {
				return false;
			}	
		}
		return true;
	}
	

	/**
	 * Returns whether the given RAS collides with the given obstacle.
	 * @param s the state to test.
	 * @param obs the obstacle.
	 * @return true if s collides with obs, and false otherwise.
	 */
	static boolean hasCollision(RobotArmState s, Obstacle obs) {
		List<Point2D> points = s.getPoints();
		Line2D line = new Line2D.Double(points.get(0), points.get(1));
		if (line.intersects(obs.getRect())) {
			return true;
		}
		line = new Line2D.Double(points.get(1), points.get(2));
		if (line.intersects(obs.getRect())) {
			return true;
		}
		return false;
	}
}
