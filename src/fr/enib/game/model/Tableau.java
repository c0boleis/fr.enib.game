/**
 * 
 */
package fr.enib.game.model;

import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.ITableau;

/**
 * @author Corentin Boleis
 *
 */
public class Tableau implements ITableau {
	
	private String id;

	/**
	 * 
	 */
	protected Tableau() {
		this.id = NOM_PAR_DEFAULT;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#getLiensEntrant()
	 */
	@Override
	public ILien[] getLiensEntrant() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#getLiensSortant()
	 */
	@Override
	public ILien[] getLiensSortant() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#estFeuille()
	 */
	@Override
	public boolean estFeuille() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObject#getId()
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IVisitableObject#visiter()
	 */
	@Override
	public void visiter() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IVisitableObject#resetVisiter()
	 */
	@Override
	public void resetVisiter() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IVisitableObject#estVisiter()
	 */
	@Override
	public boolean estVisiter() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ITableau#getDescription()
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ITableau#getUrlImage()
	 */
	@Override
	public String getUrlImage() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ITableau#getNomTableau()
	 */
	@Override
	public String getNomTableau() {
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
	public Tableau clone(){
		return new Tableau();
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObject#getDegre()
	 */
	@Override
	public float getDegre() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObject#setDegre(float)
	 */
	@Override
	public void setDegre(float newPoid) {
		// TODO Auto-generated method stub
		
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