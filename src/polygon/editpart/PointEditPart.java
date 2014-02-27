package polygon.editpart;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import polygon.model.PolyPoint;
import polygon.model.listener.ElementListener;

public class PointEditPart extends AbstractGraphicalEditPart implements ElementListener {
	public PointEditPart(PolyPoint p){
		setModel(p);
		p.addPartListener(this);
	}
	@Override
	protected IFigure createFigure() {
		IFigure e=new Ellipse();					//用椭圆来画一个小圆点
		PolyPoint p=(PolyPoint) getModel();
		e.setBounds(new Rectangle(p.getX()-PolyPoint.RADIUS,
				p.getY()-PolyPoint.RADIUS,PolyPoint.RADIUS*2,PolyPoint.RADIUS*2) );
		e.setBackgroundColor(ColorConstants.red);
		return e;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeLocation(int x, int y) {
		getFigure().setLocation(new Point(x, y));
	}

}
