package polygon.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import polygon.Activator;
import polygon.model.listener.ElementListener;

public class ModelElement implements IPropertySource {
	protected Collection<ElementListener> listenerList=new HashSet<ElementListener>();//记录对应的EditPart
	//EditPart
	public void addPartListener(ElementListener d){
		listenerList.add(d);
	}
	
	//以下的好多方法要在子类中实现
	@Override
	public Object getEditableValue() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getPropertyValue(Object id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		
	}
	
	protected static Image createImage(String name) {
		InputStream stream = Activator.class.getResourceAsStream(name);
		Image image = new Image(null, stream);
		try {
			stream.close();
		} catch (IOException ioe) {
		}
		return image;
	}
	
	
}
