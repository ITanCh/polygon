package polygon.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import polygon.model.listener.DiagramListener;

public class Diagram {
	private List<PolyPoint> pointList=new ArrayList<PolyPoint>();
	protected Collection<DiagramListener> listenerList=new HashSet<DiagramListener>();//记录对应的EditPart
	public boolean addPoint(PolyPoint p){
		if(p==null||!pointList.add(p))
			return false;
		//告知所有监听该model的editpart listener
		for (DiagramListener listener: listenerList)
			listener.addChild(p);
		return true;
	}
	
	public List getChildrenList(){
		return pointList;
	}
	
	public boolean removePoint(PolyPoint p){
		if(p!=null&&pointList.remove(p))
			return true;
		return false;
	}
	
	public void addPartListener(DiagramListener d){
		listenerList.add(d);
	}
}
