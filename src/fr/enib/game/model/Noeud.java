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
	
	private int limitLienEntrant = 1;
	
	private int limitLienSortant = -1;
	
	/**
	 * 
	 */
	public Noeud() {
		this.id = NOM_PAR_DEFAULT;
	}
	
	/**
	 * @param id
	 */
	public Noeud(String id){
		this.id = id;;
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
		/*
		 * le faite de renvoyer un tableau et non pas la liste
		 * permet que personne ne puiss utilisé la liste pour 
		 * ajouter de nouveaux liens.
		 */
		return this.liensEntrants.toArray(new ILien[0]);
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#getLiensSortant()
	 */
	@Override
	public ILien[] getLiensSortant() {
		/*
		 * le faite de renvoyer un tableau et non pas la liste
		 * permet que personne ne puiss utilisé la liste pour 
		 * ajouter de nouveaux liens.
		 */
		return this.liensSrotants.toArray(new ILien[0]);
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#estFeuille()
	 */
	@Override
	public boolean estFeuille() {
		//si le noeud n'a pas de lien sortant alors c'est une feuille
		return getLiensSortant().length<=0;
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
		if(id==null)return false;
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
		/*
		 * on suprimer le liens lié a ce noeud
		 * car un lien a besoin de sont noeud 
		 * de départ et d'arrivé pour exister
		 */
	
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
		//si le liens a déjà lié a noeud on n'a l'ajoute pas
		if(containsLienEntrant(lien.getId()))return false;
		/*
		 * si le noeud d'arrivée est null
		 * lien n'est pas corect donc on ne l'ajouter
		 * pas
		 */
		if(lien.getNoeudArrivee()==null)return false;
		/*
		 * si le noeud d'arrivée n'est pas ce noeud le
		 * lien n'est pas corect donc on ne l'ajouter
		 * pas
		 */
		if(!lien.getNoeudArrivee().getId().equals(getId()))return false;
		/*
		 * si il y a déjà assez de lien, on n'ajoute pas le lien
		 */
		if(this.liensEntrants.size()>=limitLienEntrant && limitLienEntrant>=0){
			return false;
		}
		this.liensEntrants.add(lien);
		return true;
	}
	
	private boolean containsLienEntrant(String idLien){
		ILien[] tmp = getLiensEntrant();
		for(ILien lienTmp : tmp){
			if(lienTmp.getId().equals(idLien)){
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#suprimerLienEntrant(fr.enib.game.model.interfaces.ILien)
	 */
	@Override
	public boolean suprimerLienEntrant(ILien lien) {
		int index = 0;
		ILien[] tmp = getLiensEntrant();
		for(ILien lienTmp : tmp){
			if(lienTmp.getId().equals(lien.getId())){
				break;
			}
			index++;
		}
		return this.liensEntrants.remove(index)!=null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#ajouterLienSortant(fr.enib.game.model.interfaces.ILien)
	 */
	@Override
	public boolean ajouterLienSortant(ILien lien) {
		//si le liens a déjà lié a noeud on n'a l'ajoute pas
		if(containsLienSortant(lien.getId()))return false;
		/*
		 * si le noeud d'arrivée est null
		 * lien n'est pas corect donc on ne l'ajouter
		 * pas
		 */
		if(lien.getNoeudDepart()==null)return false;
		/*
		 * si le noeud de depart n'est pas ce noeud le
		 * lien n'est pas corect donc on ne l'ajouter
		 * pas
		 */
		if(!lien.getNoeudDepart().getId().equals(getId()))return false;
		/*
		 * si il y a déjà assez de lien, on n'ajoute pas le lien
		 */
		if(this.liensEntrants.size()>=limitLienSortant && limitLienSortant >=0){
			return false;
		}
		this.liensEntrants.add(lien);
		return true;
	}
	
	private boolean containsLienSortant(String idLien){
		ILien[] tmp = getLiensSortant();
		for(ILien lienTmp : tmp){
			if(lienTmp.getId().equals(idLien)){
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#suprimerLienSortant(fr.enib.game.model.interfaces.ILien)
	 */
	@Override
	public boolean suprimerLienSortant(ILien lien) {
		int index = 0;
		ILien[] tmp = getLiensSortant();
		for(ILien lienTmp : tmp){
			if(lienTmp.getId().equals(lien.getId())){
				break;
			}
			index++;
		}
		return this.liensSrotants.remove(index)!=null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IObjectInteret#setDegreInteret(float)
	 */
	@Override
	public boolean setDegreInteret(float degre) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IObjectInteret#getDegreInteret()
	 */
	@Override
	public float getDegreInteret() {
		// TODO Auto-generated method stub
		return 0;
	}

}
