package fr.enib.game.monde.objet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Fourni les chemin pour les ressources necessaires au differents objets
 * @author Ronan MOREL
 *
 */
public class RessourceProvider {

	public static final String pathTextureSol = "data"+File.separator+"textures"+File.separator+"tSol1.jpg";
	
	public static final String pathTexturePlafond = "data"+File.separator+"textures"+File.separator+"tPlafond.jpg";
	
	public static final String pathTextureMur = "data"+File.separator+"textures"+File.separator+"tMur2.jpg";

	public static final String pathTextureCouloir = "data"+File.separator+"textures"+File.separator+"tCouloir.jpg";

	public static final String pathTableaux = "data"+File.separator+"Image_graphe"+File.separator + "/";
	
	public static List<String> getNomTableaux(){
		File folder = new File(pathTableaux);
		File[] listOfFiles = folder.listFiles();

		List<String> names = new ArrayList<>();
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".jpg")) {
	        names.add(listOfFiles[i].getPath());
	      }
	    }
	    return names;
	}
}