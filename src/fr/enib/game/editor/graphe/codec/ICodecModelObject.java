/**
 * 
 */
package fr.enib.game.editor.graphe.codec;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Corentin Boleis
 *
 */
public abstract class ICodecModelObject {
	
	/**
	 * @param obj
	 * @param node
	 * @return true si l'objet à été exporté
	 */
	public abstract boolean exportObject(Object obj,Node node);
	
	/**
	 * @param obj
	 * @param node
	 * @return true si l'objet a été chargé
	 */
	public abstract boolean importObject(Object obj,Node node);
	
	/**
	 * @param node
	 * @param name
	 * @return {@link Node}
	 */
	public static Node getNodeByName(Node node,String name){
		NodeList list = node.getChildNodes();
		int size = list.getLength();
		for(int index = 0;index<size;index++){
			Node elm = list.item(index);
			if(elm.getNodeName().equals(name)){
				return elm;
			}
		}
		return null;
	}

}
