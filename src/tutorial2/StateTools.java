package tutorial2;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

public class StateTools {
	/** The random number generator */
	public static final Random random = new Random();

	/** Sets the seed for RNG */
	public static void setSeed(long seed) {
		random.setSeed(seed);
	}

	/**
	 * Generates a random RAS with the given rod lengths.
	 * 
	 * @param length1
	 *            length of rod 1
	 * @param length2
	 *            length of rod 2.
	 * @return a random RAS.
	 */
	public static RobotArmState createRandomState(double length1, double length2) {
		double angle1 = (random.nextDouble() - 0.5) * Math.PI * 2;
		double angle2 = (random.nextDouble() - 0.5) * Math.PI * 2;
		return new RobotArmState(length1, length2, angle1, angle2);
	}

	/**
	 * Returns the total angle delta between the two states.
	 * 
	 * @param s0
	 *            the first state.
	 * @param s1
	 *            the second state.
	 * @return the total angle delta between the two states.
	 */
	public static double totalAngleDelta(RobotArmState s0, RobotArmState s1) {
		double angle1_0 = s0.getAngle1();
		double angle1_1 = s1.getAngle1();
		double angle2_0 = s0.getAngle2();
		double angle2_1 = s1.getAngle2();
		double d1 = Math.abs(angle1_1 - angle1_0);
		double d2 = Math.abs(angle2_1 - angle2_0);
		if (d1 > Math.PI) {
			d1 = 2 * Math.PI - d1;
		}
		return Math.toDegrees(d1 + d2);
	}

	/**
	 * Returns the maximum angle delta between the two states.
	 * 
	 * @param s0
	 *            the first state.
	 * @param s1
	 *            the second state.
	 * @return the maximum angle delta between the two states.
	 */
	public static double maxAngleDelta(RobotArmState s0, RobotArmState s1) {
		double angle1_0 = s0.getAngle1();
		double angle1_1 = s1.getAngle1();
		double angle2_0 = s0.getAngle2();
		double angle2_1 = s1.getAngle2();
		double d1 = Math.abs(angle1_1 - angle1_0);
		double d2 = Math.abs(angle2_1 - angle2_0);
		if (d1 > Math.PI) {
			d1 = 2 * Math.PI - d1;
		}
		return Math.toDegrees(Math.max(d1, d2));
	}

	/**
	 * Returns whether the given state is valid.
	 * 
	 * @param s
	 *            the state to test.
	 * @param obstacles
	 *            the obstacles to test against.
	 * @return true if s is a valid state, and false otherwise.
	 */
	public static boolean isValidState(RobotArmState s, List<Obstacle> obstacles) {
		for (Point2D p : s.getPoints()) {
			for (double coord : new double[] { p.getX(), p.getY() }) {
				if (coord < -1 || coord > 1) {
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
	 * Returns a state interpolated linearly between s0 and s1, where s0
	 * corresponds to t=0 and s1 corresponds to t=1.
	 * 
	 * @param s0
	 *            the first state.
	 * @param s1
	 *            the second state.
	 * @param t
	 *            the interpolation parameter.
	 * @return a state at value t between s0 (t=0) and s1 (t=1).
	 */
	public static RobotArmState interpolate(RobotArmState s0, RobotArmState s1,
			double t) {
		double angle1_0 = s0.getAngle1();
		double angle1_1 = s1.getAngle1();
		double angle2_0 = s0.getAngle2();
		double angle2_1 = s1.getAngle2();
		if (Math.abs(angle1_1 - angle1_0) > Math.PI) {
			if (angle1_0 > angle1_1) {
				angle1_0 -= 2 * Math.PI;
			} else {
				angle1_0 += 2 * Math.PI;
			}
		}
		return new RobotArmState(s0.getLength1(), s0.getLength2(), angle1_0
				* (1 - t) + angle1_1 * t, angle2_0 * (1 - t) + angle2_1 * t);
	}

	/**
	 * Returns whether there is a valid direct path between the given two robot
	 * arm states.
	 * 
	 * @param s0
	 *            the first state.
	 * @param s1
	 *            the second state.
	 * @param obstacles
	 *            the obstacles to test against.
	 * @return true if the direct path is valid, and false otherwise.
	 */
	public static boolean hasDirectPath(RobotArmState s0, RobotArmState s1,
			List<Obstacle> obstacles) {
		double maxDelta = maxAngleDelta(s0, s1);
		int numSteps = (int) Math.ceil(maxDelta * 10);
		for (int i = 1; i < numSteps; i++) {
			double t = (i / (double) numSteps);
			RobotArmState s = interpolate(s0, s1, t);
			if (!isValidState(s, obstacles)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns whether the given RAS collides with the given obstacle.
	 * 
	 * @param s
	 *            the state to test.
	 * @param obs
	 *            the obstacle.
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
