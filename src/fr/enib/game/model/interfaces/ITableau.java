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
	 * @param description
	 * @return true si la description a �t� changer
	 */
	public boolean setDescription(String description);
	
	/**
	 * @return l'URL du tableau
	 */
	public String getUrlImage();
	
	/**
	 * @param url
	 * @return true si l'url de l'image a �t� chang�
	 */
	public boolean setUrlImage(String url);
	
	/**
	 * @return le nom du tableau
	 */
	public String getNomTableau();
	
	/**
	 * @param nom
	 * @return true si le nom a �t� changer
	 */
	public boolean setNomTableau(String nom);
	
}
