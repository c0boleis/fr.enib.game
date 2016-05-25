package fr.enib.game.monde.builder;

/**
 * Interface pour actualiser lors du changement de salle
 * @author Ronan
 *
 */
public interface IActualisation {

	/**
	 * Callback permettant d'actualiser l'environnement 3D lors du changement de salle 
	 * @param id l'id de la nouvelle salle
	 */
	void changementSalle(String id);
}
