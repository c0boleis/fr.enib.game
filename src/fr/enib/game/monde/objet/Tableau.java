package fr.enib.game.monde.objet;

import fr.enib.game.model.interfaces.ITableau;
import fr.enib.game.monde.geo.Vec3;
import fr.enib.game.monde.graphic_core.Forme;
import fr.enib.game.monde.graphic_core.GTableau;
import fr.enib.game.monde.graphic_core.Shape;

/**
 * Représente un objet Tableau
 * @author Ronan MOREL
 *
 */
public class Tableau extends Objet {
	
	private float position;
	
	private Vec3 positionInRepere;
	
	private ITableau iTableau;
	
	/**
	 * 
	 * @param id l'id du tableau
	 * @param img le chemin de l'image associée
	 * @param iTableau le model associé (pour l'influance sur le graphe)
	 */
	public Tableau(String id, String img, ITableau iTableau) {
		this(id, img, 1.5f, 1.0f,iTableau);
	}
	
	/**
	 * @param id l'id du tableau
	 * @param img le chemin de l'image associée
	 * @param largeur la largeur du tableau
	 * @param hauteur la hauteur du tableau
	 * @param iTableau le model associé (pour l'influance sur le graphe)
	 */
	public Tableau(String id, String img, float largeur, float hauteur,ITableau iTableau) {
		super(id, img);
		this.iTableau = iTableau;
		Forme geo = new GTableau(largeur, hauteur); 
		this.forme = new Shape(geo, mat); 
		this.positionInRepere = new Vec3();
	}
	
	/**
	 * Renvoie la objet Cloison sur package visu.cloison permettant de dessiner une cloison
	 * @return la cloison dessiner une cloison
	 */
	public GTableau getTableau(){
		return (GTableau) ((Shape) forme).getForme();
	}

	/**
	 * Récupère la position du tableau (répère du mur)
	 * @return la position du tableau sur le mur
	 */
	public float getPosition() {
		return position;
	}

	/**
	 * Modifie la position du tableau (répère du mur)
	 * @param position la position du tableau sur le mur
	 */
	public void setPosition(float position) {
		this.position = position;
	}

	/**
	 * Récupère la position du tableau dans l'environnement 3D
	 * @return la position du tableau dans l'environnement 3D
	 */
	public Vec3 getPositionInRepere() {
		return positionInRepere;
	}

	/**
	 * Récupère la position du tableau dans l'environnement 3D
	 * @param positionInRepere la position du tableau dans l'environnement 3D
	 */
	public void setPositionInRepere(Vec3 positionInRepere) {
		this.positionInRepere = positionInRepere;
	}

	/**
	 * @return the iTableau
	 */
	public ITableau getiTableau() {
		return iTableau;
	}
	
}
