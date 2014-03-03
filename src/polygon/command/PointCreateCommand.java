package polygon.command;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import polygon.model.Diagram;
import polygon.model.PolyPoint;

public class PointCreateCommand extends Command{
	private PolyPoint point;
	private Diagram parent;
	private Rectangle bounds;
	public PointCreateCommand(PolyPoint p,Diagram pa,Rectangle b){
		point=p;
		parent=pa;
		bounds=b;
	}
	
	public void execute(){
		point.setLocation(bounds.x,bounds.y);
		parent.addChild(point);			//向diagram这个model中添加这个点
		System.out.println(point.getX()+" "+point.getY());
	}
	
	public void undo() {
		parent.removePoint(point);
	}
	
}
