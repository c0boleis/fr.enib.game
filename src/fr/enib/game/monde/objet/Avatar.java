package fr.enib.game.monde.objet;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import fr.enib.game.monde.geo.Vec3;

/**
 * 
 * @author Ronan MOREL
 *
 */
public class Avatar extends Objet {
	
	private static Avatar INSTANCE = null;

	public static final float HAUTEUR = 1.6f;

	private GLU regard;
	
	/**
	 * Constructeur prive pour applique le singleton
	 * @param id l'identifiant de l'avatar
	 */
	private Avatar(String id){
		super(id) ; 
		repere.getPostiton().fixer(0.0f,0.0f,HAUTEUR) ; 
		regard = new GLU();
	}
	
	/**
	 * Renvoie l'instance de l'avatar 
	 * @return l'instance de l'avatar
	 */
	public static Avatar get(){
		if(INSTANCE == null){
			INSTANCE = new Avatar("avatar");
		}
		return INSTANCE;
	}

	/**
	 * Dessiner l'avatar dans l'environnement 3D (Pas implemente)
	 */
	public void dessiner(GL2 gl){
	}
	
	/**
	 * Place l'avatar dans l'espace 3D
	 * @param gl l'objet permettant de placer un 
	 */
	public void placer(GL2 gl){
		gl.glLoadIdentity() ;
		Vec3 o = repere.getPostiton() ;
		Vec3 u = repere.getDirection() ; 
		regard.gluLookAt(o.x,o.y,o.z,o.x+u.x,o.y+u.y,o.z+u.z,0.0f,0.0f,1.0f) ; 
	}
	
	public Vec3 getDirectionRegard(){
		return repere.getDirection();
	}
	
	public void resetPosition(){
		//TODO probleme de position
		getPositionRepere().fixer(0.0f, 0.0f, HAUTEUR);
	}
}
