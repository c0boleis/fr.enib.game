/**
 * 
 */
package fr.enib.game.editor.graphe.codec;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import fr.enib.game.model.interfaces.IObjectPondere;

/**
 * @author Corentin Boleis
 *
 */
public class PondereObjectImportExport extends ICodecModelObject {
	
	private static final PondereObjectImportExport INSTANCE = new PondereObjectImportExport();

	private static final String POIDS = "poids";

	/**
	 * 
	 */
	private PondereObjectImportExport() {
		// rien à faire pour ce constructeur.
	}
	
	/**
	 * 
	 * @return l'unique instance de cette class
	 */
	public static final PondereObjectImportExport get(){
		return INSTANCE;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.codec.ICodecModelObject#exportObject(java.lang.Object, org.w3c.dom.Node)
	 */
	@Override
	public boolean exportObject(Object obj, Node node) {
		IObjectPondere objectPondere = (IObjectPondere)obj;
		Element elm = node.getOwnerDocument().createElement(POIDS);
		elm.setTextContent(String.valueOf(objectPondere.getPoids()));
		return node.appendChild(elm)!=null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.codec.ICodecModelObject#importObject(java.lang.Object, org.w3c.dom.Node)
	 */
	@Override
	public boolean importObject(Object obj, Node node) {
		IObjectPondere objectPondere = (IObjectPondere)obj;
		Element elm = (Element) getNodeByName(node, POIDS);
		float poids= Float.parseFloat(elm.getTextContent());
		return objectPondere.setPoids(poids);
	}

}
