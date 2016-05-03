package fr.enib.game.monde.builder;

import org.apache.log4j.Logger;

import fr.enib.game.monde.musee.Salle;
import fr.enib.game.monde.objet.RessourceProvider;
import fr.enib.game.monde.objet.Tableau;

public class Builder {

	private static Logger LOGGER = Logger.getLogger(Builder.class);
	
	public void construire(){
		LOGGER.info("construire called");
		
		Monde m = Monde.get();
		
		Salle s = new Salle("hall", 5.0f, 7.5f, 3.0f);
		//s.placer(0.0f, 0.0f, 0.0f);
		
		s.ajouterTableau(new Tableau("tab_01", RessourceProvider.pathTableaux + "allee-couverte-ploerdut.jpg"));
		
		m.setSalleCourante(s);
	}
}
