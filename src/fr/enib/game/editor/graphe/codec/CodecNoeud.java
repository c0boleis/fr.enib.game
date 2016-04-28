/**
 * 
 */
package fr.enib.game.editor.graphe.codec;

import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
public class CodecNoeud extends mxObjectCodec {
	
	/**
	 * 
	 */
	public CodecNoeud(){
		this(Model.getIModelObjectCreateur().getInstanceNoeud(),null,new String[]{},null);
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
	
	public Node afterEncode(mxCodec enc, Object obj, Node node)
	{
		if(obj instanceof INoeud){
			INoeud noeud = (INoeud)obj;
			Element elm = node.getOwnerDocument().createElement("id");
			elm.setTextContent(noeud.getId());
			node.appendChild(elm);
		}
		return node;
	}
	
	public Object afterDecode(mxCodec dec, Node node, Object obj)
	{
		if(obj instanceof Noeud){
			NoeudImportExport.get().importObject(obj, node);
		}
		return obj;
	}

}
