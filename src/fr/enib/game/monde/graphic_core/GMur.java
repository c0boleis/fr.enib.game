package fr.enib.game.monde.graphic_core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jogamp.opengl.GL2;

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
		this.trousMur = new ArrayList<>();
		this.trousMur.addAll(trousMur);
	}

	/**
	 * Dessine une cloison entiere
	 */
	@Override
	public void dessiner(GL2 gl){
		
		// Si pas de trou dans la cloison, alors on dessine un rectangle (largeur * hauteur)
		if(trousMur == null || trousMur.isEmpty()){
			//dessinerPortion(gl, 0.0f, 0.0f, largeur, hauteur);
			dessinerPortionMur(gl, 0.0f, 0.0f, largeur, hauteur);
		}
		else{ // Si la cloison comporte des trous
			/*float posPortion = -1.0f, largeurPortion = -1.0f;
			float posTrou = -1.0f;
			float posExtremite = this.largeur/2.0f;
			float posOld = -1.0f;
			float largeurTrou = -1.0f;*/

			// Mettre la liste des trous dans l'ordre decroissant des position des trous
			//Collections.sort(cloison.getTrous(), Collections.reverseOrder()); 

			Collections.sort(trousMur, new Comparator<TrouMur>() {
				public int compare(TrouMur p1, TrouMur p2){
					float res = p1.getPosition() - p2.getPosition();
					
					if(res > 0) return -1;
					else if(res < 0) return 1;
					return 0;
				}
			});
			
			/*posOld = posExtremite;
			// Pour chaque trous
			for(TrouPorte t : mur.getTrous()){
				posTrou = t.getPostionPourCloison(); // On inverse le repere des Y
				largeurTrou = t.getLargeur();

				if(posTrou > 0){
					posPortion = (posOld + (posTrou + largeurTrou/2.0f)) / 2.0f; // Milieu = (X1 + X2) / 2.0
					largeurPortion = posOld - (posTrou + largeurTrou/2.0f);
				}
				else if(posTrou < 0){
					if(posOld >= 0){
						posPortion = ((posTrou + largeurTrou/2.0f) + posOld) / 2.0f; // Milieu = (X1 + X2) / 2.0
						largeurPortion = Math.abs((posTrou + largeurTrou/2.0f) - posOld);
					}
					else{
						posPortion = ((posTrou + largeurTrou/2.0f) - posOld) / 2.0f; // Milieu = (X1 + X2) / 2.0
						largeurPortion = Math.abs((posTrou + largeurTrou/2.0f) + posOld);
					}
				}
				else if(posTrou == 0){
					posPortion = (posOld + largeurTrou/2.0f) / 2.0f; // Milieu = (X1 + X2) / 2.0
					largeurPortion = posOld - largeurTrou/2.0f;
				}

				// On dessine la portion a gauche du trou
				dessinerPortion(gl, posPortion, largeurPortion, 0.0f,t.getNomPorte());

				// On dessine la portion au dessus du trou
				dessinerPortion(gl, posTrou, largeurTrou, t.getHauteur(),null);

				posOld = posTrou - largeurTrou/2.0f;
			}

			if(posOld >= 0){
				posPortion = (-posExtremite + posOld) / 2.0f; // Milieu = (X1 + X2) / 2.0
				largeurPortion = Math.abs(-posExtremite - posOld);
			}
			else{
				posPortion = (-posExtremite + posOld) / 2.0f; // Milieu = (X1 + X2) / 2.0
				largeurPortion = Math.abs(-posExtremite - posOld);
			}

			//on dessine la portion a droite du dernier trou
			dessinerPortion(gl, posPortion, largeurPortion, 0.0f,null);*/
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
	
	public void dessinerPortionMur(GL2 gl, float x0, float y0, float x1, float y1){
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
		
		
		//Face visible
		
		// Haut Gauche
		gl.glTexCoord2f(0, 1); gl.glVertex3f(0, x0, y1); 

		// Haut Droite
		gl.glTexCoord2f(1, 1); gl.glVertex3f(0, x1, y1); 

		// Bas Droite
		gl.glTexCoord2f(1, 0); gl.glVertex3f(0, x1, y0); 

		// Bas Gauche
		gl.glTexCoord2f(0, 0); gl.glVertex3f(0, x0, y0); 

		

		//Face oposée
		// Haut Gauche
		gl.glTexCoord2f(0, 1); gl.glVertex3f(epaisseur, x1, y1); 
		
		// Haut Droite
		gl.glTexCoord2f(1, 1); gl.glVertex3f(epaisseur, x0, y1); 

		// Bas Droite
		gl.glTexCoord2f(1, 0); gl.glVertex3f(epaisseur, x0, y0); 

		// Bas Gauche
		gl.glTexCoord2f(0, 0); gl.glVertex3f(epaisseur, x1, y0); 
		
		
		//Cote gauche
		// Haut Gauche
		gl.glTexCoord2f(0, 1); gl.glVertex3f(epaisseur, x0, y1); 
		
		// Haut Droite
		gl.glTexCoord2f(1, 1); gl.glVertex3f(0, x0, y1); 

		// Bas Droite
		gl.glTexCoord2f(1, 0); gl.glVertex3f(0, x0, y0); 

		// Bas Gauche
		gl.glTexCoord2f(0, 0); gl.glVertex3f(epaisseur, x0, y0); 
		
		
		//Cote droit
		// Haut Gauche
		gl.glTexCoord2f(0, 1); gl.glVertex3f(0, x1, y1); 
		
		// Haut Droite
		gl.glTexCoord2f(1, 1); gl.glVertex3f(epaisseur, x1, y1); 

		// Bas Droite
		gl.glTexCoord2f(1, 0); gl.glVertex3f(epaisseur, x1, y0); 

		// Bas Gauche
		gl.glTexCoord2f(0, 0); gl.glVertex3f(0, x1, y0); 
				
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