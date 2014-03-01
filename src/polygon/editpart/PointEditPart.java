package polygon.editpart;

import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
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
import polygon.model.listener.ElementListener;

public class PointEditPart extends AbstractGraphicalEditPart implements ElementListener,NodeEditPart {
	
	public PointEditPart(PolyPoint p){
		setModel(p);
		p.addPartListener(this);
	}
	@Override
	protected IFigure createFigure() {
		IFigure e=new Ellipse();					//用椭圆来画一个小圆点
		PolyPoint p=(PolyPoint) getModel();
		e.setBounds(new Rectangle(p.getX()-PolyPoint.RADIUS,
				p.getY()-PolyPoint.RADIUS,PolyPoint.RADIUS*2,PolyPoint.RADIUS*2) );
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
	@Override
	public void changeLocation(int x, int y) {
		getFigure().setLocation(new Point(x, y));
	}
	
	//建立一个连线的端点
	protected ConnectionAnchor getConnectionAnchor() {
		 return new EllipseAnchor(getFigure());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections
	 * ()
	 */
	protected List getModelSourceConnections() {
		return getCastedModel().getSourceConnections();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections
	 * ()
	 */
	protected List getModelTargetConnections() {
		return getCastedModel().getTargetConnections();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef
	 * .ConnectionEditPart)
	 */
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef
	 * .Request)
	 */
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef
	 * .ConnectionEditPart)
	 */
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef
	 * .Request)
	 */
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}
	@Override
	public void changeConnection() {
		refreshSourceConnections();
		refreshTargetConnections();
	}


}
