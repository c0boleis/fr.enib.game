package fr.enib.game.monde.capteur;

import fr.enib.game.monde.musee.Salle;
import fr.enib.game.monde.objet.Objet;

public class CapteurCubique extends Capteur {

	//private static Logger LOGGER = Logger.getLogger(CapteurCubique.class);

	private float distanceX;

	private float distanceY;

	//private float distanceZ;

	/**
	 * Constructeur
	 * @param id l'identifiant du capteur
	 * @param c l'objet cible a surveiller
	 * @param dx la position en x 
	 * @param dy la position en y
	 * @param dz la position en z
	 */
	public CapteurCubique(String id, Objet c, float dx,float dy,float dz){
		super(id, c) ;
		//LOGGER.debug("capteur cubique créer:\t"+id);
		distanceX = dx;
		distanceY = dy;
		//distanceZ = dz;
		tester(0.1f);
	}

	/**
	 * Constructeur
	 * @param id l'identifiant du capteur
	 * @param c l'objet cible a surveiller
	 * @param salle la salle pour placer le capteur
	 */
	public CapteurCubique(String id, Objet c, Salle salle) {
		this(id,c,salle.getProfondeur()/2.0f,salle.getLargeur()/2.0f,salle.getHauteur()/2.0f);
	}
	
	/**
	 * 
	 * @param salle
	 */
	public void setSalle(Salle salle){
		distanceX = salle.getProfondeur()/2.0f;
		distanceY = salle.getLargeur()/2.0f;
	}

	/**
	 * test si l'avatar est present dans la zone 
	 */
	public void tester(float t){
		if (!testeX()){
			//LOGGER.debug(SORTIE) ;
			if(interieur){
				interieur = false ; 
				//LOGGER.info(SORTIE+":x;\t"+getId()) ;
				update(SORTIE,null) ;
			}

			return;
		}
		if (!testeY()){
			//LOGGER.debug(SORTIE) ;
			if(interieur){
				//LOGGER.info(SORTIE+":y;\t"+getId()) ;
				interieur = false ; 
				update(SORTIE,null) ;
			}

			return;
		}
		if(!interieur){
			//LOGGER.info(ENTREE+":\t"+getId()) ;
			interieur = true ; 
			update(ENTREE,null) ;
		}
		//LOGGER.debug("teste:\t"+getId()+"\t:"+interieur) ;

	}

	/*private boolean testeZ() {
		if(cible.getRepere().getPostiton().z<repere.getPostiton().z+distanceZ){
			return cible.getRepere().getPostiton().z>repere.getPostiton().z-distanceZ;
		}
		return false;
	}*/

	/**
	 * Verifie si la cible est à l'interieur de la zone du capteur, en y
	 * @return true si la cible est a l'interieur de la zone du capteur, en y, sinon false
	 */
	private boolean testeY() {
		if(cible.getRepere().getPostiton().y<repere.getPostiton().y+distanceY){
			return cible.getRepere().getPostiton().y>repere.getPostiton().y-distanceY;
		}
		return false;
	}

	/**
	 * Verifie si la cible est à l'interieur de la zone du capteur, en x
	 * @return true si la cible est a l'interieur de la zone du capteur, en x, sinon false
	 */
	private boolean testeX() {
		if(cible.getRepere().getPostiton().x<repere.getPostiton().x+distanceX){
			return cible.getRepere().getPostiton().x>repere.getPostiton().x-distanceX;
		}
		return false;
	}

	
}
