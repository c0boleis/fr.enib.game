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
	 * @param lien
	 * @param degre
	 * @return true si le degre est bien modifie
	 */
	public boolean modifierDegreInteret(ILien lien, float degre);
}
