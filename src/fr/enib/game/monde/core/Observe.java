package fr.enib.game.monde.core;

public interface Observe {

	public void add(Observateur obs); 
	
	public void suprimer(String nom);
	
	public void changed(String aspect, Object valeur) ;
}

