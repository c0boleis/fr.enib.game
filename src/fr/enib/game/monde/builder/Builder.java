package fr.enib.game.monde.builder;

import java.io.File;

import fr.enib.game.monde.core.Vec3;
import fr.enib.game.monde.object.Avatar;

public class Builder {
	
	//private final String PATH = "data"+File.separator+"tableaux"+File.separator;
		public static final String PATH = "data"+File.separator+"Image_graphe"+File.separator;
		
		public void construire(){

			/* ================================================================================================ */
			/* Les salles                                                                                       */
			/* ================================================================================================ */

			

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
			//MuseeIA.get().placer();
			
			Vec3 posAvatar = Monde.get().getSalleCourante().getRepere().getPostiton();
			
			//on place l'Avatar dans la salle courante.
			Avatar.get().getRepere().getPostiton().fixer(posAvatar.x, posAvatar.y, Avatar.HAUTEUR);
			
		}

}
