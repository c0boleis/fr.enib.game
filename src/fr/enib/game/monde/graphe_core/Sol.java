package fr.enib.game.monde.graphe_core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.jogamp.opengl.*;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

/**
 * Classe dessinant le sol dans un espace 3D
 */
public class Sol implements Noeud {

	private static Logger LOGGER = Logger.getLogger(Sol.class);

	private Texture texture ; 
	
	private float largeur ; 
	
	private float profondeur ; 
	
	private float demiLargeur ; 
	
	private float demiProfondeur ;

	private GL2 gl;
	
	/**
	 * Constructeur
	 * @param largeur la largeur du sol
	 * @param profondeur la largeur du sol
	 */
	public Sol(float largeur, float profondeur){
		super() ;
		this.largeur    = largeur ;
		this.profondeur = profondeur ;
		demiLargeur = largeur / 2.0f ; 
		demiProfondeur = profondeur / 2.0f ; 
		init("data"+File.separator+"textures"+File.separator+"moquette.jpg") ;  
	}

	/**
	 * Constructeur
	 * @param largeur la largeur du sol
	 * @param profondeur la profondeur du sol
	 */
	public Sol(double largeur, double profondeur){
		this((float)largeur,(float)profondeur);
	}

	/**
	 * Constructeur
	 * @param nom le nom applique au sol (exemple : sol_salle1)
	 * @param largeur la largeur du sol
	 * @param profondeur la profondeur du sol
	 */
	public Sol(String nom,float largeur, float profondeur){
		super() ; 
		this.largeur    = largeur ;
		this.profondeur = profondeur ;
		demiLargeur = largeur / 2.0f ; 
		demiProfondeur = profondeur / 2.0f ; 
		init(nom) ;  
	}

	/**
	 * Initialise le sol
	 * @param pathTexture le chemin d'acces ou se trouve la texture a appliquer
	 */
	public void init(String pathTexture){
		gl = GLContext.getCurrentGL().getGL2() ; 
		setTexture(pathTexture);
		gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE); // GL_MODULATE is default; or try GL_REPLACE, GL_DECAL, GL_BLEND

	}

	/**
	 * Applique une texture au sol
	 * @param pathTexture le chemin d'acces ou se trouve la texture a appliquer
	 */
	public void setTexture(String pathTexture){
		try {
			File infile = new File(pathTexture);
			BufferedImage image = ImageIO.read(infile);

			if(image == null)
				LOGGER.info("Erreur, texture pour le sol non trouvé") ; 

			ImageUtil.flipImageVertically(image);
			texture = TextureIO.newTexture(AWTTextureIO.newTextureData(gl.getGLProfile(), image, false));
			texture.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT); // GL_REPEAT or GL_CLAMP
			texture.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
			texture.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR); 
			// GL_LINEAR or GL_NEARESTor one of the mipmap ones
			texture.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
		} catch(IOException e){
			LOGGER.error(e);
		}
		
	}
	
	/**
	 * Dessine le sol dans un espace 3D
	 */
	@Override
	public void dessiner(GL2 gl){

		gl.glEnable(GL2.GL_TEXTURE_2D) ; 
		gl.glTexEnvi( GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);
		texture.bind(gl) ; 
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