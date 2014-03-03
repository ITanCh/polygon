package polygon.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import polygon.Activator;

public class ModelElement implements IPropertySource {
	/** An empty property descriptor. */
	//�����������Ҫ�У�����dagram��ȱ���������������쳣
	private static final IPropertyDescriptor[] EMPTY_ARRAY = new IPropertyDescriptor[0];
	//��¼��model�����м�����
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);
	
	//��Ӽ�����
	public synchronized void addPropertyChangeListener(PropertyChangeListener l) {
		if (l == null) {
			throw new IllegalArgumentException();
		}
		pcsDelegate.addPropertyChangeListener(l);
	}
	//�Ƴ�����
	public synchronized void removePropertyChangeListener(
			PropertyChangeListener l) {
		if (l != null) {
			pcsDelegate.removePropertyChangeListener(l);
		}
	}
	//��֪���еļ����߸�model�������б仯
	protected void firePropertyChange(String property, Object oldValue,Object newValue) {
		if (pcsDelegate.hasListeners(property)) {
			pcsDelegate.firePropertyChange(property, oldValue, newValue);
		}
	}
	//���µĺö෽��Ҫ��������ʵ��
	@Override
	public Object getEditableValue() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return EMPTY_ARRAY ;
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
