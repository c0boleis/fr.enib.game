package fr.enib.game.monde.graphic_core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jogamp.opengl.GL2;

/**
 * 
 * @author Ronan MOREL
 *
 */
public class GMur extends Forme {
	private float hauteur;
	private float largeur;
	private float epaisseur;

	private List<TrouMur> trousMur;
	
	/**
	 * Construteur
	 * @param hauteur la hauteur de la cloison
	 * @param largeur la largeur de la cloison
	 * @param epaisseur la largeur de la cloison
	 */
	public GMur(float hauteur, float largeur, float epaisseur){
		this(hauteur, largeur, epaisseur, null);
	}

	/**
	 * Construteur
	 * @param haut la hauteur de la cloison
	 * @param larg la largeur de la cloison
	 * @param trousMur l'ensemble des trous sur la cloison
	 */
	public GMur(float hauteur, float largeur, float epaisseur, List<TrouMur> trousMur){
		super() ; 
		this.hauteur = hauteur; 
		this.largeur = largeur;
		this.epaisseur = epaisseur;
		this.trousMur = trousMur;
	}

	/**
	 * Dessine une cloison entiere
	 */
	@Override
	public void dessiner(GL2 gl){
		
		// Si pas de trou dans la cloison, alors on dessine un rectangle (largeur * hauteur)
		if(trousMur == null || trousMur.isEmpty()){
			dessinerPortion(gl, 0.0f, 0.0f, largeur, hauteur);
		}
		else{ // Si la cloison comporte des trous
			
			// Mettre la liste des trous dans l'ordre croissant des position des trous
			Collections.sort(trousMur, new Comparator<TrouMur>() {
				public int compare(TrouMur p1, TrouMur p2){
					float res = p2.getPosition() - p1.getPosition();
					
					if(res > 0) return -1;
					else if(res < 0) return 1;
					return 0;
				}
			});

			switch(trousMur.size()){
				case 1:
					{
					TrouMur t = trousMur.get(0);
					
					//A droite de la porte
					dessinerPortion(gl, 0.0f, 0.0f, t.getPosition() - t.getLargeur()/2.0f, hauteur);
					//Au dessus de la porte
					dessinerPortion(gl,t.getPosition() - t.getLargeur()/2.0f, t.getHauteur(), t.getPosition() + t.getLargeur()/2.0f,hauteur, t.getNomSalleDestination());
					//A gauche de la porte
					dessinerPortion(gl, t.getPosition() + t.getLargeur()/2.0f, 0.0f, largeur, hauteur);
					}
					break;
				case 2:
					{
					TrouMur t1 = trousMur.get(0);
					TrouMur t2 = trousMur.get(1);
					
					//A droite de la porte 1
					dessinerPortion(gl, 0.0f, 0.0f, t1.getPosition() - t1.getLargeur()/2.0f, hauteur);
					//Au dessus de la porte 1
					dessinerPortion(gl,t1.getPosition() - t1.getLargeur()/2.0f, t1.getHauteur(), t1.getPosition() + t1.getLargeur()/2.0f,hauteur, t1.getNomSalleDestination());
					//Entre les 2 portes
					dessinerPortion(gl, t1.getPosition() + t1.getLargeur()/2.0f, 0.0f, t2.getPosition() - t2.getLargeur()/2.0f, hauteur);
					//Au dessus de la porte 1
					dessinerPortion(gl, t2.getPosition() - t2.getLargeur()/2.0f, t2.getHauteur(), t2.getPosition() + t2.getLargeur()/2.0f, hauteur, t2.getNomSalleDestination());
					//A gauche de la porte 2
					dessinerPortion(gl, t2.getPosition() + t2.getLargeur()/2.0f, 0.0f, largeur, hauteur);
					}
					
					break;
				case 3 :
					break;
			}
		}
	}

	/**
	 * Dessine un portion d'une cloison
	 * @param gl l'objet graphique permettant de dessiner
	 * @param x0 l'abcisse du point en bas à gauche
	 * @param y0 l'ordonnee du point en bas à gauche
	 * @param x1 l'abcisse du point en haut à droite
	 * @param y1 l'ordonnee du point en haut à droite
	 */
	public void dessinerPortion(GL2 gl, float x0, float y0, float x1, float y1){
		// 				*-----------* (x1,y1)
		//				|			|
		//				|			|
		//				|			|
		//				|			|
		//(x0,y0)	    *-----------*

		gl.glEnable(GL2.GL_TEXTURE_2D); 
		gl.glTexEnvi( GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);

		gl.glPushMatrix();	
		gl.glBegin(GL2.GL_QUADS); 
		
		// Haut Gauche
		gl.glTexCoord2f(0, 1); gl.glVertex3f(0, x0, y1); 

		// Haut Droite
		gl.glTexCoord2f(1, 1); gl.glVertex3f(0, x1, y1); 

		// Bas Droite
		gl.glTexCoord2f(1, 0); gl.glVertex3f(0, x1, y0); 

		// Bas Gauche
		gl.glTexCoord2f(0, 0); gl.glVertex3f(0, x0, y0); 
		
		gl.glEnd() ; 
		gl.glPopMatrix() ;
	}

	/**
	 * Dessine un portion d'une cloison
	 * @param gl l'objet graphique permettant de dessiner
	 * @param x0 l'abcisse du point en bas à gauche
	 * @param y0 l'ordonnee du point en bas à gauche
	 * @param x1 l'abcisse du point en haut à droite
	 * @param y1 l'ordonnee du point en haut à droite
	 * @param nom le nom de la salle destination à afficher
	 */
	public void dessinerPortion(GL2 gl, float x0, float y0, float x1, float y1, String nom){ // TODO nom des salles
		//			*-----------* (x1,y1)
		//				|			|
		//				|			|
		//				|			|
		//				|			|
		//(x0,y0)	    *-----------*
		
	
		gl.glEnable(GL2.GL_TEXTURE_2D); 
		gl.glTexEnvi( GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);
		
		gl.glPushMatrix();	
		gl.glBegin(GL2.GL_QUADS); 
		
		// Haut Gauche
		gl.glTexCoord2f(0, 1); gl.glVertex3f(0, x0, y1); 
		
		// Haut Droite
		gl.glTexCoord2f(1, 1); gl.glVertex3f(0, x1, y1); 
		
		// Bas Droite
		gl.glTexCoord2f(1, 0); gl.glVertex3f(0, x1, y0); 
		
		// Bas Gauche
		gl.glTexCoord2f(0, 0); gl.glVertex3f(0, x0, y0); 
		
		
		gl.glEnd() ; 
		
		gl.glPopMatrix() ;
	}
	
	
	/**
	 * Renvoie la largeur de la cloison	
	 * @return la largeur de la cloison
	 */
	public float getLargeur(){
		return largeur;
	}

	/**
	 * Renvoie la hauteur de la cloison
	 * @return la hauteur de la cloison
	 */
	public float getHauteur() {
		return hauteur;
	}

	/**
	 * Renvoie l'epaisseur de la cloison
	 * @return l'epaisseur de la cloison
	 */
	public float getEpaisseur() {
		return epaisseur;
	}
	
	
}