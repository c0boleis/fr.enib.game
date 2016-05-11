/**
 * 
 */
package fr.enib.game.editor.graphe.examples.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import fr.enib.game.editor.graphe.view.mxGraph;
import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.IModelObject;

/**
 * @author Corentin Boleis
 *
 */
public class SetIDModelObjectAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6890145175913109507L;
	
	private IModelObject modelObject =null;
	
	private mxGraph graph;

	/**
	 * @param modelObject 
	 * @param mxGraph 
	 * 
	 */
	public SetIDModelObjectAction(IModelObject modelObject, mxGraph mxGraph) {
		this.modelObject = modelObject;
		this.graph = mxGraph;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String newId = JOptionPane.showInputDialog(null, "Entrez l'id", "id");
		if(newId==null)return;
		this.modelObject.setId(newId);
		this.graph.refresh();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.AbstractAction#isEnabled()
	 */
	@Override
	public boolean isEnabled(){
		return !(modelObject instanceof ILien);
	}

}
