package fr.enib.game.model.interfaces;

/**
 * @author Corentin Boleis
 *
 */
public interface INoeud extends IModelObject,IVisitableObject{
	
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
	 * @return la liste de tous les liens qui sortent
	 * du noeud
	 */
	public ILien[] getLiensSortant();
	
	/**
	 * @return true si le le noeud n'a pas d'enfant
	 */
	public boolean estFeuille();
}
