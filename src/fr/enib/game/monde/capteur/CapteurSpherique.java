package fr.enib.game.monde.capteur;

import org.apache.log4j.Logger;

import fr.enib.game.monde.objet.Objet;

public class CapteurSpherique extends Capteur {
	
	private static Logger LOGGER = Logger.getLogger(CapteurSpherique.class);

	private float horizon ;

	/**
	 * Constructeur
	 * @param id l'identifiant du capteur
	 * @param cible l'objet que va surveiller le capteur
	 * @param horizon 
	 */
	public CapteurSpherique(String id, Objet cible, float horizon){
		super(id, cible) ; 
		this.horizon = horizon ;
		interieur = repere.getPostiton().distance(cible.repere.getPostiton()) < this.horizon ; 
	}

	/**
	 * Test si l'objet est entre ou sortie de la zone de surveillance du capteur
	 */
	public void tester(float t){
		float d = repere.getPostiton().distance(cible.repere.getPostiton()) ; 
 
		if (interieur && d > horizon){
			LOGGER.info("SORTIE") ; 
			interieur = false ; 
			update("sortie",null) ; 
		} else
		if (! interieur && d < horizon){
			LOGGER.info("ENTREE") ; 
			interieur = true ; 
			update("entree",null) ; 
		}
	}
}
