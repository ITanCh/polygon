package polygon.editpart;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import polygon.model.Diagram;
import polygon.model.PolyConnection;
import polygon.model.PolyPoint;
import polygon.model.PolyTriangle;


public class PolyEditPartFactory implements EditPartFactory {
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart editPart = null;
		if(model instanceof Diagram)
			editPart=new DiagramEditPart();
		else if(model instanceof PolyPoint)
			editPart=new PointEditPart();
		else if(model instanceof PolyConnection) 
			editPart= new ConnectionEditPart();
		else if(model instanceof PolyTriangle)
			editPart=new TriangleEditPart();
		editPart.setModel(model);
		return editPart;
	}
}
