package fr.enib.game.model.interfaces;

/**
 * un lien lie deux des noeud du graphe
 * dans un sens precis. Un lien ne peux
 * pas existé si il n'a pas de noeud 
 * d'arrivée et de  noeuds de départ.
 * @author Corentin Boleis
 */
public interface ILien extends IModelObject,IObjectPondere{
	
	/**
	 * le nom par default des Lien
	 */
	public static final String NOM_PAR_DEFAULT = "Lien";
	
	/**
	 * 
	 * @return le noeud de départ du lien.
	 */
	public INoeud getNoeudDepart();
	
	/**
	 * @return le noeud d'arrivée du lien.
	 */
	public INoeud getNoeudArrivee();

}
