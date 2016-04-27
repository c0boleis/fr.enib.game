package fr.enib.game.monde.core;

import java.util.HashMap;

public class ImplObserve implements Observe {

	public HashMap<String,Observateur> observateurs = new HashMap<String,Observateur>() ;

	public ImplObserve(){} 

	@Override
	public void add(Observateur obs){
		observateurs.put(obs.getId(), obs) ; 
	}

	@Override
	public void suprimer(String nom){
		//TODO
	}

	@Override
	public void changed(String aspect, Object valeur){
		for(Observateur obs : observateurs.values()){
			obs.update(aspect, valeur, this) ; 
		}
	}

}
