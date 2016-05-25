package fr.enib.game.monde.graphic_core;

import com.jogamp.opengl.GL2;

/**
 * Gestion des differents formes (tableau, mur, etc.)
 * @author Ronan MOREL
 *
 */
public class Shape implements IShape {

	  private Forme  forme  = null ; 
	  
	  private Materiau aspect = null ;
	  
	  /**
	   * Construteur
	   * @param geo .
	   * @param aspect .
	   */
	  public Shape(Forme geo, Materiau aspect){
	    this.forme = geo ; 
	    this.aspect = aspect ;
	  }
	  
	/**
	 * Dessine une forme dans un environnement 3D
	 * @param gl l'objet permettant de dessiner dans l'environnement 3D
	 */
	  @Override
	  public void dessiner(GL2 gl){

	    	if(aspect != null){
	      		aspect.apply(gl) ; 
	    	}
	    
	    	if(forme != null){  
	      		forme.dessiner(gl) ;
	    	}
	  }
	  
	  /**
	   * Revoie la "forme" relie a cette classe
	   * @return {@link #forme}
	   */
	  public Forme getForme(){
		  return forme;
	  }
}
