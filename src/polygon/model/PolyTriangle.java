package polygon.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class PolyTriangle extends ModelElement {

	private Diagram parent;
	private List<PolyPoint>triPoint = new ArrayList<PolyPoint>();
	private static IPropertyDescriptor[] descriptors;
	private static final String P1_PROP = "Triangle.P1";
	private static final String P2_PROP = "Triangle.P2";
	private static final String P3_PROP = "Triangle.P3";


	static {
		descriptors = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(P1_PROP, "P1"), // id and
				new TextPropertyDescriptor(P2_PROP, "P2"),
				new TextPropertyDescriptor(P3_PROP, "P3") };
	}

	public PolyTriangle(PolyPoint p1, PolyPoint p2, PolyPoint p3) {
		triPoint.add(p1);
		triPoint.add(p2);
		triPoint.add(p3);
	}

	public List<PolyPoint> getTriPoint() {
		return triPoint;
	}

	public boolean addTriPoint(PolyPoint p) {
		return triPoint.size()<3&&triPoint.add(p);
	}
	public boolean removeTriPoint(PolyPoint p){
		return triPoint.size()>0&&triPoint.remove(p);
	}
	
	public void setParent(Diagram p){
		parent=p;
	}
	
	//当形状改变时
	public void changeMe(){
		//让父图重绘自己
		parent.removeChild(this);
		parent.addChild(this);
	}
	
}
