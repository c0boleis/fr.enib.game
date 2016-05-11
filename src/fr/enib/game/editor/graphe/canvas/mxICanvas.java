/**
 * Copyright (c) 2007-2010, Gaudenz Alder, David Benson
 */
package fr.enib.game.editor.graphe.canvas;

import java.awt.Point;

import fr.enib.game.editor.graphe.view.mxCellState;

/**
 * Defines the requirements for a canvas that paints the vertices and edges of
 * a graph.
 */
public interface mxICanvas
{
	/**
	 * Sets the translation for the following drawing requests.
	 * @param x 
	 * @param y 
	 */
	void setTranslate(int x, int y);

	/**
	 * Returns the current translation.
	 * 
	 * @return Returns the current translation.
	 */
	Point getTranslate();

	/**
	 * Sets the scale for the following drawing requests.
	 * @param scale 
	 */
	void setScale(double scale);

	/**
	 * @return the scale.
	 */
	double getScale();

	/**
	 * Draws the given cell.
	 * 
	 * @param state State of the cell to be painted.
	 * @return Object that represents the cell.
	 */
	Object drawCell(mxCellState state);

	/**
	 * Draws the given label.
	 * 
	 * @param text String that represents the label.
	 * @param state State of the cell whose label is to be painted.
	 * @param html Specifies if the label contains HTML markup.
	 * @return Object that represents the label.
	 */
	Object drawLabel(String text, mxCellState state, boolean html);

}
