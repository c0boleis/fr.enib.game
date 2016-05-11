package fr.enib.game.monde.objet;

import fr.enib.game.monde.geo.Vec3;
import fr.enib.game.monde.graphic_core.Forme;
import fr.enib.game.monde.graphic_core.GTableau;
import fr.enib.game.monde.graphic_core.Shape;

/**
 * 
 * @author Ronan MOREL
 *
 */
public class Tableau extends Objet {
	
	private float position;
	
	private Vec3 positionInRepere;
	
	public Tableau(String id, String img) {
		this(id, img, 1.5f, 1.0f);
	}
	
	public Tableau(String id, String img, float largeur, float hauteur) {
		super(id, img);

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

	public float getPosition() {
		return position;
	}

	public void setPosition(float position) {
		this.position = position;
	}

	public Vec3 getPositionInRepere() {
		return positionInRepere;
	}

	public void setPositionInRepere(Vec3 positionInRepere) {
		this.positionInRepere = positionInRepere;
	}
	
}
