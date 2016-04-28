/**
 * 
 */
package fr.enib.game.editor.graphe.codec;

import java.util.Map;

import org.w3c.dom.Node;

import fr.enib.game.editor.graphe.io.mxCodec;
import fr.enib.game.editor.graphe.io.mxCodecRegistry;
import fr.enib.game.editor.graphe.io.mxObjectCodec;
import fr.enib.game.model.Model;
import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.ITableau;

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
	}
	
	public Node afterEncode(mxCodec enc, Object obj, Node node)
	{
		if(obj instanceof ITableau){
			TableauImportExport.get().exportObject(obj, node);
		}
		return node;
	}
	
	public Object afterDecode(mxCodec dec, Node node, Object obj)
	{
		if(obj instanceof ITableau){
			TableauImportExport.get().importObject(obj, node);
		}
		return obj;
	}

}
