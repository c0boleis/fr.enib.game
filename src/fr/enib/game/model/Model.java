/**
 * 
 */
package fr.enib.game.model;

import fr.enib.game.model.interfaces.IModelObject;
import fr.enib.game.model.interfaces.IModelObjectCreateur;

/**
 * @author Corentin Boleis
 *
 */
public class Model implements IModelObject{
	
	private static IModelObjectCreateur modelObjectCreateur;

	/**
	 * 
	 */
	public Model() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObject#getId()
	 */
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObject#getPoid()
	 */
	@Override
	public float getPoid() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObject#setPoid(float)
	 */
	@Override
	public void setPoid(float newPoid) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return
	 */
	public static IModelObjectCreateur getIModelObjectCreateur(){
		if(modelObjectCreateur==null){
			modelObjectCreateur = new ModelObjectCreator();
		}
		return modelObjectCreateur;
	}

}
