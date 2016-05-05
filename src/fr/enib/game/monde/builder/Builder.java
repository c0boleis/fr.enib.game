package fr.enib.game.monde.builder;

import fr.enib.game.monde.musee.Salle;
import fr.enib.game.monde.objet.RessourceProvider;
import fr.enib.game.monde.objet.Tableau;

public class Builder {
	//private static Logger LOGGER = Logger.getLogger(Builder.class);
	
	public Builder(boolean loadFromFile){
		
	}
	
	public void construire(){
		String[] ressources = new String[16];
		ressources[0] = RessourceProvider.pathTableaux + "abri-du-marin-plouhinec.jpg";
		ressources[1] = RessourceProvider.pathTableaux + "aiguille-de-la-reine-dangleterre-locmaria.jpg";
		ressources[2] = RessourceProvider.pathTableaux + "allee-couverte-malguenac.jpg";
		ressources[3] = RessourceProvider.pathTableaux + "allee-couverte-gouezec.jpg";
		ressources[4] = RessourceProvider.pathTableaux + "allee-couverte-motreff.jpg";
		ressources[5] = RessourceProvider.pathTableaux + "allee-couverte-pledran.jpg";
		ressources[6] = RessourceProvider.pathTableaux + "allee-couverte-ploubazlanec.jpg";
		ressources[7] = RessourceProvider.pathTableaux + "allee-couverte-de-guinefolle-meslin.jpg";
		ressources[8] = RessourceProvider.pathTableaux + "allee-couverte-ploerdut.jpg";
		ressources[9] = RessourceProvider.pathTableaux + "ancien-hopital-quimperle.jpg";
		ressources[10] = RessourceProvider.pathTableaux + "ancien-hopital-militaire-guingamp.jpg";
		ressources[11] = RessourceProvider.pathTableaux + "ancien-four-a-pain-cesson-sevigne.jpg";
		ressources[12] = RessourceProvider.pathTableaux + "ancien-chateau-chateauneuf-d-ille-et-vilaine.jpg";
		ressources[13] = RessourceProvider.pathTableaux + "allegorie-berne.jpg";
		ressources[14] = RessourceProvider.pathTableaux + "ancien-baptistere-pleine-fougeres.jpg";
		ressources[15] = RessourceProvider.pathTableaux + "ancien-cafe-nevez.jpg";
		
		Monde m = Monde.get();
		
		Salle h = new Salle("hall", 5.0f, 7.5f, 3.0f);
		Salle s1 = new Salle("salle_01", 5.0f, 5.0f, 3.0f);
		s1.placer(1.0f, 5.5f, 0.0f);
		
		h.ajouterSalleVoisine(s1);
		
		/*for(int i = 0; i < 10; i++){
			h.ajouterTableau(new Tableau("tab_" + String.format("%02d", i), ressources[i]));
		}
		
		for(int i = 10; i < 16; i++){
			s1.ajouterTableau(new Tableau("tab_" + String.format("%02d", i), ressources[i]));
		}*/
		
		
		
		m.setSalleCourante(h);
	}
}
