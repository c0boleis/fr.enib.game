package fr.enib.game.model.interfaces;

import java.util.ArrayList;

import fr.enib.game.model.Tableau;
import fr.enib.game.model.enums.AjoutLienInfos;

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
	 * @return {@link AjoutLienInfos#ok} si le lien a �t� ajout�
	 */
	public AjoutLienInfos ajouterLienEntrant(ILien lien);
	
	/**
	 * @return true si on peut ajouter un lien Sortant
	 */
	public boolean lienEntrantSontConectables();
	
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
	 * @return {@link AjoutLienInfos#ok} si le lien a �t� ajout�
	 */
	public AjoutLienInfos ajouterLienSortant(ILien lien);
	
	/**
	 * @return true si on peut ajouter un lien Sortant
	 */
	public boolean lienSortantSontConectables();
	
	/**
	 * @param lien
	 * @return {@link AjoutLienInfos#ok} si le lien a �t� suprim�
	 */
	public boolean suprimerLienSortant(ILien lien);
	
	/**
	 * @return true si le le noeud n'a pas d'enfant
	 */
	public boolean estFeuille();
	
	/**
	 * 
	 * @param id : id du noeud dont on cherche tous les tableaux. 
	 * @return liste des tableaux associ�s au noeud.
	 */
	public ArrayList<ITableau> getTableau(String id);
	
	/**
	 * 
	 * @return liste des noeuds descendants associ�s au noeud parent.
	 */
	public ArrayList<INoeud> getNoeudsDescendantDirect();
}
