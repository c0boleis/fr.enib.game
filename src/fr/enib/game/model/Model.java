/**
 * 
 */
package fr.enib.game.model;


import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import fr.enib.game.model.interfaces.IModel;
import fr.enib.game.model.interfaces.IModelObject;
import fr.enib.game.model.listeners.IListener;

/**
 * @author Corentin Boleis
 *
 */
public class Model implements IModel{
		
	private List<IModelObject> modelObjects = new ArrayList<IModelObject>();
	
	private static final Model INSTANCE = new Model();

	private Model() {
		// rien � faire pour ce constructeur
	}
	
	/**
	 * 
	 * @return l'unique instance du {@link Model}.
	 */
	public static final Model get(){
		return INSTANCE;
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
		if(modelObjects.isEmpty()){
			return false;
		}
		XStream xstream = new XStream(new DomDriver());
		return true;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModel#ajouterModelObject(fr.enib.game.model.interfaces.IModelObject)
	 */
	@Override
	public synchronized boolean ajouterModelObject(IModelObject noeud) {
		if(containsModeObject(noeud.getId()))return false;
		this.modelObjects.add(noeud);
		return true;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModel#getModelObjects()
	 */
	@Override
	public IModelObject[] getModelObjects() {
		return this.modelObjects.toArray(new IModelObject[0]);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	protected String getNextId(String name){
		if(!containsModeObject(name)){
			return name;
		}
		int index =1;
		String nextId = name+"_"+index;
		while(true){
			if(!containsModeObject(nextId)){
				return nextId;
			}
			index++;
			nextId = name+"_"+index;
			if(index>=Integer.MAX_VALUE-2){
				return null;
			}
		}
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModel#suprmierModelObject(fr.enib.game.model.interfaces.IModelObject)
	 */
	@Override
	public synchronized boolean suprmierModelObject(IModelObject object) {
		IModelObject[] tmp = getModelObjects();
		int index = 0;
		for(IModelObject modelObject : tmp){
			if(modelObject.getId().equals(object.getId())){
				break;
			}
			index++;
		}
		if(index>=tmp.length)return false;
		return this.modelObjects.remove(index)!=null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.listeners.IEcouteurListener#addListener(fr.enib.game.model.listeners.IListener)
	 */
	@Override
	public void addListener(IListener listener) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.listeners.IEcouteurListener#removeAllListener()
	 */
	@Override
	public void removeAllListener() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.listeners.IEcouteurListener#removeListener(fr.enib.game.model.listeners.IListener)
	 */
	@Override
	public void removeListener(IListener listener) {
		// TODO Auto-generated method stub
		
	}

}
