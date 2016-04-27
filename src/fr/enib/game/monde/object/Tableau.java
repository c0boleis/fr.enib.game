package fr.enib.game.monde.object;

import fr.enib.game.monde.graphe_core.Materiau;
import fr.enib.game.monde.graphe_core.PrimGeo;
import fr.enib.game.monde.graphe_core.Shape;

public class Tableau extends Objet {
	private float largeur;
	private float hauteur;
	private float position;
	
	public Tableau(String id, String nomTexture){
		super(id) ; 
		this.largeur = 3.0f;
		this.hauteur = 2.0f;
		this.position = 0.0f;
		PrimGeo geo = new fr.enib.game.monde.graphe_core.Tableau(hauteur, largeur) ; 
		Materiau mat = new Materiau(nomTexture) ;  
		forme = new Shape(geo,mat) ;
	}

	public float getLargeur() {
		return largeur;
	}

	public float getHauteur() {
		return hauteur;
	}

	public float getPosition() {
		return position;
	}

	public void setPosition(float position) {
		this.position = position;
	}

	
}
