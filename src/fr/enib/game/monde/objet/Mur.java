package fr.enib.game.monde.objet;

import java.util.ArrayList;
import java.util.List;

import fr.enib.game.monde.graphic_core.GMur;
import fr.enib.game.monde.graphic_core.Forme;
import fr.enib.game.monde.graphic_core.Shape;
import fr.enib.game.monde.graphic_core.TrouMur;

public class Mur extends Objet {
	
	private List<TrouMur> trous;
	
	/**
	 * Constructeur
	 * @param id l'identifiant de la cloison
	 * @param nomTexture le chemin de la texture a appliquer
	 * @param haut la hauteur de la cloison
 	 * @param large la largeur de la cloison
	 */
	public Mur(String id, String pathTexture, float hauteur, float largeur, float epaisseur){
		super(id, pathTexture) ; 
		this.trous = new ArrayList<TrouMur>();
		Forme geo = new GMur(hauteur, largeur, epaisseur, trous); 
		this.forme = new Shape(geo, mat); 
	}
	
	/**
	 * Renvoie la objet Cloison sur package visu.cloison permettant de dessiner une cloison
	 * @return la cloison dessiner une cloison
	 */
	public GMur getCloison(){
		return (GMur) ((Shape) forme).getForme();
	}

	/**
	 * Ajout d'un trou a la cloison
	 * @param trou le trou a ajouter a la cloison
	 * @return true si le trou est bien ajoute, sinon false
	 */
	public boolean addTrou(TrouMur trou){
		return trous.add(trou);
	}

	/**
	 * Renvoie la liste des trous
	 * @return la liste des trous
	 */
	public List<TrouMur> getTrous() {
		List<TrouMur> liste = new ArrayList<TrouMur>();
		liste.addAll(trous);
		return liste;
	}
	
	/**
	 * Supprimer les trous de la cloison
	 */
	public void deleteTrous(){
		trous.clear();
	}
}