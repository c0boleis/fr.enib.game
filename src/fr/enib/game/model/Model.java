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
	
	/**
	 * @return
	 */
	public static IModelObjectCreateur getIModelObjectCreateur(){
		if(modelObjectCreateur==null){
			modelObjectCreateur = new ModelObjectCreator();
		}
		return modelObjectCreateur;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObject#getDegre()
	 */
	@Override
	public float getDegre() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObject#setDegre(float)
	 */
	@Override
	public void setDegre(float newPoid) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModelObject#modifierId(java.lang.String)
	 */
	@Override
	public boolean modifierId(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}
