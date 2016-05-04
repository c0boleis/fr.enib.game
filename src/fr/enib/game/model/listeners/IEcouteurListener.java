/**
 * 
 */
package fr.enib.game.model.listeners;

/**
 * @author Corentin Boleis
 *
 */
public interface IEcouteurListener {
	
	
	/**
	 * @param listener
	 */
	public void addListener(IListener listener);
	
	/**
	 * supprime tout les listener
	 */
	public void removeAllListener();
	
	/**
	 * 
	 * @param listener IListener
	 */
	public void removeListener(IListener listener);

}
