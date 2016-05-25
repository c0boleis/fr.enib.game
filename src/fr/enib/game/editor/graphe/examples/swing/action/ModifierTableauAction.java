/**
 * 
 */
package fr.enib.game.editor.graphe.examples.swing.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import fr.enib.game.app.GraphEditeur;
import fr.enib.game.editor.graphe.model.mxCell;
import fr.enib.game.editor.graphe.model.mxICell;
import fr.enib.game.editor.graphe.view.mxGraph;
import fr.enib.game.model.Model;
import fr.enib.game.model.interfaces.IModelObject;
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

	/**
	 * 
	 */
	public static void checkFileTableau(){
		ITableau[] tableaux = getAllTableaux();
		String path = tableaux[0].getUrlImage();
		if(path==null){
			System.err.println("impossible de checker les tableaux");
			return;
		}
		for(ITableau tableau : tableaux){
			path = checkTableau(tableau, path);
		}
		File fileTest = new File(path);
		if(fileTest.exists()){
			return;
		}
		int rep = JOptionPane.showConfirmDialog(null, "Voulez remplacez : \""+path+"\", par un autre dossier?");
		if(rep!=JOptionPane.OK_OPTION)return;
		
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.showOpenDialog(null);
		File file = chooser.getSelectedFile();
		if(file==null || !file.isDirectory() || !file.exists())return;
		replaceFolder(file, path);
	}
	
	private static void replaceFolder(File file,String path){
		path+=File.separator;
		mxGraph graph = GraphEditeur.get().getGraphComponent().getGraph();
		String pathFoler = file.getPath()+File.separator;
		ITableau[] tableaux = getAllTableaux();
		for(ITableau tableau : tableaux){
			String pt = tableau.getUrlImage();
			pt = pt.replace(path, pathFoler);
			tableau.setUrlImage(pt);
		}
		Object[] tmp = graph.getChildCells(graph.getDefaultParent());
		path = path.replace(File.separator, "/");
		pathFoler = pathFoler.replace(File.separator, "/");
		for(Object object : tmp){
			if(object instanceof mxCell){
				String st = ((mxCell) object).getStyle();
				st = st.replace(path, pathFoler);
				((mxCell) object).setStyle(st);
			}
		}
		graph.refresh();
	}

	private static String checkTableau(ITableau tableau,String path){
		String pathTableau = tableau.getUrlImage();
		while(!pathTableau.contains(path)){
			path = reducePath(path);
		}
		return path;
	}

	private static String reducePath(String path){
		int k = path.lastIndexOf(File.separator);
		if(k<0){
			return "";
		}
		return path.substring(0, k);
	}

	private static ITableau[] getAllTableaux(){
		IModelObject[] tmp = Model.get().getModelObjects();
		List<ITableau> out = new ArrayList<ITableau>();
		for(IModelObject modelObject : tmp){
			if(!(modelObject instanceof ITableau))continue;
			out.add((ITableau)modelObject);
		}
		return out.toArray(new ITableau[0]);
	}

	@SuppressWarnings("unused")
	private static void updateParentAndIndex(File file){
		if(!file.exists()){
			return;
		}
		parent = file.getParentFile();
		modifyNextIndex(file);
	}

}
