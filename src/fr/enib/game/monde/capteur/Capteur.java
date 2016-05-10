package fr.enib.game.monde.capteur;

import fr.enib.game.monde.core.Situable;
import fr.enib.game.monde.objet.Objet;

public abstract class Capteur extends Situable  {
	 
	protected Objet cible ;
	
	public static final String ENTREE = "Entree";
	
	public static final String SORTIE = "Sortie";

	protected boolean interieur ; 
	
	/**
	 * Constructeur
	 * @param id l'identifiant du capteur
	 */
	public Capteur(String id){
		super(id) ;	
	}
	
	public Capteur(String id, Objet o){
		super(id) ;	
		this.cible = o;
		this.interieur = false;
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
	
	/**
	 * Verifie si la cible est a l'interieur ou exterieur de la zone du capteur
	 * @return true si la cible est a l'interieur, sinon false
	 */
	public boolean interieur(){
		return interieur;
	}

	public void setInterieur(boolean interieur) {
		this.interieur = interieur;
	}
	
	
}
