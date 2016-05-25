/**
 * 
 */
package fr.enib.game.editor.graphe.monde;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;

import com.jogamp.opengl.GL2;


/**
 * @author Corentin Boleis
 *
 */
public class Line {

	private int x1 = 0;
	private int y1 = 0;
	private int x2 = 0;
	private int y2 = 0;
	
	private int hauteur = 250;

	private Position position;

	/**
	 * @author Corentin Boleis
	 *
	 */
	public static enum Position{
		haut, 
		bas,
		droite,
		gauche,
	}

	public float getAngle(){
		switch (position) {
		case haut:

			break;
		case bas:

			break;
		case droite:

			break;
		case gauche:

			break;
		default:
			break;
		}
		return 0.0f;
	}

	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public Line(int x1,int y1,int x2,int y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param pos 
	 */
	public Line(int x1,int y1,int x2,int y2,Position pos){
		this(x1,y1,x2,y2);
		this.position = pos;
	}

	/**
	 * 
	 * @param g
	 */
	public void draw(Graphics g){
		g.drawLine(x1, y1, x2, y2);
	}

	/**
	 * 
	 * @param p1
	 * @param p2
	 */
	public Line(Point p1,Point p2){
		this(p1.x,p1.y,p2.x,p2.y);
	}

	/**
	 * 
	 * @param p1
	 * @param p2
	 * @param pos 
	 */
	public Line(Point p1,Point p2,Position pos){
		this(p1.x,p1.y,p2.x,p2.y);
		this.position= pos;
	}

	/**
	 * 
	 * @param xc1
	 * @param yc1
	 * @param xc2
	 * @param yc2
	 * @return
	 */
	public boolean collision(int xc1,int yc1,int xc2,int yc2){

		return segment2segment(new Point2D.Float(x1, y1),
				new Point2D.Float(x2, y2),
				new Point2D.Float(xc1, yc1),
				new Point2D.Float(xc2, yc2));
	}

	private static boolean segment2segment(Point2D.Float A,Point2D.Float B,Point2D.Float C,Point2D.Float D)
	{
		float Ax = A.x;
		float Ay = A.y;
		float Bx = B.x;
		float By = B.y;
		float Cx = C.x;
		float Cy = C.y;
		float Dx = D.x;
		float Dy = D.y;

		double Sx;
		double Sy;

		if(Ax==Bx)
		{
			if(Cx==Dx) return false;
			else
			{
				double pCD = (Cy-Dy)/(Cx-Dx);
				Sx = Ax;
				Sy = pCD*(Ax-Cx)+Cy;
			}
		}
		else
		{
			if(Cx==Dx)
			{
				double pAB = (Ay-By)/(Ax-Bx);
				Sx = Cx;
				Sy = pAB*(Cx-Ax)+Ay;
			}
			else
			{
				double pCD = (Cy-Dy)/(Cx-Dx);
				double pAB = (Ay-By)/(Ax-Bx);
				double oCD = Cy-pCD*Cx;
				double oAB = Ay-pAB*Ax;
				Sx = (oAB-oCD)/(pCD-pAB);
				Sy = pCD*Sx+oCD;
			}
		}
		if((Sx<Ax && Sx<Bx)|(Sx>Ax && Sx>Bx) | (Sx<Cx && Sx<Dx)|(Sx>Cx && Sx>Dx)
				| (Sy<Ay && Sy<By)|(Sy>Ay && Sy>By) | (Sy<Cy && Sy<Dy)|(Sy>Cy && Sy>Dy)) return false;
		return true; //or :     return new Point2D.Float((float)Sx,(float)Sy)
	}

	/**
	 * 
	 * @return {x1,y1,x2,y2}
	 */
	public int[] getCoordonees(){
		return new int[]{x1,y1,x2,y2};
	}

	/**
	 * @param gl
	 */
	public void display(GL2 gl) {
		gl.glPushMatrix() ; 
		gl.glTranslatef(x1, y1, 0) ; 
		gl.glRotatef(getAngle(),0.0f,0.0f,1.0f) ;
		// Si pas de trou dans la cloison, alors on dessine un rectangle (largeur * hauteur)
		dessinerPortionMur((GL2) gl);
		gl.glPopMatrix();
	}

	public void dessinerPortionMur(GL2 gl){
		// 				*-----------* (x1,y1)
		//				|			|
		//				|			|
		//				|			|
		//				|			|
		//(x0,y0)	    *-----------*

		gl.glEnable(GL2.GL_TEXTURE_2D); 
		gl.glTexEnvi( GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);

		gl.glPushMatrix();	
		gl.glBegin(GL2.GL_QUADS); 

		float longeur = getLongeur();
		//Face visible

		// Haut Gauche
		gl.glTexCoord2f(0, 1); gl.glVertex3f(0, 0, hauteur); 

		// Haut Droite
		gl.glTexCoord2f(1, 1); gl.glVertex3f(0, longeur, hauteur); 

		// Bas Droite
		gl.glTexCoord2f(1, 0); gl.glVertex3f(0, longeur, 0); 

		// Bas Gauche
		gl.glTexCoord2f(0, 0); gl.glVertex3f(0, 0, 0); 

		gl.glEnd() ; 
		gl.glPopMatrix() ;
	}
	
	public float getLongeur(){
		double deltaX = x2-x1;
		deltaX = deltaX*deltaX;
		double deltaY = y2-y1;
		deltaY = deltaY*deltaY;
		return (float) Math.sqrt(deltaX*deltaY);
	}

}
