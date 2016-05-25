package fr.enib.game.monde.builder;

import org.apache.log4j.Logger;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import fr.enib.game.monde.musee.Musee;
import fr.enib.game.monde.musee.Salle;
import fr.enib.game.monde.objet.Avatar;

/**
 * Repr�sente le monde (l'ensemble des objets) dans l'environnement 3D
 * @author Ronan MOREL
 *
 */
public class Monde {
	private static Logger LOGGER = Logger.getLogger(Monde.class);

	public float t0 = (float)(System.currentTimeMillis())/1000.f ;

	private Salle  salleCourante ; 
	
	public volatile static boolean actualisationEnCours = false;

	private IActualisation iActu = null;

	/**
	 * Singleton de la class qui permet d'y acc�der partout dans le code
	 * avec la fonction {@link #get()}
	 */
	private static Monde INSTANCE = null;

	/**
	 * Constructeur Singleton
	 */
	private Monde(){
	}

	/**
	 * Singleton
	 * @return l'instance UNIQUE de cette classe
	 */
	public static Monde get(){
		if(INSTANCE == null){
			INSTANCE = new Monde();
		}
		return INSTANCE;
	}

	/**
	 * Dessine l'environnement 3D
	 * @param drawable
	 */
	public void display(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2() ; 

		gl.glClear(GL.GL_COLOR_BUFFER_BIT) ; 
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT) ; 

		if(!actualisationEnCours){
			Avatar.get().placer(gl) ;

			if(salleCourante != null){
				salleCourante.dessiner(gl);
				if(salleCourante.voisines != null){
					for(Salle salleVoisine : salleCourante.voisines.values()){
						salleVoisine.dessiner(gl);
					}
				}
			}
		}
		
		
	}

	/**
	 * Actualiser les capteurs de cette environnement 3D
	 */
	public void actualiser(){
		if(!actualisationEnCours){
			if(salleCourante != null){
				if(!salleCourante.avatarPresent()){
					if(salleCourante.voisines != null){
						for(Salle salleVoisine : salleCourante.voisines.values()){
							if(salleVoisine.avatarPresent()){
								this.salleCourante = salleVoisine;
								if(iActu != null){
									String id = salleCourante.getId().substring(Musee.PREFIX_ID_SALLE.length());
									actualisationEnCours = true;
									iActu.changementSalle(id);
									actualisationEnCours = false;
								}
								break;
							}
						}
					}
				}
				
				salleCourante.actualiser(0.0f,0.0f) ;
				if(salleCourante.voisines != null){
					for(Salle salleVoisine : salleCourante.voisines.values()){
						salleVoisine.actualiser(0.0f, 0.0f);
					}
				}
			}
		}
	}

	/**
	 * Renvoie la salle courante du monde
	 * @return la salle courante
	 */
	public Salle getSalleCourante(){
		return salleCourante;
	}

	/**
	 * Change la salle courante du monde
	 * @param salle la nouvelle salle courante
	 */
	public void setSalleCourante(Salle salle){
		salleCourante = salle;
		LOGGER.info("salle courante : " + salleCourante.getId());
	}

	/**
	 * Modifie l'interface de callback d'actualisation de salle
	 * @param iActu l'interface de callback d'actualisation de salle
	 */
	public void setiActu(IActualisation iActu) {
		this.iActu = iActu;
	}
}
