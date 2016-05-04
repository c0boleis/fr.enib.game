/**
 * 
 */
package fr.enib.game.model;

import fr.enib.game.model.interfaces.ITableau;

/**
 * @author Corentin Boleis
 *
 */
public class Tableau extends Noeud implements ITableau{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7748082203188285353L;
	
	/**
	 * le nom par default des tableaux
	 */
	public static final String NOM_PAR_DEFAULT = "Tableau";
	
	private String description = null;
	
	private String url = null;
	
	private String nom = null;

	/**
	 * 
	 */
	public Tableau() {
		this.setId(NOM_PAR_DEFAULT);
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ITableau#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ITableau#setDescription(java.lang.String)
	 */
	@Override
	public boolean setDescription(String description) {
		if(description==null)return false;
		this.description = description;
		return true;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ITableau#getUrlImage()
	 */
	@Override
	public String getUrlImage() {
		return this.url;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ITableau#setUrlImage(java.lang.String)
	 */
	@Override
	public boolean setUrlImage(String url) {
		if(url==null)return false;
		this.url = url;
		return true;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ITableau#getNomTableau()
	 */
	@Override
	public String getNomTableau() {
		return this.nom;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ITableau#setNomTableau(java.lang.String)
	 */
	@Override
	public boolean setNomTableau(String nom) {
		if(nom==null)return false;
		this.nom = nom;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Tableau clone(){
		Tableau newTableau = new Tableau();
		newTableau.setId(Model.get().getNextId(this.getId()));
		newTableau.description = description;
		newTableau.url = url;
		newTableau.nom = nom;
		if(Model.get().ajouterModelObject(newTableau)){
			return newTableau;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ITableau#getLargeurTableau()
	 */
	@Override
	public float getLargeurTableau() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ITableau#getHauteurTableau()
	 */
	@Override
	public float getHauteurTableau() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
