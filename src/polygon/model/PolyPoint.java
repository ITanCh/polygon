package polygon.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import polygon.model.listener.ElementListener;

public class PolyPoint extends ModelElement {
	public static final int RADIUS=8;
	private int x=0, y=0;
	private String name="";
	private static final Image RECTANGLE_ICON = createImage("icons/rectangle16.gif");
	private static IPropertyDescriptor[] descriptors;
	private static final String XPOS_PROP = "Point.xPos";
	private static final String YPOS_PROP = "Point.yPos";
	private static final String NAME_PROP="Point.name";
	
	private List sourceConnections = new ArrayList();
	private List targetConnections = new ArrayList();

	static {
		descriptors = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(XPOS_PROP, "X"), // id and
				new TextPropertyDescriptor(YPOS_PROP, "Y"),
				new TextPropertyDescriptor(NAME_PROP,"Name")};
		// use a custom cell editor validator for all four array entries
		for (int i = 0; i < descriptors.length; i++) {
			((PropertyDescriptor) descriptors[i])
					.setValidator(new ICellEditorValidator() {
						public String isValid(Object value) {
							int intValue = -1;
							try {
								intValue = Integer.parseInt((String) value);
							} catch (NumberFormatException exc) {
								return "Not a number";
							}
							return (intValue >= 0) ? null
									: "Value must be >=  0";
						}
					});
		}
	} // static
	
	public PolyPoint(){};
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String n){
		name=n;
	}
	
	public boolean setLocation(int newX, int newY) {
		if (x == newX && y == newY)
			return false;
		x = newX;
		y = newY;
		for (ElementListener listener: listenerList)
			listener.changeLocation(x,y);
		return true;
	}
	
	public Image getIcon() {
		return RECTANGLE_ICON;
	}
		
	//connection
	void addConnection(PolyConnection conn) {
		if (conn == null || conn.getSource() == conn.getTarget()) {
			throw new IllegalArgumentException();
		}
		if (conn.getSource() == this) {
			sourceConnections.add(conn);
		} else if (conn.getTarget() == this) {
			targetConnections.add(conn);
		}
	}
	
	void removeConnection(PolyConnection conn) {
		if (conn == null) {
			throw new IllegalArgumentException();
		}
		if (conn.getSource() == this) {
			sourceConnections.remove(conn);
		} else if (conn.getTarget() == this) {
			targetConnections.remove(conn);
		}
	}
	public List getSourceConnections() {
		return new ArrayList(sourceConnections);
	}

	public List getTargetConnections() {
		return new ArrayList(targetConnections);
	}
	//Property
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors;
	}
	public Object getPropertyValue(Object propertyId) {
		if (XPOS_PROP.equals(propertyId)) {
			return Integer.toString(x);
		}
		if (YPOS_PROP.equals(propertyId)) {
			return Integer.toString(y);
		}	
		if(NAME_PROP.equals(propertyId)){
			return name;
		}
		return super.getPropertyValue(propertyId);
	}
		
	public void setPropertyValue(Object propertyId, Object value){
			if (XPOS_PROP.equals(propertyId)) {
				int newX = Integer.parseInt((String) value);
				setLocation(newX, y);
			} else if (YPOS_PROP.equals(propertyId)) {
				int newY = Integer.parseInt((String) value);
				setLocation(x, newY);	
			} else if(NAME_PROP.equals(propertyId)){
				String n=(String)value;
				setName(n);
			}
			else {
				super.setPropertyValue(propertyId, value);
			}
	}

}
