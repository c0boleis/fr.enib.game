package fr.enib.game.monde.graphic_core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

/**
 * Gestion des textures
 * @author Ronan MOREL
 *
 */
public class Materiau {

	private static Logger LOGGER = Logger.getLogger(Materiau.class);
	
	private static HashMap<String, BufferedImage> imgTextures = new HashMap<>();

	/**
	 * coeficient de GL_AMBIENT
	 */
	public float [] ka ;

	/**
	 * coeficient de GL_DIFFUSE
	 */
	public float [] kd ;

	/**
	 * coeficient de GL_SPECULAR
	 */
	public float [] ks ; 

	/**
	 * coeficient de GL_SHININESS
	 */
	public float ns = 0.0f ;
	public float d  = 1.0f ;

	public Texture texture = null ; 

	private GL2 gl;
	
	/**
	 * Construteur par defaut
	 */
	public Materiau(){
		this(null);
	}

	/**
	 * Construteur
	 * @param pathTexture
	 */
	public Materiau(String pathTexture){
		ka = new float[4] ; 
		kd = new float[4] ; 
		ks = new float[4] ; 
		
		ka[3] = 1.0f ;
		kd[3] = 1.0f ;
		ks[3] = 1.0f ;
		init(pathTexture) ;
	}

	/**
	 * Initialise le plafond
	 * @param pathTexture le chemin d'acces ou se trouve la texture a appliquer
	 */
	public void init(String pathTexture){
		gl = GLContext.getCurrentGL().getGL2() ; 
		setTexture(pathTexture);
		gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE); // GL_MODULATE is default; or try GL_REPLACE, GL_DECAL, GL_BLEND
	}

	/**
	 * Applique une texture au materiel
	 * @param pathTexture le chemin d'acces ou se trouve la texture a appliquer
	 */
	public void setTexture(String pathTexture){
		BufferedImage img = null;
		
		img = imgTextures.get(pathTexture);
		if(img == null){
			try {
				File infile = new File(pathTexture);
				img = ImageIO.read(infile);
				
				if(img == null){
					LOGGER.info("Erreur, texture pour le materiel non trouvé") ; 
				}
				ImageUtil.flipImageVertically(img);
				imgTextures.put(pathTexture, img);
			} catch(IOException e){
				LOGGER.error(e);
			}
		}
		texture = TextureIO.newTexture(AWTTextureIO.newTextureData(gl.getGLProfile(), img, false));
		texture.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT); // GL_REPEAT or GL_CLAMP
		texture.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
		texture.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR); // GL_LINEAR or GL_NEARESTor one of the mipmap ones
		texture.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
	}
	
	/**
	 * Definit le coefficient
	 * @param r
	 * @param v
	 * @param b
	 */
	public void setKd(float r, float v, float b){
		kd[0] = r ; 
		kd[1] = v ;
		kd[2] = b ;
	}

	/**
	 * Applique la texture à l'objet graphique associé
	 * @param gl L'objet graphique dessinant dans un espace 3D
	 */
	public void apply(GL2 gl){

		if(texture != null){
			gl.glEnable(GL2.GL_TEXTURE_2D) ; 

			gl.glTexEnvi( GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);
			texture.bind(gl);
		}

		if (ka != null) { // ambient color
			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, ka, 0);
		}

		if (kd != null) { // diffuse color
			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, kd, 0);
		}

		if (ks != null) { // specular color
			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, ks, 0);
		}

		if (Float.compare(ns, 0.0f)!=0) { // shininess
			gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, ns);
		}

		if (Float.compare(d, 1.0f)!=0) { // alpha
			// not implemented
		}
	}
}
