/**
 * 
 */
package fr.enib.game.model;


import java.util.ArrayList;
import java.util.List;

import fr.enib.game.model.interfaces.IModel;
import fr.enib.game.model.interfaces.IModelObject;
import fr.enib.game.model.interfaces.IModelObjectCreateur;

/**
 * @author Corentin Boleis
 *
 */
public class Model implements IModel{
	
	private static IModelObjectCreateur modelObjectCreateur;
	
	private List<IModelObject> modelObjects = new ArrayList<IModelObject>();
	
	private static final IModel INSTANCE = new Model();

	private Model() {
		// rien à faire pour ce constructeur
	}
	
	/**
	 * 
	 * @return l'unique instance du {@link Model}.
	 */
	public static final IModel get(){
		return INSTANCE;
	}
	
	/**
	 * @return {@link IModelObjectCreateur}
	 */
	public static IModelObjectCreateur getIModelObjectCreateur(){
		if(modelObjectCreateur==null){
			modelObjectCreateur = new ModelObjectCreator();
		}
		return modelObjectCreateur;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModel#getModelObject(java.lang.String)
	 */
	@Override
	public IModelObject getModelObject(String id) {
		IModelObject[] tmp = getModelObjects();
		for(IModelObject object : tmp){
			if(object.getId().equals(id)){
				return object;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModel#containsModeObject(java.lang.String)
	 */
	@Override
	public boolean containsModeObject(String id) {
		IModelObject[] tmp = getModelObjects();
		for(IModelObject object : tmp){
			if(object.getId().equals(id)){
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModel#sauvegarderModele()
	 */
	@Override
	public boolean sauvegarderModel() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModel#ajouterModelObject(fr.enib.game.model.interfaces.IModelObject)
	 */
	@Override
	public synchronized boolean ajouterModelObject(IModelObject noeud) {
		if(containsModeObject(noeud.getId()))return false;
		this.modelObjects.add(noeud);
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModel#getModelObjects()
	 */
	@Override
	public IModelObject[] getModelObjects() {
		return this.modelObjects.toArray(new IModelObject[0]);
	}

}
