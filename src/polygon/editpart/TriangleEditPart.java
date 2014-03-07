package polygon.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Polygon;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import polygon.model.ModelElement;
import polygon.model.PolyPoint;
import polygon.model.PolyTriangle;

public class TriangleEditPart extends AbstractGraphicalEditPart implements
PropertyChangeListener{

	// ×¢²á¼àÌý
		public void activate() {
			if (!isActive()) {
				super.activate();
				((ModelElement) getModel()).addPropertyChangeListener(this);
			}
		}

		// È¡Ïû¼àÌý
		public void deactivate() {
			if (isActive()) {
				super.deactivate();
				((ModelElement) getModel()).removePropertyChangeListener(this);
			}
		}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}

	@Override
	protected IFigure createFigure() {
		System.out.println("i create a Figure");
		Polygon ps=new Polygon();
		PolyTriangle t=getCastedModel();
		List<PolyPoint> pList=t.getTriPoint();
		for(PolyPoint pp:pList){
			System.out.println(pp.getX()+"   "+pp.getY());
			ps.getPoints().addPoint(pp.getX(),pp.getY());
		}
		ps.setFill(true);
		ps.setBackgroundColor(ColorConstants.blue);
		return ps;
	}
	
	private PolyTriangle getCastedModel(){
		return (PolyTriangle)getModel();
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}

}
