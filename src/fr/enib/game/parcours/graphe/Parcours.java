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
		this.noeudCourant = noeudCourant;
	}


	//Calcul du noeud precedent 
	public INoeud calcul_Noeud_Precedent()
	{
		/*
		 *Code si un noeud peut avoir plusieurs noeuds entrants. 
		 * 
		 * float[] calculs = new float[noeudCourant.getLiensEntrant().length];
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
		}*/
		
		//Il y a un seul lien entrant
		//Si il y a un noeud entrant ce n'est pas le sommet du graphe donc on retourne le lien precedant.
		if(noeudCourant.getLiensEntrant().length != 0)
		{
		   return noeudCourant.getLiensEntrant()[0].getNoeudDepart();
		}
		else 
		{
			return null;
		}
		
		
	}
	
	
	//Calcul du noeud des noeuds suivants :
	public INoeud[] calcul_Noeud_Suivant()
	{
		float[] calculs = new float[noeudCourant.getLiensSortant().length];
		float maxi =0;
		int nbNoeud;
		int temp; 
		INoeud noeud2 = new Noeud();
		ArrayList<INoeud> tab_noeud = new ArrayList<INoeud>();
		
		INoeud noeudGagnants[];
		
		//Si le noeud courant a un noeud precedent 
		if(calcul_Noeud_Precedent()!= null)
		{
			//On regarde le nombre de noeud sortant du noeud courant
			if(noeudCourant.getLiensSortant().length<3)
			{
				//Si il y a moins de 3 liens sortant on aura nbNoeud à la sortie (noeud courant et noeud precedent non compris)
				nbNoeud = noeudCourant.getLiensSortant().length;
			}
			else
			{
				//Si il y a plus de 3 liens sortant on aura nbNoeud à la sortie (noeud courant et noeud precedent non compris)
				nbNoeud = 3;
			}
			temp = 2;  //Compteur 
			noeudGagnants = new Noeud[nbNoeud+2]; //tableau recuperant tous les noeuds qui sont calcules par la fonction
			noeudGagnants[0] = noeudCourant; //Le noeud courant est insere dans le tableau 
			noeudGagnants[1] = calcul_Noeud_Precedent(); //Le noeud precedent est insere dans le tableau
		}
		else 
		{
			//Si le noeud courant est le premier noeud du graphe alors 
			//on a 4 a retourner  (noeud courant non compris)
			nbNoeud = 4;
			temp = 1;
			noeudGagnants = new Noeud[nbNoeud+1]; //tableau recuperant tous les noeuds qui sont calcules par la fonction
			noeudGagnants[0] = noeudCourant;  //Le noeud courant est insere dans le tableau
		}

		//Boucle permettant de recuperer tous les noeuds qui sont en dessous du noeud entrant
		for(int i =0; i< noeudCourant.getLiensSortant().length; i++)
		{
			tab_noeud.add(noeudCourant.getLiensSortant()[i].getNoeudArrivee());
		}
		
		//Boucle permettant de trouver les noeuds qui restent a trouver
		for(int i = 0; i < nbNoeud; i++)
		{
			//Si la liste n'est pas vide il y des noeuds suivants
			if(tab_noeud.isEmpty() == false)
			{
				//Pour tous les noeuds qui suivant 
				for(int j =0; j< calculs.length; j++)
				{
					//Calcule le noeud suivant qui interesse le plus l'utilisateur
					calculs[j] = noeudCourant.getLiensSortant()[j].getPoids()* noeudCourant.getLiensSortant()[j].getNoeudArrivee().getDegreInteret();
					if(calculs[j]>maxi)  //Si le calcul est le maximum
					{		
						//Si le noeud est toujouts dans la liste c'est qu'il n'est pas dans le tableau qui sera retourne donc on pourra l'inserer esuite
						if(tab_noeud.contains(noeudCourant.getLiensSortant()[j].getNoeudArrivee()) == true)
						{
							maxi = calculs[j];
							//On récupere le noeud qui a le calcul le plus eleve
							noeud2 = noeudCourant.getLiensSortant()[j].getNoeudArrivee();
						}
					}
				}
				System.out.println("Maxi : " + maxi);
				maxi = 0;
				//On supprime le noeud qui a ete retenu (avec le calcul le plus eleve)
				tab_noeud.remove(noeud2);
				//On l'insere ensuite dans le tableau qui sera retourne 
				noeudGagnants[i+temp]= noeud2;
			}
			else
			{
				//Si la liste est vide il n'y pas de noeud suivant
				System.out.println("************");
				return noeudGagnants;
			}
		}
		return noeudGagnants;	
	}
	

	
}
