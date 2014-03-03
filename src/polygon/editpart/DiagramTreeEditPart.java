package polygon.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

import polygon.model.Diagram;
import polygon.model.ModelElement;

public class DiagramTreeEditPart extends  AbstractTreeEditPart implements PropertyChangeListener{
	
	DiagramTreeEditPart(Diagram model) {
		super(model);
	}
	
	public void activate() {
		if (!isActive()) {
			super.activate();
			((ModelElement) getModel()).addPropertyChangeListener(this);
		}
	}
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			((ModelElement) getModel()).removePropertyChangeListener(this);
		}
	}
	
	
	protected void createEditPolicies() {
		// If this editpart is the root content of the viewer, then disallow
		// removal
		if (getParent() instanceof RootEditPart) {
			installEditPolicy(EditPolicy.COMPONENT_ROLE,
					new RootComponentEditPolicy());
		}
	}
	
	private EditPart getEditPartForChild(Object child) {
		return (EditPart) getViewer().getEditPartRegistry().get(child);
	}
	//获取该model的所有子model，重写
	protected List getModelChildren() {
		return ((Diagram)getModel()).getChildren(); // a list of shapes
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (Diagram.CHILD_ADDED_PROP.equals(prop)) {
			addChild(createChild(evt.getNewValue()), -1);
		} else if (Diagram.CHILD_REMOVED_PROP.equals(prop)) {
			removeChild(getEditPartForChild(evt.getNewValue()));
		} else {
			refreshVisuals();
		}
	}

}
