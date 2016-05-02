package fr.enib.game.monde.core;

public interface Observer {
	
	public String getId() ;
	
	public void update(String aspect, Object valeur, Observable de) ; 
}
