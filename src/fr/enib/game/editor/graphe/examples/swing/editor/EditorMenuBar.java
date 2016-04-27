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

import fr.enib.game.editor.graphe.analysis.GraphType;
import fr.enib.game.editor.graphe.analysis.StructuralException;
import fr.enib.game.editor.graphe.analysis.mxAnalysisGraph;
import fr.enib.game.editor.graphe.analysis.mxGraphProperties;
import fr.enib.game.editor.graphe.analysis.mxGraphStructure;
import fr.enib.game.editor.graphe.analysis.mxTraversal;
import fr.enib.game.editor.graphe.costfunction.mxCostFunction;
import fr.enib.game.editor.graphe.examples.swing.AnalyzeType;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.AlignCellsAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.AutosizeAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.BackgroundAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.BackgroundImageAction;
import fr.enib.game.editor.graphe.examples.swing.actions.ExitAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.GridColorAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.GridStyleAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.HistoryAction;
import fr.enib.game.editor.graphe.examples.swing.actions.OpenAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.PageBackgroundAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.PageSetupAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.PrintAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.PromptPropertyAction;
import fr.enib.game.editor.graphe.examples.swing.actions.SaveAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.ScaleAction;
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

/**
 * @author Corentin Boleis
 *
 */
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

		menu.add(editor.bind(mxResources.get("openFile"), new OpenAction(), "/com/mxgraph/examples/swing/images/open.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("save"), new SaveAction(false), "/com/mxgraph/examples/swing/images/save.gif"));
		menu.add(editor.bind(mxResources.get("saveAs"), new SaveAction(true), "/com/mxgraph/examples/swing/images/saveas.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("pageSetup"), new PageSetupAction(), "/com/mxgraph/examples/swing/images/pagesetup.gif"));
		menu.add(editor.bind(mxResources.get("print"), new PrintAction(), "/com/mxgraph/examples/swing/images/print.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("exit"), new ExitAction()));

		// Creates the edit menu
		menu = add(new JMenu(mxResources.get("edit")));

		menu.add(editor.bind(mxResources.get("undo"), new HistoryAction(true), "/com/mxgraph/examples/swing/images/undo.gif"));
		menu.add(editor.bind(mxResources.get("redo"), new HistoryAction(false), "/com/mxgraph/examples/swing/images/redo.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("cut"), TransferHandler.getCutAction(), "/com/mxgraph/examples/swing/images/cut.gif"));
		menu.add(editor.bind(mxResources.get("copy"), TransferHandler.getCopyAction(), "/com/mxgraph/examples/swing/images/copy.gif"));
		menu.add(editor.bind(mxResources.get("paste"), TransferHandler.getPasteAction(), "/com/mxgraph/examples/swing/images/paste.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("delete"), mxGraphActions.getDeleteAction(), "/com/mxgraph/examples/swing/images/delete.gif"));

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


//		// Creates the options menu
//		menu = add(new JMenu(mxResources.get("options")));
//
//		submenu = (JMenu) menu.add(new JMenu(mxResources.get("display")));
//		submenu.add(new TogglePropertyItem(graphComponent, mxResources.get("buffering"), "TripleBuffered", true));
//
//		submenu.add(new TogglePropertyItem(graphComponent, mxResources.get("preferPageSize"), "PreferPageSize", true, new ActionListener()
//		{
//			/**
//			 * 
//			 */
//			public void actionPerformed(ActionEvent e)
//			{
//				graphComponent.zoomAndCenter();
//			}
//		}));
//
//		// TODO: This feature is not yet implemented
//		//submenu.add(new TogglePropertyItem(graphComponent, mxResources
//		//		.get("pageBreaks"), "PageBreaksVisible", true));
//
//		submenu.addSeparator();
//
//		submenu.add(editor.bind(mxResources.get("tolerance"), new PromptPropertyAction(graphComponent, "Tolerance")));
//
//		submenu.add(editor.bind(mxResources.get("dirty"), new ToggleDirtyAction()));
//
//		submenu = (JMenu) menu.add(new JMenu(mxResources.get("zoom")));
//
//		submenu.add(new TogglePropertyItem(graphComponent, mxResources.get("centerZoom"), "CenterZoom", true));
//		submenu.add(new TogglePropertyItem(graphComponent, mxResources.get("zoomToSelection"), "KeepSelectionVisibleOnZoom", true));
//
//		submenu.addSeparator();
//
//		submenu.add(new TogglePropertyItem(graphComponent, mxResources.get("centerPage"), "CenterPage", true, new ActionListener()
//		{
//			/**
//			 * 
//			 */
//			public void actionPerformed(ActionEvent e)
//			{
//				if (graphComponent.isPageVisible() && graphComponent.isCenterPage())
//				{
//					graphComponent.zoomAndCenter();
//				}
//			}
//		}));
//
//		menu.addSeparator();
//
//		submenu = (JMenu) menu.add(new JMenu(mxResources.get("dragAndDrop")));
//
//		submenu.add(new TogglePropertyItem(graphComponent, mxResources.get("dragEnabled"), "DragEnabled"));
//		submenu.add(new TogglePropertyItem(graph, mxResources.get("dropEnabled"), "DropEnabled"));
//
//		submenu.addSeparator();
//
//		submenu.add(new TogglePropertyItem(graphComponent.getGraphHandler(), mxResources.get("imagePreview"), "ImagePreview"));
//
//		submenu = (JMenu) menu.add(new JMenu(mxResources.get("labels")));
//
//		submenu.add(new TogglePropertyItem(graph, mxResources.get("htmlLabels"), "HtmlLabels", true));
//		submenu.add(new TogglePropertyItem(graph, mxResources.get("showLabels"), "LabelsVisible", true));
//
//		submenu.addSeparator();
//
//		submenu.add(new TogglePropertyItem(graph, mxResources.get("moveEdgeLabels"), "EdgeLabelsMovable"));
//		submenu.add(new TogglePropertyItem(graph, mxResources.get("moveVertexLabels"), "VertexLabelsMovable"));
//
//		submenu.addSeparator();
//
//		submenu.add(new TogglePropertyItem(graphComponent, mxResources.get("handleReturn"), "EnterStopsCellEditing"));
//
//		menu.addSeparator();
//
//		submenu = (JMenu) menu.add(new JMenu(mxResources.get("connections")));
//
//		submenu.add(new TogglePropertyItem(graphComponent, mxResources.get("connectable"), "Connectable"));
//		submenu.add(new TogglePropertyItem(graph, mxResources.get("connectableEdges"), "ConnectableEdges"));
//
//		submenu.addSeparator();
//
//		submenu.add(new ToggleCreateTargetItem(editor, mxResources.get("createTarget")));
//		submenu.add(new TogglePropertyItem(graph, mxResources.get("disconnectOnMove"), "DisconnectOnMove"));
//
//		submenu.addSeparator();
//
//		submenu.add(editor.bind(mxResources.get("connectMode"), new ToggleConnectModeAction()));
//
//		submenu = (JMenu) menu.add(new JMenu(mxResources.get("validation")));
//
//		submenu.add(new TogglePropertyItem(graph, mxResources.get("allowDanglingEdges"), "AllowDanglingEdges"));
//		submenu.add(new TogglePropertyItem(graph, mxResources.get("cloneInvalidEdges"), "CloneInvalidEdges"));
//
//		submenu.addSeparator();
//
//		submenu.add(new TogglePropertyItem(graph, mxResources.get("allowLoops"), "AllowLoops"));
//		submenu.add(new TogglePropertyItem(graph, mxResources.get("multigraph"), "Multigraph"));

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

//		// Creates a developer menu
//		menu = add(new JMenu("Generate"));
//		menu.add(editor.bind("Null Graph", new InsertGraph(GraphType.NULL, aGraph)));
//		menu.add(editor.bind("Complete Graph", new InsertGraph(GraphType.COMPLETE, aGraph)));
//		menu.add(editor.bind("Grid", new InsertGraph(GraphType.GRID, aGraph)));
//		menu.add(editor.bind("Bipartite", new InsertGraph(GraphType.BIPARTITE, aGraph)));
//		menu.add(editor.bind("Complete Bipartite", new InsertGraph(GraphType.COMPLETE_BIPARTITE, aGraph)));
//		menu.add(editor.bind("Knight's Graph", new InsertGraph(GraphType.KNIGHT, aGraph)));
//		menu.add(editor.bind("King's Graph", new InsertGraph(GraphType.KING, aGraph)));
//		menu.add(editor.bind("Petersen", new InsertGraph(GraphType.PETERSEN, aGraph)));
//		menu.add(editor.bind("Path", new InsertGraph(GraphType.PATH, aGraph)));
//		menu.add(editor.bind("Star", new InsertGraph(GraphType.STAR, aGraph)));
//		menu.add(editor.bind("Wheel", new InsertGraph(GraphType.WHEEL, aGraph)));
//		menu.add(editor.bind("Friendship Windmill", new InsertGraph(GraphType.FRIENDSHIP_WINDMILL, aGraph)));
//		menu.add(editor.bind("Full Windmill", new InsertGraph(GraphType.FULL_WINDMILL, aGraph)));
//		menu.add(editor.bind("Knight's Tour", new InsertGraph(GraphType.KNIGHT_TOUR, aGraph)));
//		menu.addSeparator();
//		menu.add(editor.bind("Simple Random", new InsertGraph(GraphType.SIMPLE_RANDOM, aGraph)));
//		menu.add(editor.bind("Simple Random Tree", new InsertGraph(GraphType.SIMPLE_RANDOM_TREE, aGraph)));
//		menu.addSeparator();
//		menu.add(editor.bind("Reset Style", new InsertGraph(GraphType.RESET_STYLE, aGraph)));
//
//		menu = add(new JMenu("Analyze"));
//		menu.add(editor.bind("Is Connected", new AnalyzeGraph(AnalyzeType.IS_CONNECTED, aGraph)));
//		menu.add(editor.bind("Is Simple", new AnalyzeGraph(AnalyzeType.IS_SIMPLE, aGraph)));
//		menu.add(editor.bind("Is Directed Cyclic", new AnalyzeGraph(AnalyzeType.IS_CYCLIC_DIRECTED, aGraph)));
//		menu.add(editor.bind("Is Undirected Cyclic", new AnalyzeGraph(AnalyzeType.IS_CYCLIC_UNDIRECTED, aGraph)));
//		menu.add(editor.bind("BFS Directed", new InsertGraph(GraphType.BFS_DIR, aGraph)));
//		menu.add(editor.bind("BFS Undirected", new InsertGraph(GraphType.BFS_UNDIR, aGraph)));
//		menu.add(editor.bind("DFS Directed", new InsertGraph(GraphType.DFS_DIR, aGraph)));
//		menu.add(editor.bind("DFS Undirected", new InsertGraph(GraphType.DFS_UNDIR, aGraph)));
//		menu.add(editor.bind("Complementary", new AnalyzeGraph(AnalyzeType.COMPLEMENTARY, aGraph)));
//		menu.add(editor.bind("Regularity", new AnalyzeGraph(AnalyzeType.REGULARITY, aGraph)));
//		menu.add(editor.bind("Dijkstra", new InsertGraph(GraphType.DIJKSTRA, aGraph)));
//		menu.add(editor.bind("Bellman-Ford", new InsertGraph(GraphType.BELLMAN_FORD, aGraph)));
//		menu.add(editor.bind("Floyd-Roy-Warshall", new AnalyzeGraph(AnalyzeType.FLOYD_ROY_WARSHALL, aGraph)));
//		menu.add(editor.bind("Get Components", new AnalyzeGraph(AnalyzeType.COMPONENTS, aGraph)));
//		menu.add(editor.bind("Make Connected", new AnalyzeGraph(AnalyzeType.MAKE_CONNECTED, aGraph)));
//		menu.add(editor.bind("Make Simple", new AnalyzeGraph(AnalyzeType.MAKE_SIMPLE, aGraph)));
//		menu.add(editor.bind("Is Tree", new AnalyzeGraph(AnalyzeType.IS_TREE, aGraph)));
//		menu.add(editor.bind("One Spanning Tree", new AnalyzeGraph(AnalyzeType.ONE_SPANNING_TREE, aGraph)));
//		menu.add(editor.bind("Make tree directed", new InsertGraph(GraphType.MAKE_TREE_DIRECTED, aGraph)));
//		menu.add(editor.bind("Is directed", new AnalyzeGraph(AnalyzeType.IS_DIRECTED, aGraph)));
//		menu.add(editor.bind("Indegree", new InsertGraph(GraphType.INDEGREE, aGraph)));
//		menu.add(editor.bind("Outdegree", new InsertGraph(GraphType.OUTDEGREE, aGraph)));
//		menu.add(editor.bind("Is cut vertex", new InsertGraph(GraphType.IS_CUT_VERTEX, aGraph)));
//		menu.add(editor.bind("Get cut vertices", new AnalyzeGraph(AnalyzeType.GET_CUT_VERTEXES, aGraph)));
//		menu.add(editor.bind("Get cut edges", new AnalyzeGraph(AnalyzeType.GET_CUT_EDGES, aGraph)));
//		menu.add(editor.bind("Get sources", new AnalyzeGraph(AnalyzeType.GET_SOURCES, aGraph)));
//		menu.add(editor.bind("Get sinks", new AnalyzeGraph(AnalyzeType.GET_SINKS, aGraph)));
//		menu.add(editor.bind("Is biconnected", new AnalyzeGraph(AnalyzeType.IS_BICONNECTED, aGraph)));

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

		menu.add(editor.bind(mxResources.get("toBack"), mxGraphActions.getToBackAction(), "/com/mxgraph/examples/swing/images/toback.gif"));
		menu.add(editor.bind(mxResources.get("toFront"), mxGraphActions.getToFrontAction(),
				"/com/mxgraph/examples/swing/images/tofront.gif"));

		menu.addSeparator();

		JMenu submenu = (JMenu) menu.add(new JMenu(mxResources.get("align")));

		submenu.add(editor.bind(mxResources.get("left"), new AlignCellsAction(mxConstants.ALIGN_LEFT),
				"/com/mxgraph/examples/swing/images/alignleft.gif"));
		submenu.add(editor.bind(mxResources.get("center"), new AlignCellsAction(mxConstants.ALIGN_CENTER),
				"/com/mxgraph/examples/swing/images/aligncenter.gif"));
		submenu.add(editor.bind(mxResources.get("right"), new AlignCellsAction(mxConstants.ALIGN_RIGHT),
				"/com/mxgraph/examples/swing/images/alignright.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("top"), new AlignCellsAction(mxConstants.ALIGN_TOP),
				"/com/mxgraph/examples/swing/images/aligntop.gif"));
		submenu.add(editor.bind(mxResources.get("middle"), new AlignCellsAction(mxConstants.ALIGN_MIDDLE),
				"/com/mxgraph/examples/swing/images/alignmiddle.gif"));
		submenu.add(editor.bind(mxResources.get("bottom"), new AlignCellsAction(mxConstants.ALIGN_BOTTOM),
				"/com/mxgraph/examples/swing/images/alignbottom.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("autosize"), new AutosizeAction()));

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
				GraphConfigDialog dialog = new GraphConfigDialog(graphType, dialogText);
				dialog.configureLayout(graph, graphType, aGraph);
				dialog.setModal(true);
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Dimension frameSize = dialog.getSize();
				dialog.setLocation(screenSize.width / 2 - (frameSize.width / 2), screenSize.height / 2 - (frameSize.height / 2));
				dialog.setVisible(true);
			}
		}
	}

	/**
	 *
	 */
	public static class AnalyzeGraph extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6926170745240507985L;

		mxAnalysisGraph aGraph;

		/**
		 * 
		 */
		protected AnalyzeType analyzeType;

		/**
		 * Examples for calling analysis methods from mxGraphStructure 
		 */
		public AnalyzeGraph(AnalyzeType analyzeType, mxAnalysisGraph aGraph)
		{
			this.analyzeType = analyzeType;
			this.aGraph = aGraph;
		}

		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() instanceof mxGraphComponent)
			{
				mxGraphComponent graphComponent = (mxGraphComponent) e.getSource();
				mxGraph graph = graphComponent.getGraph();
				aGraph.setGraph(graph);

				if (analyzeType == AnalyzeType.IS_CONNECTED)
				{
					boolean isConnected = mxGraphStructure.isConnected(aGraph);

					if (isConnected)
					{
						System.out.println("The graph is connected");
					}
					else
					{
						System.out.println("The graph is not connected");
					}
				}
				else if (analyzeType == AnalyzeType.IS_SIMPLE)
				{
					boolean isSimple = mxGraphStructure.isSimple(aGraph);

					if (isSimple)
					{
						System.out.println("The graph is simple");
					}
					else
					{
						System.out.println("The graph is not simple");
					}
				}
				else if (analyzeType == AnalyzeType.IS_CYCLIC_DIRECTED)
				{
					boolean isCyclicDirected = mxGraphStructure.isCyclicDirected(aGraph);

					if (isCyclicDirected)
					{
						System.out.println("The graph is cyclic directed");
					}
					else
					{
						System.out.println("The graph is acyclic directed");
					}
				}
				else if (analyzeType == AnalyzeType.IS_CYCLIC_UNDIRECTED)
				{
					boolean isCyclicUndirected = mxGraphStructure.isCyclicUndirected(aGraph);

					if (isCyclicUndirected)
					{
						System.out.println("The graph is cyclic undirected");
					}
					else
					{
						System.out.println("The graph is acyclic undirected");
					}
				}
				else if (analyzeType == AnalyzeType.COMPLEMENTARY)
				{
					graph.getModel().beginUpdate();

					mxGraphStructure.complementaryGraph(aGraph);

					mxGraphStructure.setDefaultGraphStyle(aGraph, true);
					graph.getModel().endUpdate();
				}
				else if (analyzeType == AnalyzeType.REGULARITY)
				{
					try
					{
						int regularity = mxGraphStructure.regularity(aGraph);
						System.out.println("Graph regularity is: " + regularity);
					}
					catch (StructuralException e1)
					{
						System.out.println("The graph is irregular");
					}
				}
				else if (analyzeType == AnalyzeType.COMPONENTS)
				{
					Object[][] components = mxGraphStructure.getGraphComponents(aGraph);
					mxIGraphModel model = aGraph.getGraph().getModel();

					for (int i = 0; i < components.length; i++)
					{
						System.out.print("Component " + i + " :");

						for (int j = 0; j < components[i].length; j++)
						{
							System.out.print(" " + model.getValue(components[i][j]));
						}

						System.out.println(".");
					}

					System.out.println("Number of components: " + components.length);

				}
				else if (analyzeType == AnalyzeType.MAKE_CONNECTED)
				{
					graph.getModel().beginUpdate();

					if (!mxGraphStructure.isConnected(aGraph))
					{
						mxGraphStructure.makeConnected(aGraph);
						mxGraphStructure.setDefaultGraphStyle(aGraph, false);
					}

					graph.getModel().endUpdate();
				}
				else if (analyzeType == AnalyzeType.MAKE_SIMPLE)
				{
					mxGraphStructure.makeSimple(aGraph);
				}
				else if (analyzeType == AnalyzeType.IS_TREE)
				{
					boolean isTree = mxGraphStructure.isTree(aGraph);

					if (isTree)
					{
						System.out.println("The graph is a tree");
					}
					else
					{
						System.out.println("The graph is not a tree");
					}
				}
				else if (analyzeType == AnalyzeType.ONE_SPANNING_TREE)
				{
					try
					{
						graph.getModel().beginUpdate();
						aGraph.getGenerator().oneSpanningTree(aGraph, true, true);
						mxGraphStructure.setDefaultGraphStyle(aGraph, false);
						graph.getModel().endUpdate();
					}
					catch (StructuralException e1)
					{
						System.out.println("The graph must be simple and connected");
					}
				}
				else if (analyzeType == AnalyzeType.IS_DIRECTED)
				{
					boolean isDirected = mxGraphProperties.isDirected(aGraph.getProperties(), mxGraphProperties.DEFAULT_DIRECTED);

					if (isDirected)
					{
						System.out.println("The graph is directed.");
					}
					else
					{
						System.out.println("The graph is undirected.");
					}
				}
				else if (analyzeType == AnalyzeType.GET_CUT_VERTEXES)
				{
					Object[] cutVertices = mxGraphStructure.getCutVertices(aGraph);

					System.out.print("Cut vertices of the graph are: [");
					mxIGraphModel model = aGraph.getGraph().getModel();

					for (int i = 0; i < cutVertices.length; i++)
					{
						System.out.print(" " + model.getValue(cutVertices[i]));
					}

					System.out.println(" ]");
				}
				else if (analyzeType == AnalyzeType.GET_CUT_EDGES)
				{
					Object[] cutEdges = mxGraphStructure.getCutEdges(aGraph);

					System.out.print("Cut edges of the graph are: [");
					mxIGraphModel model = aGraph.getGraph().getModel();

					for (int i = 0; i < cutEdges.length; i++)
					{
						System.out.print(" " + Integer.parseInt((String) model.getValue(aGraph.getTerminal(cutEdges[i], true))) + "-"
								+ Integer.parseInt((String) model.getValue(aGraph.getTerminal(cutEdges[i], false))));
					}

					System.out.println(" ]");
				}
				else if (analyzeType == AnalyzeType.GET_SOURCES)
				{
					try
					{
						Object[] sourceVertices = mxGraphStructure.getSourceVertices(aGraph);
						System.out.print("Source vertices of the graph are: [");
						mxIGraphModel model = aGraph.getGraph().getModel();

						for (int i = 0; i < sourceVertices.length; i++)
						{
							System.out.print(" " + model.getValue(sourceVertices[i]));
						}

						System.out.println(" ]");
					}
					catch (StructuralException e1)
					{
						System.out.println(e1);
					}
				}
				else if (analyzeType == AnalyzeType.GET_SINKS)
				{
					try
					{
						Object[] sinkVertices = mxGraphStructure.getSinkVertices(aGraph);
						System.out.print("Sink vertices of the graph are: [");
						mxIGraphModel model = aGraph.getGraph().getModel();

						for (int i = 0; i < sinkVertices.length; i++)
						{
							System.out.print(" " + model.getValue(sinkVertices[i]));
						}

						System.out.println(" ]");
					}
					catch (StructuralException e1)
					{
						System.out.println(e1);
					}
				}
				else if (analyzeType == AnalyzeType.PLANARITY)
				{
					//TODO implement
				}
				else if (analyzeType == AnalyzeType.IS_BICONNECTED)
				{
					boolean isBiconnected = mxGraphStructure.isBiconnected(aGraph);

					if (isBiconnected)
					{
						System.out.println("The graph is biconnected.");
					}
					else
					{
						System.out.println("The graph is not biconnected.");
					}
				}
				else if (analyzeType == AnalyzeType.GET_BICONNECTED)
				{
					//TODO implement
				}
				else if (analyzeType == AnalyzeType.SPANNING_TREE)
				{
					//TODO implement
				}
				else if (analyzeType == AnalyzeType.FLOYD_ROY_WARSHALL)
				{
					
					ArrayList<Object[][]> FWIresult = new ArrayList<Object[][]>();
					try
					{
						//only this line is needed to get the result from Floyd-Roy-Warshall, the rest is code for displaying the result
						FWIresult = mxTraversal.floydRoyWarshall(aGraph);

						Object[][] dist = FWIresult.get(0);
						Object[][] paths = FWIresult.get(1);
						Object[] vertices = aGraph.getChildVertices(aGraph.getGraph().getDefaultParent());
						int vertexNum = vertices.length;
						System.out.println("Distances are:");

						for (int i = 0; i < vertexNum; i++)
						{
							System.out.print("[");

							for (int j = 0; j < vertexNum; j++)
							{
								System.out.print(" " + Math.round((Double) dist[i][j] * 100.0) / 100.0);
							}

							System.out.println("] ");
						}

						System.out.println("Path info:");

						mxCostFunction costFunction = aGraph.getGenerator().getCostFunction();
						mxGraphView view = aGraph.getGraph().getView();

						for (int i = 0; i < vertexNum; i++)
						{
							System.out.print("[");

							for (int j = 0; j < vertexNum; j++)
							{
								if (paths[i][j] != null)
								{
									System.out.print(" " + costFunction.getCost(view.getState(paths[i][j])));
								}
								else
								{
									System.out.print(" -");
								}
							}

							System.out.println(" ]");
						}

						try
						{
							Object[] path = mxTraversal.getWFIPath(aGraph, FWIresult, vertices[0], vertices[vertexNum - 1]);
							System.out.print("The path from " + costFunction.getCost(view.getState(vertices[0])) + " to "
									+ costFunction.getCost((view.getState(vertices[vertexNum - 1]))) + " is:");

							for (int i = 0; i < path.length; i++)
							{
								System.out.print(" " + costFunction.getCost(view.getState(path[i])));
							}

							System.out.println();
						}
						catch (StructuralException e1)
						{
							System.out.println(e1);
						}
					}
					catch (StructuralException e2)
					{
						System.out.println(e2);
					}
				}
			}
		}
	};
};