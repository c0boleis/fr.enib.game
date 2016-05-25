/**
 * 
 */
package fr.enib.game.editor.graphe.monde;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Corentin Boleis
 *
 */
public class CollisionTest extends Graphics {
	
	private List<Line> lines = new ArrayList<Line>();
	
	//private static final int MUR = 0;
	
	//private static final int TABLEAU = 1;

	/* (non-Javadoc)
	 * @see java.awt.Graphics#create()
	 */
	@Override
	public Graphics create() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#translate(int, int)
	 */
	@Override
	public void translate(int x, int y) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#getColor()
	 */
	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#setColor(java.awt.Color)
	 */
	@Override
	public void setColor(Color c) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#setPaintMode()
	 */
	@Override
	public void setPaintMode() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#setXORMode(java.awt.Color)
	 */
	@Override
	public void setXORMode(Color c1) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#getFont()
	 */
	@Override
	public Font getFont() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#setFont(java.awt.Font)
	 */
	@Override
	public void setFont(Font font) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#getFontMetrics(java.awt.Font)
	 */
	@Override
	public FontMetrics getFontMetrics(Font f) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#getClipBounds()
	 */
	@Override
	public Rectangle getClipBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#clipRect(int, int, int, int)
	 */
	@Override
	public void clipRect(int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#setClip(int, int, int, int)
	 */
	@Override
	public void setClip(int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#getClip()
	 */
	@Override
	public Shape getClip() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#setClip(java.awt.Shape)
	 */
	@Override
	public void setClip(Shape clip) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#copyArea(int, int, int, int, int, int)
	 */
	@Override
	public void copyArea(int x, int y, int width, int height, int dx, int dy) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#drawLine(int, int, int, int)
	 */
	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		lines.add(new Line(x1, y1, x2, y2));
	}
	
	public void drawLine(int x1, int y1, int x2, int y2,int type) {
		lines.add(new Line(x1, y1, x2, y2));
	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#fillRect(int, int, int, int)
	 */
	@Override
	public void fillRect(int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#clearRect(int, int, int, int)
	 */
	@Override
	public void clearRect(int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#drawRoundRect(int, int, int, int, int, int)
	 */
	@Override
	public void drawRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#fillRoundRect(int, int, int, int, int, int)
	 */
	@Override
	public void fillRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#drawOval(int, int, int, int)
	 */
	@Override
	public void drawOval(int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#fillOval(int, int, int, int)
	 */
	@Override
	public void fillOval(int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#drawArc(int, int, int, int, int, int)
	 */
	@Override
	public void drawArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#fillArc(int, int, int, int, int, int)
	 */
	@Override
	public void fillArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#drawPolyline(int[], int[], int)
	 */
	@Override
	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#drawPolygon(int[], int[], int)
	 */
	@Override
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#fillPolygon(int[], int[], int)
	 */
	@Override
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#drawString(java.lang.String, int, int)
	 */
	@Override
	public void drawString(String str, int x, int y) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#drawString(java.text.AttributedCharacterIterator, int, int)
	 */
	@Override
	public void drawString(AttributedCharacterIterator iterator, int x, int y) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, java.awt.image.ImageObserver)
	 */
	@Override
	public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, java.awt.image.ImageObserver)
	 */
	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height,
			ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, java.awt.Color, java.awt.image.ImageObserver)
	 */
	@Override
	public boolean drawImage(Image img, int x, int y, Color bgcolor,
			ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, java.awt.Color, java.awt.image.ImageObserver)
	 */
	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height,
			Color bgcolor, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, int, int, int, int, java.awt.image.ImageObserver)
	 */
	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, int, int, int, int, java.awt.Color, java.awt.image.ImageObserver)
	 */
	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2, Color bgcolor,
			ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.awt.Graphics#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * @param pOld
	 * @param pNew
	 * @return
	 */
	public boolean haveCollision(Point pOld,Point pNew){
		for(Line line : lines){
			if(line.collision(pOld.x, pOld.y, pNew.x, pOld.y)){
				return true;
			}
		}
		return false;
	}

}
