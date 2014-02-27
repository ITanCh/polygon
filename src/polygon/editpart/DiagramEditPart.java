package polygon.editpart;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import polygon.command.PointCreateCommand;
import polygon.command.PointMoveCommand;
import polygon.model.Diagram;
import polygon.model.PolyPoint;
import polygon.model.listener.DiagramListener;

public class DiagramEditPart extends AbstractGraphicalEditPart implements DiagramListener{

	public DiagramEditPart(Diagram t){
		setModel(t);
		t.addPartListener(this);		//���Ӧ��Diagram��������
	}
	@Override
	protected IFigure createFigure() {
		Figure figure = new FreeformLayer();
		figure.setBorder(new MarginBorder(3));
		figure.setLayoutManager(new FreeformLayout());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		//�Դ�editor��õ��¼���requestҪ����policyת��Ϊcommand
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new XYLayoutEditPolicy() {
			//����ͼ�ε�����
			protected Command getCreateCommand(CreateRequest request) {
				Object type = request.getNewObjectType();
				Rectangle box = (Rectangle) getConstraintFor(request);
				Diagram diagram = (Diagram) getModel();
				//���������type�����жϳ��������������
				if (type == PolyPoint.class) {
					PolyPoint p = (PolyPoint) request.getNewObject();
					return new PointCreateCommand(p, diagram, box);
				}
				return null;
			}
			
			/**
			 * Return a command for moving elements around the canvas
			 */
			protected Command createChangeConstraintCommand(
				ChangeBoundsRequest request, EditPart child, Object constraint
			) {
				PolyPoint p = (PolyPoint) child.getModel();
				Rectangle box = (Rectangle) constraint;
				return new PointMoveCommand(p, box);
			}
			
		});
	}
	
	//�Ƴ�����
	public void pointRemoved(PolyPoint p) {
		Object part = getViewer().getEditPartRegistry().get(p);
		if (part instanceof EditPart)
			removeChild((EditPart) part);
	}
	
	//��������Ӿ���	
	@Override
	public void addChild(PolyPoint p) {
		//�ڲ�������ͨ����editpartFactory����model��editpart,
		//editpart����figure��Ȼ����ӵ��������ϵ�
				addChild(createChild(p),0);
	}

}
