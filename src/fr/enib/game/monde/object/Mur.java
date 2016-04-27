package fr.enib.game.monde.object;

import java.util.ArrayList;
import java.util.List;

import fr.enib.game.monde.graphe_core.Cloison;
import fr.enib.game.monde.graphe_core.Materiau;
import fr.enib.game.monde.graphe_core.PrimGeo;
import fr.enib.game.monde.graphe_core.Shape;

public class Mur extends Objet {
	
	private List<TrouPorte> trous;

	private Materiau mat;
	
	/**
	 * Constructeur
	 * @param id l'identifiant de la cloison
	 * @param nomTexture le chemin de la texture a appliquer
	 * @param haut la hauteur de la cloison
 	 * @param large la largeur de la cloison
	 */
	public Mur(String id, String pathTexture,float haut,float large){
		super(id) ; 
		this.trous = new ArrayList<TrouPorte>();
		PrimGeo geo = new Cloison(haut, large, this); 
		this.mat = new Materiau(pathTexture) ;  
		this.forme = new Shape(geo,mat) ; 
	}
	
	/**
	 * Applique une texture a la cloison
	 * @param pathTexture le chemin de la texture a appliquer
	 */
	public void setTexture(String pathTexture){
		mat.setTexture(pathTexture);
	}
	
	/**
	 * Renvoie la objet Cloison sur package visu.cloison permettant de dessiner une cloison
	 * @return la cloison dessiner une cloison
	 */
	public Cloison getCloison(){
		return (Cloison) ((Shape)forme).getForme();
	}

	/**
	 * Ajout d'un trou a la cloison
	 * @param trou le trou a ajouter a la cloison
	 * @return true si le trou est bien ajoute, sinon false
	 */
	public boolean ajouterTrou(TrouPorte trou){
		return trous.add(trou);
	}

	/**
	 * Renvoie la liste des trous
	 * @return la liste des trous
	 */
	public List<TrouPorte> getTrous() {
		List<TrouPorte> liste = new ArrayList<TrouPorte>();
		liste.addAll(trous);
		return liste;
	}
	
	/**
	 * Supprimer les trous de la cloison
	 */
	public void suprimerLesTrous(){
		trous.clear();
	}
}