package fr.enib.game.editor.graphe.examples.swing.editor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.TransferHandler;

import fr.enib.game.editor.graphe.examples.swing.action.OpenAction;
import fr.enib.game.editor.graphe.examples.swing.action.StartSimulationAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.PrintAction;
import fr.enib.game.editor.graphe.examples.swing.editor.EditorActions.SaveAction;
import fr.enib.game.editor.graphe.swing.mxGraphComponent;
import fr.enib.game.editor.graphe.swing.util.mxGraphActions;
import fr.enib.game.editor.graphe.util.mxEvent;
import fr.enib.game.editor.graphe.util.mxEventObject;
import fr.enib.game.editor.graphe.util.mxEventSource.mxIEventListener;
import fr.enib.game.editor.graphe.util.mxResources;
import fr.enib.game.editor.graphe.view.mxGraphView;
/**
 * 
 */
public class EditorToolBar extends JToolBar
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8015443128436394471L;


	private boolean ignoreZoomChange = false;

	/**
	 * @param editor 
	 * @param orientation 
	 * 
	 */
	public EditorToolBar(final BasicGraphEditor editor, int orientation)
	{
		super(orientation);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(3, 3, 3, 3), getBorder()));
		setFloatable(false);

		add(editor.bind("Open", new OpenAction(),
				"/fr/enib/game/editor/graphe/examples/swing/images/open.gif"));
		add(editor.bind("Save", new SaveAction(false),
				"/fr/enib/game/editor/graphe/examples/swing/images/save.gif"));

		addSeparator();

		add(editor.bind("Print", new PrintAction(),
				"/fr/enib/game/editor/graphe/examples/swing/images/print.gif"));

		addSeparator();

		add(editor.bind("Cut", TransferHandler.getCutAction(),
				"/fr/enib/game/editor/graphe/examples/swing/images/cut.gif"));
		add(editor.bind("Copy", TransferHandler.getCopyAction(),
				"/fr/enib/game/editor/graphe/examples/swing/images/copy.gif"));
		add(editor.bind("Paste", TransferHandler.getPasteAction(),
				"/fr/enib/game/editor/graphe/examples/swing/images/paste.gif"));

		addSeparator();

		add(editor.bind("Delete", mxGraphActions.getDeleteAction(),
				"/fr/enib/game/editor/graphe/examples/swing/images/delete.gif"));

		addSeparator();

//		add(editor.bind("Undo", new HistoryAction(true),
//				"/fr/enib/game/editor/graphe/examples/swing/images/undo.gif"));
//		add(editor.bind("Redo", new HistoryAction(false),
//				"/fr/enib/game/editor/graphe/examples/swing/images/redo.gif"));

		addSeparator();
		
		add(editor.bind("Run", new StartSimulationAction(editor.getGraphComponent().getGraph()),
				"/fr/enib/game/editor/graphe/examples/swing/images/start.png"));

		addSeparator();

		final mxGraphView view = editor.getGraphComponent().getGraph()
				.getView();
		final JComboBox<String> zoomCombo = new JComboBox<String>(new String[] { "400%",
				"200%", "150%", "100%", "75%", "50%" });
		zoomCombo.setEditable(true);
		zoomCombo.setMinimumSize(new Dimension(75, 0));
		zoomCombo.setPreferredSize(new Dimension(75, 0));
		zoomCombo.setMaximumSize(new Dimension(75, 100));
		zoomCombo.setMaximumRowCount(9);
		add(zoomCombo);

		// Sets the zoom in the zoom combo the current value
		mxIEventListener scaleTracker = new mxIEventListener()
		{
			/**
			 * 
			 */
			public void invoke(Object sender, mxEventObject evt)
			{
				ignoreZoomChange = true;

				try
				{
					zoomCombo.setSelectedItem((int) Math.round(100 * view
							.getScale())
							+ "%");
				}
				finally
				{
					ignoreZoomChange = false;
				}
			}
		};

		// Installs the scale tracker to update the value in the combo box
		// if the zoom is changed from outside the combo box
		view.getGraph().getView().addListener(mxEvent.SCALE, scaleTracker);
		view.getGraph().getView().addListener(mxEvent.SCALE_AND_TRANSLATE,
				scaleTracker);

		// Invokes once to sync with the actual zoom value
		scaleTracker.invoke(null, null);

		zoomCombo.addActionListener(new ActionListener()
		{
			/**
			 * 
			 */
			public void actionPerformed(ActionEvent e)
			{
				mxGraphComponent graphComponent = editor.getGraphComponent();

				// Zoomcombo is changed when the scale is changed in the diagram
				// but the change is ignored here
				if (!ignoreZoomChange)
				{
					String zoom = zoomCombo.getSelectedItem().toString();

					if (zoom.equals(mxResources.get("page")))
					{
						graphComponent.setPageVisible(true);
						graphComponent
								.setZoomPolicy(mxGraphComponent.ZOOM_POLICY_PAGE);
					}
					else if (zoom.equals(mxResources.get("width")))
					{
						graphComponent.setPageVisible(true);
						graphComponent
								.setZoomPolicy(mxGraphComponent.ZOOM_POLICY_WIDTH);
					}
					else if (zoom.equals(mxResources.get("actualSize")))
					{
						graphComponent.zoomActual();
					}
					else
					{
						try
						{
							zoom = zoom.replace("%", "");
							double scale = Math.min(16, Math.max(0.01,
									Double.parseDouble(zoom) / 100));
							graphComponent.zoomTo(scale, graphComponent
									.isCenterZoom());
						}
						catch (Exception ex)
						{
							JOptionPane.showMessageDialog(editor, ex
									.getMessage());
						}
					}
				}
			}
		});
	}
}
