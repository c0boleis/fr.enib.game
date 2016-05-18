/**
 * 
 */
package fr.enib.game.model;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import fr.enib.game.editor.graphe.examples.swing.action.ModifierTableauAction;
import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.IModel;
import fr.enib.game.model.interfaces.IModelObject;
import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.model.listeners.IListener;
import fr.enib.game.model.listeners.IModelListener;
import fr.enib.game.model.xstreamData.SaveIntoXml;

/**
 * @author Corentin Boleis
 *
 */
@XStreamAlias("Model")
public class Model implements IModel{
		
	private List<IModelObject> modelObjects = new ArrayList<IModelObject>();
	
	@XStreamOmitField
	private List<IListener> listListener = new ArrayList<IListener>();
	
	@XStreamOmitField
	private static final Model INSTANCE = new Model();

	private Model() {
		// rien à faire pour ce constructeur
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
	 * @see fr.enib.game.model.interfaces.IModel#ajouterModelObject(fr.enib.game.model.interfaces.IModelObject)
	 */
	@Override
	public synchronized boolean ajouterModelObject(IModelObject noeud) {
		if(containsModeObject(noeud.getId()))return false;
		this.modelObjects.add(noeud);
		fireAddModelObject(noeud);
		return true;
	}

	/**
	 * @param noeud
	 * 
	 * Cette fonction ajoute, dans le ou les listeners, les objets créés dans le model
	 */
	private void fireAddModelObject(IModelObject noeud) {
		IListener[] listenerTab = this.listListener.toArray(new IListener[0]);
		for(IListener listener : listenerTab){
			if(listener instanceof IModelListener){
				((IModelListener) listener).iModelObjectAdded(noeud);
			}
		}
	}
	
	/**
	 * @param noeud
	 * 
	 * utilisé quand un {@link IModelObject} a été suprimé
	 */
	private void fireRemoveModelObject(IModelObject noeud) {
		IListener[] listenerTab = this.listListener.toArray(new IListener[0]);
		for(IListener listener : listenerTab){
			if(listener instanceof IModelListener){
				((IModelListener) listener).iModelObjectRemoved(noeud);
			}
		}
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
		IModelObject modelObjectRemoved = this.modelObjects.remove(index);
		if(modelObjectRemoved!=null){
			fireRemoveModelObject(modelObjectRemoved);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.listeners.IEcouteurListener#addListener(fr.enib.game.model.listeners.IListener)
	 */
	@Override
	public synchronized void addListener(IListener listener) {
		listListener.add(listener);
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.listeners.IEcouteurListener#removeAllListener()
	 */
	@Override
	public synchronized void removeAllListener() {
		
		if(!listListener.isEmpty() && listListener != null){
			listListener.clear();
		}
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.listeners.IEcouteurListener#removeListener(fr.enib.game.model.listeners.IListener)
	 * This function allow
	 */
	@Override
	public synchronized void removeListener(IListener listener) {
		if(listener != null && listListener.contains(listener)){
			listListener.remove(listener);
		}
		
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModel#refresh()
	 */
	@Override
	public void refresh() {
		IModelObject[] tmp = getModelObjects();
		for(IModelObject object : tmp){
			if( object instanceof ILien){
				((ILien) object).refresh();
			}
		}
		fireAddModelObject(null);
		ModifierTableauAction.checkFileTableau();
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModel#getRoot()
	 */
	@Override
	public INoeud getRoot() {
		IModelObject[] tmp = getModelObjects();
		INoeud noeudOut = null;
		for(IModelObject obj : tmp){
			if(!(obj instanceof INoeud))continue;
			INoeud noeud = (INoeud)obj;
			if(noeud.getLiensEntrant().length==0){
				if(noeudOut!=null){
					return null;
				}
				noeudOut = noeud;
			}
		}
		return noeudOut;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModel#sauvegarderModele()
	 */
	@Override
	public boolean sauvegarderModel(File monFichierXml) {
		// TODO Auto-generated method stub
		if(modelObjects.isEmpty()){
			return false;
		}
		SaveIntoXml masauvegarde = new SaveIntoXml(monFichierXml);
		masauvegarde.enregistrer();
		return true;
	}
	
	/* (non-Javadoc)
	 * @see fr.enib.game.model.interfaces.IModel#importerModel(java.io.File)
	 */
	@Override
	public boolean importerModel(File fichierXml) {
		if(fichierXml == null){return false;};
		if(!fichierXml.exists()){
			return false;
		}
		modelObjects.clear();
		SaveIntoXml importer = new SaveIntoXml(fichierXml);
		Model modele = importer.importer();
		this.modelObjects = modele.modelObjects;
		listListener = new ArrayList<IListener>();
		return true;
	}

}
