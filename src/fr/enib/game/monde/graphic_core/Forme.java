package fr.enib.game.monde.graphic_core;

import com.jogamp.opengl.GL2;

/**
 * 
 * @author Ronan MOREL
 *
 */
public abstract class Forme {
	
	/**
	 * Dessine une forme
	 * @param gl l'objet permettant de dessiner dans l'environnement 3D
	 */
	public abstract void dessiner(GL2 gl);
}
