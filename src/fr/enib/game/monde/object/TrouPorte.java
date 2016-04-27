package fr.enib.game.monde.object;

import fr.enib.game.monde.core.Vec3;

public class TrouPorte extends Objet{
	
	private float largeur ;
	
	private float hauteur ;
	
	private float postionPourCloison ;
	
	private Mur cloison ;
	
	private String nomPorte ;

	/**
	 * Constructeur
	 * @param id l'identifiant du trou
	 * @param position la position du trou sur la cloison
	 */
	public TrouPorte(String id,float position) {
		super(id);
		postionPourCloison = position;
		this.largeur = 1.0f;
		this.hauteur = 3.5f;
		nomPorte = null;
	}
	
	/**
	 * Constructeur
	 * @param id l'identifiant du trou
	 * @param position la position du trou sur la cloison
	 * @param t la largeur du trou
	 * @param h la hauteur du trou
	 */
	public TrouPorte(String id,float position,float t,float h) {
		super(id);
		this.postionPourCloison = position;
		this.largeur = t;
		this.hauteur = h;
		this.nomPorte = null;
	}
	
	/**
	 * Constructeur
	 * @param id l'identifiant du trou
	 * @param position la position du trou sur la cloison
	 * @param t la largeur du trou
	 * @param h la hauteur du trou
	 * @param nomNoeud ???
	 */
	public TrouPorte(String id,float position,float t,float h,String nomNoeud) {
		super(id);
		this.postionPourCloison = position;
		this.largeur = t;
		this.hauteur = h;
		this.setNomPorte(nomNoeud);
	}

	/**
	 * Renvoie la largeur du largeur
	 * @return {@link #largeur}
	 */
	public float getLargeur() {
		return largeur;
	}

	/**
	 * Change la largeur du trou
	 * @param t la nouvelle largeur du trou
	 */
	public void setLargeur(float t) {
		this.largeur = t;
	}

	/**
	 * Renvoie la hauteur du trou
	 * @return {@link #hauteur}
	 */
	public float getHauteur() {
		return hauteur;
	}

	/**
	 * Change la hauteur du trou
	 * @param h la nouvelle hauteur du trou
	 */
	public void setHauteur(float h) {
		this.hauteur = h;
	}

	/**
	 * Renvoie la cloison associe au trou
	 * @return la cloison associe au trou
	 */
	public Mur getCloison() {
		return cloison;
	}

	/**
	 * Change la cloison ou est applique le trou (Utile ?)
	 * @param c la nouvelle cloison ou se trouve le trou
	 */
	public void setCloison(Mur c) {
		this.cloison = c;
		float d = (this.cloison.getCloison().getLargeur()/2.0f)-postionPourCloison;
		float dy = (float) Math.sin(c.repere.getCapHorizontal())*(d);
		float dx = (float) Math.cos(c.repere.getCapHorizontal())*(d);
		this.repere.getPostiton().copier(
				c.repere.getDirection().x+dx,
				c.repere.getDirection().y+dy,
				c.repere.getDirection().z);
	}
	
	/**
	 * Renvoie la position du trou sur la cloison
	 * @return la position du trou sur la cloison
	 */
	public float getPostionPourCloison(){
		return postionPourCloison;
	}
	
	/**
	 * Renvoie la position dans un 
	 * @return la postion des deux coins du bas du trous
	 */
	public Vec3 getPostion(){
		return this.cloison.getRepere().getPostiton();
	}

	/**
	 * Renvoie l'identifiant de la porte (???)
	 * @return {@link #nomPorte}
	 */
	public String getNomPorte() {
		return nomPorte;
	}

	/**
	 * Change l'identifiant de la porte (???)
	 * @param nomPorte le nouvelle identifiant
	 */
	public void setNomPorte(String nomPorte) {
		this.nomPorte = nomPorte;
	}
}
