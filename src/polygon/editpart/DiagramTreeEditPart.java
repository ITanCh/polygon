package polygon.editpart;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

import polygon.model.Diagram;
import polygon.model.PolyPoint;
import polygon.model.listener.DiagramListener;

public class DiagramTreeEditPart extends  AbstractTreeEditPart implements DiagramListener{
	DiagramTreeEditPart(Diagram model){
		super(model);
		model.addPartListener(this);
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
	//获取该model的所有子model
	protected List getModelChildren() {
		return ((Diagram)getModel()).getChildrenList(); // a list of shapes
	}

	@Override
	public void addChild(PolyPoint p) {
		addChild(createChild(p),0);
	}

}
