/**
 * 
 */
package fr.enib.game.model.interfaces;

/**
 * @author Corentin Boleis
 *
 */
public interface IModel {
	
	/**
	 * @param id
	 * @return l'objet du model qui a cette id.
	 * si aucun objet n'est trouv�, return null.
	 */
	public IModelObject getModelObject(String id);
	
	/**
	 * @param id de l'objet du  model.
	 * @return true si un objet du model
	 * a d�j� cette id.
	 */
	public boolean containsModeObject(String id);
	
	/**
	 * Ajouter un noeud dans le modele 
	 * @param noeud
	 * @return true a bien ete ajoute
	 */
	public boolean ajouterUnNoeud(INoeud noeud);
	
	/**
	 * Ajouter un lien au modele 
	 * @param lien � ajouter
	 * @return true si il a bien ete ajoute 
	 */
	public boolean ajouterUnLien(ILien lien);
	

}
