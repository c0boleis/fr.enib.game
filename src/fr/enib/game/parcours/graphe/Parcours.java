/**
 * 
 */
package fr.enib.game.parcours.graphe;

import java.util.ArrayList;

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
	
	INoeud nouveauNoeudCourant = new Noeud();
	INoeud noeudCourant = new Noeud();
	
	public Parcours(INoeud nCourant) {
		this.noeudCourant = nCourant;
	}
	
	
	public void tableaux()
	{
		
	}
	
	public INoeud calcul_Noeud_Precedent()
	{
		float[] calculs = new float[noeudCourant.getLiensEntrant().length];
		float maxi = 0;
		INoeud noeud2 = new Noeud();
		if(noeudCourant.getLiensEntrant().length != 0)
		{
		  for(int j =0; j< calculs.length; j++)
			{
				calculs[j] = noeudCourant.getLiensEntrant()[j].getPoids()* noeudCourant.getLiensEntrant()[j].getNoeudDepart().getDegreInteret();
				if(calculs[j]>maxi)
				{		
					maxi = calculs[j];
					noeud2 = noeudCourant.getLiensEntrant()[j].getNoeudDepart();
				}
			}
		   return noeud2;
		}
		else 
		{
			return null;
		}
	}
	
	
	
	public INoeud[] calcul_Noeud_Suivant()
	{
		float[] calculs = new float[noeudCourant.getLiensSortant().length];
		float maxi =0;
		int nbNoeud;
		int temp; 
		INoeud noeud2 = new Noeud();
		ArrayList<INoeud> tab_noeud = new ArrayList<INoeud>();
		
		if(noeudCourant.getLiensSortant().length<3)
		{
			nbNoeud = noeudCourant.getLiensSortant().length;
		}
		else
		{
			nbNoeud = 3;
		}
		
		
		INoeud noeudGagnants[];
		if(calcul_Noeud_Precedent()!= null)
		{
			temp = 2;
			noeudGagnants = new Noeud[nbNoeud+2];
			noeudGagnants[0] = noeudCourant;
			noeudGagnants[1] = calcul_Noeud_Precedent();
		}
		else 
		{
			temp = 1;
			noeudGagnants = new Noeud[nbNoeud+1];
			noeudGagnants[0] = noeudCourant;
		}

		for(int i =0; i< noeudCourant.getLiensSortant().length; i++)
		{
			tab_noeud.add(noeudCourant.getLiensSortant()[i].getNoeudArrivee());
		}
		
		for(int nbSalle = 0; nbSalle < nbNoeud; nbSalle++)
		{
			if(tab_noeud.isEmpty() == false)
			{
				for(int j =0; j< calculs.length; j++)
				{
					calculs[j] = noeudCourant.getLiensSortant()[j].getPoids()* noeudCourant.getLiensSortant()[j].getNoeudArrivee().getDegreInteret();
					if(calculs[j]>maxi)
					{		
						if(tab_noeud.contains(noeudCourant.getLiensSortant()[j].getNoeudArrivee()) == true)
						{
							maxi = calculs[j];
							noeud2 = noeudCourant.getLiensSortant()[j].getNoeudArrivee();
						}
					}
				}
				System.out.println("Maxi : " + maxi);
				maxi = 0;
				tab_noeud.remove(noeud2);
				noeudGagnants[nbSalle+temp]= noeud2;
			}
			else
			{
				System.out.println("************");
				return noeudGagnants;
			}
		}
		return noeudGagnants;	
	}

	
}
