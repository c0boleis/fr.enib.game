/**
 * 
 */
package fr.enib.game.editor.graphe.model.graph;

import fr.enib.game.model.interfaces.IModelObject;

/**
 * @author Corentin Boleis
 *
 */
public class ModelObjectCreator implements IModelObject{
	
	/**
	 * @author Corentin Boleis
	 *
	 */
	public enum ModeObjectType{
		lien,
		noeud,
		tableau;
	}

	/**
	 * 
	 */
	private ModelObjectCreator() {
		// TODO Auto-generated constructor stub
	}
	
	public static IModelObject createLien(){
		//TODO
		return null;
	}
	
	public static IModelObject createNoeud(){
		//TODO
		return null;
	}
	
	public static IModelObject createTableau(){
		//TODO
		return null;
	}

	/* (non-Javadoc)
	 * @see graphe.model.IModelObject#getId()
	 */
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graphe.model.IModelObject#getPoid()
	 */
	@Override
	public float getPoid() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see graphe.model.IModelObject#setPoid(float)
	 */
	@Override
	public void setPoid(float arg0) {
		// TODO Auto-generated method stub
		
	}

}
