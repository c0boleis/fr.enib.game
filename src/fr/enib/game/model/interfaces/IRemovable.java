/**
 * 
 */
package fr.enib.game.model.interfaces;

/**
 * cette interface permet de savoir si l'objet et à suprimer ou non
 * cela et utilisé dans l'éditeur de graphe
 * @author Corentin Boleis
 *
 */
public interface IRemovable {

	/**
	 * @return true si l'objet à été suprimer
	 */
	public boolean remove();

}
