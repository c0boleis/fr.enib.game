/**
 * 
 */
package fr.enib.game.model.xstreamData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import fr.enib.game.model.Model;
import fr.enib.game.model.interfaces.IModelObject;

/**
 * 
 * Cette classe permet de sauvegarder un graphe sous forme d'un fichier xml.
 *
 */
public class SaveIntoXml {
	
	private File fichier;
	private XStream xstream;
	
	/**
	 * @param cheminNouveauFichier le chemin du fichier xml
	 */
	public SaveIntoXml(File cheminNouveauFichier){
		setFichier(cheminNouveauFichier);
		xstream = new XStream(new DomDriver());
	}
	
	public void enregistrer(){
		BufferedWriter buf;
		try {
			//Ecriture dans le fichier XML
			buf = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fichier), "UTF8"));
			String xml = xstream.toXML(Model.get());
			buf.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			buf.write(xml);
			buf.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Model importer(){
		InputStream inputStream = null;
		Reader reader = null;
		Model modele = null;
	    //Lecture du fichier
		try {
			inputStream = new java.io.FileInputStream(fichier);
			reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
			modele = (Model)xstream.fromXML(reader);
			//Model.get().initPostionNoeud();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return modele;
	}

	/**
	 * @return le nom du fichier
	 */
	public File getFichier() {
		return fichier;
	}

	/**
	 * @param fichier fichier de sauvegarde.
	 */
	public void setFichier(File fichier) {
		this.fichier = fichier;
	}
}
