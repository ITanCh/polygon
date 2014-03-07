package polygon.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import polygon.command.PointCreateCommand;
import polygon.command.PointMoveCommand;
import polygon.model.Diagram;
import polygon.model.PolyPoint;
import polygon.model.ModelElement;

public class DiagramEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener {

	// 注册监听
	public void activate() {
		if (!isActive()) {
			super.activate();
			((ModelElement) getModel()).addPropertyChangeListener(this);
		}
	}

	// 取消监听
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			((ModelElement) getModel()).removePropertyChangeListener(this);
		}
	}

	@Override
	protected IFigure createFigure() {
		Figure figure = new FreeformLayer();
		figure.setBorder(new MarginBorder(3));
		figure.setLayoutManager(new FreeformLayout());
		return figure;
	}

	// 命令产生于此
	@Override
	protected void createEditPolicies() {
		// disallows the removal of this edit part from its parent
		installEditPolicy(EditPolicy.COMPONENT_ROLE,new RootComponentEditPolicy());
		
		// 对从editor获得的事件，request要根据policy转换为command
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new XYLayoutEditPolicy() {
			// 创建图形的命令
			protected Command getCreateCommand(CreateRequest request) {
				Object type = request.getNewObjectType();
				Rectangle box = (Rectangle) getConstraintFor(request);
				Diagram diagram = (Diagram) getModel();
				// 根据请求的type可以判断出具体的请求类型
				if (type == PolyPoint.class) {
					PolyPoint p = (PolyPoint) request.getNewObject();
					return new PointCreateCommand(p, diagram, box);
				}
				return null;
			}

			/**
			 * Return a command for moving elements around the canvas
			 */
			protected Command createChangeConstraintCommand(ChangeBoundsRequest request, EditPart child,
					Object constraint) {
				if (child.getModel() instanceof PolyPoint) {
					PolyPoint p = (PolyPoint) child.getModel();
					Rectangle box = (Rectangle) constraint;
					Point loc = new Point(box.x, box.y);
					return new PointMoveCommand(p, loc, getCastedModel());
				}
				return null;
			}
			
			protected Command createChangeConstraintCommand(EditPart child,
					Object constraint) {
				// not used in this example
				return null;
			}

		});
	}

	// 当model发生变化的时候
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		// 当有点从该diagram中添加或者移除时
		if (Diagram.CHILD_ADDED_PROP.equals(prop)
				|| Diagram.CHILD_REMOVED_PROP.equals(prop)
				||Diagram.CHILD2_ADDED_PROP.equals(prop)) {
			refreshChildren();
		}
	}

	public void reprintChildren(){
		refreshChildren();
	}
	
	// 重写absEP中的该函数，从而可以得到model的所有子model
	// 在refreshChildren中会重新添加子editpart，并且绘制所有的子图
	protected List getModelChildren() {
		return getCastedModel().getChildren();
	}

	private Diagram getCastedModel() {
		return (Diagram) getModel();
	}

}
