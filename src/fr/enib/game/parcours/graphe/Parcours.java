/**
 * 
 */
package fr.enib.game.parcours.graphe;

import fr.enib.game.model.Noeud;
import fr.enib.game.model.Tableau;
import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.model.interfaces.ITableau;

/**
 * @author magali Hautcoeur
 *
 */
public abstract class Parcours {
	
	INoeud nouveauNoeudCourant = new Noeud();
	INoeud noeudCourant = new Noeud();
	
	public Parcours(INoeud NCourant) {
		this.noeudCourant = NCourant;
	}
	
	public abstract INoeud parcoursObjetSuivant();

}
