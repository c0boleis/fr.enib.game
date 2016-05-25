/**
 * 
 */
package fr.enib.game.editor.graphe.monde;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import fr.enib.game.monde.geo.Vec3;

/**
 * @author Corentin Boleis
 *
 */
public class Visiteur {
	
	private static double x = 100;
	
	private static double y = 50;
	
	private static GLU regard;
	
	/**
	 * angle en radian
	 */
	private static double angle = 0;
	
	private static double taille = 0.075;
	private static double tailleVision = 0.1;
	
	private static final Visiteur INSTANCE = new Visiteur();
	
	private static CarerMusee carerActuelle = null;
	
	private Visiteur(){
		x = Math.ceil(((double)MuseeTest.NOMBRE_COLLONE)/2.0);
		y = Math.ceil(((double)MuseeTest.NOMBRE_LIGNE)/2.0);
		regard = new GLU();
	}
	
	/**
	 * 
	 * @return {@link Visiteur}
	 */
	public static Visiteur get(){
		return INSTANCE;
	}
	
	/**
	 * @param dx
	 * @param dy
	 */
	public static void deplacer(double dx,double dy){
		Point pOld = MuseeTest.getPoint(x, y);
		Point pNew= MuseeTest.getPoint(x+dx, y+dy);
		Point pOut = candeplacer(pOld,pNew);
		if(pOut.x!=pOld.x){
			x+= dx;
		}
		if(pOut.y!=pOld.y){
			y+= dy;
		}

	}
	
	/**
	 * @param pOld
	 * @param pNew
	 * @return
	 */
	private static Point candeplacer(Point pOld, Point pNew) {
		if(carerActuelle==null){
			return pNew;
		}
		return carerActuelle.visiteurCanMove(pOld, pNew);
	}

	/**
	 * @param da
	 */
	public static void avancer(double da){
		double dx = da*Math.cos(angle)*MuseeTest.INVERSION_X;
		double dy = da*Math.sin(angle)*MuseeTest.INVERSION_Y;
		
		Point pOld = MuseeTest.getPoint(x, y);
		Point pNew= MuseeTest.getPoint(x+dx, y+dy);
		Point pOut = candeplacer(pOld,pNew);
		if(pOut.x!=pOld.x){
			x+= dx;
		}
		if(pOut.y!=pOld.y){
			y+= dy;
		}
	}
	
	/**
	 * @param da
	 */
	public static void tourner(double da){
		angle+= da;
	}
	
	/**
	 * @param g
	 */
	public static void draw(Graphics g){
		g.setColor(Color.BLUE);
		Point p = MuseeTest.getPoint(x, y);
		int diametre  = (int)(taille*MuseeTest.scale);
		int rayon = (int)(tailleVision*MuseeTest.scale);
		g.drawArc(p.x, p.y, diametre, diametre, 0, 360);
		g.setColor(Color.BLACK);
		int x1 = p.x+diametre/2;
		int y1 = p.y+diametre/2;
		int x2 =(int) (rayon*Math.cos(angle)+x1);
		int y2 =(int) (rayon*Math.sin(angle)+y1);
		g.drawLine(x1, y1, x2, y2);
	}

	/**
	 * @param pMax
	 * @param pMin
	 * @return 
	 */
	@SuppressWarnings("unused")
	public static boolean estPresent(Point pMax, Point pMin) {
		Point p = MuseeTest.getPoint(x, y);
		//cas X
		//sans inversion
		if(MuseeTest.INVERSION_X>0){
			if(p.x>pMax.x)return false;
			if(p.x<=pMin.x)return false;
		}
		else{
			if(p.x<=pMax.x)return false;
			if(p.x>pMin.x)return false;
		}
		if(MuseeTest.INVERSION_Y>0){
			if(p.y>pMax.y)return false;
			if(p.y<=pMin.y)return false;
		}
		else{
			if(p.y<=pMax.y)return false;
			if(p.y>pMin.y)return false;
		}
		return true;
	}
	
	public String toString(){
		return "Visiteur ("+x+","+y+")";
	}

	/**
	 * @return the carerActuelle
	 */
	public static CarerMusee getCarerActuelle() {
		return carerActuelle;
	}

	/**
	 * @param carerActuelle the carerActuelle to set
	 */
	public static void setCarerActuelle(CarerMusee carerActuelle) {
		Visiteur.carerActuelle = carerActuelle;
	}
	
	/**
	 * 
	 * @param line
	 * @return true si le visiteure regarde la line
	 */
	public static boolean isWatch(Line line){
		double da = 0.3;
		double dx = da*Math.cos(angle)*MuseeTest.INVERSION_X;
		double dy = da*Math.sin(angle)*MuseeTest.INVERSION_Y;
		
		Point pOld = MuseeTest.getPoint(x, y);
		Point pNew= MuseeTest.getPoint(x+dx, y+dy);
		
		return line.collision(pOld.x, pOld.y, pNew.x, pNew.y);
	}

	public static void placer(GL2 gl){
		gl.glLoadIdentity() ;
		Vec3 o = getPostiton() ;
		//System.out.println(o);
		Vec3 u = getDirection() ; 
		regard.gluLookAt(o.x,o.y,o.z,o.x+u.x,o.y+u.y,o.z+u.z,0.0f,0.0f,1.0f) ; 
	}

	/**
	 * @return
	 */
	private static Vec3 getDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	private static Vec3 getPostiton() {
		// TODO Auto-generated method stub
		return null;
	}
}
