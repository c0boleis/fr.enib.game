package fr.enib.game.monde.objet;

import fr.enib.game.monde.graphic_core.Forme;
import fr.enib.game.monde.graphic_core.GPlafond;
import fr.enib.game.monde.graphic_core.Shape;

public class Plafond extends Objet {
	
	public Plafond(String id, String pathTexture,float hauteur, float largeur){
		super(id, pathTexture); 
		Forme geo = new GPlafond(hauteur, largeur); 
		this.forme = new Shape(geo, mat); 
	}

}
