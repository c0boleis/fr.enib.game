package fr.enib.game.editor.graphe.examples.swing.editor;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;

import fr.enib.game.editor.graphe.examples.swing.action.ModifierTableauAction;
import fr.enib.game.editor.graphe.examples.swing.action.SetIDModelObjectAction;
import fr.enib.game.editor.graphe.examples.swing.action.SetInteretAction;
import fr.enib.game.editor.graphe.examples.swing.action.SetPoidsAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.HistoryAction;
import fr.enib.game.editor.graphe.model.mxCell;
import fr.enib.game.editor.graphe.swing.util.mxGraphActions;
import fr.enib.game.editor.graphe.util.mxResources;
import fr.enib.game.model.interfaces.IModelObject;
import fr.enib.game.model.interfaces.IObjectInteret;
import fr.enib.game.model.interfaces.IObjectPondere;
import fr.enib.game.model.interfaces.ITableau;

/**
 * @author Corentin Boleis
 *
 */
public class EditorPopupMenu extends JPopupMenu
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3132749140550242191L;

	/**
	 * @param editor
	 */
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
				SetIDModelObjectAction action = new SetIDModelObjectAction((IModelObject) objValue,editor.getGraphComponent().getGraph());
				add(editor.bind(mxResources.get("setid"), action,
						"/fr/enib/game/editor/graphe/examples/swing/images/font.gif")).setEnabled(action.isEnabled());
				
			}
			if(objValue instanceof ITableau){
				add(editor.bind(mxResources.get("set_tableau"), new ModifierTableauAction((ITableau) objValue,cell,editor.getGraphComponent().getGraph()),
						"/fr/enib/game/editor/graphe/examples/swing/images/image.gif"));
			}
			if(objValue instanceof IObjectPondere){
				add(editor.bind(mxResources.get("set_poids"), new SetPoidsAction((IObjectPondere) objValue,editor.getGraphComponent().getGraph()),
						"/fr/enib/game/editor/graphe/examples/swing/images/image.gif"));
			}
			if(objValue instanceof IObjectInteret){
				add(editor.bind(mxResources.get("set_interet"), new SetInteretAction((IObjectInteret) objValue,editor.getGraphComponent().getGraph()),
						"/fr/enib/game/editor/graphe/examples/swing/images/image.gif"));
			}
			addSeparator();
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
