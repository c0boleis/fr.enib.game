package fr.enib.game.monde.graphe_core;

import com.jogamp.opengl.GL2;


/**
 * Classe dessinant un tableau dans un espace 3D
 */
public class Tableau extends PrimGeo {

	float hauteur ;
	float largeur ;  
	float epaisseur ;

	/**
	 * Constructeur
	 * @param hauteur la hauteur du tableau
	 * @param largeur la largeur du tableau
	 */
	public Tableau(float hauteur, float largeur){
		super() ; 
		this.hauteur = hauteur  ; 
		this.largeur = largeur  ;
		this.epaisseur = 0.01f ; 
	}

	/**
	 * Dessine le tableau dans un espace 3D
	 */
	@Override
	public void dessiner(GL2 gl){

		gl.glPushMatrix() ;
		gl.glScalef( epaisseur, largeur, hauteur) ; 			
		gl.glBegin(GL2.GL_QUADS) ; 
		gl.glNormal3f(1.0f,0.0f,0.0f) ; 
		gl.glTexCoord2f(0.0f,0.0f) ; gl.glVertex3f(1.0f,-0.5f,-0.5f) ; 
		gl.glTexCoord2f(1.0f,0.0f) ; gl.glVertex3f(1.0f, 0.5f,-0.5f) ; 
		gl.glTexCoord2f(1.0f,1.0f) ; gl.glVertex3f(1.0f, 0.5f, 0.5f) ; 
		gl.glTexCoord2f(0.0f,1.0f) ; gl.glVertex3f(1.0f,-0.5f, 0.5f) ; 
		gl.glEnd() ; 
		gl.glPopMatrix() ;

	
		gl.glDisable(GL2.GL_LIGHTING) ; 
		gl.glDisable(GL2.GL_TEXTURE_2D) ; 

		gl.glColor3f(0.4f,0.8f,0.8f) ; 
		gl.glPushMatrix() ;
		gl.glScalef(1.0f, largeur, hauteur) ; 			
		gl.glBegin(GL2.GL_QUADS) ; 
		gl.glNormal3f(1.0f,0.0f,0.0f) ; 
		gl.glVertex3f(0.0f,  0.5f,-0.5f) ; 
		gl.glVertex3f(0.0f, -0.5f,-0.5f) ; 
		gl.glVertex3f(0.0f, -0.5f, 0.5f) ; 
		gl.glVertex3f(0.0f,  0.5f, 0.5f) ; 
		gl.glEnd() ; 
		gl.glPopMatrix() ;  

		gl.glEnable(GL2.GL_LIGHTING) ; 
		gl.glEnable(GL2.GL_TEXTURE_2D) ; 
	}
}
