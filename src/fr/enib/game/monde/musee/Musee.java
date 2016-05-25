package fr.enib.game.monde.musee;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import fr.enib.game.model.interfaces.ITableau;
import fr.enib.game.monde.builder.Monde;
import fr.enib.game.monde.geo.Vec3;
import fr.enib.game.monde.objet.Tableau;

/**
 * Contruction du musée dans l'environnement 3D
 * @author Ronan MOREL
 *
 */
public class Musee {
	private static Logger LOGGER = Logger.getLogger(Musee.class);
	
	private HashMap<String,Salle> salles;
	private HashMap<String,ITableau[]> listeTableaux;
	private HashMap<String,ITableau[]> listeTableauxTmp;
	
	private Salle salleCourante;

	public static final String PREFIX_ID_SALLE = "salle_";
	
	private float largeurSalle;
	private float hauteurSalle;
	private float profondeurSalle;
	private float distanceMur;
	
	private Vec3 positionCentre;
	
	private Monde monde;
	
	/**
	 * Constructeur
	 */
	public Musee(){
		this(new LinkedHashMap<String, ITableau[]>());
	}
	
	/**
	 * Constructeur
	 * @param listeTableaux la liste des noeuds avec le nom du noeud et la liste de tableaux associés
	 */
	public Musee(HashMap<String,ITableau[]> listeTableaux){
		this.distanceMur = 0.5f;
		this.largeurSalle = 5.0f;
		this.hauteurSalle = 3.0f;
		this.profondeurSalle = 5.0f;
				
		this.salles = new LinkedHashMap<>();
		this.listeTableaux = listeTableaux;
		this.listeTableauxTmp = new LinkedHashMap<>();
		
		this.positionCentre = new Vec3(0f, 0f, 0f);
		this.monde = Monde.get();
	}

	/**
	 * Ajout une liste de tableau en fonction d'un noeud
	 * @param nomNoeud le nom du noeud
	 * @param tableaux la liste de tableaux à ajouter
	 */
	public void ajouterListeTableaux(String nomNoeud, List<ITableau> tableaux){
		ITableau[] tabs;
		
		tabs = listeTableaux.get(nomNoeud);
		
		if(tabs == null){
			tabs = limitTableaux(tableaux);
			
			if(tabs != null){
				listeTableauxTmp.put(nomNoeud, tabs);
				listeTableaux.put(nomNoeud, tabs);
			}
		}
	}
	
	/**
	 * Limite le nombre de tableau (limité à 10)
	 * @param tableaux la liste des tableaux en entrée
	 * @return une liste de tableaux avec une taille inférieur ou égale à 10
	 */
	private ITableau[] limitTableaux(List<ITableau> tableaux){
		return limitTableaux(tableaux, 10);
	}
	
	/**
	 * Limite le nombre de tableau
	 * @param tableaux la liste de tableau en entrée
	 * @param nbre le nombre auquel on veut limiter le nombre de tableaux
	 * @return une liste de tableaux avec une taille inférieur ou égale à <i>nbre</i>
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
	 * Générer les salles du musée (a utilisé en association avec ajouterListeTableaux)
	 */
	public void genererSalles(){
		if(salleCourante == null){
			Map.Entry<String, ITableau[]> entry= listeTableauxTmp.entrySet().iterator().next();
			String k = entry.getKey();
			construireSalle(k);
		}
		int nbPlace = getNombrePlaceSalle();
		
		for(Entry<String, ITableau[]> entry : listeTableauxTmp.entrySet()) {
		    if(nbPlace > 0){
				String k = entry.getKey();
			    if(construireSalle(k)){
					ITableau[] v = entry.getValue();
					listeTableaux.put(k, v);
			    	nbPlace--;
			    }
		    }
		}
		
		placerSalles();
		
		for(Entry<String, ITableau[]> entry : listeTableaux.entrySet()) {
		    String k = entry.getKey();
		    ITableau[] v = entry.getValue();
		    ajouterTableau(k, v);
		}
		listeTableauxTmp.clear();
	}
	
	/**
	 * Construire une salle en mémoire et l'ajout à la liste des salles existantes
	 * @param nom le nom du noeud de la salle a construire
	 * @return true si la salle a été construite
	 */
	private boolean construireSalle(String nom){
		Salle s;
		
		s = getSalleById(nom);
		
		if(s == null){
			s = new Salle(PREFIX_ID_SALLE + nom, this.largeurSalle, this.profondeurSalle, this.hauteurSalle, nom);
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
	
	/**
	 * Récupère le nombre de salle disponible autour de la position de la salle courante
	 * @return le nombre de salle disponible
	 */
	private int getNombrePlaceSalle(){
		int nb = 0;
		
		if(getSalleAtPlace(positionCentre.x + profondeurSalle + distanceMur, positionCentre.y, positionCentre.z) == null){ // avant
			nb++;
		}
		if(getSalleAtPlace(positionCentre.x - profondeurSalle - distanceMur, positionCentre.y, positionCentre.z) == null){ // arriere
			nb++;		
		}
		if(getSalleAtPlace(positionCentre.x, positionCentre.y + largeurSalle + distanceMur, positionCentre.z) == null){ // gauche
			nb++;
		}
		 if(getSalleAtPlace(positionCentre.x, positionCentre.y - largeurSalle - distanceMur, positionCentre.z) == null){ // droite
			nb++;
		}
		
		return nb;
	}
	
	/**
	 * Place les salles dans l'environnement virtuel en fonction de la salle courante
	 */
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
					LOGGER.debug("pas de place - " + s.getId());
				}
			}
		}
	}
	
	/**
	 * Recupére une salle à une position données
	 * @param x la coordonnées en x de la salle
	 * @param y la coordonnées en Y de la salle
	 * @param z la coordonnées en z de la salle
	 * @return la salle à cette poisition, sinon null
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
	 * Récupère la salle en fonction de son id
	 * @param id l'id de la salle (ou le nom du noeud)
	 * @return la salle correspondant au noeud, sinon null
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
	 * Ajoute une liste de tableaux à une salle
	 * @param nom le nom du noeud ou l'id de la salle
	 * @param tableaux la liste des tableaux 
	 */
	private void ajouterTableau(String nom, ITableau[] tableaux){
		Salle s = getSalleById(nom);
		if(s == null){
			LOGGER.debug("pas de salle");
			return;
		}
		if(s.isTableauxPlaced()){
			return;
		}
		for(ITableau t : tableaux){
			s.ajouterTableau(new Tableau(t.getId(), t.getUrlImage(), t.getLargeurTableau(), t.getHauteurTableau(),t));
		}
		s.setTableauxPlaced(true);
	}

	/**
	 * Change de la salle courante
	 * @param positionAvatar la position de l'avatar au moment du changement de pièce
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
				monde.setSalleCourante(this.salleCourante);
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
				monde.setSalleCourante(this.salleCourante);
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
				monde.setSalleCourante(this.salleCourante);
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
				monde.setSalleCourante(this.salleCourante);
			}
			else{
				LOGGER.info("Probleme chagement salle");
			}
		}
		//LOGGER.info("new pos -> x : " + positionCentre.x + ", " + positionCentre.y);
	}
}