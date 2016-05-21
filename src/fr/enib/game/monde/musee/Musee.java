package fr.enib.game.monde.musee;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
	
	//private enum Orientation{
	//	AVANT, ARRIERE, GAUCHE, DROITE
	//}
	//private Orientation orientationAvatar;
	
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
		//this.orientationAvatar = Orientation.AVANT;
		this.monde = Monde.get();
	}

	/**
	 * 
	 * @param nomNoeud
	 * @param tableaux
	 */
	public void ajouterListeTableaux(String nomNoeud, List<ITableau> tableaux){
		ITableau[] tabs;
		
		tabs = listeTableaux.get(nomNoeud);
		
		if(tabs == null){
			tabs = limitTableaux(tableaux);
			
			if(tabs != null){
				listeTableaux.put(nomNoeud, tabs);
			}
		}
	}
	
	/**
	 * Limite le nombre de tableau
	 * @param tableaux
	 * @return
	 */
	private ITableau[] limitTableaux(List<ITableau> tableaux){
		return limitTableaux(tableaux, 10);
	}
	
	/**
	 * Limite le nombre de tableau
	 * @param tableaux
	 * @param nbre
	 * @return
	 */
	private ITableau[] limitTableaux(List<ITableau> tableaux, int nbre){
		if(nbre > tableaux.size()){
			nbre = tableaux.size();
		}
		ITableau[] tabs = new ITableau[nbre];
		
		for(int i = 0; i < nbre; i++){
			tabs[i] = tableaux.get(i);
		}
		return tabs;
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
	 * @return
	 */
	private boolean construireSalle(String nom){
		Salle s;
		
		s = getSalleById(nom);
		
		if(s == null){
			s = new Salle(PREFIX_ID_SALLE + nom, this.largeurSalle, this.profondeurSalle, this.hauteurSalle);
			if(salleCourante == null){
				this.salleCourante = s;
				this.salleCourante.capteurPresenceAvatar.setInterieur(true);
				s.placer(positionCentre);
				s.setPlaced(true);
				monde.setSalleCourante(this.salleCourante);
			}		
			salles.put(s.getId(), s);
			return true;
		}
		return false;
	}
	
	private void placerSalles(){
		for(Salle s : salles.values()){
			if(!s.isPlaced()){
				if(getSalleAtPlace(positionCentre.x + profondeurSalle + distanceMur, positionCentre.y, positionCentre.z) == null){ // avant
					s.placer(positionCentre.x + profondeurSalle + distanceMur, positionCentre.y, positionCentre.z);
					salleCourante.ajouterSalleVoisine(s);
					s.setPlaced(true);
				}
				else if(getSalleAtPlace(positionCentre.x - profondeurSalle - distanceMur, positionCentre.y, positionCentre.z) == null){ // arriere
					s.placer(positionCentre.x - profondeurSalle - distanceMur, positionCentre.y, positionCentre.z);	
					salleCourante.ajouterSalleVoisine(s);
					s.setPlaced(true);		
				}
				else if(getSalleAtPlace(positionCentre.x, positionCentre.y + largeurSalle + distanceMur, positionCentre.z) == null){ // gauche
					s.placer(positionCentre.x, positionCentre.y + largeurSalle + distanceMur, positionCentre.z);
					salleCourante.ajouterSalleVoisine(s);
					s.setPlaced(true);
				}
				else if(getSalleAtPlace(positionCentre.x, positionCentre.y - largeurSalle - distanceMur, positionCentre.z) == null){ // droite
					s.placer(positionCentre.x, positionCentre.y - largeurSalle - distanceMur, positionCentre.z);
					salleCourante.ajouterSalleVoisine(s);
					s.setPlaced(true);
				}
				else{
					LOGGER.info("pas de place - " + s.getId());
				}
			}
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	private Salle getSalleAtPlace(float x, float y, float z){
		for(Salle s : salles.values()){
			if(s.getPositionRepere().x == x && s.getPositionRepere().y == y && s.getPositionRepere().z == z){
				return s;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	private Salle getSalleById(String id){
		String idTmp;
		if(!id.contains(PREFIX_ID_SALLE)){
			idTmp = PREFIX_ID_SALLE + id;
		}
		else{
			idTmp = id;
		}
		
		return salles.get(idTmp);
	}
	
	/**
	 * 
	 * @param nom
	 * @param tableaux
	 */
	private void ajouterTableau(String nom, ITableau[] tableaux){
		Salle s = getSalleById(nom);
		if(s == null){
			LOGGER.info("Problème ajout tableaux ");
			return;
		}
		if(s.isTableauxPlaced()){
			//LOGGER.info("tableau deja ajoute");
			return;
		}
		for(ITableau t : tableaux){
			s.ajouterTableau(new Tableau(t.getId(), t.getUrlImage(), t.getLargeurTableau(), t.getHauteurTableau(),t));
		}
		s.setTableauxPlaced(true);
	}

	/**
	 * 
	 * @param listeTableaux
	 */
	public void setListeTableaux(HashMap<String, ITableau[]> listeTableaux) {
		this.listeTableaux = listeTableaux;
	}

	/**
	 * Change de la piece centrale
	 * @param positionAvatar
	 */
	public void setPositionCentre(Vec3 positionAvatar) {
		float x = positionAvatar.x - positionCentre.x;
		float y = positionAvatar.y - positionCentre.y;
		
		if(x > profondeurSalle/2.0f + distanceMur){
			positionCentre.x = positionCentre.x + profondeurSalle + distanceMur;
			Salle s = getSalleAtPlace(positionCentre.x, positionCentre.y, positionCentre.z);
			if(s != null){
				salleCourante = s;
				s.clearTableaux();
				s.setTableauxPlaced(false);
			}
			else{
				LOGGER.info("Probleme chagement salle");
			}
		}
		else if(x < -(profondeurSalle/2.0f + distanceMur)){
			positionCentre.x = positionCentre.x - profondeurSalle - distanceMur;
			Salle s = getSalleAtPlace(positionCentre.x, positionCentre.y, positionCentre.z);
			if(s != null){
				salleCourante = s;
				s.clearTableaux();
				s.setTableauxPlaced(false);
			}
			else{
				LOGGER.info("Probleme chagement salle");
			}
		}
		else if(y > largeurSalle/2.0f + distanceMur){
			positionCentre.y = positionCentre.y + largeurSalle + distanceMur;
			Salle s = getSalleAtPlace(positionCentre.x, positionCentre.y, positionCentre.z);
			if(s != null){
				salleCourante = s;
				s.clearTableaux();
				s.setTableauxPlaced(false);
			}
			else{
				LOGGER.info("Probleme chagement salle");
			}
			
		}
		else if(y < -(largeurSalle/2.0f + distanceMur)){
			positionCentre.y = positionCentre.y - largeurSalle - distanceMur;
			Salle s = getSalleAtPlace(positionCentre.x, positionCentre.y, positionCentre.z);
			if(s != null){
				salleCourante = s;
				s.clearTableaux();
				s.setTableauxPlaced(false);
			}
			else{
				LOGGER.info("Probleme chagement salle");
			}
		}
		//LOGGER.info("new pos -> x : " + positionCentre.x + ", " + positionCentre.y);
	}
}