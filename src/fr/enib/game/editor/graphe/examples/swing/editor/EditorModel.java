/**
 * 
 */
package fr.enib.game.editor.graphe.examples.swing.editor;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import fr.enib.game.model.Model;
import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.IModelObject;
import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.model.interfaces.ITableau;
import fr.enib.game.model.listeners.IModelListener;

/**
 * @author Corentin Boleis
 *
 */
public class EditorModel extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6402658670828973037L;
	
	private JTree tree;
	
	private IModelListener modelListener;

	/**
	 * 
	 */
	public EditorModel() {
		this.setViewportView(getTree());
		Model.get().addListener(getModelListener());
	}

	/**
	 * @return the tree
	 */
	private JTree getTree() {
		if(tree == null){
			tree = new JTree();
			refresh();
		}
		return tree;
	}
	
	private DefaultMutableTreeNode getRoot(){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Model");
		root.add(getNodeNoeuds());
		root.add(getNodeLiens());
		root.add(getNodeTableaux());
		return root;
	}
	
	private void refresh(){
		getTree().setModel(new DefaultTreeModel(getRoot()));
	}
	
	private DefaultMutableTreeNode getNodeNoeuds(){
		DefaultMutableTreeNode noeudNoeuds = new DefaultMutableTreeNode("Noeuds");
		IModelObject[] tmp = Model.get().getModelObjects();
		DefaultMutableTreeNode node =null;
		for(IModelObject obj : tmp){
			if(!(obj instanceof INoeud))continue;
			if(obj instanceof ITableau)continue;
			node = new DefaultMutableTreeNode(obj.getId());
			noeudNoeuds.add(node);
		}
		return noeudNoeuds;
	}
	
	private DefaultMutableTreeNode getNodeLiens(){
		DefaultMutableTreeNode noeudLiens = new DefaultMutableTreeNode("Liens");
		IModelObject[] tmp = Model.get().getModelObjects();
		DefaultMutableTreeNode node =null;
		for(IModelObject obj : tmp){
			if(!(obj instanceof ILien))continue;
			node = new DefaultMutableTreeNode(obj.getId());
			noeudLiens.add(node);
		}
		return noeudLiens;
	}
	
	private DefaultMutableTreeNode getNodeTableaux(){
		DefaultMutableTreeNode noeudTableau = new DefaultMutableTreeNode("Tableaux");
		IModelObject[] tmp = Model.get().getModelObjects();
		DefaultMutableTreeNode node =null;
		for(IModelObject obj : tmp){
			if(!(obj instanceof ITableau))continue;
			node = new DefaultMutableTreeNode(obj.getId());
			noeudTableau.add(node);
		}
		return noeudTableau;
	}

	/**
	 * @return the modelListener
	 */
	public IModelListener getModelListener() {
		if(modelListener == null){
			modelListener = new IModelListener() {
				
				/*
				 * (non-Javadoc)
				 * @see fr.enib.game.model.listeners.IModelListener#iModelObjectAdded(fr.enib.game.model.interfaces.IModelObject)
				 */
				@Override
				public void iModelObjectAdded(IModelObject object) {
					refresh();
				}

				@Override
				public void iModelObjectRemoved(IModelObject object) {
					refresh();
				}
			};
		}
		return modelListener;
	}

}
