/**
 * 
 */
package fr.enib.game.editor.graphe.codec;

import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.enib.game.editor.graphe.io.mxCellCodec;
import fr.enib.game.editor.graphe.io.mxCodec;
import fr.enib.game.editor.graphe.io.mxCodecRegistry;
import fr.enib.game.editor.graphe.io.mxObjectCodec;
import fr.enib.game.editor.graphe.model.mxCell;
import fr.enib.game.model.Model;
import fr.enib.game.model.Noeud;
import fr.enib.game.model.interfaces.INoeud;

/**
 * @author Corentin Boleis
 * @see mxCodecRegistry
 *
 */
public class CodecNoeud extends mxCellCodec {
	
	/**
	 * 
	 */
	public CodecNoeud(){
		this(new Noeud(),null,new String[]{},null);
	}

	/**
	 * @param template
	 */
	public CodecNoeud(Object template) {
		super(template);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param template
	 * @param exclude
	 * @param idrefs
	 * @param mapping
	 */
	public CodecNoeud(Object template, String[] exclude, String[] idrefs,
			Map<String, String> mapping) {
		super(template, exclude, idrefs, mapping);
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.io.mxObjectCodec#afterEncode(fr.enib.game.editor.graphe.io.mxCodec, java.lang.Object, org.w3c.dom.Node)
	 */
	@Override
	public Node afterEncode(mxCodec enc, Object obj, Node node)
	{
		if(obj instanceof INoeud){
			NoeudImportExport.get().exportObject(obj, node);
		}
		return super.afterEncode(enc, obj, node);
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.io.mxObjectCodec#afterDecode(fr.enib.game.editor.graphe.io.mxCodec, org.w3c.dom.Node, java.lang.Object)
	 */
	@Override
	public Object afterDecode(mxCodec dec, Node node, Object obj)
	{
		if(obj instanceof Noeud){
			NoeudImportExport.get().importObject(obj, node);
		}
		return super.afterDecode(dec, node, obj);
	}

}
