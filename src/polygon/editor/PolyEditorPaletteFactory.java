package polygon.editor;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.jface.resource.ImageDescriptor;

import polygon.Activator;
import polygon.model.PolyPoint;

public class PolyEditorPaletteFactory {
	private static PaletteContainer createShapesDrawer() {
		PaletteDrawer componentsDrawer = new PaletteDrawer("Shapes");

		//设置该部件对应的类为TcRectangle.class
		CombinedTemplateCreationEntry component = new CombinedTemplateCreationEntry(
				"Point", "Create a Point", PolyPoint.class,
				new SimpleFactory(PolyPoint.class),
				ImageDescriptor.createFromFile(Activator.class,"icons/rectangle16.gif"), 
				ImageDescriptor.createFromFile(Activator.class, "icons/rectangle24.gif"));
		componentsDrawer.add(component);
		return componentsDrawer;
	}
	public static PaletteRoot createPalette() {
		PaletteRoot palette = new PaletteRoot();
		palette.add(createShapesDrawer());
		return palette;
	}
}
