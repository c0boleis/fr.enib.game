package fr.enib.game.monde.builder;

import java.util.List;

import fr.enib.game.model.Lien;
import fr.enib.game.model.Noeud;
import fr.enib.game.model.Tableau;
import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.model.interfaces.ITableau;
import fr.enib.game.monde.musee.Musee;
import fr.enib.game.monde.objet.Avatar;
import fr.enib.game.monde.objet.RessourceProvider;
import fr.enib.game.parcours.graphe.Parcours;

/**
 * 
 * @author Ronan MOREL
 *
 */
public class Builder {
	//private static Logger LOGGER = Logger.getLogger(Builder.class);

	private INoeud[] noeuds;
	
	private Musee musee;
	
	private ITableau[][] listeTableau;
	
	private Parcours parcoursP;
	
	public Builder(boolean loadFromFile){
		musee = new Musee();
		
		IActualisation iActu = new IActualisation() {
			@Override
			public void changementSalle(String id) {
				INoeud n = getNoeudById(id);
				if(n != null){
					parcoursP.setNoeudCourant(n);
					Monde.get().clear();
					musee.clear(); 
					musee.setPositionCentre(Avatar.get().getPositionRepere());
					construire();
					System.out.println(Avatar.get().getPositionRepere());
				}
			}
		};
		Monde.get().setiActu(iActu);
		
		noeuds = new INoeud[9];
		noeuds[0] = new Noeud("X");
		noeuds[1] = new Noeud("A");
		noeuds[2] = new Noeud("AB");
		noeuds[3] = new Noeud("AC");
		noeuds[4] = new Noeud("B11");
		noeuds[5] = new Noeud("B12");
		noeuds[6] = new Noeud("C11");
		noeuds[7] = new Noeud("C12");
		noeuds[8] = new Noeud("C13");
		

		//Creation des liens : 
		ILien lienXA = new Lien((Noeud) noeuds[0],(Noeud)noeuds[1]);
		ILien lienAB = new Lien((Noeud)noeuds[1],(Noeud)noeuds[2]);
		ILien lienAC = new Lien((Noeud)noeuds[1],(Noeud)noeuds[3]);
		ILien lienBB11 = new Lien((Noeud)noeuds[2],(Noeud)noeuds[4]);
		ILien lienBB12 = new Lien((Noeud)noeuds[2],(Noeud)noeuds[5]);
		ILien lienCC11 = new Lien((Noeud)noeuds[3],(Noeud)noeuds[6]);
		ILien lienCC12 = new Lien((Noeud)noeuds[3],(Noeud)noeuds[7]);
		ILien lienCC13 = new Lien((Noeud)noeuds[3],(Noeud)noeuds[8]);
		
		//Affectation des poids a chaque lien
		lienXA.setPoids(1);
		lienAB.setPoids(3);
		lienAC.setPoids(2);
		lienBB11.setPoids(2);
		lienBB12.setPoids(3);
		lienCC11.setPoids(6);
		lienCC12.setPoids(2);
		lienCC13.setPoids(3);
		
	
		//Affectation des degres au noeud
		noeuds[0].setDegreInteret(6);
		noeuds[1].setDegreInteret(20);
		noeuds[2].setDegreInteret(5);
		noeuds[3].setDegreInteret(15);
		noeuds[4].setDegreInteret(7);
		noeuds[5].setDegreInteret(10);
		noeuds[6].setDegreInteret(12);
		noeuds[7].setDegreInteret(5);
		noeuds[8].setDegreInteret(8);
		
		listeTableau = getTableau();
		
		parcoursP = new Parcours(noeuds[0]);
	}
	
	public ITableau[][] getTableau(){
		List<String> imgs = RessourceProvider.getNomTableaux();
		
		ITableau[] l1 = new ITableau[10];
		int dep = 0;
		for(int i = dep; i < l1.length + dep; i++){
			Tableau t = new Tableau();
			t.setId("tab_" + String.format("%02d", i));
			t.setUrlImage(imgs.get(i));
			t.setLongueurTableau(1.0f);
			t.setLargeurTableau(2.0f);
			l1[i-dep] = t;
		}

		ITableau[] l2 = new ITableau[10];
		dep = 10;
		for(int i = dep; i < l1.length + dep; i++){
			Tableau t = new Tableau();
			t.setId("tab_" + String.format("%02d", i));
			t.setUrlImage(imgs.get(i));
			t.setLongueurTableau(1.0f);
			t.setLargeurTableau(2.0f);
			l2[i-dep] = t;
		}
		
		ITableau[] l3 = new ITableau[10];
		dep = 20;
		for(int i = dep; i < l1.length + dep; i++){
			Tableau t = new Tableau();
			t.setId("tab_" + String.format("%02d", i));
			t.setUrlImage(imgs.get(i));
			t.setLongueurTableau(1.0f);
			t.setLargeurTableau(2.0f);
			l3[i-dep] = t;
		}
		
		ITableau[] l4 = new ITableau[10];
		dep = 30;
		for(int i = dep; i < l1.length + dep; i++){
			Tableau t = new Tableau();
			t.setId("tab_" + String.format("%02d", i));
			t.setUrlImage(imgs.get(i));
			t.setLongueurTableau(1.0f);
			t.setLargeurTableau(2.0f);
			l4[i-dep] = t;
		}
		
		ITableau[] l5 = new ITableau[10];
		dep = 40;
		for(int i = dep; i < l1.length + dep; i++){
			Tableau t = new Tableau();
			t.setId("tab_" + String.format("%02d", i));
			t.setUrlImage(imgs.get(i));
			t.setLongueurTableau(1.0f);
			t.setLargeurTableau(2.0f);
			l5[i-dep] = t;
		}
		
		ITableau[] l6 = new ITableau[10];
		dep = 50;
		for(int i = dep; i < l1.length + dep; i++){
			Tableau t = new Tableau();
			t.setId("tab_" + String.format("%02d", i));
			t.setUrlImage(imgs.get(i));
			t.setLongueurTableau(1.0f);
			t.setLargeurTableau(2.0f);
			l6[i-dep] = t;
		}

		ITableau[][] tabTab = new ITableau[5][];
	
		tabTab[0] = l1;
		tabTab[1] = l2;
		tabTab[2] = l3;
		tabTab[3] = l4;
		tabTab[4] = l5;
		
		return tabTab;
	}
	
	public INoeud getNoeudById(String id){
		if(id != null){
			for(INoeud n : noeuds){
				if(id.equals(n.getId())){
					return n;
				}
			}
		}
		return null;
	}
	
	public void construire(){
		INoeud[] noeuds = parcoursP.calcul_Noeud_Suivant();

		if(musee != null && noeuds != null && noeuds.length > 0 && listeTableau != null){
			for(int i = 0; i < noeuds.length; i++){
				if(noeuds[i] != null && noeuds[i].getId() != null)
				musee.ajouterListeTableau(noeuds[i].getId(), listeTableau[i]);
			}
			musee.genererSalles();
		}
	}
}