package polygon.model;

import java.util.ArrayList;
import java.util.List;

public class Diagram extends ModelElement{
	//Property ID
	public static final String CHILD_ADDED_PROP = "ShapesDiagram.ChildAdded";
	public static final String CHILD_REMOVED_PROP = "ShapesDiagram.ChildRemoved";
	
	private List<PolyPoint> pointList=new ArrayList<PolyPoint>();

	//model的变化必须告诉所有的监听者这个变化
	public boolean addChild(PolyPoint p){
		if(p!=null&&pointList.add(p)){	
			//告知所有监听该model的editpart listener
			firePropertyChange(CHILD_ADDED_PROP,null,p);
			return true;
		}
		return false;
	}
	
	public boolean removePoint(PolyPoint p){
		if(p!=null&&pointList.remove(p)){
			firePropertyChange(CHILD_REMOVED_PROP, null, p);
			return true;
		}
		return false;
	}
	
	//获得所有子model
	public List getChildren(){
		return pointList;
	}
	
}
