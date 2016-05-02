package fr.enib.game.monde.objet;

import com.jogamp.opengl.GL2;

import fr.enib.game.monde.core.Situable;
import fr.enib.game.monde.graphic_core.Materiau;
import fr.enib.game.monde.graphic_core.Noeud;

public class Objet extends Situable {
	 
	public Noeud  forme  = null ;

	protected Materiau mat;
	
	public Objet(String id){
		super(id) ;
	}
	
	public Objet(String id, String pathTexture){
		super(id) ;
		this.mat = new Materiau(pathTexture);
	}

	public Objet(String id, Noeud noeud){
		super(id) ;
		forme = noeud ; 
	}

	public void fixerForme(Noeud noeud){
		forme = noeud ;
	}

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
