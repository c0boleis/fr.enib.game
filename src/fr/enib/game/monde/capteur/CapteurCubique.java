package fr.enib.game.monde.capteur;

import fr.enib.game.monde.musee.Salle;
import fr.enib.game.monde.objet.Objet;

/**
 * Capteur cubique (carre)
 * @author Ronan Morel
 *
 */
public class CapteurCubique extends Capteur {

	//private static Logger LOGGER = Logger.getLogger(CapteurCubique.class);

	private float distanceX;

	private float distanceY;
	
	private float offset;

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
		//tester(0.1f);
		offset = 0.01f;
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
	@Override
	public void tester(float t){
		if (!testeX()){
			if(interieur){
				interieur = false ; 
				update(SORTIE, cible);
			}
			return;
		}
		if (!testeY()){
			if(interieur){
				interieur = false ; 
				update(SORTIE, cible);
			}
			return;
		}
		if(!interieur){
			//LOGGER.info(ENTREE + ":\t" + getId()) ;
			interieur = true ; 
			update(ENTREE, cible);
		}
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
		return cible.getPositionRepere().y < getPositionRepere().y + distanceY - offset
			&& cible.getPositionRepere().y > getPositionRepere().y - distanceY + offset;
	}

	/**
	 * Verifie si la cible est à l'interieur de la zone du capteur, en x
	 * @return true si la cible est a l'interieur de la zone du capteur, en x, sinon false
	 */
	private boolean testeX() {
		return cible.getPositionRepere().x < getPositionRepere().x + distanceX - offset
			&& cible.getPositionRepere().x > getPositionRepere().x - distanceX + offset;
	}

	
}
