package fr.enib.game.monde.graphic_core;

import com.jogamp.opengl.GL2;

/**
 * 
 * @author Ronan MOREL
 *
 */
public interface IShape {
	
	/**
	 * Dessine une forme dans un environnement 3D
	 * @param gl l'objet permettant de dessiner dans l'environnement 3D
	 */
	public void dessiner(GL2 gl); 
}
