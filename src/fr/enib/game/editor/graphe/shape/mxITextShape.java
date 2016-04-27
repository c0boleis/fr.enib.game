/**
 * Copyright (c) 2010, Gaudenz Alder, David Benson
 */
package fr.enib.game.editor.graphe.shape;

import java.util.Map;

import fr.enib.game.editor.graphe.canvas.mxGraphics2DCanvas;
import fr.enib.game.editor.graphe.view.mxCellState;

public interface mxITextShape
{
	/**
	 * 
	 */
	void paintShape(mxGraphics2DCanvas canvas, String text, mxCellState state,
			Map<String, Object> style);

}
