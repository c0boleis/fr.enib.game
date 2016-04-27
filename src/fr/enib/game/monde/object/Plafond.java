package fr.enib.game.monde.object;

public class Plafond extends Objet {

	public Plafond(String id, String nomTexture, float largeur, float profondeur){
		super(id) ; 
		forme = new fr.enib.game.monde.graphe_core.Plafond(nomTexture,largeur,profondeur) ; 
	}
	
	public void setTexture(String textureSol){
		((fr.enib.game.monde.graphe_core.Plafond) forme).setTexture(textureSol);
	}
}
