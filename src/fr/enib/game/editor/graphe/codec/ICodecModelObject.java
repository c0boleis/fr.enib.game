/**
 * 
 */
package fr.enib.game.editor.graphe.codec;

import org.w3c.dom.Node;

/**
 * @author Corentin Boleis
 *
 */
public interface ICodecModelObject {
	
	/**
	 * @param obj
	 * @param node
	 * @return
	 */
	public boolean exportObject(Object obj,Node node);
	
	/**
	 * @param obj
	 * @param node
	 * @return
	 */
	public boolean importObject(Object obj,Node node);

}
