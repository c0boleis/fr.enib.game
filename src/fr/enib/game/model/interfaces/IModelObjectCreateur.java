/**
 * 
 */
package fr.enib.game.model.interfaces;

/**
 * @author Corentin Boleis
 *
 */
public interface IModelObjectCreateur {
	
	/**
	 * @param noeudDepart
	 * @param noeudArrivee
	 * @return {@link ILien} si le lien a été créé, si non
	 * null.
	 */
	public ILien creerLien(INoeud noeudDepart,INoeud noeudArrivee);
	
	/**
	 * @param nom
	 * @param url
	 * @param descpription
	 * @return {@link ITableau} si le tableau a été créé, si non
	 * null.
	 */
	public ITableau creerTableau(String nom,String url,String descpription);
	
	/**
	 * @param nom
	 * @return {@link INoeud} si le noeud a été créé, si non
	 * null.
	 */
	public INoeud creerNoeud(String nom);

	
	/**
	 * @return {@link ILien}
	 */
	public ILien getInstanceLien();
	
	/**
	 * @return {@link INoeud}
	 */
	public INoeud getInstanceNoeud();
	
	/**
	 * @return {@link ITableau}
	 */
	public ITableau getInstanceTableau();
	
	

}
