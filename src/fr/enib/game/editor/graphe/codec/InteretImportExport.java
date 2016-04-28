/**
 * 
 */
package fr.enib.game.editor.graphe.codec;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import fr.enib.game.model.interfaces.IObjectInteret;

/**
 * @author Corentin Boleis
 *
 */
public class InteretImportExport extends ICodecModelObject {
	
	private static final InteretImportExport INSTANCE = new InteretImportExport();
	
	private static final String INTERET = "interet";

	/**
	 * 
	 */
	private InteretImportExport() {
		// rien à faire pour ce constructeur.
	}
	
	/**
	 * 
	 * @return l'unique instance de cette class
	 */
	public static final InteretImportExport get(){
		return INSTANCE;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.codec.ICodecModelObject#exportObject(java.lang.Object, org.w3c.dom.Node)
	 */
	@Override
	public boolean exportObject(Object obj, Node node) {
		IObjectInteret objectInteret = (IObjectInteret)obj;
		Element elm = node.getOwnerDocument().createElement(INTERET);
		elm.setTextContent(String.valueOf(objectInteret.getDegreInteret()));
		return node.appendChild(elm)!=null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.codec.ICodecModelObject#importObject(java.lang.Object, org.w3c.dom.Node)
	 */
	@Override
	public boolean importObject(Object obj, Node node) {
		IObjectInteret objectInteret = (IObjectInteret)obj;
		Element elm = (Element) getNodeByName(node, INTERET);
		float interet= Float.parseFloat(elm.getTextContent());
		return objectInteret.modifierDegreInteret(interet);
	}

}
