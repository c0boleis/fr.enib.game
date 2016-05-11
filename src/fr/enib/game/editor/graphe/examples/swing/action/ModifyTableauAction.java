/**
 * 
 */
package fr.enib.game.editor.graphe.examples.swing.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import fr.enib.game.model.Tableau;
import fr.enib.game.model.interfaces.ITableau;

/**
 * @author Corentin Boleis
 *
 */
public class ModifyTableauAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4759409945837834694L;
	
	private ITableau tableau;
	
	public ModifyTableauAction(ITableau tableau) {
		this.tableau = tableau;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		//TODO add filter
		chooser.showOpenDialog(null);
		File file = chooser.getSelectedFile();
		if(file!=null){
			//TODO check with the absolute path
			tableau.setUrlImage(file.getPath());
		}
	}

}
