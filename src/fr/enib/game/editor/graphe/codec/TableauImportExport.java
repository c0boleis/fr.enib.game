/**
 * 
 */
package fr.enib.game.editor.graphe.codec;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import fr.enib.game.model.interfaces.IModelObject;
import fr.enib.game.model.interfaces.ITableau;
import fr.enib.game.model.interfaces.IVisitableObject;

/**
 * @author Corentin Boleis
 *
 */
public class TableauImportExport extends ICodecModelObject {
	
	private static final TableauImportExport INSTANCE = new TableauImportExport();
	
	/**
	 * 
	 */
	public static final String DESCRIPTION_TABLEAU = "discription";
	
	/**
	 * 
	 */
	public static final String URL_TABLEAU = "url";
	
	/**
	 * 
	 */
	public static final String NOM_TABLEAU = "nom";

	/**
	 * 
	 */
	private TableauImportExport() {
		// rien à faire pour ce constructeur.
	}
	
	/**
	 * 
	 * @return l'unique instance de cette class
	 */
	public static final TableauImportExport get(){
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
		ITableau noeud = (ITableau)obj;
		
		Element elm = node.getOwnerDocument().createElement(DESCRIPTION_TABLEAU);
		elm.setTextContent(noeud.getDescription());
		if(node.appendChild(elm)==null)return false;
		
		elm = node.getOwnerDocument().createElement(URL_TABLEAU);
		elm.setTextContent(noeud.getUrlImage());
		if(node.appendChild(elm)==null)return false;
		
		elm = node.getOwnerDocument().createElement(NOM_TABLEAU);
		elm.setTextContent(noeud.getUrlImage());
		return node.appendChild(elm)!=null;
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
		ITableau tableau = (ITableau)obj;
		Element elm = (Element) getNodeByName(node, DESCRIPTION_TABLEAU);
		String decription = elm.getTextContent();
		if(!tableau.setDescription(decription))return false;
		
		elm = (Element) getNodeByName(node, URL_TABLEAU);
		String url = elm.getTextContent();
		if(!tableau.setUrlImage(url))return false;
		
		elm = (Element) getNodeByName(node, URL_TABLEAU);
		String nom = elm.getTextContent();
		return tableau.setNomTableau(nom);
	}

}
