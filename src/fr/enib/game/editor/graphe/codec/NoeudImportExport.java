/**
 * 
 */
package fr.enib.game.editor.graphe.codec;

import org.w3c.dom.Node;

import fr.enib.game.model.Model;
import fr.enib.game.model.interfaces.IModelObject;
import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.model.interfaces.IObjectInteret;
import fr.enib.game.model.interfaces.IObjectPondere;
import fr.enib.game.model.interfaces.IVisitableObject;

/**
 * @author Corentin Boleis
 *
 */
public class NoeudImportExport extends ICodecModelObject {
	
	private static final NoeudImportExport INSTANCE = new NoeudImportExport();

	/**
	 * 
	 */
	private NoeudImportExport() {
		// rien à faire pour ce constructeur.
	}
	
	/**
	 * 
	 * @return l'unique instance de cette class
	 */
	public static final NoeudImportExport get(){
		return INSTANCE;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.codec.ICodecModelObject#exportObject(java.lang.Object, org.w3c.dom.Node)
	 */
	@Override
	public boolean exportObject(Object obj, Node node) {
		if(obj instanceof IModelObject){
			if(!ModelObjectImportExport.get().exportObject(obj, node)){
				return false;
			}
		}
		else{
			return false;
		}
		if(obj instanceof IVisitableObject){
			if(!VisitableObjectImportExport.get().exportObject(obj, node)){
				return false;
			}
		}
		if(obj instanceof IObjectInteret){
			if(!InteretImportExport.get().exportObject(obj, node)){
				return false;
			}
		}
		if(obj instanceof IObjectPondere){
			if(!PondereObjectImportExport.get().exportObject(obj, node)){
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.codec.ICodecModelObject#importObject(java.lang.Object, org.w3c.dom.Node)
	 */
	@Override
	public boolean importObject(Object obj, Node node) {
		if(obj instanceof IModelObject){
			if(!ModelObjectImportExport.get().importObject(obj, node)){
				return false;
			}
		}
		else{
			return false;
		}
		if(obj instanceof IVisitableObject){
			if(!VisitableObjectImportExport.get().importObject(obj, node)){
				return false;
			}
		}
		if(obj instanceof IObjectInteret){
			if(!InteretImportExport.get().importObject(obj, node)){
				return false;
			}
		}
		if(obj instanceof IObjectPondere){
			if(!PondereObjectImportExport.get().importObject(obj, node)){
				return false;
			}
		}
		if(!(obj instanceof INoeud)){
			return false;
		}
		INoeud noeud = (INoeud) obj;
		//TODO
		Model.get().ajouterModelObject(noeud);
		return true;
	}

}
