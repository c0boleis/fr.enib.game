package fr.enib.game.editor.graphe.examples.swing.editor;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;

import fr.enib.game.editor.graphe.examples.swing.action.SetIDModelObjectAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.HistoryAction;
import fr.enib.game.editor.graphe.model.mxCell;
import fr.enib.game.editor.graphe.swing.util.mxGraphActions;
import fr.enib.game.editor.graphe.util.mxResources;
import fr.enib.game.model.interfaces.IModelObject;

public class EditorPopupMenu extends JPopupMenu
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3132749140550242191L;

	public EditorPopupMenu(BasicGraphEditor editor)
	{
		boolean selected = !editor.getGraphComponent().getGraph()
				.isSelectionEmpty();
		
		Object obj = editor.getGraphComponent().getGraph().getSelectionCell();
		if(obj instanceof mxCell){
			mxCell cell  =(mxCell) obj;
			Object objValue =cell.getValue();
			if(objValue instanceof IModelObject){
				/*
				 * add model object menbu
				 */
				add(editor.bind(mxResources.get("setid"), new SetIDModelObjectAction((IModelObject) objValue,editor.getGraphComponent().getGraph()),
						"/fr/enib/game/editor/graphe/examples/swing/images/font.gif"));
				addSeparator();
			}
		}


		add(editor.bind(mxResources.get("undo"), new HistoryAction(true),
				"/fr/enib/game/editor/graphe/examples/swing/images/undo.gif"));

		addSeparator();

		add(
				editor.bind(mxResources.get("cut"), TransferHandler
						.getCutAction(),
						"/fr/enib/game/editor/graphe/examples/swing/images/cut.gif"))
				.setEnabled(selected);
		add(
				editor.bind(mxResources.get("copy"), TransferHandler
						.getCopyAction(),
						"/fr/enib/game/editor/graphe/examples/swing/images/copy.gif"))
				.setEnabled(selected);
		add(editor.bind(mxResources.get("paste"), TransferHandler
				.getPasteAction(),
				"/fr/enib/game/editor/graphe/examples/swing/images/paste.gif"));

		addSeparator();

		add(
				editor.bind(mxResources.get("delete"), mxGraphActions
						.getDeleteAction(),
						"/fr/enib/game/editor/graphe/examples/swing/images/delete.gif"))
				.setEnabled(selected);

		addSeparator();

		// Creates the format menu
		JMenu menu = (JMenu) add(new JMenu(mxResources.get("format")));

		EditorMenuBar.populateFormatMenu(menu, editor);

		// Creates the shape menu
		menu = (JMenu) add(new JMenu(mxResources.get("shape")));

		EditorMenuBar.populateShapeMenu(menu, editor);

		addSeparator();

		add(
				editor.bind(mxResources.get("edit"), mxGraphActions
						.getEditAction())).setEnabled(selected);

		addSeparator();

		add(editor.bind(mxResources.get("selectVertices"), mxGraphActions
				.getSelectVerticesAction()));
		add(editor.bind(mxResources.get("selectEdges"), mxGraphActions
				.getSelectEdgesAction()));

		addSeparator();

		add(editor.bind(mxResources.get("selectAll"), mxGraphActions
				.getSelectAllAction()));
	}

}
