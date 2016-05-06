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
public class ParcoursDegre extends Parcours {
	
	/**
	 * @param NCourant
	 */
	public ParcoursDegre(INoeud NCourant) {
		super(NCourant);
		// TODO Auto-generated constructor stub
	}

	float degreGagnant = 0;
		
	public INoeud parcoursObjetSuivant(){
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
			   if(this.noeudCourant.getLiensSortant()[i].getNoeudArrivee().getDegreInteret()> this.degreGagnant)
				{
					//On change la valeur du poidsGagnant et on affecte le noeud d'arrivee au nouveauNoeudCourant
				    this.degreGagnant = this.noeudCourant.getLiensSortant()[i].getNoeudArrivee().getDegreInteret();
					this.nouveauNoeudCourant = this.noeudCourant.getLiensSortant()[i].getNoeudArrivee();
				}
			}
			System.out.println("Le Degre est :" + this.degreGagnant + " donc le noeud est : " + this.nouveauNoeudCourant);
			return this.nouveauNoeudCourant;
		}
		
	}

}
