/**
 * 
 */
package fr.enib.game.model.interfaces;

/**
 * @author magali
 * Un Lien a un poids 
 */
public interface IObjectPondere {
	
	/**
	 * 
	 * @param lien
	 * @param poids
	 * @return true si le poids a ete modifie
	 */
	public boolean setPoids(float poids);
	
	/**
	 * 
	 * @return le poids de l'objet
	 */
	public float getPoids();
}
