package fr.enib.game.model.interfaces;

import fr.enib.game.model.Noeud;

/**
 * un lien lie deux des noeud du graphe
 * dans un sens precis. Un lien ne peux
 * pas exist� si il n'a pas de noeud 
 * d'arriv�e et de  noeuds de d�part.
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
	
	/**
	 * @param nouveauNoeud le noeud d'arriv�e du lien.
	 * @return retourne null si pas d'erreur
	 */
	public Object setNoeudArrivee(Noeud nouveauNoeud);
	
	/**
	 * 
	 * @param nouveauNoeud le noeud de d�part du lien.
	 * @return retourne null si pas d'erreur.
	 */
	public Object setNoeudDepart(Noeud nouveauNoeud);

}
