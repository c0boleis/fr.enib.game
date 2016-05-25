/**
 * Copyright (c) 2007-2012, JGraph Ltd
 */
package fr.enib.game.editor.graphe.util;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import fr.enib.game.editor.graphe.canvas.mxGraphics2DCanvas;
import fr.enib.game.editor.graphe.canvas.mxICanvas;
import fr.enib.game.editor.graphe.canvas.mxImageCanvas;
import fr.enib.game.editor.graphe.view.mxGraph;
import fr.enib.game.editor.graphe.view.mxGraphView;
import fr.enib.game.editor.graphe.view.mxTemporaryCellStates;

public class mxCellRenderer
{
	/**
	 * 
	 */
	private mxCellRenderer()
	{
		// static class
	}

	/**
	 * Draws the given cells using a Graphics2D canvas and returns the buffered image
	 * that represents the cells.
	 * 
	 * @param graph Graph to be painted onto the canvas.
	 * @return Returns the image that represents the canvas.
	 */
	public static mxICanvas drawCells(mxGraph graph, Object[] cells,
			double scale, mxRectangle clip, CanvasFactory factory)
	{
		mxICanvas canvas = null;

		if (cells == null)
		{
			cells = new Object[] { graph.getModel().getRoot() };
		}

		// Gets the current state of the view
		mxGraphView view = graph.getView();

		// Keeps the existing translation as the cells might
		// be aligned to the grid in a different way in a graph
		// that has a translation other than zero
		boolean eventsEnabled = view.isEventsEnabled();

		// Disables firing of scale events so that there is no
		// repaint or update of the original graph
		view.setEventsEnabled(false);

		// Uses the view to create temporary cell states for each cell
		mxTemporaryCellStates temp = new mxTemporaryCellStates(view, scale,
				cells);

		try
		{
			if (clip == null)
			{
				clip = graph.getPaintBounds(cells);
			}

			if (clip != null && clip.getWidth() > 0 && clip.getHeight() > 0)
			{
				Rectangle rect = clip.getRectangle();
				canvas = factory.createCanvas(rect.width + 1, rect.height + 1);

				if (canvas != null)
				{
					double previousScale = canvas.getScale();
					Point previousTranslate = canvas.getTranslate();

					try
					{
						canvas.setTranslate(-rect.x, -rect.y);
						canvas.setScale(view.getScale());

						for (int i = 0; i < cells.length; i++)
						{
							graph.drawCell(canvas, cells[i]);
						}
					}
					finally
					{
						canvas.setScale(previousScale);
						canvas.setTranslate(previousTranslate.x,
								previousTranslate.y);
					}
				}
			}
		}
		finally
		{
			temp.destroy();
			view.setEventsEnabled(eventsEnabled);
		}

		return canvas;
	}

	/**
	 * 
	 */
	public static BufferedImage createBufferedImage(mxGraph graph,
			Object[] cells, double scale, Color background, boolean antiAlias,
			mxRectangle clip)
	{
		return createBufferedImage(graph, cells, scale, background, antiAlias,
				clip, new mxGraphics2DCanvas());
	}

	/**
	 * 
	 */
	public static BufferedImage createBufferedImage(mxGraph graph,
			Object[] cells, double scale, final Color background,
			final boolean antiAlias, mxRectangle clip,
			final mxGraphics2DCanvas graphicsCanvas)
	{
		mxImageCanvas canvas = (mxImageCanvas) drawCells(graph, cells, scale,
				clip, new CanvasFactory()
				{
					public mxICanvas createCanvas(int width, int height)
					{
						return new mxImageCanvas(graphicsCanvas, width, height,
								background, antiAlias);
					}

				});

		return (canvas != null) ? canvas.destroy() : null;
	}

	/**
	 * 
	 */
	public static abstract class CanvasFactory
	{

		/**
		 * Separates the creation of the canvas from its initialization, when the
		 * size of the required graphics buffer / document / container is known.
		 */
		public abstract mxICanvas createCanvas(int width, int height);

	}

}
