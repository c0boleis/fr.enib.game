/**
 * 
 */
package fr.enib.game.editor.graphe.codec;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import fr.enib.game.model.interfaces.IVisitableObject;

/**
 * @author Corentin Boleis
 *
 */
public class VisitableObjectImportExport extends ICodecModelObject {
	
	private static final VisitableObjectImportExport INSTANCE = new VisitableObjectImportExport();

	/**
	 * 
	 */
	private VisitableObjectImportExport() {
		// rien à faire pour ce constructeur.
	}
	
	/**
	 * 
	 * @return l'unique instance de cette class
	 */
	public static final VisitableObjectImportExport get(){
		return INSTANCE;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.codec.ICodecModelObject#exportObject(java.lang.Object, org.w3c.dom.Node)
	 */
	@Override
	public boolean exportObject(Object obj, Node node) {
		IVisitableObject visitableObject = (IVisitableObject)obj;
		Element elm = node.getOwnerDocument().createElement("visiter");
		elm.setTextContent(String.valueOf(visitableObject.estVisiter()));
		return node.appendChild(elm)!=null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.codec.ICodecModelObject#importObject(java.lang.Object, org.w3c.dom.Node)
	 */
	@Override
	public boolean importObject(Object obj, Node node) {
		Element elm = (Element) getNodeByName(node, "visiter");
		boolean visiter= Boolean.parseBoolean(elm.getTextContent());
		if(visiter){
			((IVisitableObject) obj).visiter();
		}
		else{
			((IVisitableObject) obj).resetVisiter();
		}
		return true;
	}

}
