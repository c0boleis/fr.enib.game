package fr.enib.game.model.interfaces;

/**
 * un lien lie deux dex noeud du graphe
 * dans un sens precis
 * @author Corentin Boleis
 */
public interface ILien extends IModelObject{
	
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
