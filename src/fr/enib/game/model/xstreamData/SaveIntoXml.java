/**
 * 
 */
package fr.enib.game.model.xstreamData;

import java.io.BufferedWriter;
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

import fr.enib.game.model.interfaces.IModelObject;

/**
 * 
 * Cette classe permet de sauvegarder un graphe sous forme d'un fichier xml.
 *
 */
public class SaveIntoXml {
	
	private String fichier;
	private XStream xstream;
	
	public SaveIntoXml(String cheminNouveauFichier){
		setFichier(cheminNouveauFichier);
		
		xstream = new XStream(new DomDriver());
		
	}
	
	public void enregistrer(){
		BufferedWriter buf;
		try {
			//Ecriture dans le fichier XML
			buf = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fichier), "UTF8"));
			//String xml = xstream.toXML(Model.get());
			buf.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			//buf.write(xml);
			buf.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save(){
		try {
			// Instanciation de la classe XStream
			XStream xstream = new XStream(new DomDriver());
			// Instanciation de la classe Entete
			//Entete entete = new Entete("Titre de l'article", new Date());
			// Instanciation de la classe Article
			//Article article = new Article(entete, "Un synopsis bien placé !!! <strong>avec une balise HTML</strong>");

			// Instanciation d'un fichier c:/temp/article.xml
			//File fichier = new File("c:/temp/article.xml");
			// Instanciation d'un flux de sortie fichier vers
			// c:/temp/article.xml
			FileOutputStream fos = new FileOutputStream(fichier);
			try {
				// Sérialisation de l'objet article dans c:/temp/article.xml
				//xstream.toXML(article, fos);
			} finally {
				// On s'assure de fermer le flux quoi qu'il arrive
				fos.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void ouvrir(){
		InputStream inputStream = null;
		Reader reader = null;
	    //Lecture du fichier
		try {
			inputStream = new java.io.FileInputStream(fichier);
			reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
			xstream.fromXML(reader);
			//Model.get().initPostionNoeud();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return le nom du fichier
	 */
	public String getFichier() {
		return fichier;
	}

	/**
	 * @param affecte le nouveau fichier
	 */
	public void setFichier(String fichier) {
		this.fichier = fichier;
	}
}
