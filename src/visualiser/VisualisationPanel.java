package visualiser;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.Timer;

import tutorial2.Obstacle;
import tutorial2.ProblemSpec;
import tutorial2.RobotArmState;
import tutorial2.StateTools;

public class VisualisationPanel extends JComponent {
	/** UID, as required by Swing */
	private static final long serialVersionUID = -4286532773714402501L;
	
	private ProblemSpec problemSpec = new ProblemSpec();
	private Visualiser visualiser;
	
	private AffineTransform translation = AffineTransform.getTranslateInstance(1, -1);
	private AffineTransform transform = null;
	
	private RobotArmState currentState;
	private boolean animating = false;
	private boolean displayingSolution = false;
	private Timer animationTimer;
	
	private int resolution; // # of frames per solution step.
	private int framePeriod; // 1000 / framerate
	private Integer frameNumber = null;
	private int maxFrameNumber;
	
	private int samplingPeriod;
	
	public VisualisationPanel(Visualiser visualiser) {
		super();
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.visualiser = visualiser;
	}
	
	public void setDisplayingSolution(boolean displayingSolution) {
		this.displayingSolution = displayingSolution;
		repaint();
	}
	
	public boolean isDisplayingSolution() {
		return displayingSolution;
	}
	
	public void setFramerate(int framerate) {
		boolean mustRestart = (framePeriod == Integer.MAX_VALUE);
		if (framerate > 0) {
			framePeriod = 1000 / framerate;	
		} else {
			framePeriod = Integer.MAX_VALUE;
		}
		if (animationTimer != null) {
			if (mustRestart) {
				animationTimer.stop();
			}
			animationTimer.setDelay(framePeriod);
			if (mustRestart) {
				animationTimer.start();
			}
		}
	}
	
	public void setSamplingPeriod(int samplingPeriod) {
		this.samplingPeriod = samplingPeriod;
		if (displayingSolution) {
			repaint();
		}
	}
	
	public void setResolution(int resolution) {
		int oldResolution = this.resolution;
		this.resolution = resolution;
		if (!problemSpec.solutionLoaded()) {
			return;
		}
		maxFrameNumber = resolution * (problemSpec.getPath().size() - 1);
		if (!animating) {
			return;
		}
		int newFrameNumber = (int)Math.round((double)frameNumber * resolution / oldResolution);
		if (newFrameNumber > maxFrameNumber) {
			newFrameNumber = maxFrameNumber;
		}
		if (resolution > oldResolution) {
			visualiser.updateFrameSlider();
			gotoFrame(newFrameNumber);
		} else {
			gotoFrame(newFrameNumber);
			visualiser.updateFrameSlider();
		}
	}
	
	public int getResolution() {
		return resolution;
	}
	
	public int getMaxFrameNumber() {
		return maxFrameNumber;
	}
	
	public void initAnimation() {
		if (!problemSpec.solutionLoaded()) {
			return;
		}
		if (animationTimer != null) {
			animationTimer.stop();
		}
		animating = true;
		gotoFrame(0);
		maxFrameNumber = resolution * (problemSpec.getPath().size() - 1);
		animationTimer = new Timer(framePeriod, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int newFrameNumber = frameNumber+1;
				if (newFrameNumber >= maxFrameNumber) {
					animationTimer.stop();
					visualiser.setPlaying(false);
				}
				if (newFrameNumber <= maxFrameNumber) {
					gotoFrame(newFrameNumber);
				}
			}
		});
		visualiser.setPlaying(false);
		visualiser.updateFrameSlider();
	}
	
	public RobotArmState getState(int frameNumber) {
		double stepIndex = ((double)frameNumber) / resolution;
		int flooredIndex = (int)Math.floor(stepIndex);
		if (flooredIndex == stepIndex) {
			return problemSpec.getPath().get(flooredIndex);
		} 
		RobotArmState s0 = problemSpec.getPath().get(flooredIndex);
		RobotArmState s1 = problemSpec.getPath().get(flooredIndex + 1);
		double t = stepIndex - flooredIndex;
		return StateTools.interpolate(s0, s1, t); 
	}
	
	public void gotoFrame(int frameNumber) {
		if (!animating || (this.frameNumber != null && this.frameNumber == frameNumber)) {
			return;
		}
		this.frameNumber = frameNumber;
		visualiser.setFrameNumber(frameNumber);
		currentState = getState(frameNumber);
		repaint();
	}
	
	public int getFrameNumber() {
		return frameNumber;
	}
	
	public void playPauseAnimation() {
		if (animationTimer.isRunning()) {
			animationTimer.stop();
			visualiser.setPlaying(false);
		} else {
			if (frameNumber >= maxFrameNumber) {
				gotoFrame(0);
			}
			animationTimer.start();
			visualiser.setPlaying(true);
		}
	}
	
	public void stopAnimation() {
		if (animationTimer != null) {
			animationTimer.stop();
		}
		animating = false;
		visualiser.setPlaying(false);
		frameNumber = null;
	}
	
	public ProblemSpec getProblemSetup() {
		return problemSpec;
	}
	
	public void calculateTransform() {
		transform = AffineTransform.getScaleInstance(
				getWidth() / 2, -getHeight() / 2);
		transform.concatenate(translation);
	}
	
	public void paintState(Graphics2D g2, RobotArmState s) {
		if (s == null) {
			return;
		}
		Path2D.Float path = new Path2D.Float();
		
		List<Point2D> points = s.getPoints();
		Point2D p = points.get(0);
		path.moveTo(p.getX(), p.getY());
		for (int i = 1; i < points.size(); i++) {
			p = points.get(i);
			path.lineTo(p.getX(), p.getY());
		}
		path.transform(transform);
		g2.draw(path);
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		if (!problemSpec.problemLoaded()) {
			return;
		}
		calculateTransform();
		Graphics2D g2 = (Graphics2D)graphics;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());

		List<Obstacle> obstacles = problemSpec.getObstacles();
		if (obstacles != null) {
			g2.setColor(Color.red);
			for (Obstacle obs : problemSpec.getObstacles()) {
				Shape transformed = transform.createTransformedShape(obs.getRect());
				g2.fill(transformed);
			}
		}
		
		g2.setStroke(new BasicStroke(2));
		if (!animating) {
			if (displayingSolution) {
				List<RobotArmState> path = problemSpec.getPath();
				for (int i = 0; i < maxFrameNumber; i += samplingPeriod) {
					float t = (float)i / maxFrameNumber;
					g2.setColor(new Color(0, t, 1-t));
					paintState(g2, getState(i));
				}
				g2.setColor(Color.green);
				paintState(g2, getState(maxFrameNumber));
			} else {
				g2.setColor(Color.blue);	
				paintState(g2, problemSpec.getInitialState());
				
				g2.setColor(Color.green);
				paintState(g2, problemSpec.getGoalState());
			}
		} else {
			g2.setColor(Color.blue);
			paintState(g2, currentState);
		}
	}
}