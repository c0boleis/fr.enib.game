/**
 * 
 */
package fr.enib.game.model;

import java.util.ArrayList;
import java.util.List;

import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.INoeud;

/**
 * @author Corentin Boleis
 *
 */
public class Noeud implements INoeud {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 734404660422963476L;
	
	private String id;
	
	private boolean visiter = false;
	
	private List<ILien> liensEntrants = new ArrayList<ILien>();
	
	private List<ILien> liensSrotants = new ArrayList<ILien>();
	
	/**
	 * 
	 */
	public Noeud() {
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
	 * @see fr.enib.game.model.interfaces.IVisitableObject#visiter()
	 */
	@Override
	public void visiter() {
		this.visiter = true;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IVisitableObject#resetVisiter()
	 */
	@Override
	public void resetVisiter() {
		this.visiter = false;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IVisitableObject#estVisiter()
	 */
	@Override
	public boolean estVisiter() {
		return this.visiter;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#getLiensEntrant()
	 */
	@Override
	public ILien[] getLiensEntrant() {
		return this.liensEntrants.toArray(new ILien[0]);
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#getLiensSortant()
	 */
	@Override
	public ILien[] getLiensSortant() {
		return this.liensSrotants.toArray(new ILien[0]);
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#estFeuille()
	 */
	@Override
	public boolean estFeuille() {
		return getLiensEntrant().length<=0;
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
	 * @see fr.enib.game.model.interfaces.IClonableObject#cloneObject()
	 */
	@Override
	public Noeud cloneObject(){
		Noeud newNoeud = new Noeud();
		newNoeud.id = Model.get().getNextId(id);
		if(Model.get().ajouterModelObject(newNoeud)){
			return newNoeud;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObject#modifierId(java.lang.String)
	 */
	@Override
	public boolean setId(String id) {
		if(Model.get().containsModeObject(id))return false;
		this.id = id;
		return true;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IRemovable#remove()
	 */
	@Override
	public boolean remove() {
		Model.get().suprmierModelObject(this);
		if(!suprimerLienEntrants()){
			return false;
		}
		if(!suprimerLienSortants()){
			return false;
		}
		return true;
	}
	
	private boolean suprimerLienEntrants(){
		ILien[] tmp = getLiensEntrant();
		for(ILien lien : tmp){
			if(!lien.remove()){
				return false;
			}
		}
		this.liensEntrants.clear();
		return true;
	}
	
	private boolean suprimerLienSortants(){
		ILien[] tmp = getLiensSortant();
		for(ILien lien : tmp){
			if(!lien.remove()){
				return false;
			}
		}
		this.liensSrotants.clear();
		return true;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#ajouterLienEntrant(fr.enib.game.model.interfaces.ILien)
	 */
	@Override
	public boolean ajouterLienEntrant(ILien lien) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#suprimerLienEntrant(fr.enib.game.model.interfaces.ILien)
	 */
	@Override
	public boolean suprimerLienEntrant(ILien lien) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#ajouterLienSortant(fr.enib.game.model.interfaces.ILien)
	 */
	@Override
	public boolean ajouterLienSortant(ILien lien) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#suprimerLienSortant(fr.enib.game.model.interfaces.ILien)
	 */
	@Override
	public boolean suprimerLienSortant(ILien lien) {
		// TODO Auto-generated method stub
		return false;
	}

}
