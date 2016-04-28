/**
 * 
 */
package fr.enib.game.model;

import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.INoeud;

/**
 * @author Corentin Boleis
 *
 */
public class Lien implements ILien {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6374209663292757875L;
	
	private String id;
	
	private Noeud noeudArrivee;
	
	private Noeud noeudDepart;
	
	private float poids;
	
	/**
	 * @param noeudA
	 * @param noeudD
	 */
	public Lien(Noeud noeudA,Noeud noeudD){
		
		this.noeudArrivee = noeudA;
		this.noeudDepart = noeudD;
	}

	/**
	 * 
	 */
	public Lien() {
		this.id = NOM_PAR_DEFAULT;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObject#getId()
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ILien#getNoeudDepart()
	 */
	@Override
	public INoeud getNoeudDepart() {
		return this.noeudDepart;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ILien#getNoeudArrivee()
	 */
	@Override
	public INoeud getNoeudArrivee() {
		return this.noeudArrivee;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return getId();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Lien clone(){
		return new Lien();
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObject#modifierId(java.lang.String)
	 */
	@Override
	public boolean setId(String id) {
		if(id==null)return false;
		if(Model.get().containsModeObject(id))return false;
		this.id = id;
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IObjectPondere#setPoids(float)
	 */
	@Override
	public boolean setPoids(float poids) {
		if(poids<0)return false;
		this.poids = poids;
		return true;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IObjectPondere#getPoids()
	 */
	@Override
	public float getPoids() {
		return this.poids;
	}

}
