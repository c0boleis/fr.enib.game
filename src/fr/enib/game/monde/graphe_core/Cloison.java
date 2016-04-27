package fr.enib.game.monde.graphe_core;

import java.util.Collections;
import java.util.Comparator;

import com.jogamp.opengl.GL2;

import fr.enib.game.monde.object.Mur;
import fr.enib.game.monde.object.TrouPorte;

/**
 * Classe servant a dessiner la cloison dans un espace 3D
 */
public class Cloison extends PrimGeo {

	private float hauteur ;
	private float largeur ;  

	private Mur mur;

	/**
	 * Construteur
	 * @param hauteur la hauteur de la cloison
	 * @param largeur_init la largeur de la cloison
	 */
	public Cloison(float hauteur, float largeur_init){
		super() ; 
		this.hauteur = hauteur  ; 
		this.largeur = largeur_init  ;
	}

	/**
	 * Construteur
	 * @param haut la hauteur de la cloison
	 * @param larg la largeur de la cloison
	 * @param c l'objet Cloison du package simu en relation cette cloison qui va etre dessine
	 */
	public Cloison(float haut, float larg,Mur m){
		super() ; 
		this.hauteur = haut  ; 
		this.largeur = larg  ;
		this.mur = m;
	}

	/**
	 * Dessine une cloison entiere
	 */
	public void dessiner(GL2 gl){
		
		// Si pas de trou dans la cloison, alors on dessine un rectangle (largeur * hauteur)
		if(mur.getTrous().isEmpty()){
			dessinerPortion(gl, 0.0f, largeur, 0.0f,null);
		}
		else{ // Si la cloison comporte des trous
			float posPortion = -1.0f, largeurPortion = -1.0f;
			float posTrou = -1.0f;
			float posExtremite = this.largeur/2.0f;
			float posOld = -1.0f;
			float largeurTrou = -1.0f;

			// Mettre la liste des trous dans l'ordre decroissant des position des trous
			//Collections.sort(cloison.getTrous(), Collections.reverseOrder()); 

			Collections.sort(mur.getTrous(), new Comparator<TrouPorte>() {
				public int compare(TrouPorte p1, TrouPorte p2){
					float res = p1.getPostionPourCloison() - p2.getPostionPourCloison();
					
					if(res > 0) return -1;
					else if(res < 0) return 1;
					return 0;
				}
			});
			
			posOld = posExtremite;
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
			dessinerPortion(gl, posPortion, largeurPortion, 0.0f,null);
		}
	}

	/**
	 * Dessine un portion d'une cloison
	 * @param gl l'objet graphique permettant de dessiner
	 * @param positionCentre la position centrale de la portion (par rapport à la largeur)
	 * @param largeur la largeur de la portion a dessiner
	 * @param z0 la hauteur de la portion a dessiner
	 * @param nom le nom de la portion
	 */
	public void dessinerPortion(GL2 gl,float positionCentre, float largeur, float z0, String nom){
		// 				*-----------* (xhaut,yhaut)
		//				|			|
		//				|			|
		//				|			|
		//				|			|
		//(xbas,ybas)	*-----------*

		float xbas = positionCentre - largeur/2.0f;
		float ybas = z0;

		float xhaut = positionCentre + largeur/2.0f;
		float yhaut = this.hauteur;

		//float coefTexture = 0.5f;


		gl.glEnable(GL2.GL_TEXTURE_2D) ; 
		gl.glTexEnvi( GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);

		gl.glPushMatrix() ;	
		gl.glBegin(GL2.GL_QUADS) ; 
		// Top left
		gl.glTexCoord2f(0, 1); gl.glVertex3f(0, xbas, yhaut); 

		// Top right
		gl.glTexCoord2f(1, 1); gl.glVertex3f(0, xhaut, yhaut); 

		// Bottom right
		gl.glTexCoord2f(1, 0); gl.glVertex3f(0, xhaut, ybas); 

		// Bottom left
		gl.glTexCoord2f(0, 0); gl.glVertex3f(0, xbas, ybas); 
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
}
