package polygon.model;

import java.util.ArrayList;
import java.util.List;

public class Diagram extends ModelElement{
	//Property ID
	public static final String CHILD_ADDED_PROP = "Diagram.ChildAdded";
	public static final String CHILD2_ADDED_PROP = "Diagram.ChildAdded2";
	public static final String CHILD_REMOVED_PROP = "ShapesDiagram.ChildRemoved";
	
	private List<PolyPoint> pointList=new ArrayList<PolyPoint>();
	private List<PolyTriangle> triangleList=new ArrayList<PolyTriangle>();

	//model的变化必须告诉所有的监听者这个变化
	public boolean addChild(ModelElement p){
		if (p instanceof PolyPoint) {
			if (p != null && pointList.add((PolyPoint) p)) {
				// 告知所有监听该model的editpart listener
				firePropertyChange(CHILD_ADDED_PROP, null, p);
				return true;
			}
		}
		else if(p instanceof PolyTriangle){
			if (p != null && triangleList.add((PolyTriangle) p)) {
				// 告知所有监听该model的editpart listener
				firePropertyChange(CHILD2_ADDED_PROP, null, p);
				return true;
			}
		}
		return false;
	}
	
	public boolean removeChild(ModelElement p){
		if (p instanceof PolyPoint) {
			if (p != null && triangleList.remove((PolyTriangle)p)) {
				firePropertyChange(CHILD_REMOVED_PROP, null, p);
				return true;
			}
		}
		else if(p instanceof PolyTriangle){
			if (p != null && triangleList.remove((PolyTriangle) p)) {
				// 告知所有监听该model的editpart listener
				firePropertyChange(CHILD2_ADDED_PROP, null, p);
				return true;
			}
		}
		return false;
	}
	
	//获得所有子model
	public List getChildren(){
		List l=new ArrayList();
		for(PolyPoint p: pointList)
			l.add(p);
		for(PolyTriangle t: triangleList)
			l.add(t);
		return l;
	}
	//获取所有的三角形
	public List<PolyTriangle> getTriangle(){
		//返回一个新队列，防止对原队列的修改
		return new ArrayList<PolyTriangle>(triangleList);
	}
	//找到这个新边所组成的新三角形
	public boolean findTriangle(PolyPoint p1,PolyPoint p2){
		boolean flag=false;
		for(Object obj: p1.getAllConnections()){
			PolyPoint p3=((PolyConnection)obj).getTarget();
			if(p3==p1)p3=((PolyConnection)obj).getSource();
			for(Object obj2: p3.getAllConnections()){
				if(p2==((PolyConnection)obj2).getTarget()||p2==((PolyConnection)obj2).getSource()){
					if(p1!=p2&&p2!=p3&&p1!=p3){
						PolyTriangle t=new PolyTriangle(p1,p2,p3);
						t.setParent(this);
						addChild(t);
						flag=true;
					}
				}
			}
		}
		return flag;
	}
	
}
