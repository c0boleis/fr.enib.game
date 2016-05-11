/**
 * 
 */
package fr.enib.game.editor.graphe.examples.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import fr.enib.game.editor.graphe.view.mxGraph;
import fr.enib.game.model.interfaces.IObjectInteret;

/**
 * @author Corentin Boleis
 *
 */
public class SetInteretAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7613556110216900376L;
	
	private IObjectInteret objectPondere;
	
	private mxGraph graph;
	
	/**
	 * @param object
	 * @param graph
	 */
	public SetInteretAction(IObjectInteret object, mxGraph graph) {
		this.objectPondere = object;
		this.graph = graph;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String newPoids = JOptionPane.showInputDialog(null, "Entrez le degré d'interet", this.objectPondere.getDegreInteret());
		if(newPoids==null)return;
		newPoids = newPoids.replace(",", ".");
		Double value = Double.parseDouble(newPoids);
		this.objectPondere.setDegreInteret(value.floatValue());
		this.graph.refresh();
	}

}
