package polygon.editpart;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import polygon.model.Diagram;
import polygon.model.PolyPoint;

public class PolyTreeEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if(model instanceof PolyPoint){
			return new PolyPointTreeEditPart((PolyPoint) model); 
		}
		if(model instanceof Diagram){
			return new DiagramTreeEditPart((Diagram)model);
		}
		return null;
	}

}
