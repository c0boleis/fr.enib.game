/**
 * 
 */
package fr.enib.game.editor.graphe.codec;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import fr.enib.game.model.interfaces.IModelObject;
import fr.enib.game.model.interfaces.INoeud;

/**
 * @author Corentin Boleis
 *
 */
public class ModelObjectImportExport extends ICodecModelObject {
	
	private static final ModelObjectImportExport INSTANCE = new ModelObjectImportExport();
	
	private ModelObjectImportExport(){
		//rien à faire
	}
	
	/**
	 * @return l'unique instance de cette class
	 */
	public static final ModelObjectImportExport get(){
		return INSTANCE;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.codec.ICodecModelObject#exportObject(java.lang.Object, org.w3c.dom.Node)
	 */
	@Override
	public boolean exportObject(Object obj, Node node) {
		INoeud noeud = (INoeud)obj;
		Element elm = node.getOwnerDocument().createElement("id");
		elm.setTextContent(noeud.getId());
		return node.appendChild(elm)!=null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.codec.ICodecModelObject#importObject(java.lang.Object, org.w3c.dom.Node)
	 */
	@Override
	public boolean importObject(Object obj, Node node) {
		Element elm = (Element) getNodeByName(node, "id");
		String id = elm.getTextContent();
		return ((IModelObject) obj).modifierId(id);
	}

}
