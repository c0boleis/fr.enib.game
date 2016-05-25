package fr.enib.game.monde.objet;

import fr.enib.game.monde.graphic_core.Forme;
import fr.enib.game.monde.graphic_core.GPlafond;
import fr.enib.game.monde.graphic_core.Shape;

/**
 * Represente un objet Plafond
 * @author Ronan MOREL
 *
 */
public class Plafond extends Objet {
	
	/**
	 * Construteur
	 * @param id l'id du plafond
	 * @param pathTexture le chemin vers la texture du plafond
	 * @param profondeur la profondeur du plafond
	 * @param largeur la largeur du plafond
	 */
	public Plafond(String id, String pathTexture,float profondeur, float largeur){
		super(id, pathTexture); 
		Forme geo = new GPlafond(profondeur, largeur); 
		this.forme = new Shape(geo, mat); 
	}

}
