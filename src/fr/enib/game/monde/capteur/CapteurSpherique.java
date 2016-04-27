package fr.enib.game.monde.capteur;

import fr.enib.game.monde.object.Objet;

/**
 * Classe representant un capteur spherique
 */
public class CapteurSpherique extends Capteur {
	
	//private static Logger LOGGER = Logger.getLogger(CapteurSpherique.class);

	private Objet cible ;
	private float horizon ;
	private boolean interieur ; 

	/**
	 * Constructeur
	 * @param id l'identifiant du capteur
	 * @param cible l'objet que va surveiller le capteur
	 * @param horizon 
	 */
	public CapteurSpherique(String id, Objet cible, float horizon){
		super(id) ; 
		this.cible = cible ;
		this.horizon = horizon ;
		interieur = repere.getPostiton().distance(cible.repere.getPostiton()) < this.horizon ; 
	}

	/**
	 * Test si l'objet est entre ou sortie de la zone de surveillance du capteur
	 */
	public void tester(float t){
		float d = repere.getPostiton().distance(cible.repere.getPostiton()) ; 
 
		if (interieur && d > horizon){
			//LOGGER.info("SORTIE") ; 
			interieur = false ; 
			changed("sortie",null) ; 
		} else
		if (! interieur && d < horizon){
			//LOGGER.info("ENTREE") ; 
			interieur = true ; 
			changed("entree",null) ; 
		}
	}
}