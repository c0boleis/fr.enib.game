package fr.enib.game.monde.builder;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import fr.enib.game.monde.musee.Salle;
import fr.enib.game.monde.objet.Avatar;
import fr.enib.game.monde.objet.Objet;

public class Monde {
	private static Logger LOGGER = Logger.getLogger(Monde.class);

	public float t0 = (float)(System.currentTimeMillis())/1000.f ;

	//private float horloge = 0.0f ;
	private Salle  salleCourante ; 

	private static HashMap<String,Objet> objets     = new HashMap<String,Objet>() ;
	//private static HashMap<String,Capteur> capteurs = new HashMap<String,Capteur>() ;
	private static HashMap<String,Salle> salles     = new HashMap<String,Salle>() ; 

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
	public void ajouter(Objet objet){
		objets.put(objet.getId(),objet) ; 
	}

	public static void ajouter(String nomSalle, Objet objet){
		Salle laSalle = donnerSalle(nomSalle) ; 
		laSalle.ajouter(objet) ; 
	}

	/*public void ajouterCapteur(Capteur capteur){
		capteurs.put(capteur.getId(),capteur) ; 
	}*/

	public void ajouterSalle(Salle salle){
		if(salleCourante == null) salleCourante = salle;
		salles.put(salle.getId(),salle) ; 
	}

	public static Salle donnerSalle(String id){
		return salles.get(id) ; 
	}

	public void display(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2() ; 

		gl.glClear(GL.GL_COLOR_BUFFER_BIT) ; 
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT) ; 

		Avatar.get().placer(gl) ;

		salleCourante.dessiner(gl) ; 
		/*if(salleCourante.voisines != null){
			for(Salle salleVoisine : salleCourante.voisines.values()){
				salleVoisine.dessiner(gl);
			}
		}*/
		

		/*for(Salle s : salles.values()){
				s.dessiner(gl);
		}*/
	}

	public void actualiser(float t){
		salleCourante.actualiser(0.0f,0.0f) ;
		
		//on créer un copie de la liste des salles voisines
		//pour éviter : java.util.ConcurrentModificationException
		/*List<Salle> listeTmp = new ArrayList<Salle>();
		listeTmp.addAll(salleCourante.voisines.values());
		
		for(Salle s : listeTmp){
			s.actualiser(0.0f,0.0f) ;
		}*/
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
		LOGGER.info("salle courante :" + salleCourante.getId());
	}


	/*@Override
	public void update(String aspect, Object valeur, Observe de) {
		LOGGER.info("update monde:\t"+aspect);
		if(aspect.equals(Capteur.ENTREE)){
			if(!salleCourante.avatarPresent()){
				for(Salle salle : salleCourante.voisines.values()){
					if(salle.avatarPresent()){
						salleCourante = salle;
						LOGGER.info("changement de salle courante");
						break;
					}
				}
			}
		}
	}*/
}
