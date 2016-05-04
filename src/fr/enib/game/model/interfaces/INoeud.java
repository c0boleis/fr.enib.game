package fr.enib.game.model.interfaces;

/**
 * @author Corentin Boleis
 *
 */
public interface INoeud extends IModelObject,IVisitableObject,IObjectInteret{
	
	/**
	 * le nom par default des noeuds
	 */
	public static final String NOM_PAR_DEFAULT = "Noeud";
	
	/**
	 * @return la liste de tous les liens
	 * qui sorte du noeud
	 */
	public ILien[] getLiensEntrant();
	
	/**
	 * @param lien
	 * @return true si le lien a �t� ajout�
	 */
	public boolean ajouterLienEntrant(ILien lien);
	
	/**
	 * @param lien
	 * @return true si le lien a �t� suprim�
	 */
	public boolean suprimerLienEntrant(ILien lien);
	
	/**
	 * @return la liste de tous les liens qui sortent
	 * du noeud
	 */
	public ILien[] getLiensSortant();
	
	/**
	 * @param lien
	 * @return true si le lien a �t� ajout�
	 */
	public boolean ajouterLienSortant(ILien lien);
	
	/**
	 * @param lien
	 * @return true si le lien a �t� suprim�
	 */
	public boolean suprimerLienSortant(ILien lien);
	
	/**
	 * @return true si le le noeud n'a pas d'enfant
	 */
	public boolean estFeuille();
}
