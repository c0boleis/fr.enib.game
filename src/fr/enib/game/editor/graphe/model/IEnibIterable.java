/**
 * 
 */
package fr.enib.game.editor.graphe.model;

/**
 * @author Corentin Boleis
 *
 */
public interface IEnibIterable {
	
	/**
	 * @return la prochaine instance de l'Objet
	 */
	public Object getNext();
	
	/**
	 * @return le number d'instance créer
	 */
	public int getNumberOfInstance();

}
