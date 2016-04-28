package fr.enib.game.model.interfaces;


/**
 * @author Corentin Boleis
 *
 */
public interface IModel {
	
	/**
	 * @param id
	 * @return l'objet du model qui a cette id.
	 * si aucun objet n'est trouvé, return null.
	 */
	public IModelObject getModelObject(String id);
	
	/**
	 * @param id de l'objet du  model.
	 * @return true si un objet du model
	 * a déjà cette id.
	 */
	public boolean containsModeObject(String id);
	
	/**
	 * Ajouter un objet au model
	 * @see ILien
	 * @see INoeud
	 * @see ITableau
	 * @param objet
	 * @return true a bien ete ajouté
	 */
	public boolean ajouterModelObject(IModelObject objet);
	
	/**
	 * @param object
	 * @return true si l'objet a été suprimé
	 */
	public boolean suprmierModelObject(IModelObject object);
	
	/**
	 * 
	 * @return tout les object contenue dans
	 * le model.
	 */
	public IModelObject[] getModelObjects();
	
	/**
	 * 
	 * @return true si sauvegarde reussi
	 */
	public boolean sauvegarderModel();
	

}
