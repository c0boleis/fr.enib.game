package fr.enib.game.monde.core;

public interface Observateur {
	
	public String getId() ;
	
	public void update(String aspect, Object valeur, Observe de) ; 
}

