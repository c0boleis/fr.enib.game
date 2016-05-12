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
		root.add(getNodeTreeModel());
		return root;
	}
	
	private void refresh(){
		getTree().setModel(new DefaultTreeModel(getRoot()));
	}
	
	private DefaultMutableTreeNode getNodeNoeuds(){
		int count = getNumberOfUnlinkNoeud();
		String stCount = count<=0?"":"("+String.valueOf(count)+")";
		DefaultMutableTreeNode noeudNoeuds = new DefaultMutableTreeNode("Noeuds"+stCount);
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
			ILien lien = (ILien)obj;
			node = new DefaultMutableTreeNode(lien.getId());
			INoeud noeudArrivee = lien.getNoeudArrivee();
			String arv = "Arrivée : "+noeudArrivee==null?"null":noeudArrivee.getId();
			INoeud noeudDepart = lien.getNoeudDepart();
			String dep = "Départ : "+noeudDepart==null?"null":noeudDepart.getId();
			node.add(new DefaultMutableTreeNode(dep));
			node.add(new DefaultMutableTreeNode(arv));
			noeudLiens.add(node);
		}
		return noeudLiens;
	}
	
	private DefaultMutableTreeNode getNodeTreeModel(){
		int count = getNumberOfUnlinkNode();
		String stCount = count<=1?"":"("+String.valueOf(count)+")";
		DefaultMutableTreeNode nodeTreeModel = new DefaultMutableTreeNode("Tree"+stCount);
		INoeud noeudRoot = getModelRoot();
		if(noeudRoot==null){
			return nodeTreeModel;
		}
		DefaultMutableTreeNode nodeRoot = fillNodeModel(noeudRoot);
		nodeTreeModel.add(nodeRoot);
		return nodeTreeModel;
	}
	
	private static DefaultMutableTreeNode fillNodeModel(INoeud noeudModel){
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(noeudModel.getId());
		ILien[] liens = noeudModel.getLiensSortant();
		for(ILien lien : liens){
			node.add(fillNodeModel(lien.getNoeudArrivee()));
		}
		return node;
	}
	
	private DefaultMutableTreeNode getNodeTableaux(){
		int count = getNumberOfUnlinkTableau();
		String stCount = count<=0?"":"("+String.valueOf(count)+")";
		DefaultMutableTreeNode noeudTableau = new DefaultMutableTreeNode("Tableaux"+stCount);
		IModelObject[] tmp = Model.get().getModelObjects();
		DefaultMutableTreeNode node =null;
		for(IModelObject obj : tmp){
			if(!(obj instanceof ITableau))continue;
			String inf = "";
			if(((ITableau)obj).getLiensEntrant().length==0){
				inf="(**)";
			}
			node = new DefaultMutableTreeNode(obj.getId()+inf);
			noeudTableau.add(node);
		}
		return noeudTableau;
	}
	
	private INoeud getModelRoot(){
		IModelObject[] tmp = Model.get().getModelObjects();
		INoeud noeudOut = null;
		for(IModelObject obj : tmp){
			if(!(obj instanceof INoeud))continue;
			INoeud noeud = (INoeud)obj;
			if(noeud.getLiensEntrant().length==0){
				if(noeudOut!=null){
					return null;
				}
				noeudOut = noeud;
			}
		}
		return noeudOut;
	}
	
	private int getNumberOfUnlinkNode(){
		IModelObject[] tmp = Model.get().getModelObjects();
		int count = 0;
		for(IModelObject obj : tmp){
			if(!(obj instanceof INoeud))continue;
			INoeud noeud = (INoeud)obj;
			if(noeud.getLiensEntrant().length==0){
				count++;
			}
		}
		return count;
	}
	
	private int getNumberOfUnlinkNoeud(){
		IModelObject[] tmp = Model.get().getModelObjects();
		int count = 0;
		for(IModelObject obj : tmp){
			if(!(obj instanceof INoeud))continue;
			if(obj instanceof ITableau)continue;
			INoeud noeud = (INoeud)obj;
			if(noeud.getLiensEntrant().length==0){
				count++;
			}
		}
		return count;
	}
	
	private int getNumberOfUnlinkTableau(){
		IModelObject[] tmp = Model.get().getModelObjects();
		int count = 0;
		for(IModelObject obj : tmp){
			if(!(obj instanceof ITableau))continue;
			INoeud noeud = (INoeud)obj;
			if(noeud.getLiensEntrant().length==0){
				count++;
			}
		}
		return count;
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
