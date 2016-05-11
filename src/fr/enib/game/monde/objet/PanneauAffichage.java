package fr.enib.game.monde.objet;

import fr.enib.game.monde.graphic_core.Forme;
import fr.enib.game.monde.graphic_core.GPanneauAffichage;
import fr.enib.game.monde.graphic_core.Shape;

/**
 * 
 * @author Ronan MOREL
 *
 */
public class PanneauAffichage extends Objet {
	
	private float position;
	
	public PanneauAffichage(String id, String img) {
		this(id, img, 1.5f, 1.0f);
	}
	
	public PanneauAffichage(String id, String img, float largeur, float hauteur) {
		super(id, img);

		Forme geo = new GPanneauAffichage(largeur, hauteur, ""); 
		this.forme = new Shape(geo, mat); 
	}
	
	/**
	 * Renvoie la objet Cloison sur package visu.cloison permettant de dessiner une cloison
	 * @return la cloison dessiner une cloison
	 */
	public GPanneauAffichage getPanneau(){
		return (GPanneauAffichage) ((Shape) forme).getForme();
	}

	public float getPosition() {
		return position;
	}

	public void setPosition(float position) {
		this.position = position;
	}
}
