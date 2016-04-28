package fr.enib.game.model.interfaces;

/**
 * un lien lie deux dex noeud du graphe
 * dans un sens precis
 * @author Corentin Boleis
 */
public interface ILien extends IModelObject,IObjectPondere{
	
	/**
	 * le nom par default des Lien
	 */
	public static final String NOM_PAR_DEFAULT = "Lien";
	
	/**
	 * 
	 * @return le noeud de d�part du lien.
	 */
	public INoeud getNoeudDepart();
	
	/**
	 * @return le noeud d'arriv�e du lien.
	 */
	public INoeud getNoeudArrivee();

}
