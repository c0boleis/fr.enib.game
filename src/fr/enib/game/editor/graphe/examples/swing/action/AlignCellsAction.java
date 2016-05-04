package fr.enib.game.editor.graphe.examples.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import fr.enib.game.editor.graphe.swing.util.mxGraphActions;
import fr.enib.game.editor.graphe.view.mxGraph;

/**
 *
 */
@SuppressWarnings("serial")
public class AlignCellsAction extends AbstractAction
{
	/**
	 * 
	 */
	protected String align;

	/**
	 * 
	 * @param align 
	 */
	public AlignCellsAction(String align)
	{
		this.align = align;
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e)
	{
		mxGraph graph = mxGraphActions.getGraph(e);

		if (graph != null && !graph.isSelectionEmpty())
		{
			graph.alignCells(align);
		}
	}
}