package fr.enib.game.monde.builder;

import java.io.File;

import org.apache.log4j.Logger;

import fr.enib.game.monde.musee.Salle;

public class Builder {
	public static final String PATH = "data"+File.separator+"Image_graphe"+File.separator;

	private static Logger LOGGER = Logger.getLogger(Builder.class);
	
	public void construire(){
		LOGGER.info("construire called");
		
		Monde m = Monde.get();
		
		Salle s = new Salle("hall", 5.0f, 7.5f, 5.0f);
		//s.placer(0.0f, 0.0f, 0.0f);
		
		m.setSalleCourante(s);
	}
}
