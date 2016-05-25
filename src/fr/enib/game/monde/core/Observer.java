package fr.enib.game.monde.core;

/**
 * Pattern Observer/Observable
 * @author Ronan MOREL
 *
 */
public interface Observer {
	
	public String getId() ;
	
	public void update(String aspect, Object valeur, Observable de) ; 
}
