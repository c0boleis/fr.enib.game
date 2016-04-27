package fr.enib.game.editor.graphe.shape;

import java.awt.Rectangle;

import fr.enib.game.editor.graphe.canvas.mxGraphics2DCanvas;
import fr.enib.game.editor.graphe.util.mxConstants;
import fr.enib.game.editor.graphe.util.mxUtils;
import fr.enib.game.editor.graphe.view.mxCellState;

public class mxDoubleEllipseShape extends mxEllipseShape
{

	/**
	 * 
	 */
	public void paintShape(mxGraphics2DCanvas canvas, mxCellState state)
	{
		super.paintShape(canvas, state);

		int inset = (int) Math.round((mxUtils.getFloat(state.getStyle(),
				mxConstants.STYLE_STROKEWIDTH, 1) + 3)
				* canvas.getScale());

		Rectangle rect = state.getRectangle();
		int x = rect.x + inset;
		int y = rect.y + inset;
		int w = rect.width - 2 * inset;
		int h = rect.height - 2 * inset;
		
		canvas.getGraphics().drawOval(x, y, w, h);
	}

}
