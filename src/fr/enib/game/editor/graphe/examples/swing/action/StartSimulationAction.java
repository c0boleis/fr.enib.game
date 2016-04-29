/**
 * 
 */
package fr.enib.game.editor.graphe.examples.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import fr.enib.game.app.Viewer;
import fr.enib.game.editor.graphe.view.mxGraph;
import fr.enib.game.model.interfaces.IModelObject;

/**
 * @author Corentin Boleis
 *
 */
public class StartSimulationAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6890145175913109507L;
	
	/**
	 * 
	 */
	public StartSimulationAction() {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Viewer.main(new String[]{"false"});
	}

}
