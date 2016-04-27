package fr.enib.game.model.interfaces;

/**
 * @author Corentin Boleis
 *
 */
public interface ITableau extends INoeud{
	
	/**
	 * le nom par default des tableaux
	 */
	public static final String NOM_PAR_DEFAULT = "Tableau";
	
	/**
	 * @return la description du tableau
	 */
	public String getDescription();
	
	/**
	 * @return l'URL du tableau
	 */
	public String getUrlImage();
	
	/**
	 * @return le nom du tableau
	 */
	public String getNomTableau();
	
}
