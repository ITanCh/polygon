package polygon.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import polygon.model.listener.DiagramListener;
import polygon.model.listener.ElementListener;

public class PolyPoint extends ModelElement {
	public static final int RADIUS=8;
	private int x=0, y=0;
	private String name="point";
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
		//这个需要根据具体情况修改
		for (int i = 0; i < descriptors.length-1; i++) {
			((PropertyDescriptor) descriptors[i])
					.setValidator(new ICellEditorValidator() {
						public String isValid(Object value) {
							int intValue = -1;
							try {
								intValue = Integer.parseInt((String) value);
							} catch (NumberFormatException exc) {
								return "Not a number";
							}
							return null;
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
			System.out.println("add source!!!!!!!!!!!!!!!");
			for (ElementListener listener: listenerList)
				listener.changeConnection();
		} else if (conn.getTarget() == this) {
			targetConnections.add(conn);
			for (ElementListener listener: listenerList)
				listener.changeConnection();
		}
	}
	
	void removeConnection(PolyConnection conn) {
		if (conn == null) {
			throw new IllegalArgumentException();
		}
		if (conn.getSource() == this) {
			sourceConnections.remove(conn);
			for (ElementListener listener: listenerList)
				listener.changeConnection();
		} else if (conn.getTarget() == this) {
			targetConnections.remove(conn);
			for (ElementListener listener: listenerList)
				listener.changeConnection();
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
				System.out.println("name "+n);
				setName(n);
			}
			else {
				super.setPropertyValue(propertyId, value);
			}
	}

}
