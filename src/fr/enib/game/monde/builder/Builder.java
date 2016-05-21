package fr.enib.game.monde.builder;

import org.apache.log4j.Logger;

import fr.enib.game.model.Model;
import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.monde.musee.Musee;
import fr.enib.game.monde.objet.Avatar;
import fr.enib.game.parcours.graphe.Parcours;

/**
 * 
 * @author Ronan MOREL
 *
 */
public class Builder {
	private static Logger LOGGER = Logger.getLogger(Builder.class);

	private INoeud[] noeuds;
	
	private Musee musee;
	
	private Parcours parcoursP;
	
	/**
	 * Constructeur
	 */
	public Builder(){
		musee = new Musee();
		
		IActualisation iActu = new IActualisation() {
			@Override
			public void changementSalle(String id) {
				INoeud n = getNoeudById(id);
				if(n != null){
					parcoursP.setNoeudCourant(n);
					musee.setPositionCentre(Avatar.get().getPositionRepere());
					construire();
				}
			}
		};
		Monde.get().setiActu(iActu);
		
		INoeud n = Model.get().getRoot();
		if(n == null){
			LOGGER.error("Error");
		}
		parcoursP = new Parcours(n);
	}
	
	/**
	 * Recupere un noeud (parmis les noeuds courants (courant + voisins) ) en fonction de son id
	 * @param id l'id du noeud a trouver
	 * @return le noeud est fonction de l'ID, s'il ne trouve pas -> null
	 */
	public INoeud getNoeudById(String id){
		if(id == null){
			LOGGER.error("id null");
			return null;
		}
		if(noeuds == null){
			LOGGER.error("noeuds null");
			return null;
		}
		for(INoeud n : noeuds){
			if(id.equals(n.getId())){
				return n;
			}
		}
		return null;
	}
	
	/**
	 * Recalcul les noeuds et dessine les salles
	 */
	public void construire(){
		long time = System.currentTimeMillis();
		noeuds = parcoursP.calcul_Noeud_Suivant();
		
		if(musee == null){
			LOGGER.info("Error musée null");
			return;
		}
		if(noeuds == null){
			LOGGER.info("noeuds null");
			return;
		}
		if(noeuds.length <= 0){
			LOGGER.info("nombre de noeuds = 0");
			return;
		}
		
		boolean error = false;
		for(int i = 0; i < noeuds.length; i++){
			if(!error && noeuds[i] == null){
				LOGGER.info("noeuds[" + i + "] null");
				error = true;
			}
			if(!error && noeuds[i].getId() == null){
				LOGGER.info("noeuds[" + i + "] id null");
				error = true;
			}
			if(!error && noeuds[i].getTableau() == null){
				LOGGER.info("noeuds[" + i + "] tableaux null");
				error = true;
			}
			if(!error && noeuds[i].getTableau().isEmpty()){
				LOGGER.info("noeuds[" + i + "] " + noeuds[i].getId() + " tableaux => 0");
				error = true;
			}
			if(!error){
				musee.ajouterListeTableaux(noeuds[i].getId(), noeuds[i].getTableau());
			}
			error = false;
		}
		musee.genererSalles();
		long time2 = System.currentTimeMillis();
		System.err.println("construire : " +(time2-time));
	}
	
}