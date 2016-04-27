package fr.enib.game.editor.graphe.examples.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import fr.enib.game.editor.graphe.examples.swing.editor.BasicGraphEditor;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions;

/**
 *
 */
@SuppressWarnings("serial")
public class ExitAction extends AbstractAction
{
	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e)
	{
		BasicGraphEditor editor = EditorActions.getEditor(e);

		if (editor != null)
		{
			editor.exit();
		}
	}
}