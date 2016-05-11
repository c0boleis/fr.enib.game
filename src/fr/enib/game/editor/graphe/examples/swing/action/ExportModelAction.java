/**
 * 
 */
package fr.enib.game.editor.graphe.examples.swing.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import fr.enib.game.model.Model;

/**
 * @author Corentin Boleis
 *
 */
public class ExportModelAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6549356078950273085L;

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File file = chooser.getSelectedFile();
		if(file!=null){
			Model.get().sauvegarderModel(file);
		}
	}

}
