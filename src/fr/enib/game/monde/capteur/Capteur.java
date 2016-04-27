package fr.enib.game.monde.capteur;

import fr.enib.game.monde.core.Situable;
import fr.enib.game.monde.object.Objet;

public abstract class Capteur extends Situable  {
	 
	private Objet cible ;
	
	public static final String ENTREE = "Entree";
	
	public static final String SORTIE = "Sortie";

	/**
	 * Constructeur
	 * @param id l'identifiant du capteur
	 */
	public Capteur(String id){
		super(id) ;	
	}

	public abstract void tester(float t) ;

	/**
	 * Renvoie l'objet que surveille le capteur
	 * @return {@link #cible}
	 */
	public Objet getCible() {
		return cible;
	}

	/**
	 * Change la cible du capteur
	 * @param c la nouvelle cible
	 */
	public void setCible(Objet c) {
		this.cible = c;
	} 
}