package fr.enib.game.model.interfaces;

import java.io.Serializable;

/**
 * Tout objet d'un graphe implement
 * cette class
 * @author Corentin Boleis
 *
 */
public interface IModelObject extends IClonableObject,IRemovable, Serializable{
	
	/**
	 * il ne doit pas exist" deux objet
	 * avec le même id.
	 * @return the id.
	 */
	public String getId();
	
	/**
	 * Modifier l'id d'un noeud ou d'un lien
	 * @param id
	 * @return true si l'objet a bien change de nom
	 */
	public boolean setId(String id);


}
