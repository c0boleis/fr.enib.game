package fr.enib.game.monde.objet;

import com.jogamp.opengl.GL2;

import fr.enib.game.monde.core.Situable;
import fr.enib.game.monde.graphic_core.Materiau;
import fr.enib.game.monde.graphic_core.IShape;

/**
 * Réprente un objet dans un environnement 3D (qui a un matériau, une position et un forme)
 * @author Ronan MOREL
 *
 */
public class Objet extends Situable {
	 
	public IShape  forme  = null ;

	protected Materiau mat;
	
	/**
	 * Constructeur
	 * @param id l'id de l'objet
	 */
	public Objet(String id){
		super(id) ;
	}
	
	/**
	 * Constructeur
	 * @param id l'id de l'objet
	 * @param pathTexture le chemin où charger la texture de l'objet
	 */
	public Objet(String id, String pathTexture){
		super(id) ;
		this.mat = new Materiau(pathTexture);
	}

	/**
	 * Constructeur
	 * @param id l'id de l'objet
	 * @param noeud la forme associée à l'objet
	 */
	public Objet(String id, IShape noeud){
		super(id) ;
		forme = noeud ; 
	}

	/**
	 * Modifie la forme associée à l'objet
	 * @param noeud la futur forme de l'objet
	 */
	public void setForme(IShape noeud){
		forme = noeud ;
	}

	/**
	 * Dessine une forme
	 * @param gl l'objet permettant de dessiner dans l'environnement 3D
	 */
	public void dessiner(GL2 gl){
		if(forme !=null){
			if(getMaitre()!=null){
				repere.push(gl,getMaitre().getRepere());
			}
			else{
				repere.push(gl);
			}
			forme.dessiner(gl) ; 
			repere.pop(gl) ; 
		} 
		
	}
	
	/**
	 * Applique une texture a la cloison
	 * @param pathTexture le chemin de la texture a appliquer
	 */
	public void setTexture(String pathTexture){
		mat.setTexture(pathTexture);
	}
}
