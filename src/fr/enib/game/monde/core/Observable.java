package fr.enib.game.monde.core;

/**
 * 
 * @author Ronan MOREL
 *
 */
public interface Observable {
	public void add(Observer obs); 
	
	public void delete(String name);
	
	public void update(String aspect, Object value) ;
}
