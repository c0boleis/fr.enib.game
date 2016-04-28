package fr.enib.game.monde.builder;

import java.io.File;

import fr.enib.game.monde.core.Vec3;
import fr.enib.game.monde.object.Avatar;
import fr.enib.game.monde.object.Couloir;
import fr.enib.game.monde.object.Mur;
import fr.enib.game.monde.object.Salle;
import fr.enib.game.monde.object.Tableau;
import fr.enib.game.monde.object.TrouPorte;

public class Builder {
	
	//private final String PATH = "data"+File.separator+"tableaux"+File.separator;
		public static final String PATH = "data"+File.separator+"Image_graphe"+File.separator;
		
		public void construire(){

			Monde monde = Monde.get();
			
			/* ================================================================================================ */
			/* Les salles                                                                                       */
			/* ================================================================================================ */

			float distanceMur = 0.5f;
			Salle hall = new Salle("hall", 20.1f, 10.0f, 5.0f);
			
			((Mur) hall.getMurAvant()).ajouterTrou(new TrouPorte("trou001_"+hall.getId(), 5.0f));
			((Mur) hall.getMurAvant()).ajouterTrou(new TrouPorte("trou002_"+hall.getId(), 15.0f));
			
			Couloir porte_hallsalle4 = new Couloir("porte1", 1.0f+0.05f, distanceMur, 2.0f);
			Couloir porte_hallsalle1 = new Couloir("porte2", 1.0f+0.05f, distanceMur, 2.0f);
			
			Salle salle1 = new Salle("salle1", 10.0f, 10.0f, 5.0f);

			((Mur) salle1.getMurAvant()).ajouterTrou(new TrouPorte("trou001_"+salle1.getId(), 5.0f-distanceMur/2.0f-0.05f));
			((Mur) salle1.getMurArriere()).ajouterTrou(new TrouPorte("trou002_"+salle1.getId(), 5.0f+distanceMur/2.0f+0.05f));
			Couloir porte_salle12 = new Couloir("porte3", 1.0f+0.05f, distanceMur, 2.0f);
			
			Salle salle2 = new Salle("salle2", 10.0f, 10.0f, 5.0f);

			((Mur) salle2.getMurArriere()).ajouterTrou(new TrouPorte("trou001_"+salle2.getId(), 5.0f+distanceMur/2.0f+0.05f));
			((Mur) salle2.getMurAvant()).ajouterTrou(new TrouPorte("trou002_"+salle2.getId(), 5.0f));
			Couloir porte_salle23 = new Couloir("porte4", 1.0f+0.05f, distanceMur, 2.0f);
			
			Salle salle3 = new Salle("salle3", 10.0f, 10.0f, 5.0f);

			((Mur) salle3.getMurDroite()).ajouterTrou(new TrouPorte("trou001_"+salle3.getId(), 5.0f));
			((Mur) salle3.getMurArriere()).ajouterTrou(new TrouPorte("trou002_"+salle3.getId(), 5.0f-distanceMur/2.0f+0.05f));
			
			Couloir porte_salle34 = new Couloir("porte5", 1.0f+0.05f, distanceMur, 2.0f);
			
			Salle salle4 = new Salle("salle4", 10.0f, 10.0f, 5.0f);

			((Mur) salle4.getMurArriere()).ajouterTrou(new TrouPorte("trou001_"+salle4.getId(), 5.0f-distanceMur/2.0f+0.05f));
			((Mur) salle4.getMurAvant()).ajouterTrou(new TrouPorte("trou002_"+salle4.getId(), 5.0f+distanceMur/2.0f-0.05f));
			
			hall.placer(new Vec3(0.0f, 0.0f, 0.0f));
			salle1.placer(new Vec3(10.0f+distanceMur, -5.0f-(distanceMur/2.0f), 0.0f));
			salle2.placer(new Vec3(20.0f+(2.0f*distanceMur), -5.0f-(distanceMur/2.0f), 0.0f));
			salle3.placer(new Vec3(20.0f+(2.0f*distanceMur), 5.0f+(distanceMur/2.0f), 0.0f));
			salle4.placer(new Vec3(10.0f+distanceMur, 5.0f+(distanceMur/2.0f), 0.0f));


			porte_hallsalle4.placer(new Vec3(5.0f+(distanceMur/2.0f), 5.0f+0.05f, 0.0f));
			porte_hallsalle1.placer(new Vec3(5.0f+(distanceMur/2.0f), -5.0f+0.05f, 0.0f));
			
			porte_salle12.placer(new Vec3(15.0f+distanceMur*1.5f, -5.0f+0.05f, 0.0f));
			porte_salle23.placer(new Vec3(20.0f+distanceMur+0.5f, 0.0f, 0.0f));
			porte_salle34.placer(new Vec3(15.0f+distanceMur*1.5f, 5.0f+0.05f, 0.0f));
			
			/* ================================================================================================ */
			/* Les objets                                                                                       */
			/* ================================================================================================ */

			hall.ajouterTableau(new Tableau("tab001_"+ hall.getId(), "data"+File.separator+"tableaux"+File.separator+"REN001.jpg")); 
			hall.ajouterTableau(new Tableau("tab002_"+ hall.getId(), "data"+File.separator+"tableaux"+File.separator+"REN002.jpg")); 
			
			salle1.ajouterTableau(new Tableau("tab001_"+ salle1.getId(), "data"+File.separator+"tableaux"+File.separator+"REN003.jpg")); 
			salle1.ajouterTableau(new Tableau("tab002_"+ salle1.getId(), "data"+File.separator+"tableaux"+File.separator+"REN004.jpg")); 
			
			salle2.ajouterTableau(new Tableau("tab001_"+ salle2.getId(), "data"+File.separator+"tableaux"+File.separator+"REN005.jpg")); 
			salle2.ajouterTableau(new Tableau("tab002_"+ salle2.getId(), "data"+File.separator+"tableaux"+File.separator+"WAR001.jpg")); 
			
			salle3.ajouterTableau(new Tableau("tab001_"+ salle3.getId(), "data"+File.separator+"tableaux"+File.separator+"WAR002.jpg")); 
			salle3.ajouterTableau(new Tableau("tab002_"+ salle3.getId(), "data"+File.separator+"tableaux"+File.separator+"WAR003.jpg")); 
			
			salle4.ajouterTableau(new Tableau("tab001_"+ salle4.getId(), "data"+File.separator+"tableaux"+File.separator+"WAR004.jpg")); 
			salle4.ajouterTableau(new Tableau("tab002_"+ salle4.getId(), "data"+File.separator+"tableaux"+File.separator+"MILLET001.jpg")); 
			
			
			/* ================================================================================================ */
			/* Placement des objets                                                                             */
			/* ================================================================================================ */
			

			/*hall.placerTableau("tab001", "Droite", null);
			hall.placerTableau("tab002", "Gauche", null);

			salle1.placerTableau("tab001", "Droite", null);
			salle1.placerTableau("tab002", "Gauche", null);

			salle2.placerTableau("tab001", "Avant", null);
			salle2.placerTableau("tab002", "Droite", null);

			salle3.placerTableau("tab001", "Avant", null);
			salle3.placerTableau("tab002", "Gauche", null);

			salle4.placerTableau("tab001", "Droite", null);
			salle4.placerTableau("tab002", "Gauche", null);*/
			

			// Ajout de la liste des tableaux du graphe
			/*EnregistrerUtilisateur tmp = new EnregistrerUtilisateur();
			tmp.ouvrir();
			FichierXml f = new FichierXml(Utilisateur.getInstance().getFichier());
			f.ouvrir();*/
			
			
//			List<graphe.Tableau> listTabs;// = Model.get().getListeTableau();
//
//			listTabs = Model.get().getNoeud("Religion").selectionerNTableaux(5);
//			
//			List<Tableau> tableaux_hall = new ArrayList<Tableau>();
//			if(!listTabs.isEmpty()){
//				for(int i = 0; i < listTabs.size(); i++){
//					tableaux_hall.add(new Tableau("tab0"+(i+1), PATH + listTabs.get(i).getId()));
//				}
//			}
//			musee.getSalle(0,0).ajouterTableau(tableaux_hall);
			
			
			//MuseeIA.get().placer(0, 0, 0, Model.get().getNoeud("Patrimoine"));
			//Salle s1 = new Salle("Salle_0-1", 5.0f, 5.0f, 5.0f);
			//s1.placer(0.0f, 0.0f, 0.0f);
			
			monde.setSalleCourante(hall);
			
			Vec3 posAvatar = monde.getSalleCourante().getRepere().getPostiton();
			
			//on place l'Avatar dans la salle courante.
			Avatar.get().getRepere().getPostiton().fixer(posAvatar.x, posAvatar.y, Avatar.HAUTEUR);
			
		}

}
