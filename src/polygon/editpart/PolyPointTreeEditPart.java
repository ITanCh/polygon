package polygon.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import polygon.model.ModelElement;
import polygon.model.PolyPoint;

public class PolyPointTreeEditPart extends AbstractTreeEditPart  implements PropertyChangeListener {
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
		PolyPoint p=getCastedModel();
		String s=p.getName()+"("+p.getX()+","+p.getY()+")";
		return s;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (PolyPoint.LOCATION_PROP.equals(prop)|| PolyPoint.NAME_CHANGE_PROP.equals(prop)) 
			refreshVisuals();
	}
		
}
