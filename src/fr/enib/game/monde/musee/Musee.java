package fr.enib.game.monde.musee;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import fr.enib.game.model.interfaces.ITableau;
import fr.enib.game.monde.builder.Monde;
import fr.enib.game.monde.objet.Tableau;

public class Musee {
	private static Logger LOGGER = Logger.getLogger(Musee.class);
	
	private HashMap<String,Salle> salles;
	private HashMap<String,ITableau[]> listeTableaux;

	private Salle salleCourante;

	private static final String prefixIdSalle = "salle_";
	
	private float largeurSalle;
	private float hauteurSalle;
	private float profondeurSalle;
	private float distanceMur;
	
	private Monde monde;
	
	public Musee(){
		this(new HashMap<>());
	}
	
	public Musee(HashMap<String,ITableau[]> listeTableaux){
		this.distanceMur = 0.5f;
		this.largeurSalle = 5.0f;
		this.hauteurSalle = 3.0f;
		this.profondeurSalle = 5.0f;
				
		this.salles = new HashMap<>();
		this.listeTableaux = listeTableaux;
		
		this.monde = Monde.get();
	}

	public void ajouterListeTableau(String nomNoeud, ITableau[] tableaux){
		if(tableaux != null){
			listeTableaux.put(nomNoeud, tableaux);
		}
	}
	
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
	
	private void construireSalle(String nom){
		Salle s = new Salle(prefixIdSalle + nom, this.largeurSalle, this.profondeurSalle, this.hauteurSalle);
		if(salleCourante == null){
			this.salleCourante = s;
			monde.setSalleCourante(this.salleCourante);
		}		
		salles.put(s.getId(), s);
	}
	
	private void placerSalles(){
		boolean avant =false;
		boolean gauche=false;
		boolean droite=false;
		boolean arriere=false;
		
		if(this.salleCourante == null) {
			LOGGER.info("Salle courante null");
			return;
		}
		
		for(Salle v : salles.values()){
			if(v.getId().equals(salleCourante.getId())){
				v.placer(0.0f, 0.0f, 0.0f);
			}
			else{
				if(!avant){
					v.placer(profondeurSalle + distanceMur, 0.0f, 0.0f);
					salleCourante.ajouterSalleVoisine(v);
					avant = true;
				}
				else if(!gauche){
					v.placer(0.0f, largeurSalle + distanceMur, 0.0f);
					salleCourante.ajouterSalleVoisine(v);
					gauche = true;
				}
				else if(!droite){
					v.placer(0.0f, -largeurSalle - distanceMur, 0.0f);
					salleCourante.ajouterSalleVoisine(v);
					droite = true;
				}
				else if(!arriere){
					v.placer(-profondeurSalle - distanceMur, 0.0f, 0.0f);
					salleCourante.ajouterSalleVoisine(v);
					arriere = true;
				}
				else{
					LOGGER.info("Trop de salle voisine, ne gère pas cela pour le moment");
				}
			}
		}
	}
	
	private void ajouterTableau(String nom, ITableau[] tableaux){
		Salle s = salles.get(prefixIdSalle + nom);
		if(s != null){
			for(ITableau t : tableaux){
				s.ajouterTableau(new Tableau(t.getId(), t.getUrlImage(), t.getLargeurTableau(), t.getHauteurTableau()));
			}
		}
		else{
			LOGGER.info("Problème construction salle");
		}
	}
	
}
