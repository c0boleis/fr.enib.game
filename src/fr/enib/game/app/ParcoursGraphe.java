package fr.enib.game.app;

import java.util.ArrayList;
import java.util.List;

import fr.enib.game.model.Lien;
import fr.enib.game.model.Noeud;
import fr.enib.game.model.Tableau;
import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.model.interfaces.ITableau;
import fr.enib.game.parcours.graphe.Parcours;

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
		
	
		//Creation des noeuds :
		INoeud noeud1 = new Noeud();
		INoeud noeud2 = new Noeud();
		INoeud noeud3 = new Noeud();
		INoeud noeud4 = new Noeud();
		INoeud noeud5 = new Noeud();
		INoeud noeud6 = new Noeud();
		INoeud noeud7 = new Noeud();
		INoeud noeud8 = new Noeud();
		INoeud noeud9 = new Noeud();
		INoeud noeud10 = new Noeud();
		
		//Création d'un noeud qui est tableau 
		//ITableau tab1 = new Tableau();
		//tab1.setUrlImage("C:\\Users\\magal_000\\git\\fr.enib.game\\data\\Image_graphe");
		//tab1.setNomTableau("abri-et-rampe-de-mise-a-leau-du-canot-de-sauvetage-hoedic.jpg");
		//tab1.setId("Tableau plage");
		
		
		//On nomme les noeuds du graphe :		
		noeud1.setId("Patrimoine");
		noeud2.setId("Architecture");
		noeud3.setId("Paysage");
		noeud4.setId("Maison");
		noeud5.setId("Chateau");
		noeud6.setId("Plage");
		noeud7.setId("Montagne");
		noeud8.setId("blabla");
		noeud9.setId("blublu");
		noeud10.setId("A");
		
		//Creation des liens : 
		ILien lien10 = new Lien((Noeud)noeud10,(Noeud)noeud1);
		ILien lien1 = new Lien((Noeud)noeud1,(Noeud)noeud2);
		ILien lien2 = new Lien((Noeud)noeud1,(Noeud)noeud3);
		ILien lien2_1 = new Lien((Noeud)noeud1,(Noeud)noeud8);
		ILien lien2_2 = new Lien((Noeud)noeud1,(Noeud)noeud9);
		ILien lien3 = new Lien((Noeud)noeud2,(Noeud)noeud4);
		ILien lien4 = new Lien((Noeud)noeud2,(Noeud)noeud5);
		ILien lien5 = new Lien((Noeud)noeud3,(Noeud)noeud6);
		ILien lien6 = new Lien((Noeud)noeud3,(Noeud)noeud7);
		//ILien lien7 = new Lien((Noeud)noeud6,(Noeud)tab1);
		
		//System.out.println(lien7.getNoeudDepart() + "--->" + lien7.getNoeudArrivee());
		
		//Affectation des poids a chaque lien
		lien1.setPoids(2);
		lien2.setPoids(5);
		lien3.setPoids(2);
		lien4.setPoids(3);
		lien5.setPoids(6);
		lien6.setPoids(4);
		lien2_1.setPoids(2);
		lien2_2.setPoids(2);
		lien10.setPoids(2);
		
	
		//Affectation des degres au noeud
		noeud1.setDegreInteret(20);
		noeud2.setDegreInteret(5);
		noeud3.setDegreInteret(15);
		noeud4.setDegreInteret(7);
		noeud5.setDegreInteret(10);
		noeud6.setDegreInteret(12);
		noeud7.setDegreInteret(5);
		noeud8.setDegreInteret(4);
		noeud9.setDegreInteret(2);
		noeud10.setDegreInteret(6);
		
		//Test du parcours du graphe : 
		Parcours parcoursP = new Parcours(noeud1);
		
		INoeud n[] = parcoursP.calcul_Noeud_Suivant();
		for(int i = 0; i<n.length; i++)
		{
			System.out.println(n[i].getId());
		}
		
		 /*Parcours parcoursP2 = new ParcoursPoids(noeud2);	
		 parcoursP2.parcoursObjetSuivant();
		 INoeud n[] = parcoursP2.calcul_Noeud_Suivant();
		 for(int i = 0; i<n.length; i++)
		 {
			System.out.println(n[i].getId());
		 }*/
		
		//System.out.println(parcoursTestFeuille2.noeud_tableau().getUrlImage() + parcoursTestFeuille2.noeud_tableau().getNomTableau());
		
		
		//abri-et-rampe-de-mise-a-leau-du-canot-de-sauvetage-hoedic.jpg
		//ancien-chateau-baratoux-saint-brieuc.jpg
	}

}
