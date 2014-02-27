package polygon.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import polygon.editpart.PolyEditPartFactory;
import polygon.editpart.PolyTreeEditPartFactory;
import polygon.model.Diagram;

public class PolygonEditor extends GraphicalEditorWithFlyoutPalette  {
private Diagram diagram=new Diagram();
	
	public PolygonEditor(){
		setEditDomain(new DefaultEditDomain(this));
	}
	
	
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		//设置EditPartFactory
		viewer.setEditPartFactory(new PolyEditPartFactory());
		//设置根EditPart
		viewer.setRootEditPart(new ScalableFreeformRootEditPart());
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		//用来获取PaletteRoot
		//PaletteRoot对象是Palette树结构的树根，
		//可以通过add函数向其中插入PaletteEntry对象
		return PolyEditorPaletteFactory.createPalette();
	}

	@Override
	protected void initializeGraphicalViewer() {
		//设置context，就是一个编辑器的总Model,用来容纳所有的其他的Model	
		GraphicalViewer viewer=getGraphicalViewer();
		viewer.setContents(diagram);
		viewer.addDropTargetListener(new TemplateTransferDropTargetListener(viewer));
	}
	

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}
	public Object getAdapter(Class type) {
		//TODO 这个函数用于获得指定类型的适配器类，
		//一般重载这个函数创建自己的Outline Page。
		//这个函数可以用来创建很多其它的东西，比如Property Sheet，你可以参考其父类的实现。
		if (type == IContentOutlinePage.class)
			return new TcOutlinePage(new TreeViewer());
		return super.getAdapter(type);
	}
	
	//设置监听，当拖动palette里的部件时,具体要求要根据部件的具体设置
	//参考TcEditorPaletteFactory.createPalette();
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}
		};
	}
	
	//设置自己的outline，和主editor差不多
	public class TcOutlinePage extends ContentOutlinePage {
		
		public TcOutlinePage(EditPartViewer viewer) {
			super(viewer);
		}

		public void createControl(Composite parent) {
			// create outline viewer page
			getViewer().createControl(parent);
			// configure outline viewer
			getViewer().setEditDomain(getEditDomain());
			getViewer().setEditPartFactory(new PolyTreeEditPartFactory());
			// configure & add context menu to viewer
			ContextMenuProvider cmProvider = new PolyEditorContextMenuProvider(
					getViewer(), getActionRegistry());
			getViewer().setContextMenu(cmProvider);
			getSite().registerContextMenu(
					"org.eclipse.gef.examples.shapes.outline.contextmenu",
					cmProvider, getSite().getSelectionProvider());
			// hook outline viewer
			getSelectionSynchronizer().addViewer(getViewer());
			// initialize outline viewer with model
			getViewer().setContents(diagram);
			// show outline viewer
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.part.IPage#dispose()
		 */
		public void dispose() {
			// unhook outline viewer
			getSelectionSynchronizer().removeViewer(getViewer());
			// dispose
			super.dispose();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.part.IPage#getControl()
		 */
		public Control getControl() {
			return getViewer().getControl();
		}

		/**
		 * @see org.eclipse.ui.part.IPageBookViewPage#init(org.eclipse.ui.part.IPageSite)
		 */
		public void init(IPageSite pageSite) {
			super.init(pageSite);
			ActionRegistry registry = getActionRegistry();
			IActionBars bars = pageSite.getActionBars();
			String id = ActionFactory.UNDO.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.REDO.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.DELETE.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
		}
	}
}
