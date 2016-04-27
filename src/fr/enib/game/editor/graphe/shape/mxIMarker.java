package fr.enib.game.editor.graphe.shape;

import fr.enib.game.editor.graphe.canvas.mxGraphics2DCanvas;
import fr.enib.game.editor.graphe.util.mxPoint;
import fr.enib.game.editor.graphe.view.mxCellState;

public interface mxIMarker
{
	/**
	 * 
	 */
	mxPoint paintMarker(mxGraphics2DCanvas canvas, mxCellState state, String type,
			mxPoint pe, double nx, double ny, double size, boolean source);

}
