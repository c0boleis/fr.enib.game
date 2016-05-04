/**
 * 
 */
package fr.enib.game.model.listeners;

import fr.enib.game.model.interfaces.IModelObject;

/**
 * @author Corentin Boleis
 *
 */
public interface IModelListener extends IListener{
	
	/**
	 * appellé quand un {@link IModelObject} a été ajouté.
	 * @param object qui a été ajouté
	 */
	public void iModelObjectAdded(IModelObject object);

}
