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
	 * appell� quand un {@link IModelObject} a �t� ajout�.
	 * @param object qui a �t� ajout�
	 */
	public void iModelObjectAdded(IModelObject object);

}
