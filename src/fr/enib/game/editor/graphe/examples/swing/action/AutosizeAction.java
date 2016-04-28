package fr.enib.game.editor.graphe.examples.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import fr.enib.game.editor.graphe.model.mxIGraphModel;
import fr.enib.game.editor.graphe.swing.util.mxGraphActions;
import fr.enib.game.editor.graphe.view.mxGraph;

/**
 *
 */
@SuppressWarnings("serial")
public class AutosizeAction extends AbstractAction
{
	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e)
	{
		mxGraph graph = mxGraphActions.getGraph(e);

		if (graph != null && !graph.isSelectionEmpty())
		{
			Object[] cells = graph.getSelectionCells();
			mxIGraphModel model = graph.getModel();

			model.beginUpdate();
			try
			{
				for (int i = 0; i < cells.length; i++)
				{
					graph.updateCellSize(cells[i]);
				}
			}
			finally
			{
				model.endUpdate();
			}
		}
	}
}
