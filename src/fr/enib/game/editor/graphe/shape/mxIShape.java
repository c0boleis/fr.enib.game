package fr.enib.game.editor.graphe.shape;

import fr.enib.game.editor.graphe.canvas.mxGraphics2DCanvas;
import fr.enib.game.editor.graphe.view.mxCellState;

public interface mxIShape
{
	/**
	 * 
	 */
	void paintShape(mxGraphics2DCanvas canvas, mxCellState state);

}
