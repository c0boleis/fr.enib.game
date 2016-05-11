package fr.enib.game.monde.graphic_core;

import java.awt.Font;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

/**
 * 
 * @author Ronan MOREL
 *
 */
public class GPanneauAffichage extends Forme {
	float hauteur ;
	float largeur ;  
	float epaisseur ;

	String text;
	/**
	 * Constructeur
	 * @param hauteur la hauteur du tableau
	 * @param largeur la largeur du tableau
	 */
	public GPanneauAffichage(float hauteur, float largeur, String text){
		this(hauteur, largeur, 0.05f, text);
	}
	
	/**
	 * Constructeur
	 * @param hauteur la hauteur du tableau
	 * @param largeur la largeur du tableau
	 * @param epaisseur l'epaisseur du tableau
	 */
	public GPanneauAffichage(float hauteur, float largeur, float epaisseur, String text){
		super() ; 
		this.hauteur = hauteur; 
		this.largeur = largeur;
		this.epaisseur = epaisseur; 
		this.text = text;
	}

	/**
	 * Dessine le tableau dans un espace 3D
	 */
	@Override
	public void dessiner(GL2 gl){
		gl.glPushMatrix() ;
		gl.glScalef(epaisseur, largeur, hauteur) ; 			
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

		TextRenderer t = new TextRenderer(new Font("SansSerif", Font.BOLD, 12));
		t.begin3DRendering();
		t.draw("Coucou", 100, 100);
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

	public float getHauteur() {
		return hauteur;
	}

	public float getLargeur() {
		return largeur;
	}

	public float getEpaisseur() {
		return epaisseur;
	}
}
