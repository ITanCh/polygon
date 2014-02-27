package polygon.command;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import polygon.model.PolyPoint;

public class PointMoveCommand extends Command {
	private final PolyPoint point;
	private final Rectangle box;
	private Rectangle oldBox;
	
	public PointMoveCommand(PolyPoint p, Rectangle b) {
		point= p;
		box = b;
		setLabel("Move Point");
	}
	
	public void execute() {
		point.setLocation(box.x, box.y);
	}
	public void undo() {
		point.setLocation(oldBox.x, oldBox.y);
	}
	
}
