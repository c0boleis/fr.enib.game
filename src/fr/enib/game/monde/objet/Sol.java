package fr.enib.game.monde.objet;

import fr.enib.game.monde.graphic_core.Forme;
import fr.enib.game.monde.graphic_core.GSol;
import fr.enib.game.monde.graphic_core.Shape;

public class Sol extends Objet {
	
	public Sol(String id, String pathTexture, float hauteur, float largeur){
		super(id, pathTexture); 
		Forme geo = new GSol(hauteur, largeur); 
		this.forme = new Shape(geo, mat); 
	}

}
