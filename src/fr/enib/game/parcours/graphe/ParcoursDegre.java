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
public class ParcoursDegre {
	
	float degreGagnant = 0;
	INoeud nouveauNoeudCourant = new Noeud();
	INoeud noeudCourant = new Noeud();
	
	public ParcoursDegre(INoeud NCourant){
		noeudCourant =NCourant;
	}
	
	public INoeud parcoursNoeudEnfant(){
		//la boucle parcours tous les liens sortant du noeud
		for(int i =0; i< noeudCourant.getLiensSortant().length; i++)
		{
		   //Si le poids du lien est supérieur au poidsGagnant
			System.out.println(noeudCourant.getLiensSortant()[i].getNoeudArrivee().getDegreInteret());
		   if(noeudCourant.getLiensSortant()[i].getNoeudArrivee().getDegreInteret()> degreGagnant)
			{
				//On change la valeur du poidsGagnant et on affecte le noeud d'arrivee au nouveauNoeudCourant
			    degreGagnant = noeudCourant.getLiensSortant()[i].getNoeudArrivee().getDegreInteret();
				nouveauNoeudCourant = noeudCourant.getLiensSortant()[i].getNoeudArrivee();
			}
		}
		System.out.println("Le Degre est :" + degreGagnant + " donc le noeud est : " + nouveauNoeudCourant);
		return nouveauNoeudCourant;
		
	}

}
