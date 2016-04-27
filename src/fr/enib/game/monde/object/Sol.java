package fr.enib.game.monde.object;

public class Sol extends Objet {

	public Sol(String id, String nomTexture, float largeur, float profondeur){
		super(id) ; 
		forme = new fr.enib.game.monde.graphe_core.Sol(nomTexture,largeur,profondeur) ; 
	}
	
	public void setTexture(String textureSol){
		((fr.enib.game.monde.graphe_core.Sol) forme).setTexture(textureSol);
	}
}