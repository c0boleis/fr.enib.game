package fr.enib.game.model.interfaces;

/**
 * 
 * @author Corentin Boleis
 *
 */
public interface IVisitableObject {
	
	/**
	 * visite le noeud ce qui revient
	 * a changer la valeur de visiter � true
	 */
	public void visiter();
	
	/**
	 * change la valeur de visiter a false
	 */
	public void resetVisiter();
	
	/**
	 * @return true si le noeud a �t� visit�.
	 */
	public boolean estVisiter();

}
