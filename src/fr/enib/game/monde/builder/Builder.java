package fr.enib.game.monde.builder;

import java.util.List;

import fr.enib.game.model.Tableau;
import fr.enib.game.model.interfaces.ITableau;
import fr.enib.game.monde.musee.Musee;
import fr.enib.game.monde.objet.RessourceProvider;

public class Builder {
	//private static Logger LOGGER = Logger.getLogger(Builder.class);
	
	public Builder(boolean loadFromFile){
		
	}
	
	public void construire(){
		List<String> imgs = RessourceProvider.getNomTableaux();
		
		Musee m = new Musee();
		
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

		m.ajouterListeTableau("toto", (ITableau[]) l1);
		m.ajouterListeTableau("titi", (ITableau[]) l2);
		m.ajouterListeTableau("tata", (ITableau[]) l3);
		m.ajouterListeTableau("tutu", (ITableau[]) l4);
		m.ajouterListeTableau("tyty", (ITableau[]) l5);
		m.ajouterListeTableau("tete", (ITableau[]) l6);
		
		m.genererSalles();
	}
}
/*
Monde m = Monde.get();

Salle h = new Salle("hall", 5.0f, 5.0f, 3.0f);
Salle s1 = new Salle("salle_01", 5.0f, 5.0f, 3.0f);
s1.placer(6.0f, 0.0f, 0.0f); // Mur avant milieu
//s1.placer(0.0f, 6.0f, 0.0f); // Mur gauche milieu
//s1.placer(0.0f, -6.0f, 0.0f); // Mur droite milieu
//s1.placer(-6.0f, 0.0f, 0.0f); // Mur arriere milieu

h.ajouterSalleVoisine(s1);

for(int i = 0; i < 12; i++){
	h.ajouterTableau(new Tableau("tab_" + String.format("%02d", i), ressources[i]));
}

for(int i = 12; i < 16; i++){
	s1.ajouterTableau(new Tableau("tab_" + String.format("%02d", i), ressources[i]));
}
m.setSalleCourante(h);*/