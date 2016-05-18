/**
 * 
 */
package fr.enib.game.parcours.graphe;

import java.util.ArrayList;


import java.util.Collections;
import java.util.List;

import fr.enib.game.model.Lien;
import fr.enib.game.model.Noeud;
import fr.enib.game.model.Tableau;
import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.model.interfaces.ITableau;

/**
 * @author magali Hautcoeur
 *
 */
public class Parcours {
	
	INoeud noeudPrecedent;
	INoeud noeudCourant;
	
	public Parcours(INoeud nCourant) {
		this.noeudCourant = nCourant;
	}

	
	/**
	 * @return the noeudCorant
	 */
	public INoeud getNoeudCourant() {
		return noeudCourant;
	}


	/**
	 * @param noeudCourant the noeudCourant to set
	 */
	public void setNoeudCourant(INoeud noeudCourant) {
		this.noeudPrecedent = this.noeudCourant;
		this.noeudCourant = noeudCourant;
	}


	
	
	/**
	 * @return la liste des Noeuds suivants
	 */
	public INoeud[] calcul_Noeud_Suivant()
	{
		
		List<INoeud> noeud = new ArrayList<INoeud>();
		List<INoeud> noeudEnfant = new ArrayList<INoeud>();
		
		//on recupere la liste des noeuds descendant du noeud courant 
		noeudEnfant = this.noeudCourant.getNoeudsDescendantDirect();
		int compt = 3;
		//On ajoute le noeud courant dans la liste des noeuds 
		noeud.add(this.noeudCourant); 
		
		//Si le noeud courant n'a pas de noeud precedant  
		if(this.noeudPrecedent == null)	
		{
			//on devra fournir 4 noeuds 
			compt = 4;
		}else{
			//Sinon on ajoute le noeud precedant dans la liste
			noeud.add(this.noeudPrecedent);
		}

		
		//On effectue les calculs 
		this.noeudCourant.calculValeurParcoursGraphe();
		
		//Si le noeud precedent est dans la liste des noeuds enfants on le supprime 
		noeudEnfant.remove(this.noeudPrecedent);
		//On range la liste des noeuds enfant du plus grand au plus petit 
		Collections.sort(noeudEnfant, new VisitableObjectComparator());
		//Si il y a plus de noeud enfant que l'on veut 
		if(noeudEnfant.size() > compt)
		{
			//On recupere compt noeud enfant de la liste
			noeudEnfant = noeudEnfant.subList(0, compt);
		}
		//On affiche la liste de noeudEnfant dans la liste noeud
		noeud.addAll(noeudEnfant);
	
		//On retourne le tableau de noeud		
		return noeud.toArray(new INoeud[0]);	
	}
	

	
}
