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

	//model�ı仯����������еļ���������仯
	public boolean addChild(ModelElement p){
		if (p instanceof PolyPoint) {
			if (p != null && pointList.add((PolyPoint) p)) {
				// ��֪���м�����model��editpart listener
				firePropertyChange(CHILD_ADDED_PROP, null, p);
				return true;
			}
		}
		else if(p instanceof PolyTriangle){
			if (p != null && triangleList.add((PolyTriangle) p)) {
				// ��֪���м�����model��editpart listener
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
				// ��֪���м�����model��editpart listener
				firePropertyChange(CHILD2_ADDED_PROP, null, p);
				return true;
			}
		}
		return false;
	}
	
	//���������model
	public List getChildren(){
		List l=new ArrayList();
		for(PolyPoint p: pointList)
			l.add(p);
		for(PolyTriangle t: triangleList)
			l.add(t);
		return l;
	}
	//��ȡ���е�������
	public List<PolyTriangle> getTriangle(){
		//����һ���¶��У���ֹ��ԭ���е��޸�
		return new ArrayList<PolyTriangle>(triangleList);
	}
	//�ҵ�����±�����ɵ���������
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
