package fr.enib.game.editor.graphe.examples.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.w3c.dom.Document;

import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions;
import fr.enib.game.editor.graphe.io.mxCodec;
import fr.enib.game.editor.graphe.swing.mxGraphComponent;
import fr.enib.game.editor.graphe.util.mxUtils;
import fr.enib.game.editor.graphe.view.mxGraph;


/**
 *
 */
@SuppressWarnings("serial")
public class StylesheetAction extends AbstractAction
{
	/**
	 * 
	 */
	protected String stylesheet;

	/**
	 * @param stylesheet 
	 * 
	 */
	public StylesheetAction(String stylesheet)
	{
		this.stylesheet = stylesheet;
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() instanceof mxGraphComponent)
		{
			mxGraphComponent graphComponent = (mxGraphComponent) e
					.getSource();
			mxGraph graph = graphComponent.getGraph();
			mxCodec codec = new mxCodec();
			Document doc = mxUtils.loadDocument(EditorActions.class
					.getResource(stylesheet).toString());

			if (doc != null)
			{
				codec.decode(doc.getDocumentElement(),
						graph.getStylesheet());
				graph.refresh();
			}
		}
	}
}