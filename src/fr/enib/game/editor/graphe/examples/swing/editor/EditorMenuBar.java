package fr.enib.game.editor.graphe.examples.swing.editor;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;

import fr.enib.game.editor.graphe.analysis.StructuralException;
import fr.enib.game.editor.graphe.analysis.mxAnalysisGraph;
import fr.enib.game.editor.graphe.analysis.mxGraphProperties;
import fr.enib.game.editor.graphe.analysis.mxGraphProperties.GraphType;
import fr.enib.game.editor.graphe.analysis.mxGraphStructure;
import fr.enib.game.editor.graphe.analysis.mxTraversal;
import fr.enib.game.editor.graphe.examples.swing.action.AlignCellsAction;
import fr.enib.game.editor.graphe.examples.swing.action.AutosizeAction;
import fr.enib.game.editor.graphe.examples.swing.action.ExitAction;
import fr.enib.game.editor.graphe.examples.swing.action.ExportModelAction;
import fr.enib.game.editor.graphe.examples.swing.action.OpenAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.BackgroundAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.BackgroundImageAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.ColorAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.GridColorAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.GridStyleAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.HistoryAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.KeyValueAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.PageBackgroundAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.PageSetupAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.PrintAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.PromptPropertyAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.PromptValueAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.SaveAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.ScaleAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.SetLabelPositionAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.SetStyleAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.StyleAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.ToggleAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.ToggleGridItem;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.ToggleOutlineItem;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.TogglePropertyItem;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.ToggleRulersItem;
import fr.enib.game.editor.graphe.model.mxIGraphModel;
import fr.enib.game.editor.graphe.swing.mxGraphComponent;
import fr.enib.game.editor.graphe.swing.util.mxGraphActions;
import fr.enib.game.editor.graphe.util.mxConstants;
import fr.enib.game.editor.graphe.util.mxPoint;
import fr.enib.game.editor.graphe.util.mxResources;
import fr.enib.game.editor.graphe.view.mxGraph;
import fr.enib.game.editor.graphe.view.mxGraphView;

public class EditorMenuBar extends JMenuBar
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4060203894740766714L;

	/**
	 * @param editor
	 */
	public EditorMenuBar(final BasicGraphEditor editor)
	{
		final mxGraphComponent graphComponent = editor.getGraphComponent();
		final mxGraph graph = graphComponent.getGraph();
		mxAnalysisGraph aGraph = new mxAnalysisGraph();

		JMenu menu = null;
		JMenu submenu = null;

		// Creates the file menu
		menu = add(new JMenu(mxResources.get("file")));

		menu.add(editor.bind(mxResources.get("openFile"), new OpenAction(), "/fr/enib/game/editor/graphe/examples/swing/images/open.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("save"), new SaveAction(false), "/fr/enib/game/editor/graphe/examples/swing/images/save.gif"));
		menu.add(editor.bind(mxResources.get("saveAs"), new SaveAction(true), "/fr/enib/game/editor/graphe/examples/swing/images/saveas.gif"));

		menu.addSeparator();
		
		menu.add(editor.bind(mxResources.get("export"), new ExportModelAction(), "/fr/enib/game/editor/graphe/examples/swing/images/save.gif"));
		
		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("pageSetup"), new PageSetupAction(), "/fr/enib/game/editor/graphe/examples/swing/images/pagesetup.gif"));
		menu.add(editor.bind(mxResources.get("print"), new PrintAction(), "/fr/enib/game/editor/graphe/examples/swing/images/print.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("exit"), new ExitAction()));

		// Creates the edit menu
		menu = add(new JMenu(mxResources.get("edit")));

		menu.add(editor.bind(mxResources.get("undo"), new HistoryAction(true), "/fr/enib/game/editor/graphe/examples/swing/images/undo.gif"));
		menu.add(editor.bind(mxResources.get("redo"), new HistoryAction(false), "/fr/enib/game/editor/graphe/examples/swing/images/redo.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("cut"), TransferHandler.getCutAction(), "/fr/enib/game/editor/graphe/examples/swing/images/cut.gif"));
		menu.add(editor.bind(mxResources.get("copy"), TransferHandler.getCopyAction(), "/fr/enib/game/editor/graphe/examples/swing/images/copy.gif"));
		menu.add(editor.bind(mxResources.get("paste"), TransferHandler.getPasteAction(), "/fr/enib/game/editor/graphe/examples/swing/images/paste.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("delete"), mxGraphActions.getDeleteAction(), "/fr/enib/game/editor/graphe/examples/swing/images/delete.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("selectAll"), mxGraphActions.getSelectAllAction()));
		menu.add(editor.bind(mxResources.get("selectNone"), mxGraphActions.getSelectNoneAction()));

		// Creates the view menu
		menu = add(new JMenu(mxResources.get("view")));

		JMenuItem item = menu.add(new TogglePropertyItem(graphComponent, mxResources.get("pageLayout"), "PageVisible", true,
				new ActionListener()
				{
					/**
					 * 
					 */
					public void actionPerformed(ActionEvent e)
					{
						if (graphComponent.isPageVisible() && graphComponent.isCenterPage())
						{
							graphComponent.zoomAndCenter();
						}
						else
						{
							graphComponent.getGraphControl().updatePreferredSize();
						}
					}
				}));

		item.addActionListener(new ActionListener()
		{
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() instanceof TogglePropertyItem)
				{
					final mxGraphComponent graphComponent = editor.getGraphComponent();
					TogglePropertyItem toggleItem = (TogglePropertyItem) e.getSource();

					if (toggleItem.isSelected())
					{
						// Scrolls the view to the center
						SwingUtilities.invokeLater(new Runnable()
						{
							/*
							 * (non-Javadoc)
							 * @see java.lang.Runnable#run()
							 */
							public void run()
							{
								graphComponent.scrollToCenter(true);
								graphComponent.scrollToCenter(false);
							}
						});
					}
					else
					{
						// Resets the translation of the view
						mxPoint tr = graphComponent.getGraph().getView().getTranslate();

						if (tr.getX() != 0 || tr.getY() != 0)
						{
							graphComponent.getGraph().getView().setTranslate(new mxPoint());
						}
					}
				}
			}
		});


		menu.addSeparator();

		menu.add(new ToggleGridItem(editor, mxResources.get("grid")));
		menu.add(new ToggleRulersItem(editor, mxResources.get("rulers")));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("zoom")));

		submenu.add(editor.bind("400%", new ScaleAction(4)));
		submenu.add(editor.bind("200%", new ScaleAction(2)));
		submenu.add(editor.bind("150%", new ScaleAction(1.5)));
		submenu.add(editor.bind("100%", new ScaleAction(1)));
		submenu.add(editor.bind("75%", new ScaleAction(0.75)));
		submenu.add(editor.bind("50%", new ScaleAction(0.5)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("custom"), new ScaleAction(0)));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("zoomIn"), mxGraphActions.getZoomInAction()));
		menu.add(editor.bind(mxResources.get("zoomOut"), mxGraphActions.getZoomOutAction()));

		// Creates the shape menu
		menu = add(new JMenu(mxResources.get("shape")));

		populateShapeMenu(menu, editor);

		// Creates the diagram menu
		menu = add(new JMenu(mxResources.get("diagram")));

		menu.add(new ToggleOutlineItem(editor, mxResources.get("outline")));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("background")));

		submenu.add(editor.bind(mxResources.get("backgroundColor"), new BackgroundAction()));
		submenu.add(editor.bind(mxResources.get("backgroundImage"), new BackgroundImageAction()));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("pageBackground"), new PageBackgroundAction()));

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("grid")));

		submenu.add(editor.bind(mxResources.get("gridSize"), new PromptPropertyAction(graph, "Grid Size", "GridSize")));
		submenu.add(editor.bind(mxResources.get("gridColor"), new GridColorAction()));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("dashed"), new GridStyleAction(mxGraphComponent.GRID_STYLE_DASHED)));
		submenu.add(editor.bind(mxResources.get("dot"), new GridStyleAction(mxGraphComponent.GRID_STYLE_DOT)));
		submenu.add(editor.bind(mxResources.get("line"), new GridStyleAction(mxGraphComponent.GRID_STYLE_LINE)));
		submenu.add(editor.bind(mxResources.get("cross"), new GridStyleAction(mxGraphComponent.GRID_STYLE_CROSS)));

		// Creates the window menu
		menu = add(new JMenu(mxResources.get("window")));

		UIManager.LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();

		for (int i = 0; i < lafs.length; i++)
		{
			final String clazz = lafs[i].getClassName();
			
			menu.add(new AbstractAction(lafs[i].getName())
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = 7588919504149148501L;

				public void actionPerformed(ActionEvent e)
				{
					editor.setLookAndFeel(clazz);
				}
			});
		}


		// Creates the help menu
		menu = add(new JMenu(mxResources.get("help")));

		item = menu.add(new JMenuItem(mxResources.get("aboutGraphEditor")));
		item.addActionListener(new ActionListener()
		{
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				editor.about();
			}
		});
	}

	/**
	 * Adds menu items to the given shape menu. This is factored out because
	 * the shape menu appears in the menubar and also in the popupmenu.
	 */
	public static void populateShapeMenu(JMenu menu, BasicGraphEditor editor)
	{
		menu.add(editor.bind(mxResources.get("toBack"), mxGraphActions.getToBackAction(), "/fr/enib/game/editor/graphe/examples/swing/images/toback.gif"));
		menu.add(editor.bind(mxResources.get("toFront"), mxGraphActions.getToFrontAction(),
				"/fr/enib/game/editor/graphe/examples/swing/images/tofront.gif"));

		menu.addSeparator();

		JMenu submenu = (JMenu) menu.add(new JMenu(mxResources.get("align")));

		submenu.add(editor.bind(mxResources.get("left"), new AlignCellsAction(mxConstants.ALIGN_LEFT),
				"/fr/enib/game/editor/graphe/examples/swing/images/alignleft.gif"));
		submenu.add(editor.bind(mxResources.get("center"), new AlignCellsAction(mxConstants.ALIGN_CENTER),
				"/fr/enib/game/editor/graphe/examples/swing/images/aligncenter.gif"));
		submenu.add(editor.bind(mxResources.get("right"), new AlignCellsAction(mxConstants.ALIGN_RIGHT),
				"/fr/enib/game/editor/graphe/examples/swing/images/alignright.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("top"), new AlignCellsAction(mxConstants.ALIGN_TOP),
				"/fr/enib/game/editor/graphe/examples/swing/images/aligntop.gif"));
		submenu.add(editor.bind(mxResources.get("middle"), new AlignCellsAction(mxConstants.ALIGN_MIDDLE),
				"/fr/enib/game/editor/graphe/examples/swing/images/alignmiddle.gif"));
		submenu.add(editor.bind(mxResources.get("bottom"), new AlignCellsAction(mxConstants.ALIGN_BOTTOM),
				"/fr/enib/game/editor/graphe/examples/swing/images/alignbottom.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("autosize"), new AutosizeAction()));

	}

	/**
	 * Adds menu items to the given format menu. This is factored out because
	 * the format menu appears in the menubar and also in the popupmenu.
	 */
	public static void populateFormatMenu(JMenu menu, BasicGraphEditor editor)
	{
		JMenu submenu = (JMenu) menu.add(new JMenu(mxResources.get("background")));

		submenu.add(editor.bind(mxResources.get("fillcolor"), new ColorAction("Fillcolor", mxConstants.STYLE_FILLCOLOR),
				"/fr/enib/game/editor/graphe/examples/swing/images/fillcolor.gif"));
		submenu.add(editor.bind(mxResources.get("gradient"), new ColorAction("Gradient", mxConstants.STYLE_GRADIENTCOLOR)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("image"), new PromptValueAction(mxConstants.STYLE_IMAGE, "Image")));
		submenu.add(editor.bind(mxResources.get("shadow"), new ToggleAction(mxConstants.STYLE_SHADOW)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("opacity"), new PromptValueAction(mxConstants.STYLE_OPACITY, "Opacity (0-100)")));

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("label")));

		submenu.add(editor.bind(mxResources.get("fontcolor"), new ColorAction("Fontcolor", mxConstants.STYLE_FONTCOLOR),
				"/fr/enib/game/editor/graphe/examples/swing/images/fontcolor.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("labelFill"), new ColorAction("Label Fill", mxConstants.STYLE_LABEL_BACKGROUNDCOLOR)));
		submenu.add(editor.bind(mxResources.get("labelBorder"), new ColorAction("Label Border", mxConstants.STYLE_LABEL_BORDERCOLOR)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("rotateLabel"), new ToggleAction(mxConstants.STYLE_HORIZONTAL, true)));

		submenu.add(editor.bind(mxResources.get("textOpacity"), new PromptValueAction(mxConstants.STYLE_TEXT_OPACITY, "Opacity (0-100)")));

		submenu.addSeparator();

		JMenu subsubmenu = (JMenu) submenu.add(new JMenu(mxResources.get("position")));

		subsubmenu.add(editor.bind(mxResources.get("top"), new SetLabelPositionAction(mxConstants.ALIGN_TOP, mxConstants.ALIGN_BOTTOM)));
		subsubmenu.add(editor.bind(mxResources.get("middle"),
				new SetLabelPositionAction(mxConstants.ALIGN_MIDDLE, mxConstants.ALIGN_MIDDLE)));
		subsubmenu.add(editor.bind(mxResources.get("bottom"), new SetLabelPositionAction(mxConstants.ALIGN_BOTTOM, mxConstants.ALIGN_TOP)));

		subsubmenu.addSeparator();

		subsubmenu.add(editor.bind(mxResources.get("left"), new SetLabelPositionAction(mxConstants.ALIGN_LEFT, mxConstants.ALIGN_RIGHT)));
		subsubmenu.add(editor.bind(mxResources.get("center"),
				new SetLabelPositionAction(mxConstants.ALIGN_CENTER, mxConstants.ALIGN_CENTER)));
		subsubmenu.add(editor.bind(mxResources.get("right"), new SetLabelPositionAction(mxConstants.ALIGN_RIGHT, mxConstants.ALIGN_LEFT)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("wordWrap"), new KeyValueAction(mxConstants.STYLE_WHITE_SPACE, "wrap")));
		submenu.add(editor.bind(mxResources.get("noWordWrap"), new KeyValueAction(mxConstants.STYLE_WHITE_SPACE, null)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("hide"), new ToggleAction(mxConstants.STYLE_NOLABEL)));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("line")));

		submenu.add(editor.bind(mxResources.get("linecolor"), new ColorAction("Linecolor", mxConstants.STYLE_STROKECOLOR),
				"/fr/enib/game/editor/graphe/examples/swing/images/linecolor.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("orthogonal"), new ToggleAction(mxConstants.STYLE_ORTHOGONAL)));
		submenu.add(editor.bind(mxResources.get("dashed"), new ToggleAction(mxConstants.STYLE_DASHED)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("linewidth"), new PromptValueAction(mxConstants.STYLE_STROKEWIDTH, "Linewidth")));

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("connector")));

		submenu.add(editor.bind(mxResources.get("straight"), new SetStyleAction("straight"),
				"/fr/enib/game/editor/graphe/examples/swing/images/straight.gif"));

		submenu.add(editor.bind(mxResources.get("horizontal"), new SetStyleAction(""), "/fr/enib/game/editor/graphe/examples/swing/images/connect.gif"));
		submenu.add(editor.bind(mxResources.get("vertical"), new SetStyleAction("vertical"),
				"/fr/enib/game/editor/graphe/examples/swing/images/vertical.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("entityRelation"), new SetStyleAction("edgeStyle=mxEdgeStyle.EntityRelation"),
				"/fr/enib/game/editor/graphe/examples/swing/images/entity.gif"));
		submenu.add(editor.bind(mxResources.get("arrow"), new SetStyleAction("arrow"), "/fr/enib/game/editor/graphe/examples/swing/images/arrow.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("plain"), new ToggleAction(mxConstants.STYLE_NOEDGESTYLE)));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("linestart")));

		submenu.add(editor.bind(mxResources.get("open"), new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_OPEN),
				"/fr/enib/game/editor/graphe/examples/swing/images/open_start.gif"));
		submenu.add(editor.bind(mxResources.get("classic"), new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_CLASSIC),
				"/fr/enib/game/editor/graphe/examples/swing/images/classic_start.gif"));
		submenu.add(editor.bind(mxResources.get("block"), new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_BLOCK),
				"/fr/enib/game/editor/graphe/examples/swing/images/block_start.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("diamond"), new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_DIAMOND),
				"/fr/enib/game/editor/graphe/examples/swing/images/diamond_start.gif"));
		submenu.add(editor.bind(mxResources.get("oval"), new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_OVAL),
				"/fr/enib/game/editor/graphe/examples/swing/images/oval_start.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("none"), new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.NONE)));
		submenu.add(editor.bind(mxResources.get("size"), new PromptValueAction(mxConstants.STYLE_STARTSIZE, "Linestart Size")));

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("lineend")));

		submenu.add(editor.bind(mxResources.get("open"), new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_OPEN),
				"/fr/enib/game/editor/graphe/examples/swing/images/open_end.gif"));
		submenu.add(editor.bind(mxResources.get("classic"), new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC),
				"/fr/enib/game/editor/graphe/examples/swing/images/classic_end.gif"));
		submenu.add(editor.bind(mxResources.get("block"), new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_BLOCK),
				"/fr/enib/game/editor/graphe/examples/swing/images/block_end.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("diamond"), new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_DIAMOND),
				"/fr/enib/game/editor/graphe/examples/swing/images/diamond_end.gif"));
		submenu.add(editor.bind(mxResources.get("oval"), new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_OVAL),
				"/fr/enib/game/editor/graphe/examples/swing/images/oval_end.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("none"), new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.NONE)));
		submenu.add(editor.bind(mxResources.get("size"), new PromptValueAction(mxConstants.STYLE_ENDSIZE, "Lineend Size")));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("alignment")));

		submenu.add(editor.bind(mxResources.get("left"), new KeyValueAction(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT),
				"/fr/enib/game/editor/graphe/examples/swing/images/left.gif"));
		submenu.add(editor.bind(mxResources.get("center"), new KeyValueAction(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER),
				"/fr/enib/game/editor/graphe/examples/swing/images/center.gif"));
		submenu.add(editor.bind(mxResources.get("right"), new KeyValueAction(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_RIGHT),
				"/fr/enib/game/editor/graphe/examples/swing/images/right.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("top"), new KeyValueAction(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_TOP),
				"/fr/enib/game/editor/graphe/examples/swing/images/top.gif"));
		submenu.add(editor.bind(mxResources.get("middle"), new KeyValueAction(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE),
				"/fr/enib/game/editor/graphe/examples/swing/images/middle.gif"));
		submenu.add(editor.bind(mxResources.get("bottom"), new KeyValueAction(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM),
				"/fr/enib/game/editor/graphe/examples/swing/images/bottom.gif"));

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("spacing")));

		submenu.add(editor.bind(mxResources.get("top"), new PromptValueAction(mxConstants.STYLE_SPACING_TOP, "Top Spacing")));
		submenu.add(editor.bind(mxResources.get("right"), new PromptValueAction(mxConstants.STYLE_SPACING_RIGHT, "Right Spacing")));
		submenu.add(editor.bind(mxResources.get("bottom"), new PromptValueAction(mxConstants.STYLE_SPACING_BOTTOM, "Bottom Spacing")));
		submenu.add(editor.bind(mxResources.get("left"), new PromptValueAction(mxConstants.STYLE_SPACING_LEFT, "Left Spacing")));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("global"), new PromptValueAction(mxConstants.STYLE_SPACING, "Spacing")));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("sourceSpacing"), new PromptValueAction(mxConstants.STYLE_SOURCE_PERIMETER_SPACING,
				mxResources.get("sourceSpacing"))));
		submenu.add(editor.bind(mxResources.get("targetSpacing"), new PromptValueAction(mxConstants.STYLE_TARGET_PERIMETER_SPACING,
				mxResources.get("targetSpacing"))));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("perimeter"), new PromptValueAction(mxConstants.STYLE_PERIMETER_SPACING,
				"Perimeter Spacing")));

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("direction")));

		submenu.add(editor.bind(mxResources.get("north"), new KeyValueAction(mxConstants.STYLE_DIRECTION, mxConstants.DIRECTION_NORTH)));
		submenu.add(editor.bind(mxResources.get("east"), new KeyValueAction(mxConstants.STYLE_DIRECTION, mxConstants.DIRECTION_EAST)));
		submenu.add(editor.bind(mxResources.get("south"), new KeyValueAction(mxConstants.STYLE_DIRECTION, mxConstants.DIRECTION_SOUTH)));
		submenu.add(editor.bind(mxResources.get("west"), new KeyValueAction(mxConstants.STYLE_DIRECTION, mxConstants.DIRECTION_WEST)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("rotation"), new PromptValueAction(mxConstants.STYLE_ROTATION, "Rotation (0-360)")));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("rounded"), new ToggleAction(mxConstants.STYLE_ROUNDED)));

		menu.add(editor.bind(mxResources.get("style"), new StyleAction()));
	}

	/**
	 *
	 */
	public static class InsertGraph extends AbstractAction
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4010463992665008365L;

		/**
		 * 
		 */
		protected GraphType graphType;

		protected mxAnalysisGraph aGraph;

		/**
		 * @param aGraph 
		 * 
		 */
		public InsertGraph(GraphType tree, mxAnalysisGraph aGraph)
		{
			this.graphType = tree;
			this.aGraph = aGraph;
		}

		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() instanceof mxGraphComponent)
			{
				mxGraphComponent graphComponent = (mxGraphComponent) e.getSource();
				mxGraph graph = graphComponent.getGraph();

				// dialog = new FactoryConfigDialog();
				String dialogText = "";
				if (graphType == GraphType.NULL)
					dialogText = "Configure null graph";
				else if (graphType == GraphType.COMPLETE)
					dialogText = "Configure complete graph";
				else if (graphType == GraphType.NREGULAR)
					dialogText = "Configure n-regular graph";
				else if (graphType == GraphType.GRID)
					dialogText = "Configure grid graph";
				else if (graphType == GraphType.BIPARTITE)
					dialogText = "Configure bipartite graph";
				else if (graphType == GraphType.COMPLETE_BIPARTITE)
					dialogText = "Configure complete bipartite graph";
				else if (graphType == GraphType.BFS_DIR)
					dialogText = "Configure BFS algorithm";
				else if (graphType == GraphType.BFS_UNDIR)
					dialogText = "Configure BFS algorithm";
				else if (graphType == GraphType.DFS_DIR)
					dialogText = "Configure DFS algorithm";
				else if (graphType == GraphType.DFS_UNDIR)
					dialogText = "Configure DFS algorithm";
				else if (graphType == GraphType.DIJKSTRA)
					dialogText = "Configure Dijkstra's algorithm";
				else if (graphType == GraphType.BELLMAN_FORD)
					dialogText = "Configure Bellman-Ford algorithm";
				else if (graphType == GraphType.MAKE_TREE_DIRECTED)
					dialogText = "Configure make tree directed algorithm";
				else if (graphType == GraphType.KNIGHT_TOUR)
					dialogText = "Configure knight's tour";
				else if (graphType == GraphType.GET_ADJ_MATRIX)
					dialogText = "Configure adjacency matrix";
				else if (graphType == GraphType.FROM_ADJ_MATRIX)
					dialogText = "Input adjacency matrix";
				else if (graphType == GraphType.PETERSEN)
					dialogText = "Configure Petersen graph";
				else if (graphType == GraphType.WHEEL)
					dialogText = "Configure Wheel graph";
				else if (graphType == GraphType.STAR)
					dialogText = "Configure Star graph";
				else if (graphType == GraphType.PATH)
					dialogText = "Configure Path graph";
				else if (graphType == GraphType.FRIENDSHIP_WINDMILL)
					dialogText = "Configure Friendship Windmill graph";
				else if (graphType == GraphType.INDEGREE)
					dialogText = "Configure indegree analysis";
				else if (graphType == GraphType.OUTDEGREE)
					dialogText = "Configure outdegree analysis";
//			GraphConfigDialog dialog = new GraphConfigDialog(graphType, dialogText);
//				dialog.configureLayout(graph, graphType, aGraph);
//				dialog.setModal(true);
//				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//				Dimension frameSize = dialog.getSize();
//				dialog.setLocation(screenSize.width / 2 - (frameSize.width / 2), screenSize.height / 2 - (frameSize.height / 2));
//				dialog.setVisible(true);
			}
		}
	}
};