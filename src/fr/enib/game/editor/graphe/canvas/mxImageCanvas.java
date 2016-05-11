/**
 * Copyright (c) 2007-2010, Gaudenz Alder, David Benson
 */
package fr.enib.game.editor.graphe.canvas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import fr.enib.game.editor.graphe.util.mxUtils;
import fr.enib.game.editor.graphe.view.mxCellState;

/**
 * An implementation of a canvas that uses Graphics2D for painting. To use an
 * image canvas for an existing graphics canvas and create an image the
 * following code is used:
 * 
 * <code>BufferedImage image = mxCellRenderer.createBufferedImage(graph, cells, 1, Color.white, true, null, canvas);</code> 
 */
public class mxImageCanvas implements mxICanvas
{

	/**
	 * 
	 */
	protected mxGraphics2DCanvas canvas;

	/**
	 * 
	 */
	protected Graphics2D previousGraphics;

	/**
	 * 
	 */
	protected BufferedImage image;

	/**
	 * @param canvas 
	 * @param width 
	 * @param height 
	 * @param background 
	 * @param antiAlias 
	 * 
	 */
	public mxImageCanvas(mxGraphics2DCanvas canvas, int width, int height,
			Color background, boolean antiAlias)
	{
		this(canvas, width, height, background, antiAlias, true);
	}
	
	/**
	 * @param canvas 
	 * @param width 
	 * @param height 
	 * @param background 
	 * @param antiAlias 
	 * @param textAntiAlias 
	 * 
	 */
	public mxImageCanvas(mxGraphics2DCanvas canvas, int width, int height,
			Color background, boolean antiAlias, boolean textAntiAlias)
	{
		this.canvas = canvas;
		previousGraphics = canvas.getGraphics();
		image = mxUtils.createBufferedImage(width, height, background);

		if (image != null)
		{
			Graphics2D g = image.createGraphics();
			mxUtils.setAntiAlias(g, antiAlias, textAntiAlias);
			canvas.setGraphics(g);
		}
	}

	/**
	 * @return {@link mxGraphics2DCanvas}
	 * 
	 */
	public mxGraphics2DCanvas getGraphicsCanvas()
	{
		return canvas;
	}

	/**
	 * @return {@link BufferedImage}
	 * 
	 */
	public BufferedImage getImage()
	{
		return image;
	}

	/**
	 * 
	 */
	public Object drawCell(mxCellState state)
	{
		return canvas.drawCell(state);
	}

	/**
	 * 
	 */
	public Object drawLabel(String label, mxCellState state, boolean html)
	{
		return canvas.drawLabel(label, state, html);
	}

	/**
	 * 
	 */
	public double getScale()
	{
		return canvas.getScale();
	}

	/**
	 * 
	 */
	public Point getTranslate()
	{
		return canvas.getTranslate();
	}

	/**
	 * 
	 */
	public void setScale(double scale)
	{
		canvas.setScale(scale);
	}

	/**
	 * 
	 */
	public void setTranslate(int dx, int dy)
	{
		canvas.setTranslate(dx, dy);
	}

	/**
	 * @return {@link BufferedImage}
	 * 
	 */
	public BufferedImage destroy()
	{
		BufferedImage tmp = image;

		if (canvas.getGraphics() != null)
		{
			canvas.getGraphics().dispose();
		}
		
		canvas.setGraphics(previousGraphics);

		previousGraphics = null;
		canvas = null;
		image = null;

		return tmp;
	}

}
