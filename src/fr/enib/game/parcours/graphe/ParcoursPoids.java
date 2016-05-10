/**
 * 
 */
package fr.enib.game.parcours.graphe;

import fr.enib.game.model.Noeud;
import fr.enib.game.model.interfaces.INoeud;

/**
 * @author magali Hautcoeur
 *
 */
public class ParcoursPoids extends Parcours {
	
	/**
	 * @param NCourant
	 */
	public ParcoursPoids(INoeud NCourant) {
		super(NCourant);
		// TODO Auto-generated constructor stub
	}

	float poidsGagnant = 0;
	
	
	/**
	 * @author magali Hautcoeur
	 * Fonction permettant de parcourir le graphe a partir du noeud courant 
	 * pour trouver le lien qui a le poids le plus fort ainsi que le noeud ou le lien arrive.
	 * 
	 */
	public INoeud parcoursObjetSuivant()
	{
		System.out.println("-------------- Le noeud courant est : "+ this.noeudCourant.getId() + " --------------");
		//la boucle parcours tous les liens sortant du noeud
		if(this.noeudCourant.getLiensSortant().length == 0)
		{
			System.out.println("Le noeud courant est une feuille");
			return this.noeudCourant;
		}
		else
		{
			for(int i =0; i< this.noeudCourant.getLiensSortant().length; i++)
			{	
				//Si le poids du lien est supérieur au poidsGagnant
				if(this.noeudCourant.getLiensSortant()[i].getPoids()> this.poidsGagnant)
				{
					//On change la valeur du poidsGagnant et on affecte le noeud d'arrivee au nouveauNoeudCourant
					this.poidsGagnant = this.noeudCourant.getLiensSortant()[i].getPoids();
					this.nouveauNoeudCourant = this.noeudCourant.getLiensSortant()[i].getNoeudArrivee();
				}
			}
			System.out.println("Le poids est :" + this.poidsGagnant + " donc le noeud est : " + this.nouveauNoeudCourant);
			return this.nouveauNoeudCourant;
		}
		
	}

}
