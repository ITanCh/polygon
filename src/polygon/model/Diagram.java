package polygon.model;

import java.util.ArrayList;
import java.util.List;

public class Diagram extends ModelElement{
	//Property ID
	public static final String CHILD_ADDED_PROP = "ShapesDiagram.ChildAdded";
	public static final String CHILD_REMOVED_PROP = "ShapesDiagram.ChildRemoved";
	
	private List<PolyPoint> pointList=new ArrayList<PolyPoint>();

	//model�ı仯����������еļ���������仯
	public boolean addChild(PolyPoint p){
		if(p!=null&&pointList.add(p)){	
			//��֪���м�����model��editpart listener
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
	
	//���������model
	public List getChildren(){
		return pointList;
	}
	
}
