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
public class ParcoursPoids {
	
	float poidsGagnant = 0;
	INoeud nouveauNoeudCourant = new Noeud();
	INoeud noeudCourant = new Noeud();
	
	public ParcoursPoids(INoeud NCourant){
	     noeudCourant = NCourant;
	}
	
	
	/**
	 * @author magali Hautcoeur
	 * Fonction permettant de parcourir le graphe a partir du noeud courant 
	 * pour trouver le lien qui a le poids le plus fort ainsi que le noeud ou le lien arrive.
	 * 
	 */
	public INoeud parcoursLiensSortant()
	{
			
		//la boucle parcours tous les liens sortant du noeud
		for(int i =0; i< noeudCourant.getLiensSortant().length; i++)
		{
			//Si le poids du lien est supérieur au poidsGagnant
			if(noeudCourant.getLiensSortant()[i].getPoids()> poidsGagnant)
			{
				//On change la valeur du poidsGagnant et on affecte le noeud d'arrivee au nouveauNoeudCourant
				poidsGagnant = noeudCourant.getLiensSortant()[i].getPoids();
				nouveauNoeudCourant = noeudCourant.getLiensSortant()[i].getNoeudArrivee();
			}
		}
		System.out.println("Le poids est :" + poidsGagnant + " donc le noeud est : " + nouveauNoeudCourant);
		return nouveauNoeudCourant;
		
	}

}
