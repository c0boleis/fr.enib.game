/**
 * 
 */
package fr.enib.game.model.interfaces;

/**
 * @author magali
 * Un noeud a un degre d'interet 
 */
public interface IObjectInteret {

	/**
	 * 
	 * @param degre
	 * @return true si le degre est bien modifie
	 */
	public boolean setDegreInteret(float degre);
	
	/**
	 * @return le degré d'interet de l'objet
	 */
	public float getDegreInteret();
}
