/**
 * 
 */
package fr.enib.game.editor.graphe.codec;

import java.util.Map;

import org.w3c.dom.Node;

import fr.enib.game.editor.graphe.io.mxCellCodec;
import fr.enib.game.editor.graphe.io.mxCodec;
import fr.enib.game.editor.graphe.io.mxCodecRegistry;
import fr.enib.game.editor.graphe.io.mxObjectCodec;
import fr.enib.game.model.Lien;
import fr.enib.game.model.Model;
import fr.enib.game.model.interfaces.ILien;

/**
 * @author Corentin Boleis
 * @see mxCodecRegistry
 *
 */
public class CodecLien extends mxCellCodec {
	
	/**
	 * 
	 */
	public CodecLien(){
		this(new Lien(),null,new String[]{},null);
	}

	/**
	 * @param template
	 */
	public CodecLien(Object template) {
		super(template);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param template
	 * @param exclude
	 * @param idrefs
	 * @param mapping
	 */
	public CodecLien(Object template, String[] exclude, String[] idrefs,
			Map<String, String> mapping) {
		super(template, exclude, idrefs, mapping);
	}
	
	public Node afterEncode(mxCodec enc, Object obj, Node node)
	{
		if(obj instanceof ILien){
			LienImportExport.get().exportObject(obj, node);
		}
		return super.afterEncode(enc, obj, node);
	}
	
	public Object afterDecode(mxCodec dec, Node node, Object obj)
	{
		if(obj instanceof ILien){
			LienImportExport.get().importObject(obj, node);
		}
		return super.afterDecode(dec, node, obj);
	}

}
