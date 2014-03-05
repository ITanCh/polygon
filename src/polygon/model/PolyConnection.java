package polygon.model;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.draw2d.Graphics;

public class PolyConnection extends ModelElement {

	public static final Integer SOLID_CONNECTION = new Integer(
			Graphics.LINE_SOLID);
	public static final Integer DASHED_CONNECTION = new Integer(
			Graphics.LINE_DASH);
	public static final String ICON1="icons/line16.gif";

	/** Property ID to use when the line style of this connection is modified. */
	public static final String LINESTYLE_PROP = "LineStyle";
	private static final IPropertyDescriptor[] descriptors = new IPropertyDescriptor[1];
	private static final String SOLID_STR = "Solid";
	private static final String DASHED_STR = "Dashed";
	/** True, if the connection is attached to its endpoints. */
	private boolean isConnected;
	/** Line drawing style for this connection. */
	private int lineStyle = Graphics.LINE_SOLID;
	/** Connection's source endpoint. */
	private PolyPoint source;
	/** Connection's target endpoint. */
	private PolyPoint target;

	static {
		descriptors[0] = new ComboBoxPropertyDescriptor(LINESTYLE_PROP,
				LINESTYLE_PROP, new String[] { SOLID_STR, DASHED_STR });
	}

	public PolyConnection(PolyPoint source, PolyPoint target) {
		reconnect(source, target);
	}

	public int getLineStyle() {
		return lineStyle;
	}

	public PolyPoint getSource() {
		return source;
	}

	public PolyPoint getTarget() {
		return target;
	}

	// 连接与断开连接
	public void disconnect() {
		if (isConnected) {
			source.removeConnection(this);
			target.removeConnection(this);
			isConnected = false;
		}
	}

	public void reconnect() {
		if (!isConnected) {
			source.addConnection(this);
			target.addConnection(this);
			isConnected = true;
		}
	}

	public void reconnect(PolyPoint newSource, PolyPoint newTarget) {
		if (newSource == null || newTarget == null || newSource == newTarget) {
			throw new IllegalArgumentException();
		}
		disconnect();
		this.source = newSource;
		this.target = newTarget;
		reconnect();
	}

	// Set the line drawing style of this connection.
	public void setLineStyle(int lineStyle) {
		if (lineStyle != Graphics.LINE_DASH && lineStyle != Graphics.LINE_SOLID) {
			throw new IllegalArgumentException();
		}
		this.lineStyle = lineStyle;
		firePropertyChange(LINESTYLE_PROP, null, new Integer(this.lineStyle));
	}

	// Property
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors;
	}

	public Object getPropertyValue(Object id) {
		if (id.equals(LINESTYLE_PROP)) {
			if (getLineStyle() == Graphics.LINE_DASH)
				// Dashed is the second value in the combo dropdown
				return new Integer(1);
			// Solid is the first value in the combo dropdown
			return new Integer(0);
		}
		return super.getPropertyValue(id);
	}

	public void setPropertyValue(Object id, Object value) {
		if (id.equals(LINESTYLE_PROP))
			setLineStyle(new Integer(1).equals(value) ? Graphics.LINE_DASH
					: Graphics.LINE_SOLID);
		else
			super.setPropertyValue(id, value);
	}

}