/**
 * 
 */
package fr.enib.game.editor.graphe.codec;

import java.util.Map;

import fr.enib.game.editor.graphe.io.mxCodecRegistry;
import fr.enib.game.editor.graphe.io.mxObjectCodec;
import fr.enib.game.model.Model;

/**
 * @author Corentin Boleis
 * @see mxCodecRegistry
 */
public class CodecTableau extends mxObjectCodec {
	
	/**
	 * 
	 */
	public CodecTableau(){
		this(Model.getIModelObjectCreateur().getInstanceTableau(),null,new String[]{},null);
	}

	/**
	 * @param template
	 */
	public CodecTableau(Object template) {
		super(template);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param template
	 * @param exclude
	 * @param idrefs
	 * @param mapping
	 */
	public CodecTableau(Object template, String[] exclude, String[] idrefs,
			Map<String, String> mapping) {
		super(template, exclude, idrefs, mapping);
		// TODO Auto-generated constructor stub
	}

}
