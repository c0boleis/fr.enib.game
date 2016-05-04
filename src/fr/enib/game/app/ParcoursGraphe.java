package fr.enib.game.app;

import java.util.ArrayList;
import java.util.List;

import fr.enib.game.model.Lien;
import fr.enib.game.model.Noeud;
import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.INoeud;

/**
 * @author Ronan Morel
 *
 */
public class ParcoursGraphe {
	
	public static void parcoursSortant(INoeud noeudCourant)
	{
		float poidsGagnant = 0; 
		ILien[] liensSortant = new ILien[0];
		System.out.println(noeudCourant.getLiensEntrant().length);
		for(int i =0; i> noeudCourant.getLiensEntrant().length; i++)
		{
			if(liensSortant[i].getPoids()> poidsGagnant)
			{
				poidsGagnant = liensSortant[i].getPoids();
			}
		}
		System.out.println("Le poids est :" + poidsGagnant);
		
	}

	/**
	 * test les parcours de graphe
	 * @param args
	 */
	public static void main(String[] args) {
		INoeud noeud1 = new Noeud();
		INoeud noeud2 = new Noeud();
		INoeud noeud3 = new Noeud();
		
		noeud1.setId("Patrimoine");
		noeud2.setId("Architecture");
		noeud3.setId("Paysage");
		
		ILien lien1 = new Lien((Noeud)noeud1,(Noeud)noeud2);
		ILien lien2 = new Lien((Noeud)noeud1,(Noeud)noeud3);
		lien1.setPoids(2);
		lien2.setPoids(5);
		
		
		parcoursSortant(noeud1);
	}

}
