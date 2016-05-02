package fr.enib.game.monde.graphic_core;

import com.jogamp.opengl.GL2;

public class Shape implements Noeud {

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
	   * Dessine une forme dans un espace 3D
	   */
	  @Override
	  public void dessiner(GL2 gl){

	    	if(aspect != null){
	      		aspect.appliques_toi(gl) ; 
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
