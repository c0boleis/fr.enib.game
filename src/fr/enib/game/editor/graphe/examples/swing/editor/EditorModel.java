/**
 * 
 */
package fr.enib.game.editor.graphe.examples.swing.editor;

import javax.swing.JScrollPane;
import javax.swing.JTree;

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
		}
		return tree;
	}

}
