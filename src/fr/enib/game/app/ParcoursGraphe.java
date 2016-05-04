package fr.enib.game.app;

import fr.enib.game.model.Lien;
import fr.enib.game.model.Noeud;
import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.INoeud;

/**
 * @author Ronan Morel
 *
 */
public class ParcoursGraphe {

	/**
	 * test les parcours de graphe
	 * @param args
	 */
	public static void main(String[] args) {
		INoeud noeud1 = new Noeud();
		INoeud noeud2 = new Noeud();
		
		noeud1.setId("Patrimoine");
		noeud2.setId("Architecture");
		
		ILien lien1 = new Lien((Noeud)noeud1,(Noeud)noeud2);
		
		System.out.println(noeud1.ajouterLienSortant(lien1));
		System.out.println(noeud2.ajouterLienEntrant(lien1));
		
		//System.out.println(noeud1.getId() + " : " + noeud1.getLiensSortant()[0].getId());
		
	}

}
