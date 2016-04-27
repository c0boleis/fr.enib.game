package fr.enib.game.editor.graphe.examples.swing.actions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import fr.enib.game.editor.graphe.examples.swing.editor.BasicGraphEditor;
import fr.enib.game.editor.graphe.examples.swing.editor.DefaultFileFilter;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions;
import fr.enib.game.editor.graphe.io.mxCodec;
import fr.enib.game.editor.graphe.swing.mxGraphComponent;
import fr.enib.game.editor.graphe.util.mxCellRenderer;
import fr.enib.game.editor.graphe.util.mxResources;
import fr.enib.game.editor.graphe.util.mxUtils;
import fr.enib.game.editor.graphe.util.mxXmlUtils;
import fr.enib.game.editor.graphe.util.png.mxPngEncodeParam;
import fr.enib.game.editor.graphe.util.png.mxPngImageEncoder;
import fr.enib.game.editor.graphe.view.mxGraph;

/**
 *
 */
@SuppressWarnings("serial")
public class SaveAction extends AbstractAction
{
	/**
	 * 
	 */
	protected boolean showDialog;

	/**
	 * 
	 */
	protected String lastDir = null;

	/**
	 * @param showDialog 
	 * 
	 */
	public SaveAction(boolean showDialog)
	{
		this.showDialog = showDialog;
	}

	/**
	 * Saves XML+PNG format.
	 */
	protected void saveXmlPng(BasicGraphEditor editor, String filename,
			Color bg) throws IOException
	{
		mxGraphComponent graphComponent = editor.getGraphComponent();
		mxGraph graph = graphComponent.getGraph();

		// Creates the image for the PNG file
		BufferedImage image = mxCellRenderer.createBufferedImage(graph,
				null, 1, bg, graphComponent.isAntiAlias(), null,
				graphComponent.getCanvas());

		// Creates the URL-encoded XML data
		mxCodec codec = new mxCodec();
		String xml = URLEncoder.encode(
				mxXmlUtils.getXml(codec.encode(graph.getModel())), "UTF-8");
		mxPngEncodeParam param = mxPngEncodeParam
				.getDefaultEncodeParam(image);
		param.setCompressedText(new String[] { "mxGraphModel", xml });

		// Saves as a PNG file
		FileOutputStream outputStream = new FileOutputStream(new File(
				filename));
		try
		{
			mxPngImageEncoder encoder = new mxPngImageEncoder(outputStream,
					param);

			if (image != null)
			{
				encoder.encode(image);

				editor.setModified(false);
				editor.setCurrentFile(new File(filename));
			}
			else
			{
				JOptionPane.showMessageDialog(graphComponent,
						mxResources.get("noImageData"));
			}
		}
		finally
		{
			outputStream.close();
		}
	}

	private String askFileName(BasicGraphEditor editor,
			DefaultFileFilter xmlPngFilter,
			FileFilter vmlFileFilter, mxGraphComponent graphComponent){
		String filename = null;
		FileFilter selectedFilter = null;
		String wd;

		if (lastDir != null)
		{
			wd = lastDir;
		}
		else if (editor.getCurrentFile() != null)
		{
			wd = editor.getCurrentFile().getParent();
		}
		else
		{
			wd = System.getProperty("user.dir");
		}

		JFileChooser fc = new JFileChooser(wd);

		// Adds the default file format
		FileFilter defaultFilter = xmlPngFilter;
		fc.addChoosableFileFilter(defaultFilter);
		fc.addChoosableFileFilter(vmlFileFilter);
		// Adds a filter for each supported image format
//		Object[] imageFormats = ImageIO.getReaderFormatNames();
//
//		// Finds all distinct extensions
//		HashSet<String> formats = new HashSet<String>();
//
//		for (int i = 0; i < imageFormats.length; i++)
//		{
//			String ext = imageFormats[i].toString().toLowerCase();
//			formats.add(ext);
//		}
//
//		imageFormats = formats.toArray();
//
//		for (int i = 0; i < imageFormats.length; i++)
//		{
//			String ext = imageFormats[i].toString();
//			fc.addChoosableFileFilter(new DefaultFileFilter("."
//					+ ext, ext.toUpperCase() + " "
//							+ mxResources.get("file") + " (." + ext + ")"));
//		}

		// Adds filter that accepts all supported image formats
		fc.addChoosableFileFilter(new DefaultFileFilter.ImageFileFilter(
				mxResources.get("allImages")));
		fc.setFileFilter(defaultFilter);
		int rc = fc.showDialog(null, mxResources.get("save"));

		if (rc != JFileChooser.APPROVE_OPTION)
		{
			return null;
		}
		else
		{
			lastDir = fc.getSelectedFile().getParent();
		}

		filename = fc.getSelectedFile().getAbsolutePath();
		selectedFilter = fc.getFileFilter();

		if (selectedFilter instanceof DefaultFileFilter)
		{
			String ext = ((DefaultFileFilter) selectedFilter)
					.getExtension();

			if (!filename.toLowerCase().endsWith(ext))
			{
				filename += ext;
			}
		}

		if (new File(filename).exists()
				&& JOptionPane.showConfirmDialog(graphComponent,
						mxResources.get("overwriteExistingFile")) != JOptionPane.YES_OPTION)
		{
			return null;
		}
		return filename;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		BasicGraphEditor editor = EditorActions.getEditor(e);

		if (editor == null)return;
		mxGraphComponent graphComponent = editor.getGraphComponent();
		mxGraph graph = graphComponent.getGraph();
		DefaultFileFilter xmlPngFilter = new DefaultFileFilter(".png",
				"PNG+XML " + mxResources.get("file") + " (.png)");
		FileFilter vmlFileFilter = new DefaultFileFilter(".xml",
				"VML " + mxResources.get("file") + " (.xml)");
		String filename = null;

		if (showDialog || editor.getCurrentFile() == null)
		{
			filename = askFileName(editor,xmlPngFilter,vmlFileFilter,graphComponent);
		}
		else
		{
			filename = editor.getCurrentFile().getAbsolutePath();
		}
		if(filename==null)return;
		try
		{
			String ext = filename
					.substring(filename.lastIndexOf('.') + 1);

			if (!ext.equalsIgnoreCase("xml"))return;
			
			mxCodec codec = new mxCodec();
			codec.setEncodeDefaults(true);
			String xml = mxXmlUtils.getXml(codec.encode(graph
					.getModel()));

			mxUtils.writeFile(xml, filename);

			editor.setModified(false);
			editor.setCurrentFile(new File(filename));
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(graphComponent,
					ex.toString(), mxResources.get("error"),
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
