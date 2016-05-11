/**
 * 
 */
package fr.enib.game.editor.graphe.codec;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import fr.enib.game.model.Model;
import fr.enib.game.model.Noeud;
import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.IModelObject;
import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.model.interfaces.IObjectInteret;
import fr.enib.game.model.interfaces.IObjectPondere;
import fr.enib.game.model.interfaces.IVisitableObject;

/**
 * @author Corentin Boleis
 *
 */
public class LienImportExport extends ICodecModelObject {
	
	private static final LienImportExport INSTANCE = new LienImportExport();
	
	private static final String ID_NOEUD_ARRIVEE = "id_arrivee";
	
	private static final String ID_NOEUD_DEPART = "id_depart";
	
	private LienImportExport() {
		// rien à faire
	}
	
	/**
	 * @return l'unique instance de cette calss
	 */
	public static final LienImportExport get(){
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
		ILien visitableObject = (ILien)obj;
		Element elm = node.getOwnerDocument().createElement(ID_NOEUD_ARRIVEE);
		INoeud noeud = visitableObject.getNoeudArrivee();
		String idNoeud = "N/A";
		if(noeud !=null){
			idNoeud = noeud.getId();
		}
		
		elm.setTextContent(idNoeud);
		if(node.appendChild(elm)==null)return false;
		
		noeud = visitableObject.getNoeudDepart();
		idNoeud = "N/A";
		if(noeud !=null){
			idNoeud = noeud.getId();
		}
		elm = node.getOwnerDocument().createElement(ID_NOEUD_DEPART);
		elm.setTextContent(idNoeud);
		return node.appendChild(elm)!=null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.codec.ICodecModelObject#importObject(java.lang.Object, org.w3c.dom.Node)
	 */
	@Override
	public boolean importObject(Object obj, Node node) {
		if(obj instanceof IModelObject){
			ModelObjectImportExport.get().importObject(obj, node);
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
		ILien lien = (ILien)obj;
		Element elm = (Element) getNodeByName(node, ID_NOEUD_ARRIVEE);
		String id_arrivee = elm.getTextContent();
		//TODO
		
		elm = (Element) getNodeByName(node, ID_NOEUD_DEPART);
		String id_depart = elm.getTextContent();
		//TODO
		Model.get().ajouterModelObject(lien);
		return true;
	}

}
