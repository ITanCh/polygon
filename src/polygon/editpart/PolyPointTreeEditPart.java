package polygon.editpart;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import polygon.model.PolyPoint;

public class PolyPointTreeEditPart extends AbstractTreeEditPart {
	PolyPointTreeEditPart(PolyPoint model){
		super(model);
	}

	private PolyPoint getCastedModel() {
		return (PolyPoint) getModel();
	}
	
	protected Image getImage() {
		return getCastedModel().getIcon();
	}
	
	protected String getText() {
		return getCastedModel().toString();
	}
		
}
