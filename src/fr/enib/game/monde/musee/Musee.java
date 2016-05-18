package fr.enib.game.monde.musee;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import fr.enib.game.model.interfaces.ITableau;
import fr.enib.game.monde.builder.Monde;
import fr.enib.game.monde.geo.Vec3;
import fr.enib.game.monde.objet.Tableau;

/**
 * 
 * @author Ronan MOREL
 *
 */
public class Musee {
	private static Logger LOGGER = Logger.getLogger(Musee.class);
	
	private HashMap<String,Salle> salles;
	private HashMap<String,ITableau[]> listeTableaux;

	private Salle salleCourante;

	public static final String PREFIX_ID_SALLE = "salle_";
	
	private float largeurSalle;
	private float hauteurSalle;
	private float profondeurSalle;
	private float distanceMur;
	
	private Vec3 positionCentre;
	
	private enum Orientation{
		AVANT, ARRIERE, GAUCHE, DROITE
	}
	private Orientation orientationAvatar;
	
	private Monde monde;
	
	/**
	 * 
	 */
	public Musee(){
		this(new LinkedHashMap<>());
	}
	
	/**
	 * 
	 * @param listeTableaux
	 */
	public Musee(HashMap<String,ITableau[]> listeTableaux){
		this.distanceMur = 0.5f;
		this.largeurSalle = 5.0f;
		this.hauteurSalle = 3.0f;
		this.profondeurSalle = 5.0f;
				
		this.salles = new LinkedHashMap<>();
		this.listeTableaux = listeTableaux;
		
		this.positionCentre = new Vec3(0f, 0f, 0f);
		this.orientationAvatar = Orientation.AVANT;
		this.monde = Monde.get();
	}

	/**
	 * 
	 * @param nomNoeud
	 * @param tableaux
	 */
	public void ajouterListeTableau(String nomNoeud, ITableau[] tableaux){
		if(tableaux != null){
			listeTableaux.put(nomNoeud, tableaux);
		}
	}
	
	/**
	 * 
	 */
	public void genererSalles(){
		for(Entry<String, ITableau[]> entry : listeTableaux.entrySet()) {
		    String k = entry.getKey();
		    construireSalle(k);
		}
		placerSalles();
		
		for(Entry<String, ITableau[]> entry : listeTableaux.entrySet()) {
		    String k = entry.getKey();
		    ITableau[] v = entry.getValue();
		    ajouterTableau(k, v);
		}
	}
	
	/**
	 * 
	 * @param nom
	 */
	private void construireSalle(String nom){
		Salle s = new Salle(PREFIX_ID_SALLE + nom, this.largeurSalle, this.profondeurSalle, this.hauteurSalle);
		if(salleCourante == null){
			this.salleCourante = s;
			this.salleCourante.capteurPresenceAvatar.setInterieur(true);
			monde.setSalleCourante(this.salleCourante);
		}		
		salles.put(s.getId(), s);
	}
	
	private void placerSalleAvant(){
		int nbSalle = salles.size();
		if(nbSalle >= 2){
			((Salle) salles.values().toArray()[0]).placer(positionCentre.x, positionCentre.y, positionCentre.z);
			((Salle) salles.values().toArray()[1]).placer(positionCentre.x - profondeurSalle - distanceMur, positionCentre.y, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[1]));
		}
		if(nbSalle >= 3){
			((Salle) salles.values().toArray()[2]).placer(positionCentre.x + profondeurSalle + distanceMur, positionCentre.y, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[2]));
		}
		if(nbSalle >= 4){
			((Salle) salles.values().toArray()[3]).placer(positionCentre.x, positionCentre.y + largeurSalle + distanceMur, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[3]));
		}
		if(nbSalle == 5){
			((Salle) salles.values().toArray()[4]).placer(positionCentre.x, positionCentre.y - largeurSalle - distanceMur, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[4]));
		}
		if(nbSalle < 2 || nbSalle > 5){
			LOGGER.info("Erreur sur le nombre de salles : " + nbSalle);
		}
	}
	
	private void placerSalleArriere(){
		int nbSalle = salles.size();
		if(nbSalle >= 2){
			((Salle) salles.values().toArray()[0]).placer(positionCentre.x, positionCentre.y, positionCentre.z);
			((Salle) salles.values().toArray()[1]).placer(positionCentre.x + profondeurSalle + distanceMur, positionCentre.y, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[1]));
		}
		if(nbSalle >= 3){
			((Salle) salles.values().toArray()[2]).placer(positionCentre.x - profondeurSalle - distanceMur, positionCentre.y, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[2]));
		}
		if(nbSalle >= 4){
			((Salle) salles.values().toArray()[3]).placer(positionCentre.x, positionCentre.y - largeurSalle - distanceMur, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[3]));
		}
		if(nbSalle == 5){
			((Salle) salles.values().toArray()[4]).placer(positionCentre.x, positionCentre.y + largeurSalle + distanceMur, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[4]));
		}
		if(nbSalle < 2 || nbSalle > 5){
			LOGGER.info("Erreur sur le nombre de salles : " + nbSalle);
		}
	}
	
	private void placerSalleGauche(){
		int nbSalle = salles.size();
		if(nbSalle >= 2){
			((Salle) salles.values().toArray()[0]).placer(positionCentre.x, positionCentre.y, positionCentre.z);
			((Salle) salles.values().toArray()[1]).placer(positionCentre.x - largeurSalle - distanceMur, positionCentre.y, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[1]));
		}
		if(nbSalle >= 3){
			((Salle) salles.values().toArray()[1]).placer(positionCentre.x + largeurSalle + distanceMur, positionCentre.y, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[2]));
		}
		if(nbSalle >= 4){
			((Salle) salles.values().toArray()[3]).placer(positionCentre.x, positionCentre.y - profondeurSalle - distanceMur, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[3]));
		}
		if(nbSalle == 5){
			((Salle) salles.values().toArray()[4]).placer(positionCentre.x, positionCentre.y + profondeurSalle + distanceMur, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[4]));
		}
		if(nbSalle < 2 || nbSalle > 5){
			LOGGER.info("Erreur sur le nombre de salles : " + nbSalle);
		}
	}
	
	private void placerSalleDroite(){
		int nbSalle = salles.size();
		if(nbSalle >= 2){
			((Salle) salles.values().toArray()[0]).placer(positionCentre.x, positionCentre.y, positionCentre.z);
			((Salle) salles.values().toArray()[1]).placer(positionCentre.x + largeurSalle + distanceMur, positionCentre.y, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[0]));
		}
		if(nbSalle >= 3){
			((Salle) salles.values().toArray()[1]).placer(positionCentre.x - largeurSalle - distanceMur, positionCentre.y, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[1]));
		}
		if(nbSalle >= 4){
			((Salle) salles.values().toArray()[3]).placer(positionCentre.x, positionCentre.y + profondeurSalle + distanceMur, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[2]));
		}
		if(nbSalle == 5){
			((Salle) salles.values().toArray()[4]).placer(positionCentre.x, positionCentre.y - profondeurSalle - distanceMur, positionCentre.z);
			((Salle) salles.values().toArray()[0]).ajouterSalleVoisine(((Salle) salles.values().toArray()[3]));
		}
		if(nbSalle < 2 || nbSalle > 5){
			LOGGER.info("Erreur sur le nombre de salles : " + nbSalle);
		}
	}
	
	private void placerSalles(){
		switch(orientationAvatar){
			case AVANT:
				placerSalleAvant();
				break;
			case ARRIERE:
				placerSalleArriere();
				break;
			case GAUCHE:
				placerSalleGauche();
				break;
			case DROITE:
				placerSalleDroite();
				break;
		}
	}
	
	/**
	 * 
	 * @param nom
	 * @param tableaux
	 */
	private void ajouterTableau(String nom, ITableau[] tableaux){
		Salle s = salles.get(PREFIX_ID_SALLE + nom);
		if(s != null){
			for(ITableau t : tableaux){
				s.ajouterTableau(new Tableau(t.getId(), t.getUrlImage(), t.getLargeurTableau(), t.getHauteurTableau()));
			}
		}
		else{
			LOGGER.info("Problème construction salle");
		}
	}

	public void setListeTableaux(HashMap<String, ITableau[]> listeTableaux) {
		this.listeTableaux = listeTableaux;
	}
	
	public void clear(){
		listeTableaux.clear();
		salleCourante = null;
		salles.clear();
	}

	public void setPositionCentre(Vec3 positionAvatar) {
		float x = positionAvatar.x - positionCentre.x;
		float y = positionAvatar.y - positionCentre.y;
		
		if(x > profondeurSalle/2.0f + distanceMur){
			orientationAvatar = Orientation.AVANT;
			positionCentre.x = positionCentre.x + profondeurSalle + distanceMur;
		}
		else if(x < -(profondeurSalle/2.0f + distanceMur)){
			orientationAvatar = Orientation.ARRIERE;
			positionCentre.x = positionCentre.x - profondeurSalle - distanceMur;
		}
		else if(y > largeurSalle/2.0f + distanceMur){
			orientationAvatar = Orientation.GAUCHE;
			positionCentre.y = positionCentre.y + largeurSalle + distanceMur;
			
		}
		else if(y < -(largeurSalle/2.0f + distanceMur)){
			orientationAvatar = Orientation.DROITE;
			positionCentre.y = positionCentre.y - largeurSalle - distanceMur;
		}
		LOGGER.info("new pos -> x : " + positionCentre.x + ", " + positionCentre.y);
	}
}