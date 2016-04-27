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
	
	private String id;

	/**
	 * 
	 */
	protected Lien() {
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
	 * @see fr.enib.game.model.interfaces.IModelObject#getPoid()
	 */
	@Override
	public float getDegre() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObject#setPoid(float)
	 */
	@Override
	public void setDegre(float newDegre) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ILien#getNoeudDepart()
	 */
	@Override
	public INoeud getNoeudDepart() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ILien#getNoeudArrivee()
	 */
	@Override
	public INoeud getNoeudArrivee() {
		// TODO Auto-generated method stub
		return null;
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
	public boolean modifierId(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}
