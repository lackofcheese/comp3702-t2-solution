package tutorial2;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import search.StateWithMap;

/**
 * Represents a state of the robot arm for Tutorial 2.
 * 
 * @author lackofcheese
 */
public class RobotArmState extends StateWithMap {
	private double length1;
	private double length2;
	private double angle1;
	private double angle2;
	private List<Point2D> points;

	/**
	 * Constructor. Creates a state of the robot arm with the given parameters.
	 * 
	 * @param length1
	 *            the length of the first rod.
	 * @param length2
	 *            the length of the second rod.
	 * @param angle1
	 *            the angle of the first rod.
	 * @param angle2
	 *            the angle of the second rod.
	 */
	public RobotArmState(double length1, double length2, double angle1,
			double angle2) {
		super();
		this.length1 = length1;
		this.length2 = length2;
		this.angle1 = angle1;
		this.angle2 = angle2;
		points = new ArrayList<Point2D>();
		double x = 0, y = 0;
		points.add(new Point2D.Double(x, y));
		x += length1 * Math.cos(angle1);
		y += length1 * Math.sin(angle1);
		points.add(new Point2D.Double(x, y));
		x += length2 * (Math.cos(angle1 + angle2));
		y += length2 * (Math.sin(angle1 + angle2));
		points.add(new Point2D.Double(x, y));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof RobotArmState)) {
			return false;
		}
		RobotArmState rs2 = (RobotArmState) obj;
		return (this.angle1 == rs2.angle1 && this.angle2 == rs2.angle2);
	}

	@Override
	public String toString() {
		return String.format("(%.1f<%.1f°, %.1f<%.1f°)", length1,
				Math.toDegrees(angle1), length2, Math.toDegrees(angle2));
	}

	@Override
	public int hashCode() {
		return Double.valueOf(angle1).hashCode() + 7
				* Double.valueOf(angle2).hashCode();
	}

	/**
	 * Returns the length of the first rod.
	 * 
	 * @return the length of the first rod.
	 */
	public double getLength1() {
		return length1;
	}

	/**
	 * Returns the length of the second rod.
	 * 
	 * @return the length of the second rod.
	 */
	public double getLength2() {
		return length2;
	}

	/**
	 * Returns the angle of the first rod.
	 * 
	 * @return the angle of the first rod.
	 */
	public double getAngle1() {
		return angle1;
	}

	/**
	 * Returns the angle of the second rod.
	 * 
	 * @return the angle of the second rod.
	 */
	public double getAngle2() {
		return angle2;
	}

	/**
	 * Returns the endpoints of the rods.
	 * 
	 * @return the endpoints of the rods.
	 */
	public List<Point2D> getPoints() {
		return new ArrayList<Point2D>(points);
	}
}