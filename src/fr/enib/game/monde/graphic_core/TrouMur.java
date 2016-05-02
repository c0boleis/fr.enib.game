package fr.enib.game.monde.graphic_core;

public class TrouMur {
	private float hauteur;
	private float largeur;
	private float position;
	
	public TrouMur(float largeur, float hauteur, float position) {
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.position = position;
	}
	public float getHauteur() {
		return hauteur;
	}
	public void setHauteur(float hauteur) {
		this.hauteur = hauteur;
	}
	public float getLargeur() {
		return largeur;
	}
	public void setLargeur(float largeur) {
		this.largeur = largeur;
	}
	public float getPosition() {
		return position;
	}
	public void setPosition(float position) {
		this.position = position;
	}
}
