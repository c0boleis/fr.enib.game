package fr.enib.game.monde.graphic_core;

import com.jogamp.opengl.GL2;

public class GSol extends Forme {
	
	private float largeur ; 
	
	private float profondeur ; 
	
	private float demiLargeur ; 
	
	private float demiProfondeur ;
	
	/**
	 * Constructeur
	 * @param largeur la largeur du sol
	 * @param profondeur la largeur du sol
	 */
	public GSol(float largeur, float profondeur){
		super();
		this.largeur    = largeur ;
		this.profondeur = profondeur ;
		this.demiLargeur = largeur / 2.0f ; 
		this.demiProfondeur = profondeur / 2.0f ; 
	}
	
	/**
	 * Dessine le sol dans un espace 3D
	 */
	@Override
	public void dessiner(GL2 gl){
		gl.glEnable(GL2.GL_TEXTURE_2D) ; 
		gl.glTexEnvi( GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);
		//texture.bind(gl) ; 
		
		gl.glBegin(GL2.GL_QUADS);
		gl.glNormal3f(0.0f,0.0f,1.0f) ; 

		gl.glTexCoord2f(0.0f,0.0f) ; //gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glVertex2f(-demiProfondeur,-demiLargeur);

		gl.glTexCoord2f(profondeur,0.0f) ; //gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glVertex2f(demiProfondeur, -demiLargeur);

		gl.glTexCoord2f(profondeur,largeur) ;//gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex2f(demiProfondeur, demiLargeur);

		gl.glTexCoord2f(0.0f,largeur) ;//gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex2f(-demiProfondeur,demiLargeur);
		gl.glEnd();
		gl.glDisable(GL2.GL_TEXTURE_2D) ; 
	}
}
