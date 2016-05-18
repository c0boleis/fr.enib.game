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
		noeudEnfant = this.noeudCourant.getNoeudsDescendantDirect();
		int compt = 3;
		
		noeud.add(this.noeudCourant); 
		if(this.noeudPrecedent == null)	
		{
			compt = 4;
		}else{
			noeud.add(this.noeudPrecedent);
		}

		
		
		this.noeudCourant.calculValeurParcoursGraphe();
		noeudEnfant.remove(this.noeudPrecedent);
		Collections.sort(noeudEnfant, new VisitableObjectComparator());
		if(noeudEnfant.size() > compt)
		{
			noeudEnfant = noeudEnfant.subList(0, compt);
		}
		noeud.addAll(noeudEnfant);
	
		
		return noeud.toArray(new INoeud[0]);	
	}
	

	
}
