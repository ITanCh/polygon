package polygon.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class PolyPoint extends ModelElement {
	
	public static final int RADIUS=8;
	private int x=0, y=0;
	private String name="point";
	private Diagram parent;
	
	public static final String ICON1="icons/point16.gif";
	private static final Image RECTANGLE_ICON = createImage(ICON1);
	
	private static IPropertyDescriptor[] descriptors;
	private static final String XPOS_PROP = "Point.xPos";
	private static final String YPOS_PROP = "Point.yPos";
	private static final String NAME_PROP="Point.name";
	
	public static final String LOCATION_PROP = "Point.Location";
	public static final String NAME_CHANGE_PROP = "Point.Name.Change";
	public static final String SOURCE_CONNECTIONS_PROP = "Point.SourceConn";
	public static final String TARGET_CONNECTIONS_PROP = "Point.TargetConn";
	
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
	
	public void setParent(Diagram p){
		parent=p;
	}
	public Diagram getParent(){
		return parent;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String n){
		name=n;
		firePropertyChange(NAME_CHANGE_PROP , null, name);
	}
	
	public Image getIcon() {
		return RECTANGLE_ICON;
	}
	
	//性质的改变,通知监听者
	public boolean setLocation(int newX, int newY) {
		if (x == newX && y == newY)
			return false;
		x = newX;
		y = newY;
		Point location=new Point(x,y);
		firePropertyChange(LOCATION_PROP, null, location);
		return true;
	}
	
	public Point getLocation(){
		return new Point(x,y);
	}
	
	//connection
	void addConnection(PolyConnection conn) {
		if (conn == null || conn.getSource() == conn.getTarget()) {
			throw new IllegalArgumentException();
		}
		if (conn.getSource() == this) {
			sourceConnections.add(conn);
			firePropertyChange(SOURCE_CONNECTIONS_PROP, null, conn);
		} else if (conn.getTarget() == this) {
			targetConnections.add(conn);
			firePropertyChange(TARGET_CONNECTIONS_PROP, null, conn);
		}
	}
	
	void removeConnection(PolyConnection conn) {
		if (conn == null) {
			throw new IllegalArgumentException();
		}
		if (conn.getSource() == this) {
			sourceConnections.remove(conn);
			firePropertyChange(SOURCE_CONNECTIONS_PROP, null, conn);
		} else if (conn.getTarget() == this) {
			targetConnections.remove(conn);
			firePropertyChange(TARGET_CONNECTIONS_PROP, null, conn);
		}
	}
	public List getSourceConnections() {
		return new ArrayList(sourceConnections);
	}

	public List getTargetConnections() {
		return new ArrayList(targetConnections);
	}
	
	public List getAllConnections(){
		ArrayList array=new ArrayList(sourceConnections);
		array.addAll(targetConnections);
		return array;
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
