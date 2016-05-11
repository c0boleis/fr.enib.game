/**
 * 
 */
package fr.enib.game.editor.graphe.examples.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import fr.enib.game.editor.graphe.view.mxGraph;
import fr.enib.game.model.interfaces.IObjectPondere;

/**
 * @author Corentin Boleis
 *
 */
public class SetPoidsAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7613556110216900376L;
	
	private IObjectPondere objectPondere;
	
	private mxGraph graph;
	
	/**
	 * @param object
	 * @param graph
	 */
	public SetPoidsAction(IObjectPondere object, mxGraph graph) {
		this.objectPondere = object;
		this.graph = graph;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String newPoids = JOptionPane.showInputDialog(null, "Entrez le poids", this.objectPondere.getPoids());
		if(newPoids==null)return;
		newPoids = newPoids.replace(",", ".");
		Double value = Double.parseDouble(newPoids);
		this.objectPondere.setPoids(value.floatValue());
		this.graph.refresh();
	}

}
