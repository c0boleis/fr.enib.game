/**
 * 
 */
package fr.enib.game.editor.graphe.examples.swing.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Timer;

import fr.enib.game.app.GraphEditeur;
import fr.enib.game.app.Viewer;
import fr.enib.game.editor.graphe.monde.DessinMusee;
import fr.enib.game.editor.graphe.view.mxGraph;

/**
 * @author Corentin Boleis
 *
 */
public class StartSimulationAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6890145175913109507L;
	
	private mxGraph mxGraph;
	
	/**
	 * @param mxGraph 
	 * 
	 */
	public StartSimulationAction(mxGraph mxGraph) {
		this.mxGraph = mxGraph;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(GraphEditeur.DESSIN_2D){
			DessinMusee.main(null);
		}
		else{
			Viewer.main(new String[]{"false"});
			runDraw();
		}
	}
	

	
    private void runDraw() {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	mxGraph.refresh();
            }
        };
        Timer timer = new Timer(500,al);
        timer.start();
    }

}
