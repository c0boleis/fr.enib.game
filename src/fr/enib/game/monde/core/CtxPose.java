package fr.enib.game.monde.core;

import fr.enib.game.monde.geo.Vec3;

/**
 * Permet de placer les objets dans l'environnement 3D
 * @author Ronan MOREL
 *
 */
public class CtxPose implements Observer {
	
	//private static Logger LOGGER = Logger.getLogger(CtxPose.class);

	public static final String POSE = "Pose";
	
	private String id ;
	
	private Situable maitre ; 
	
	private Situable esclave ;
	
	private Vec3 translation ;
	
	private float angle ;

	/**
	 * 
	 * @param id
	 * @param maitre
	 * @param esclave
	 * @param x translation en x dans le repere de maitre
	 * @param y translation en y dans le repere de maitre
	 * @param z translation en z dans le repere de maitre
	 * @param angle de rotation selon z
	 */
	public CtxPose(String id, Situable maitre, Situable esclave, float x , float y, float z, float angle){
		this.id      = id ;
		this.maitre  = maitre ;
		this.esclave = esclave ; 
		maitre.add(this) ; 
		translation  = new Vec3(x,y,z) ; 
		this.angle   = angle ;
	}

	/**
	 * @return {@link #id}
	 */
	public String getId(){
		return id ;
	}

	@Override
	public void update(String aspect, Object valeur, Observable de){
		//LOGGER.info("PRE-CONTRAINTE")  ; 
		if(de == maitre && aspect.equals(POSE)){
			//LOGGER.info("CONTRAINTE APPLIQUUE");
			esclave.repere.copier(maitre.repere) ;
			esclave.repere.deplacer(translation) ; 
			esclave.repere.tourner(angle) ;  
		}
	}
}
