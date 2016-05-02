package fr.enib.game.monde.graphic_core;

import com.jogamp.opengl.GL2;

public class GPlafond extends Forme {
	private float largeur ;
	private float profondeur ;  
	private float l_2 ; 
	private float p_2 ;
	
	/**
	 * Constructeur
	 * @param largeur la largeur du plafond
	 * @param profondeur la largeur du plafond
	 */
	public GPlafond(float largeur, float profondeur){
		super() ; 
		this.largeur = largeur;
		this.profondeur = profondeur;
		this.l_2 = largeur / 2.0f ; 
		this.p_2 = profondeur / 2.0f ; 
	}


	/**
	 * Dessine le plafond dans un espace 3D
	 */
	@Override
	public void dessiner(GL2 gl) {
		gl.glEnable(GL2.GL_TEXTURE_2D) ; 
		gl.glTexEnvi( GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);
		//texture.bind(gl) ; 
		gl.glBegin(GL2.GL_QUADS);
		gl.glNormal3f(0.0f,0.0f,-1.0f) ; 

		gl.glTexCoord2f(0.0f,0.0f) ; //gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glVertex2f(-p_2,-l_2);

		gl.glTexCoord2f(profondeur,0.0f) ; //gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glVertex2f(-p_2, l_2);

		gl.glTexCoord2f(profondeur,largeur) ;//gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex2f(p_2, l_2);

		gl.glTexCoord2f(0.0f,largeur) ;//gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex2f(p_2,-l_2);
		gl.glEnd();
		gl.glDisable(GL2.GL_TEXTURE_2D) ; 
	}
	
}
