package polygon.command;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import polygon.model.Diagram;
import polygon.model.PolyPoint;
import polygon.model.PolyTriangle;

public class PointMoveCommand extends Command {
	private final PolyPoint point;
	private final Point loc;
	private Point oldLoc;
	private Diagram parent;
	
	public PointMoveCommand(PolyPoint p, Point b,Diagram d) {
		point= p;
		loc = b;
		parent=d;
		setLabel("Move Point");
	}
	
	public void execute() {
		oldLoc=new Point(point.getX(),point.getY());
		point.setLocation(loc.x,loc.y);
		//一个命令可能对多个model产生影响
		//该命令不仅会导致point的变化，还会导致相关的三角形移动
		List<PolyTriangle> l=parent.getTriangle();
		//找出所有含有改点的三角形，重新绘制
		for(PolyTriangle t:l){
			if(t.getTriPoint().contains(point))
				t.changeMe();
		}
	}
	public void undo() {
		point.setLocation(oldLoc.x, oldLoc.y);
	}
	
}
