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

	/**
	 * 
	 */
	public EditorModel() {
		this.setViewportView(getTree());
	}

	/**
	 * @return the tree
	 */
	private JTree getTree() {
		if(tree == null){
			tree = new JTree();
			tree.setModel(new DefaultTreeModel(getRoot()));
		}
		return tree;
	}
	
	private DefaultMutableTreeNode getRoot(){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Model");
		root.add(getNodeNoeuds());
		root.add(getNodeLiens());
		return root;
	}
	
	private DefaultMutableTreeNode getNodeNoeuds(){
		DefaultMutableTreeNode noeudNoeuds = new DefaultMutableTreeNode("Noeuds");
		IModelObject[] tmp = Model.get().getModelObjects();
		DefaultMutableTreeNode node =null;
		for(IModelObject obj : tmp){
			if(!(obj instanceof INoeud))continue;
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
	
	private DefaultMutableTreeNode getNodeTableau(){
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

}
