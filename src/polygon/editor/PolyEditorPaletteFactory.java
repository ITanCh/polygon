package polygon.editor;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteToolbar;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.jface.resource.ImageDescriptor;

import polygon.Activator;
import polygon.model.PolyConnection;
import polygon.model.PolyPoint;

public class PolyEditorPaletteFactory {
	// drawer
	private static PaletteContainer createShapesDrawer() {
		PaletteDrawer componentsDrawer = new PaletteDrawer("Shapes");

		// 设置该部件对应的类为TcRectangle.class
		CombinedTemplateCreationEntry component = new CombinedTemplateCreationEntry(
				"Point", "Create a Point", PolyPoint.class, new SimpleFactory(
						PolyPoint.class), ImageDescriptor.createFromFile(
						Activator.class, PolyPoint.ICON1),
				ImageDescriptor.createFromFile(Activator.class,
						"icons/rectangle24.gif"));
		componentsDrawer.add(component);
		return componentsDrawer;
	}

	// group
	private static PaletteContainer createToolsGroup(PaletteRoot palette) {
		PaletteToolbar toolbar = new PaletteToolbar("Tools");

		// Add a selection tool to the group
		ToolEntry tool = new PanningSelectionToolEntry();
		toolbar.add(tool);
		palette.setDefaultEntry(tool);

		// Add a marquee tool to the group
		toolbar.add(new MarqueeToolEntry());

		// Add (solid-line) connection tool
		tool = new ConnectionCreationToolEntry("Solid connection",
				"Create a solid-line connection", new CreationFactory() {
					public Object getNewObject() {
						return null;
					}

					// see ShapeEditPart#createEditPolicies()
					// this is abused to transmit the desired line style
					public Object getObjectType() {
						return PolyConnection.SOLID_CONNECTION;
					}
				}, ImageDescriptor.createFromFile(Activator.class,
						PolyConnection.ICON1),
				ImageDescriptor.createFromFile(Activator.class,
						"icons/connection_s24.gif"));
		toolbar.add(tool);
		return toolbar;
	}

	public static PaletteRoot createPalette() {
		PaletteRoot palette = new PaletteRoot();	
		palette.add(createToolsGroup(palette));
		palette.add(createShapesDrawer());
		return palette;
	}
}
