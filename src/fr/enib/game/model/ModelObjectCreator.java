package fr.enib.game.model;

import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.IModelObjectCreateur;
import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.model.interfaces.ITableau;


/**
 * @author Corentin Boleis
 *
 */
public class ModelObjectCreator implements IModelObjectCreateur{

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObjectCreateur#creerLien(fr.enib.game.model.interfaces.INoeud, fr.enib.game.model.interfaces.INoeud)
	 */
	@Override
	public ILien creerLien(INoeud noeudDepart, INoeud noeudArrivee) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObjectCreateur#creerTableau(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ITableau creerTableau(String nom, String url, String descpription) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObjectCreateur#creerNoeud(java.lang.String)
	 */
	@Override
	public INoeud creerNoeud(String nom) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObjectCreateur#getInstanceLien()
	 */
	@Override
	public ILien getInstanceLien() {
		return new Lien();
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObjectCreateur#getInstanceNoeud()
	 */
	@Override
	public INoeud getInstanceNoeud() {
		return new Noeud();
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObjectCreateur#getInstanceTableau()
	 */
	@Override
	public ITableau getInstanceTableau() {
		return new Tableau();
	}
	
	
}
