package polygon.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import polygon.command.ConnectionCreateCommand;
import polygon.command.ConnectionReconnectCommand;
import polygon.model.PolyConnection;
import polygon.model.PolyPoint;
import polygon.model.ModelElement;

public class PointEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener, NodeEditPart {

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
	@Override
	protected IFigure createFigure() {
		IFigure e = new Ellipse(); // 用椭圆来画一个小圆点
		PolyPoint p = (PolyPoint) getModel();
		e.setBounds(new Rectangle(p.getX() - PolyPoint.RADIUS, p.getY()
				- PolyPoint.RADIUS, PolyPoint.RADIUS * 2, PolyPoint.RADIUS * 2));
		e.setBackgroundColor(ColorConstants.red);
		return e;
	}

	@Override
	protected void createEditPolicies() {
		// allow the creation of connections and
		// and the reconnection of connections between Shape instances
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new GraphicalNodeEditPolicy() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
					 * getConnectionCompleteCommand
					 * (org.eclipse.gef.requests.CreateConnectionRequest)
					 */
					protected Command getConnectionCompleteCommand(
							CreateConnectionRequest request) {
						ConnectionCreateCommand cmd = (ConnectionCreateCommand) request
								.getStartCommand();
						cmd.setTarget((PolyPoint) getHost().getModel());
						return cmd;
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
					 * getConnectionCreateCommand
					 * (org.eclipse.gef.requests.CreateConnectionRequest)
					 */
					protected Command getConnectionCreateCommand(
							CreateConnectionRequest request) {
						PolyPoint source = (PolyPoint) getHost().getModel();
						int style = ((Integer) request.getNewObjectType())
								.intValue();
						ConnectionCreateCommand cmd = new ConnectionCreateCommand(
								source, style);
						request.setStartCommand(cmd);
						return cmd;
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
					 * getReconnectSourceCommand
					 * (org.eclipse.gef.requests.ReconnectRequest)
					 */
					protected Command getReconnectSourceCommand(
							ReconnectRequest request) {
						PolyConnection conn = (PolyConnection) request
								.getConnectionEditPart().getModel();
						PolyPoint newSource = (PolyPoint) getHost().getModel();
						ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(
								conn);
						cmd.setNewSource(newSource);
						return cmd;
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
					 * getReconnectTargetCommand
					 * (org.eclipse.gef.requests.ReconnectRequest)
					 */
					protected Command getReconnectTargetCommand(
							ReconnectRequest request) {
						PolyConnection conn = (PolyConnection) request
								.getConnectionEditPart().getModel();
						PolyPoint newTarget = (PolyPoint) getHost().getModel();
						ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(
								conn);
						cmd.setNewTarget(newTarget);
						return cmd;
					}
				});

	}

	private PolyPoint getCastedModel() {
		return (PolyPoint) getModel();
	}

	// 获取connections,重写的absEP里的函数
	protected List getModelSourceConnections() {
		return getCastedModel().getSourceConnections();
	}

	protected List getModelTargetConnections() {
		return getCastedModel().getTargetConnections();
	}

	// 建立一个连线的端点,重写NodeEditPart里的函数
	protected ConnectionAnchor getConnectionAnchor() {
		return new EllipseAnchor(getFigure());
	}
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return getConnectionAnchor();
	}
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (PolyPoint.SOURCE_CONNECTIONS_PROP.equals(prop)) {
			refreshSourceConnections();
		} else if (PolyPoint.TARGET_CONNECTIONS_PROP.equals(prop)) {
			refreshTargetConnections();
		}else if (PolyPoint.LOCATION_PROP.equals(prop)) {
			Point newLocation=(Point)evt.getNewValue();
			getFigure().setLocation(newLocation);
		}
	}

}
