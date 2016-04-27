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

	public boolean modifierPoids(ILien lien, float poids);
}
