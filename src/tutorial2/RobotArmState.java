package tutorial2;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import search.StateWithMap;

public class RobotArmState extends StateWithMap {
	private double length1;
	private double length2;
	private double angle1;
	private double angle2;
	private List<Point2D> points;

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

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof RobotArmState)) {
			return false;
		}
		RobotArmState rs2 = (RobotArmState) obj;
		return (this.angle1 == rs2.angle1 && this.angle2 == rs2.angle2);
	}

	public String toString() {
		return String.format("(%.1f<%.1f°, %.1f<%.1f°)", length1,
				Math.toDegrees(angle1), length2, Math.toDegrees(angle2));
	}

	public int hashCode() {
		return Double.valueOf(angle1).hashCode() + 7
				* Double.valueOf(angle2).hashCode();
	}

	public double getLength1() {
		return length1;
	}

	public double getLength2() {
		return length2;
	}

	public double getAngle1() {
		return angle1;
	}

	public double getAngle2() {
		return angle2;
	}

	public List<Point2D> getPoints() {
		return new ArrayList<Point2D>(points);
	}
}