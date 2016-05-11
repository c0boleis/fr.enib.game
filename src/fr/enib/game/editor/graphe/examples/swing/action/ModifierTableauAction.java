/**
 * 
 */
package fr.enib.game.editor.graphe.examples.swing.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import fr.enib.game.editor.graphe.model.mxCell;
import fr.enib.game.editor.graphe.model.mxICell;
import fr.enib.game.editor.graphe.view.mxGraph;
import fr.enib.game.model.interfaces.ITableau;

/**
 * @author Corentin Boleis
 *
 */
public class ModifierTableauAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4759409945837834694L;
	
	private ITableau tableau;
	
	private mxICell cell;
	
	private mxGraph graph;
	
	private static File parent = null;
	
	private static int indexFile = 0;
	
	/**
	 * @param tableau
	 * @param cell 
	 * @param graph 
	 */
	public ModifierTableauAction(ITableau tableau,mxICell cell,mxGraph graph) {
		this.tableau = tableau;
		this.cell = cell;
		this.graph = graph;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		if(parent!=null){
			chooser.setCurrentDirectory(parent);;
		}
		//TODO add filter
		chooser.showOpenDialog(null);
		File file = chooser.getSelectedFile();
		if(file!=null){
			parent = file.getParentFile();
			//TODO check with the absolute path
			tableau.setUrlImage(file.getPath());
			cell.setStyle("icon;image="+file.getPath().replace(File.separator, "/"));
//			cell.setStyle("icon;image=/fr/enib/game/editor/graphe/examples/swing/images/wrench.png");
			graph.refresh();
		}
	}
	
	public static void modifyNextIndex(File file){
		if(parent==null){
			indexFile = -1;
			return;
		}
		int index=0;
		File[] files = parent.listFiles();
		for(File fileTmp : files){
			if(file.getPath().equals(fileTmp.getPath())){
				break;
			}
			index++;
		}
		if(index<(files.length-1)){
			indexFile = index;
		}
	}
	
	public static File useNextFile(ITableau tableau, mxCell cell){
		if(parent==null || indexFile<0){
			return  null;
		}
		File[] files = parent.listFiles();
		if(indexFile<files.length){
			File file= files[indexFile];
			tableau.setUrlImage(file.getPath());
			cell.setStyle("icon;image="+file.getPath().replace(File.separator, "/"));
			indexFile++;
		}
		return null;
	}
	
	public static void updateParentAndIndex(File file){
		if(!file.exists()){
			return;
		}
		parent = file.getParentFile();
		modifyNextIndex(file);
	}

}
