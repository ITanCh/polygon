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
		t.addPartListener(this);		//与对应的Diagram产生关联
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
		//对从editor获得的事件，request要根据policy转换为command
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new XYLayoutEditPolicy() {
			//创建图形的命令
			protected Command getCreateCommand(CreateRequest request) {
				Object type = request.getNewObjectType();
				Rectangle box = (Rectangle) getConstraintFor(request);
				Diagram diagram = (Diagram) getModel();
				//根据请求的type可以判断出具体的请求类型
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
	
	//移除矩形
	public void pointRemoved(PolyPoint p) {
		Object part = getViewer().getEditPartRegistry().get(p);
		if (part instanceof EditPart)
			removeChild((EditPart) part);
	}
	
	//向画面中添加矩形	
	@Override
	public void addChild(PolyPoint p) {
		//内部函数是通过该editpartFactory创建model的editpart,
		//editpart创建figure，然后添加到根画布上的
				addChild(createChild(p),0);
	}

}
