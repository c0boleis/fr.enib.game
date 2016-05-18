package fr.enib.game.monde.builder;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import fr.enib.game.monde.capteur.Capteur;
import fr.enib.game.monde.musee.Musee;
import fr.enib.game.monde.musee.Salle;
import fr.enib.game.monde.objet.Avatar;
import fr.enib.game.monde.objet.Objet;

/**
 * 
 * @author Ronan MOREL
 *
 */
public class Monde {
	private static Logger LOGGER = Logger.getLogger(Monde.class);

	public float t0 = (float)(System.currentTimeMillis())/1000.f ;

	private Salle  salleCourante ; 
	
	public volatile static boolean actualisationEnCours = false;

	private IActualisation iActu = null;
	
	//private static HashMap<String,Objet> objets     = new HashMap<String,Objet>() ;
	//private static HashMap<String,Capteur> capteurs = new HashMap<String,Capteur>() ;
	//private static HashMap<String,Salle> salles     = new HashMap<String,Salle>() ; 

	/**
	 * Singleton de la class qui permet d'y accéder partout dans le code
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
	 * Ajout d'un objet au monde
	 * @param objet l'objet a ajouter
	 */
	/*public void ajouter(Objet objet){
		objets.put(objet.getId(),objet) ; 
	}

	public static void ajouter(String nomSalle, Objet objet){
		Salle laSalle = donnerSalle(nomSalle) ; 
		laSalle.ajouter(objet) ; 
	}

	public void ajouterCapteur(Capteur capteur){
		capteurs.put(capteur.getId(),capteur) ; 
	}

	public void ajouterSalle(Salle salle){
		if(salleCourante == null) salleCourante = salle;
		salles.put(salle.getId(),salle) ; 
	}

	public static Salle donnerSalle(String id){
		return salles.get(id) ; 
	}*/

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
	 * Renvoie une copie de la liste des salles presente dans le monde
	 * @return une copie de {@link #salles}
	 */
	/*public static HashMap<String,Salle> getSalles() {
		// on fait une copie du HashMap pour éviter l'ajout de salle via getSalles()
		HashMap<String,Salle> map = new HashMap<String,Salle>();
		map.putAll(salles);
		return map;
	}*/

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

	public void setiActu(IActualisation iActu) {
		this.iActu = iActu;
	}
	
	/*public void clear(){
		salles.clear();
		capteurs.clear();
		objets.clear();
	}*/
}
