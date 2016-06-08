/**
 * 
 */
package fr.enib.game.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import fr.enib.game.model.enums.AjoutLienInfos;
import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.model.interfaces.ITableau;

/**
 * @author Corentin Boleis
 *
 */
@XStreamAlias("Noeud")
public class Noeud implements INoeud {
	
	@XStreamOmitField
	private static final long serialVersionUID = 734404660422963476L;
	
	private String id;
	
	private boolean visiter = false;
	
	private List<ILien> liensEntrants = new ArrayList<ILien>();
	
	private List<ILien> liensSortants = new ArrayList<ILien>();
	
	protected int limitLienEntrant = 1;
	
	protected int limitLienSortant = -1;
	
	private double valeur = Double.NaN;
	/**
	 * 
	 */
	public Noeud() {
		this.id = NOM_PAR_DEFAULT;
		this.limitLienEntrant = 1;
		this.limitLienSortant = -1;
	}
	
	/**
	 * @param id
	 */
	public Noeud(String id){
		this();
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
		return this.liensSortants.toArray(new ILien[0]);
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
		return getId()+"("+this.getDegreInteret()+")";
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IClonableObject#cloneObject()
	 */
	@Override
	public Noeud cloneObject(Object object){
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
		this.liensSortants.clear();
		return true;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#ajouterLienEntrant(fr.enib.game.model.interfaces.ILien)
	 */
	@Override
	public AjoutLienInfos ajouterLienEntrant(ILien lien) {
		//si le liens a déjà lié a noeud on n'a l'ajoute pas
		if(containsLienEntrant(lien.getId()))return AjoutLienInfos.contains;
		/*
		 * si le noeud d'arrivée est null
		 * lien n'est pas corect donc on ne l'ajouter
		 * pas
		 */
		if(lien.getNoeudArrivee()==null)return AjoutLienInfos.noeudnull;
		/*
		 * si le noeud d'arrivée n'est pas ce noeud le
		 * lien n'est pas corect donc on ne l'ajouter
		 * pas
		 */
		if(!lien.getNoeudArrivee().getId().equals(getId()))return AjoutLienInfos.noeudFaux;
		/*
		 * si il y a déjà assez de lien, on n'ajoute pas le lien
		 */
		if(this.liensEntrants.size()>=limitLienEntrant && limitLienEntrant>=0){
			return AjoutLienInfos.limit;
		}
		this.liensEntrants.add(lien);
		return AjoutLienInfos.ok;
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
		System.out.println("## essai ## lien entrant suprimer");
		ILien[] tmp = getLiensEntrant();
		for(ILien lienTmp : tmp){
			if(lienTmp.getId().equals(lien.getId())){
				break;
			}
			index++;
		}
		if(index>=this.liensEntrants.size())return false;
		System.out.println("lien entrant suprimer");
		return this.liensEntrants.remove(index)!=null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#ajouterLienSortant(fr.enib.game.model.interfaces.ILien)
	 */
	@Override
	public AjoutLienInfos ajouterLienSortant(ILien lien) {
		//si le liens a déjà lié a noeud on n'a l'ajoute pas
		if(containsLienSortant(lien.getId()))return AjoutLienInfos.contains;
		/*
		 * si le noeud d'arrivée est null
		 * lien n'est pas corect donc on ne l'ajouter
		 * pas
		 */
		if(lien.getNoeudDepart()==null)return  AjoutLienInfos.noeudnull;
		/*
		 * si le noeud de depart n'est pas ce noeud le
		 * lien n'est pas corect donc on ne l'ajouter
		 * pas
		 */
		if(!lien.getNoeudDepart().getId().equals(getId()))return  AjoutLienInfos.noeudFaux;
		/*
		 * si il y a déjà assez de lien, on n'ajoute pas le lien
		 */
		if(this.liensSortants.size()>=limitLienSortant && limitLienSortant >=0){
			return AjoutLienInfos.limit;
		}
		this.liensSortants.add(lien);
		return AjoutLienInfos.ok;
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
		System.out.println("## essai ## lien sortant suprimer");
		ILien[] tmp = getLiensSortant();
		for(ILien lienTmp : tmp){
			if(lienTmp.getId().equals(lien.getId())){
				break;
			}
			index++;
		}
		if(index>=this.liensSortants.size())return false;
		System.out.println("lien sortant suprimer");
		return this.liensSortants.remove(index)!=null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IObjectInteret#setDegreInteret(float)
	 */
	@Override
	public boolean setDegreInteret(float degre) {
		return true;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IObjectInteret#getDegreInteret()
	 */
	@Override
	public float getDegreInteret() {
		float degreInteret = 0.0f;
		ArrayList<INoeud> noeuds = getDescendantDirect();
		for(INoeud noeud : noeuds){
			degreInteret += noeud.getDegreInteret();
		}
		return degreInteret;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#lienEntrantSontConectables()
	 */
	@Override
	public boolean lienEntrantSontConectables() {
		return this.liensEntrants.size()<this.limitLienEntrant || this.limitLienEntrant<0;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#lienSortantSontConectables()
	 */
	@Override
	public boolean lienSortantSontConectables() {
		return this.liensSortants.size()<this.limitLienSortant|| this.limitLienSortant<0;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#getTableau(int)
	 */
	@Override
	public ArrayList<ITableau> getTableau() {
		return getTableau(true);
	}
	
	protected ArrayList<ITableau> getTableau(boolean sort) {
		ArrayList<ITableau> mesTableaux = new ArrayList<ITableau>();
		ArrayList<INoeud> noeudsEnfants = this.getDescendantDirect();
		for(INoeud noeud : noeudsEnfants){
			if(!(noeud instanceof Noeud))continue;
			ArrayList<ITableau> tab = ((Noeud) noeud).getTableau(false);
			mesTableaux.addAll(tab);
		}
		//on trie le tableau de tableaux pour éviter les doublons
		if(sort){
			ArrayList<ITableau> tmp = new ArrayList<ITableau>();
			for(ITableau tableau : mesTableaux){
				if(!tmp.contains(tableau)){
					tmp.add(tableau);
				}
			}
			mesTableaux = tmp;
		}
		return mesTableaux;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#getNoeudsDirect(java.lang.String)
	 */
	@Override
	public ArrayList<INoeud> getNoeudsDescendantDirect() {
		ArrayList<INoeud> mesNoeudsDescendantsDirects = new ArrayList<INoeud>();
		ILien[] mesLiens = this.getLiensSortant();
		for(ILien lien : mesLiens){
			if(lien.getNoeudArrivee() instanceof ITableau)continue;
			mesNoeudsDescendantsDirects.add(lien.getNoeudArrivee());
		}
		return mesNoeudsDescendantsDirects;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IVisitableObject#valeurDeParcours()
	 */
	@Override
	public double valeurDeParcours() {
		return this.valeur;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IVisitableObject#serValeurDeParcours(double)
	 */
	@Override
	public void setValeurDeParcours(double valeur) {
		this.valeur = valeur;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#calculValeurParcoursGraphe()
	 */
	@Override
	public void calculValeurParcoursGraphe() {
		Model.get().resetValeursParcours();
		calculValeurParcoursGraphe(0.0);
	}
	
	protected void calculValeurParcoursGraphe(double valeur) {
		ILien[] liens = getLiensSortant();
		for(ILien lien : liens){
			Noeud n = (Noeud) lien.getNoeudArrivee();
			double transition = valeur+lien.getPoids()*n.getDegreInteret();
			if(n.getValeurDeParcours()==Double.NaN){
				n.setValeurDeParcours(transition);
			}else if(transition>n.getValeurDeParcours()){
				n.setValeurDeParcours(transition);
			}
			n.calculValeurParcoursGraphe(getValeurDeParcours());
		}
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IVisitableObject#getValeurDeParcours()
	 */
	@Override
	public double getValeurDeParcours() {
		return valeur;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.INoeud#getDescendantDirect()
	 */
	@Override
	public ArrayList<INoeud> getDescendantDirect() {
		ArrayList<INoeud> mesNoeudsDescendantsDirects = new ArrayList<INoeud>();
		ILien[] mesLiens = this.getLiensSortant();
		for(ILien lien : mesLiens){
			mesNoeudsDescendantsDirects.add(lien.getNoeudArrivee());
		}
		return mesNoeudsDescendantsDirects;
	}

}
