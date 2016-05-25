package fr.enib.game.monde.graphic_core;

/**
 * Trou dans un mur
 * @author Ronan MOREL
 */
public class TrouMur {
	private float hauteur;
	private float largeur;
	private float position;
	private String salleDestination;
	
	/**
	 * Constructeur
	 * @param largeur la largeur du trou dans le mur
	 * @param hauteur la hauteur du trou dans le mur
	 * @param position la position sur le mur (repere 1D du mur)
	 * @param nomSalle le nom de la salle destination
	 */
	public TrouMur(float largeur, float hauteur, float position, String nomSalle) {
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.position = position;
		this.salleDestination = nomSalle;
	}
	
	/**
	 * Récupère la hauteur du trou dans le mur
	 * @return la hauteur du trou dans le mur
	 */
	public float getHauteur() {
		return hauteur;
	}
	
	/**
	 * Modifie la hauteur du trou dans le mur
	 * @return la hauteur du trou dans le mur
	 */
	public void setHauteur(float hauteur) {
		this.hauteur = hauteur;
	}
	
	/**
	 * Récupère la largeur du trou dans le mur
	 * @return la largeur du trou dans le mur
	 */
	public float getLargeur() {
		return largeur;
	}
	
	/**
	 * Modifie la largeur du trou dans le mur
	 * @return la largeur du trou dans le mur
	 */
	public void setLargeur(float largeur) {
		this.largeur = largeur;
	}
	
	/**
	 * Récupère la position du trou dans le mur
	 * @return la position du trou dans le mur
	 */
	public float getPosition() {
		return position;
	}
	
	/**
	 * Modifie la position du trou dans le mur
	 * @return la position du trou dans le mur
	 */
	public void setPosition(float position) {
		this.position = position;
	}
	
	/**
	 * Récupère le nom de la salle destination
	 * @return le nom de la salle destination
	 */
	public String getNomSalleDestination() {
		return salleDestination;
	}
	
	/**
	 * Modifie le nom de la salle destination
	 * @return le nom de la salle destination
	 */
	public void setNomSalleDestination(String salleDestination) {
		this.salleDestination = salleDestination;
	}
	
}
