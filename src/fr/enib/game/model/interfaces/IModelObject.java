package fr.enib.game.model.interfaces;

/**
 * Tout objet d'un graphe implement
 * cette class
 * @author Corentin Boleis
 *
 */
public interface IModelObject extends Cloneable{
	
	/**
	 * il ne doit pas exist" deux objet
	 * avec le même id.
	 * @return the id.
	 */
	public String getId();
	
	/**
	 * @return le poid de l'objet qui est utilisé
	 * pour les parcours de graphe.
	 */
	public float getPoid();
	
	/**
	 * @param newPoid
	 */
	public void setPoid(float newPoid);

}
