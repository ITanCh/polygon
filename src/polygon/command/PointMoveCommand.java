package polygon.command;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import polygon.model.PolyPoint;

public class PointMoveCommand extends Command {
	private final PolyPoint point;
	private final Point loc;
	private Point oldLoc;
	
	public PointMoveCommand(PolyPoint p, Point b) {
		point= p;
		loc = b;
		setLabel("Move Point");
	}
	
	public void execute() {
		oldLoc=new Point(point.getX(),point.getY());
		point.setLocation(loc.x,loc.y);
	}
	public void undo() {
		point.setLocation(oldLoc.x, oldLoc.y);
	}
	
}
