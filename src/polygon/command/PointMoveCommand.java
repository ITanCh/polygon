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
		//һ��������ܶԶ��model����Ӱ��
		//��������ᵼ��point�ı仯�����ᵼ����ص��������ƶ�
		List<PolyTriangle> l=parent.getTriangle();
		//�ҳ����к��иĵ�������Σ����»���
		for(PolyTriangle t:l){
			if(t.getTriPoint().contains(point))
				t.changeMe();
		}
	}
	public void undo() {
		point.setLocation(oldLoc.x, oldLoc.y);
	}
	
}
