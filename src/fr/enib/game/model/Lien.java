/**
 * 
 */
package fr.enib.game.model;

import fr.enib.game.model.enums.AjoutLienInfos;
import fr.enib.game.model.exceptions.LimitLienException;
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
	
	private INoeud noeudArrivee;
	
	private INoeud noeudDepart;
	
	private float poids;
	
	/**
	 * 
	 * @param noeudD Noeud de depart
	 * @param noeudA Noeud d'arrivee
	 */
	public Lien(INoeud noeudD,INoeud noeudA){
		if(noeudD == null || noeudA == null){
			throw new NullPointerException("les noeuds d'un lien ne peuvent pas etre null");
		}
		this.noeudArrivee = noeudA;
		this.noeudDepart = noeudD;
		this.id = noeudDepart.getId()+"_vers_"+noeudArrivee.getId();
		
		AjoutLienInfos outLien = this.noeudDepart.ajouterLienSortant(this);
		if(outLien != AjoutLienInfos.ok){
			if(outLien == AjoutLienInfos.limit){
				throw new LimitLienException("limite de liens sortant dans le noeud de départ : "+this.noeudDepart.getId());
			}
			throw new IllegalArgumentException("le liens sortant "+this.id+" n'a pas pu etre ajouter"
					+ " au noeud de départ "+this.noeudArrivee.getId()+" --> "+outLien.toString());
		}
		outLien = this.noeudArrivee.ajouterLienEntrant(this);
		if(outLien != AjoutLienInfos.ok){
			if(outLien == AjoutLienInfos.limit){
				throw new LimitLienException("limite de liens entrant dans le noeud de arrivée : "+this.noeudArrivee.getId());
			}
			throw new IllegalArgumentException("le liens entrant"+this.id+" n'a pas pu etre ajouter"
					+ " au noeud d'arrivée "+this.noeudArrivee.getId()+" --> "+outLien.toString());
		}
		System.out.println("Lien créer");
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
	 * @see fr.enib.game.model.interfaces.IClonableObject#cloneObject()
	 */
	@Override
	public Lien cloneObject(){
		Lien newLien = new Lien();
//		/*
//		 * comme il ne peut pas exité deux objet avec la même
//		 * id on demande a Model de générer un nouvelle id
//		 */
//		newLien.id = Model.get().getNextId(id);
//		if(Model.get().ajouterModelObject(newLien)){
//			return newLien;
//		}
		return newLien;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObject#modifierId(java.lang.String)
	 */
	@Override
	public boolean setId(String id) {
		//l'id ne peut pas etre null
		if(id==null)return false;
		//l'id ne dois pas déja existé dans le Model
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

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IRemovable#remove()
	 */
	@Override
	public boolean remove() {
		//on surprimer ce noeud dans le Model
		if(Model.get().suprmierModelObject(this)){
			/*
			 * on casse le lien 
			 * avec le noeud d'arrivée
			 */
			if(this.getNoeudArrivee()!=null){
				if(!this.getNoeudArrivee().suprimerLienEntrant(this)){
					return false;
				}
			}
			//on casse le lien avec le noeud de départ
			if(this.getNoeudArrivee()!=null){
				if(!this.getNoeudArrivee().suprimerLienEntrant(this)){
					return false;
				}
			}
			return true;
		}
		return false;
	}

}
