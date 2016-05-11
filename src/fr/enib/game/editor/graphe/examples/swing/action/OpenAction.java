package fr.enib.game.editor.graphe.examples.swing.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.w3c.dom.Document;

import fr.enib.game.editor.graphe.examples.swing.editor.BasicGraphEditor;
import fr.enib.game.editor.graphe.examples.swing.editor.DefaultFileFilter;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions;
import fr.enib.game.editor.graphe.io.mxCodec;
import fr.enib.game.editor.graphe.io.mxGdCodec;
import fr.enib.game.editor.graphe.model.mxGraphModel;
import fr.enib.game.editor.graphe.util.mxResources;
import fr.enib.game.editor.graphe.util.mxUtils;
import fr.enib.game.editor.graphe.util.mxXmlUtils;
import fr.enib.game.editor.graphe.util.png.mxPngTextDecoder;
import fr.enib.game.editor.graphe.view.mxGraph;
import fr.enib.game.model.Model;


/**
 *
 */
@SuppressWarnings("serial")
public class OpenAction extends AbstractAction
{
	/**
	 * 
	 */
	protected String lastDir;

	/**
	 * 
	 */
	protected void resetEditor(BasicGraphEditor editor)
	{
		editor.setModified(false);
		editor.getUndoManager().clear();
		editor.getGraphComponent().zoomAndCenter();
	}

	/**
	 * Reads XML+PNG format.
	 */
	protected void openXmlPng(BasicGraphEditor editor, File file)
			throws IOException
	{
		Map<String, String> text = mxPngTextDecoder
				.decodeCompressedText(new FileInputStream(file));

		if (text != null)
		{
			String value = text.get("mxGraphModel");

			if (value != null)
			{
				Document document = mxXmlUtils.parseXml(URLDecoder.decode(
						value, "UTF-8"));
				mxCodec codec = new mxCodec(document);
				codec.decode(document.getDocumentElement(), editor
						.getGraphComponent().getGraph().getModel());
				editor.setCurrentFile(file);
				resetEditor(editor);

				return;
			}
		}

		JOptionPane.showMessageDialog(editor,
				mxResources.get("imageContainsNoDiagramData"));
	}

	/**
	 * @throws IOException
	 *
	 */
	protected void openGD(BasicGraphEditor editor, File file,
			String gdText)
	{
		mxGraph graph = editor.getGraphComponent().getGraph();

		// Replaces file extension with .mxe
		String filename = file.getName();
		filename = filename.substring(0, filename.length() - 4) + ".mxe";

		if (new File(filename).exists()
				&& JOptionPane.showConfirmDialog(editor,
						mxResources.get("overwriteExistingFile")) != JOptionPane.YES_OPTION)
		{
			return;
		}
		((mxGraphModel) graph.getModel()).clear();
		mxGdCodec.decode(gdText, graph);
		editor.getGraphComponent().zoomAndCenter();
		editor.setCurrentFile(new File(lastDir + "/" + filename));
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e)
	{
		BasicGraphEditor editor = EditorActions.getEditor(e);

		if (editor != null)
		{
			if (!editor.isModified()
					|| JOptionPane.showConfirmDialog(editor,
							mxResources.get("loseChanges")) == JOptionPane.YES_OPTION)
			{
				mxGraph graph = editor.getGraphComponent().getGraph();

				if (graph != null)
				{
					String wd = (lastDir != null) ? lastDir : System
							.getProperty("user.dir");

					JFileChooser fc = new JFileChooser(wd);

					// Adds file filter for supported file format
					//					DefaultFileFilter defaultFilter = new DefaultFileFilter(
					//							".mxe", mxResources.get("allSupportedFormats")
					//									+ " (.mxe, .png, .vdx)")
					//					{
					//
					//						public boolean accept(File file)
					//						{
					//							String lcase = file.getName().toLowerCase();
					//
					//							return super.accept(file)
					//									|| lcase.endsWith(".png")
					//									|| lcase.endsWith(".vdx");
					//						}
					//					};
					//					fc.addChoosableFileFilter(defaultFilter);
					DefaultFileFilter filter = new DefaultFileFilter(".mxe",
							"mxGraph Editor " + mxResources.get("file")
							+ " (.mxe)");
					fc.addChoosableFileFilter(filter);

					fc.setFileFilter(filter);

					int rc = fc.showDialog(null,
							mxResources.get("openFile"));

					if (rc == JFileChooser.APPROVE_OPTION)
					{
						lastDir = fc.getSelectedFile().getParent();

						try
						{
							Document document = mxXmlUtils
									.parseXml(mxUtils.readFile(fc
											.getSelectedFile()
											.getAbsolutePath()));

							mxCodec codec = new mxCodec(document);
							codec.decode(
									document.getDocumentElement(),
									graph.getModel());
							editor.setCurrentFile(fc
									.getSelectedFile());

							resetEditor(editor);
							Model.get().refresh();
						}
						catch (IOException ex)
						{
							ex.printStackTrace();
							JOptionPane.showMessageDialog(
									editor.getGraphComponent(),
									ex.toString(),
									mxResources.get("error"),
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		}
	}
}