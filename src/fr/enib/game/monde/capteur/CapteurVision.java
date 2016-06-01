package fr.enib.game.monde.capteur;

import org.apache.log4j.Logger;

import fr.enib.game.monde.geo.Vec3;
import fr.enib.game.monde.objet.Avatar;
import fr.enib.game.monde.objet.Objet;
import fr.enib.game.monde.objet.Tableau;

/**
 * Capteur agissant lorsqu'on regarde une cible
 * @author Ronan Morel
 *
 */
public class CapteurVision extends Capteur{

	private static Logger LOGGER = Logger.getLogger(CapteurVision.class);
	
	private Avatar avatar;
	
	private static final float INCREMENT_DEGRE_INTERET = 0.05f;

	private static final float DISTANCE_INTERET_TABLEAU = 1.0f;
	
	/**
	 * Constructeur
	 * @param id l'identifiant du capteur
	 * @param avatar l'objet source pour avoir les positions du regard
	 * @param cible l'objet que va surveiller le capteur
	 */
	public CapteurVision(String id, Avatar avatar, Objet cible){
		super(id, cible) ; 
		this.avatar = avatar;
	}

	/**
	 * test si l'avatar est present dans la zone de la cible
	 */
	@Override
	public void tester(float t) { //TODO avoir la direction de l'avatar vers le tableau
		Tableau tab = (Tableau) cible;
		//Vec3 posUser = avatar.getPositionRepere();
		//Vec3 posDistUser = avatar.getPositionRepere();
		float distance = calculDistancePoints(tab.getPositionInRepere(), avatar.getPositionRepere());
		//System.out.println("D : " + distance);
//		LOGGER.info("Capteur visition teste distance : " + distance+ "<"+this.dis);
		if( distance <= DISTANCE_INTERET_TABLEAU){
			//float dir = avatar.getDirectionRegard().x;
//			LOGGER.info("Capteur visition teste : " + dir);
			//if(dir >= 0.75 && dir < 1){
				tab.getiTableau().setDegreInteret(tab.getiTableau().getDegreInteret()+ INCREMENT_DEGRE_INTERET);
				LOGGER.info("Capteur visition : " + getId()+ " (" + tab.getiTableau().getDegreInteret()+ ")"); 
			//}
		}
	}
	
	/**
	 * Calcul la distance entre 2 points dans un repere
	 * @param p1 le point 1 dans le repere
	 * @param p2 le point 2 dans le repere
	 * @return la distance entre les 2 points dans un repere
	 */
	private float calculDistancePoints(Vec3 p1, Vec3 p2){
		return(float) Math.sqrt( (p2.x-p1.x)*(p2.x-p1.x) + (p2.y-p1.y)*(p2.y-p1.y) );
	}

}
