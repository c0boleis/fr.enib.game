/**
 * 
 */
package fr.enib.game.model;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import fr.enib.game.editor.graphe.examples.swing.action.ModifierTableauAction;
import fr.enib.game.editor.graphe.model.mxCell;
import fr.enib.game.model.interfaces.ITableau;

/**
 * @author Corentin Boleis
 *
 */
@XStreamAlias("Tableau")
public class Tableau extends Noeud implements ITableau{
	
	private float largeurTableau = 1.0f;
	
	private float longueurTableau = 1.0f;
	
	private float degreeInteret = 1.0f;
	
	/**
	 * 
	 */
	@XStreamOmitField
	private static final long serialVersionUID = 7748082203188285353L;
	
	/**
	 * le nom par default des tableaux
	 */
	@XStreamOmitField
	public static final String NOM_PAR_DEFAULT = "Tableau";
	
	private String description = null;
	
	private String url = null;
	
	private String nom = null;

	/**
	 * 
	 */
	public Tableau() {
		this.setId(NOM_PAR_DEFAULT);
		this.limitLienEntrant = -1;
		this.limitLienSortant = 0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Tableau))return false;
		return ((Tableau)o).getId().equals(getId());
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
	public Tableau cloneObject(Object object){
		Tableau newTableau = new Tableau();
		newTableau.setId(Model.get().getNextId(this.getId()));
		newTableau.description = description;
		newTableau.url = url;
		newTableau.nom = nom;
		if(Model.get().ajouterModelObject(newTableau)){
			ModifierTableauAction.useNextFile(newTableau, (mxCell) object);
			return newTableau;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ITableau#getHauteurTableau()
	 */
	@Override
	public float getHauteurTableau() {
		return longueurTableau;
	}
	
	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.ITableau#getLargeurTableau()
	 */
	@Override
	public float getLargeurTableau() {
		return largeurTableau;
	}

	/**
	 * @param longueurTableau the longueurTableau to set
	 */
	public void setLongueurTableau(float longueurTableau) {
		this.longueurTableau = longueurTableau;
	}

	/**
	 * @param largeurTableau the largeurTableau to set
	 */
	public void setLargeurTableau(float largeurTableau) {
		this.largeurTableau = largeurTableau;
	}
	
	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#getTableau(int)
	 */
	@Override
	public ArrayList<ITableau> getTableau() {
		ArrayList<ITableau> mesTableaux = new ArrayList<ITableau>();
		mesTableaux.add(this);
		return mesTableaux;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.enib.game.model.Noeud#getTableau(boolean)
	 */
	@Override
	protected ArrayList<ITableau> getTableau(boolean sort) {
		ArrayList<ITableau> mesTableaux = new ArrayList<ITableau>();
		mesTableaux.add(this);
		return mesTableaux;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.enib.game.model.Noeud#setDegreInteret(float)
	 */
	@Override
	public boolean setDegreInteret(float degre) {
		this.degreeInteret = degre;
		return true;
	}

	/* 
	 * (non-Javadoc)
	 * @see fr.enib.game.model.Noeud#getDegreInteret()
	 */
	@Override
	public float getDegreInteret() {
		return this.degreeInteret;
	}
}
