package polygon.editpart;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import polygon.model.Diagram;
import polygon.model.PolyConnection;
import polygon.model.PolyPoint;


public class PolyEditPartFactory implements EditPartFactory {
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		// TODO Auto-generated method stub
		EditPart editPart = null;
		if(model instanceof Diagram)
			editPart=new DiagramEditPart((Diagram)model);
		else if(model instanceof PolyPoint)
			editPart=new PointEditPart((PolyPoint)model);
		else if(model instanceof PolyConnection) {
			editPart= new ConnectionEditPart((PolyConnection)model);
		}
		return editPart;
	}
}
