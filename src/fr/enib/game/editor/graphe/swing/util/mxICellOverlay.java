package fr.enib.game.editor.graphe.swing.util;

import fr.enib.game.editor.graphe.util.mxRectangle;
import fr.enib.game.editor.graphe.view.mxCellState;

public interface mxICellOverlay
{

	/**
	 * 
	 */
	mxRectangle getBounds(mxCellState state);

}
