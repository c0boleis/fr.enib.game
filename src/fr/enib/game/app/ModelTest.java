package fr.enib.game.app;

import fr.enib.game.model.Lien;
import fr.enib.game.model.Model;
import fr.enib.game.model.Noeud;
import fr.enib.game.model.Tableau;

/**
 * @author Roanan Morel
 *
 */
public class ModelTest {

	/**
	 * test le model de données
	 * @param args
	 */
	public static void main(String[] args) {
		Model monModel = Model.get();
		Noeud noeud = new Noeud();
		Tableau monTab = new Tableau();
		Lien monLien = new Lien(); 
		
		monModel.ajouterModelObject(monTab);
		monModel.ajouterModelObject(noeud);
		monModel.ajouterModelObject(monLien);
		
		//monModel.sauvegarderModel();

	}

}
