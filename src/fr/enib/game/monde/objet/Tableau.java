package fr.enib.game.monde.objet;

import fr.enib.game.monde.graphic_core.Forme;
import fr.enib.game.monde.graphic_core.GTableau;
import fr.enib.game.monde.graphic_core.Shape;

public class Tableau extends Objet {
	
	private float position;
	
	public Tableau(String id, String img) {
		this(id, img, 1.5f, 1.0f);
	}
	
	public Tableau(String id, String img, float largeur, float hauteur) {
		super(id, img);

		Forme geo = new GTableau(largeur, hauteur); 
		this.forme = new Shape(geo, mat); 
	}
	
	/**
	 * Renvoie la objet Cloison sur package visu.cloison permettant de dessiner une cloison
	 * @return la cloison dessiner une cloison
	 */
	public GTableau getTableau(){
		return (GTableau) ((Shape) forme).getForme();
	}

	public float getPosition() {
		return position;
	}

	public void setPosition(float position) {
		this.position = position;
	}
	
}
