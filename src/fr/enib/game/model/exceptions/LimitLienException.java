/**
 * 
 */
package fr.enib.game.model.exceptions;

/**
 * @author Corentin Boleis
 *
 */
public class LimitLienException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8365415503558987483L;
	
	/**
	 * 
	 */
	public LimitLienException(){
		//rien à faire dans cette fonction
	}

	/**
	 * @param message
	 */
	public LimitLienException(String message){
		super(message);
	}

}
