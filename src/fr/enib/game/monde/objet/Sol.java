package fr.enib.game.monde.objet;

import fr.enib.game.monde.graphic_core.Forme;
import fr.enib.game.monde.graphic_core.GSol;
import fr.enib.game.monde.graphic_core.Shape;

/**
 * Représente un objet Sol
 * @author Ronan MOREL
 *
 */
public class Sol extends Objet {
	
	/**
	 * Construteur
	 * @param id l'id du sol
	 * @param pathTexture le chemin vers la texture du sol
	 * @param profondeur la profondeur du sol
	 * @param largeur la largeur du sol
	 */
	public Sol(String id, String pathTexture, float profondeur, float largeur){
		super(id, pathTexture); 
		Forme geo = new GSol(profondeur, largeur); 
		this.forme = new Shape(geo, mat); 
	}

}
