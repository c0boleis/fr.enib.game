package fr.enib.game.app;

import java.util.ArrayList;
import java.util.List;

import fr.enib.game.model.Lien;
import fr.enib.game.model.Noeud;
import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.parcours.graphe.ParcoursDegre;
import fr.enib.game.parcours.graphe.ParcoursPoids;

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
		
		//On nomme les noeuds du graphe :		
		noeud1.setId("Patrimoine");
		noeud2.setId("Architecture");
		noeud3.setId("Paysage");
		
		//Creation des liens : 
		ILien lien1 = new Lien((Noeud)noeud1,(Noeud)noeud2);
		ILien lien2 = new Lien((Noeud)noeud1,(Noeud)noeud3);
		
		//Affectation des poids a chaque lien
		lien1.setPoids(2);
		lien2.setPoids(5);
		
		noeud1.setDegreInteret(20);
		noeud2.setDegreInteret(5);
		noeud3.setDegreInteret(15);
		
		
		ParcoursPoids parcoursP = new ParcoursPoids(noeud1);	
		parcoursP.parcoursLiensSortant();
		
		ParcoursDegre parcoursD = new ParcoursDegre(noeud1);	
		parcoursD.parcoursNoeudEnfant();
	}

}
