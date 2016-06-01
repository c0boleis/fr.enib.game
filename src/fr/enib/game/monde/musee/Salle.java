package fr.enib.game.monde.musee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.jogamp.opengl.GL2;

import fr.enib.game.monde.capteur.Capteur;
import fr.enib.game.monde.capteur.CapteurCubique;
import fr.enib.game.monde.capteur.CapteurVision;
import fr.enib.game.monde.core.CtxPose;
import fr.enib.game.monde.core.Observable;
import fr.enib.game.monde.core.Observer;
import fr.enib.game.monde.core.Situable;
import fr.enib.game.monde.geo.Vec3;
import fr.enib.game.monde.graphic_core.TrouMur;
import fr.enib.game.monde.objet.Avatar;
import fr.enib.game.monde.objet.Mur;
import fr.enib.game.monde.objet.Objet;
import fr.enib.game.monde.objet.Plafond;
import fr.enib.game.monde.objet.RessourceProvider;
import fr.enib.game.monde.objet.Sol;
import fr.enib.game.monde.objet.Tableau;
import fr.enib.game.monde.objet.TypeObjet;

/**
 * Représente une salle dans l'environnement 3D
 * @author Ronan MOREL
 *
 */
public class Salle extends Situable implements Observer{
	private static Logger LOGGER = Logger.getLogger(Salle.class);

	private static  final float ESPACE_ENTRE_TABLEAU = 0.25f;
	
	public CapteurCubique capteurPresenceAvatar;

	public int nbrPorte;
	public float hauteurPorte, largeurPorte, epaisseurMur;
	protected float largeur, profondeur, hauteur;

	public HashMap<String,Objet>   objets;
	public HashMap<String,Capteur> capteurs;
	public HashMap<String,Capteur> capteursTableaux;
	public HashMap<String,Salle>   voisines;
	
	public String nomNoeud;
	
	private boolean placed;
	private boolean tableauxPlaced;
	
	private static final String PREFIX_ID_TABLEAU = "Tableau_";
	
	/**
	 * Constructeur (pour la classe Couloir)
	 * @param id
	 */
	protected Salle(String id){
		super(id);
		this.largeur = 5.0f;
		this.profondeur = 5.0f;
		this.hauteur = 3.0f;
		// pour l'instant une salle ne peut pas avoir de maitre.
		this.setMaitre(null);
		this.objets = new HashMap<String,Objet>() ;
		this.capteurs = new HashMap<String,Capteur>();
		this.capteursTableaux = new HashMap<String,Capteur>();
	}
	
	/**
	 * Constructeur
	 * @param id l'id de la salle
	 * @param largeur la largeur de la salle
	 * @param profondeur la pronfondeur de la salle
	 * @param hauteur la hauteur de la salle
	 */
	public Salle(String id, float largeur, float profondeur, float hauteur, String nomNoeud){
		this(id, largeur, profondeur, hauteur, 0.1f, 2.0f, 2.5f, nomNoeud);
	}

	/**
	 * Constructeur
	 * @param id l'id de la salle
	 * @param largeur la largeur de la salle
	 * @param profondeur la pronfondeur de la salle
	 * @param hauteur la hauteur de la salle
	 * @param largeurPorte la largeur des portes de la salle
	 * @param hauteurPorte la hauteur des portes de la salle
	 */
	public Salle(String id, float largeur, float profondeur, float hauteur, float epaisseurMur, float largeurPorte, float hauteurPorte, String nomNoeud){
		super(id);

		this.objets     = new HashMap<String,Objet>() ;
		this.capteurs   = new HashMap<String,Capteur>() ;
		this.voisines   = new HashMap<String,Salle>() ;
		this.capteursTableaux = new HashMap<String,Capteur>();

		this.nbrPorte = 0;
		this.largeur = largeur;
		this.profondeur = profondeur;
		this.hauteur = hauteur;

		this.largeurPorte = largeurPorte;
		this.hauteurPorte = hauteurPorte;
		this.epaisseurMur = epaisseurMur;
		this.placed = false;
		this.tableauxPlaced = false;
		
		this.nomNoeud = nomNoeud;
		
		// pour l'instant une salle ne peut pas avoir de maitre.
		this.setMaitre(null);
		
		//on met a jour les information du capteur
		capteurPresenceAvatar = new CapteurCubique("capt"+getId(),Avatar.get(), this);
		capteurPresenceAvatar.add(this);
		ajouter(capteurPresenceAvatar);
		capteurPresenceAvatar.setSalle(this);

		ajouter( new Sol(TypeObjet.SOL.toString() + getId() , RessourceProvider.pathTextureSol, largeur, profondeur));
		ajouter( new Plafond(TypeObjet.PLAFOND.toString() + getId(), RessourceProvider.pathTexturePlafond, largeur, profondeur));  
		ajouter( new Mur(TypeObjet.MUR_GAUCHE.toString() + getId(), RessourceProvider.pathTextureMur, hauteur, profondeur, epaisseurMur)); 
		ajouter( new Mur(TypeObjet.MUR_ARRIERE.toString() + getId(), RessourceProvider.pathTextureMur, hauteur, largeur, epaisseurMur)); 
		ajouter( new Mur(TypeObjet.MUR_DROIT.toString() + getId() , RessourceProvider.pathTextureMur, hauteur, profondeur, epaisseurMur));
		ajouter( new Mur(TypeObjet.MUR_AVANT.toString() + getId(), RessourceProvider.pathTextureMur, hauteur, largeur, epaisseurMur)); 

		//on place les éléments de la salle par raport à son repère.
		getPlafond().placer(0.0f,0.0f,this.hauteur); 
		getSol().placer(0.0f,0.0f,0.0f);

		getMurGauche().orienter((float) +Math.PI/2.0f); 
		getMurGauche().placer(this.profondeur/2.0f,this.largeur/2.0f,0.0f); 

		getMurDroite().orienter((float) -Math.PI/2.0f); 
		getMurDroite().placer(-this.profondeur/2.0f,-this.largeur/2.0f,0.0f);

		getMurArriere().orienter((float) (Math.PI)); 
		getMurArriere().placer(-this.profondeur/2.0f, this.largeur/2.0f,0.0f);

		getMurAvant().placer(this.profondeur/2.0f, -this.largeur/2.0f, 0.0f); 
	}

	/**
	 * Ajout d'un objet dans la salle (tableau, etc.)
	 * @param objet l'objet à ajouter dans la salle
	 */
	public void ajouter(Objet objet){
		objets.put(objet.getId(),objet);
		//tout élément ajouté à la salle est esclave de celle ci.
		this.ajouterEsclave(objet);
	}

	/**
	 * Ajout d'un capteur dans la salle
	 * @param capteur le capteur a ajouter dans la salle
	 */
	public void ajouter(Capteur capteur){
		capteurs.put(capteur.getId(),capteur);
		//tout élément ajouté à la salle est esclave de celle ci.
		new CtxPose("ctxPose"+capteur.getId(), this, capteur, 0.0f, 0.0f, 0.0f, 0.0f);
		this.ajouterEsclave(capteur);
	}
	
	/**
	 * Ajout d'un capteur dans la salle
	 * @param capteur le capteur a ajouter dans la salle
	 */
	public void ajouterCapteurTableau(Capteur capteur){
		capteursTableaux.put(capteur.getId(),capteur);
		//tout élément ajouté à la salle est esclave de celle ci.
		new CtxPose("ctxPose"+capteur.getId(), this, capteur, 0.0f, 0.0f, 0.0f, 0.0f);
		this.ajouterEsclave(capteur);
	}

	/**
	 * Ajout d'une salle voisine a la salle
	 * @param salle la salle voisine a ajouter
	 */
	public void ajouterSalleVoisine(Salle salle){
		voisines.put(salle.getId(),salle);
		salle.voisines.put(this.getId(), this);
		ajouterPorte(salle);
		salle.ajouterPorte(this);
	}
	
	/**
	 * Ajout d'une salle voisine a la salle
	 * @param salle la salle voisine a ajouter
	 */
	public void ajouterSalleVoisine(Salle salle,boolean ajouterPorte){
		voisines.put(salle.getId(),salle);
		salle.voisines.put(this.getId(), this);
		if(ajouterPorte){
			ajouterPorte(salle);
		}
	}
	
	/**
	 * Ajoute toutes les portes a mettre par rapport au salles voisines
	 */
	public void ajouterToutPorte(){
		List<Salle> listeTmp = new ArrayList<Salle>();
		listeTmp.addAll(voisines.values());
		for(Salle salleVoisine : listeTmp){
			if(!(salleVoisine instanceof Couloir)){
				this.ajouterPorte(salleVoisine);
			}
		}
	}
	
	/**
	 * Supprime tous les tableaux présents dans la salle
	 */
	public void clearTableaux(){
		Iterator<Objet> it = objets.values().iterator();

		while (it.hasNext())
		{
		  Objet obj = it.next();
		  if (obj.getId().contains(PREFIX_ID_TABLEAU)){
			    it.remove();
		  }
		}

		Mur c = getCloison(TypeObjet.MUR_AVANT.toString());
		if(c != null){
			if(c.getEsclave() != null){
				c.clearAllEsclave();
			}
		}
		c = getCloison(TypeObjet.MUR_ARRIERE.toString());
		if(c != null){
			if(c.getEsclave() != null){
				c.clearAllEsclave();
			}
		}
		c = getCloison(TypeObjet.MUR_GAUCHE.toString());
		if(c != null){
			if(c.getEsclave() != null){
				c.clearAllEsclave();
			}
		}
		c = getCloison(TypeObjet.MUR_DROIT.toString());
		if(c != null){
			if(c.getEsclave() != null){
				c.clearAllEsclave();
			}
		}
		
		
		capteursTableaux.clear();
	}

	/**
	 * Ajout d'un couloir au niveau d'une porte
	 * @param couloir le couloir a ajouter
	 * @param salle_voisine la salle voisine a laquelle le couloir est relie
	 */
	public void ajouterCouloirPorte(Salle couloir, Salle salle_voisine){
		voisines.put(couloir.getId(),couloir);
		salle_voisine.voisines.put(couloir.getId(),couloir);
	}

	/**
	 * Ajout d'une porte a la salle en fonction de la salle voisine 
	 * @param salleVoisine la salle voisine ou l'on veut ajouter la porte
	 */
	public void ajouterPorte(Salle salleVoisine){
		float dM = -1.0f;
		float pTrouSalle;
		
		float dx = salleVoisine.getPosition().x - this.getPosition().x;
		float dy = salleVoisine.getPosition().y - this.getPosition().y;

		Couloir porte = null;

		if(dx >= (salleVoisine.profondeur + this.profondeur)/2.0){ // Mur Avant
			dM = dx - (salleVoisine.profondeur + this.profondeur)/2.0f;

			if(dy == 0){
				pTrouSalle = this.largeur / 2.0f;
			}
			else if(dy < 0){ // La salle voisine est a droite de la salle
				pTrouSalle = 0.0f; 
			}
			else{ // La salle voisine est a gauche de la salle
				pTrouSalle = 0.0f; 
			}
			
			if(getMurAvant() == null){
				LOGGER.info("Probleme mur avant -> null");
				return;
			}
			
			getMurAvant().addTrou(new TrouMur(largeurPorte, hauteurPorte, pTrouSalle, salleVoisine.nomNoeud));
			this.nbrPorte++;

			if(!voisines.values().isEmpty()){	
				if(voisines.get("porte" + getId() + "_" + salleVoisine.getId()) == null &&
						voisines.get("porte" + salleVoisine.getId() + "_" + getId()) == null){
					porte = new Couloir("porte" + getId() + "_" + salleVoisine.getId(), largeurPorte, dM, hauteurPorte);
					ajouterCouloirPorte(porte, salleVoisine);
					porte.placer(new Vec3(dx/2.0f + getPosition().x, dy + getPosition().y, 0.0f));
				}
			}
		}
		else if(dx <= -(salleVoisine.profondeur + this.profondeur)/2.0){ // Mur Arriere
			dM = -dx - (salleVoisine.profondeur + this.profondeur)/2.0f;
			
			if(dy == 0){
				pTrouSalle = this.largeur / 2.0f;
			}
			else if(dy < 0){ // La salle voisine est a droite de la salle
				pTrouSalle = 0.0f; 
			}
			else{ // La salle voisine est a gauche de la salle
				pTrouSalle = 0.0f; 
			}
			
			if(getMurArriere() == null){
				LOGGER.info("Probleme mur avant -> null");
				return;
			}
			
			getMurArriere().addTrou(new TrouMur(largeurPorte, hauteurPorte, pTrouSalle, salleVoisine.nomNoeud));
			this.nbrPorte++;

			if(!voisines.values().isEmpty()){	
				if(voisines.get("porte" + getId() + "_" + salleVoisine.getId()) == null &&
						voisines.get("porte" + salleVoisine.getId() + "_" + getId()) == null){
					porte = new Couloir("porte" + getId() + "_" + salleVoisine.getId(), largeurPorte, dM, hauteurPorte);
					ajouterCouloirPorte(porte, salleVoisine);
					porte.placer(new Vec3(dx/2.0f + getPosition().x, dy + getPosition().y, 0.0f));
				}
			}
		}
		else if(dy >= (salleVoisine.largeur + this.largeur)/2.0f){ // Mur Gauche
			dM = dy - (salleVoisine.largeur + this.largeur)/2.0f;

			if(dx == 0){
				pTrouSalle = this.profondeur / 2.0f;
			}
			else if(dx < 0){ // La salle voisine est en arriere de la salle
				pTrouSalle = 0.0f; 
			}
			else{ // La salle voisine esten arriere de la salle
				pTrouSalle = 0.0f; 
			}
			
			if(getMurGauche() == null){
				LOGGER.info("Probleme mur avant -> null");
				return;
			}
			
			getMurGauche().addTrou(new TrouMur(largeurPorte, hauteurPorte, pTrouSalle, salleVoisine.nomNoeud));
			this.nbrPorte++;

			if(!voisines.values().isEmpty()){
				if(voisines.get("porte" + getId() + "_" + salleVoisine.getId()) == null &&
						voisines.get("porte" + salleVoisine.getId() + "_" + getId()) == null){
					porte = new Couloir("porte" + getId() + "_" + salleVoisine.getId(), largeurPorte, dM, hauteurPorte);
					ajouterCouloirPorte(porte, salleVoisine);
					porte.placer(new Vec3(dx/2.0f + getPosition().x, dy/2.0f + getPosition().y, 0.0f));
					porte.orienter((float) Math.PI/2.0f);
				}
			}
		}
		else if(dy <= -(salleVoisine.largeur + this.largeur)/2.0f){ // Mur Droite
			dM = -dy - (salleVoisine.largeur + this.largeur)/2.0f;	
			
			if(dx == 0){
				pTrouSalle = this.profondeur / 2.0f;
			}
			else if(dx < 0){ // La salle voisine est en arriere de la salle
				pTrouSalle = 0.0f; 
			}
			else{ // La salle voisine esten arriere de la salle
				pTrouSalle = 0.0f;
			}
			
			if(getMurDroite() == null){
				LOGGER.info("Probleme mur avant -> null");
				return;
			}
			
			getMurDroite().addTrou(new TrouMur(largeurPorte, hauteurPorte, pTrouSalle, salleVoisine.nomNoeud));
			this.nbrPorte++;

			if(!voisines.values().isEmpty()){
				if(voisines.get("porte" + getId() + "_" + salleVoisine.getId()) == null &&
						voisines.get("porte" + salleVoisine.getId() + "_" + getId()) == null){
					porte = new Couloir("porte" + getId() + "_" + salleVoisine.getId(), largeurPorte, dM, hauteurPorte);
					ajouterCouloirPorte(porte, salleVoisine);
					porte.placer(new Vec3(dx/2.0f + getPosition().x, dy/2.0f + getPosition().y, 0.0f));
					porte.orienter((float) Math.PI/2.0f);
				}
			}
		}
	}

	/**
	 * Ajoute un tableau a la salle
	 * @param tableau le tableau a ajouter
	 * @param mur le mur sur lequel on va mettre le tableau
	 * @return true si on a reussi a mettre le tableau, sinon false
	 */
	public boolean ajouterTableau(Tableau tableau, TypeObjet mur) {
		Mur c = getCloison(mur.toString());
		if(c == null) return false;

		float posTab = trouverPlaceTableau(tableau.getTableau().getLargeur(), mur);
		
		if(posTab > -999.0f){
			this.objets.put(tableau.getId(), tableau);

			tableau.setPosition(posTab);
			tableau.placer(0.0f, posTab, this.getHauteur()/2);
			tableau.orienter((float) Math.PI);

			
			float x_tab = 0.0f;
			float y_tab = 0.0f;
			float z_tab = 0.0f;
			if(mur.equals(TypeObjet.MUR_AVANT)){
				x_tab = c.getPositionRepere().x + getPositionRepere().x;
				y_tab = c.getPositionRepere().y + tableau.getPosition() + getPositionRepere().y;
			}
			else if(mur.equals(TypeObjet.MUR_ARRIERE)){
				x_tab = c.getPositionRepere().x + getPositionRepere().x;
				y_tab = c.getPositionRepere().y - tableau.getPosition() + getPositionRepere().y;
			}
			else if(mur.equals(TypeObjet.MUR_GAUCHE)){
				x_tab = c.getPositionRepere().x - tableau.getPosition() + getPositionRepere().x;
				y_tab = c.getPositionRepere().y + getPositionRepere().y;
			}
			else if(mur.equals(TypeObjet.MUR_DROIT)){
				x_tab = c.getPositionRepere().x + tableau.getPosition() + getPositionRepere().x;
				y_tab = c.getPositionRepere().y + getPositionRepere().y;
			}

			z_tab = c.getPositionRepere().z + getPosition().z;

			tableau.setPositionInRepere(new Vec3(x_tab, y_tab , z_tab));
			
			Capteur capTab = new CapteurVision("cpt_" + getId()+ "_" + tableau.getId(), Avatar.get(), tableau);
			capTab.add(this);
			ajouterCapteurTableau(capTab);
			
			return c.ajouterEsclave(tableau);
		}
		return false;
	}

	/**
	 * Cherche s'il y a une place sur un mur pour metter un tableau
	 * @param largeurTableau la largeur du tableau a ajouter
	 * @param mur le mur sur lequel on veut mettre le tableau
	 * @return -1000.0 s'il n'y a pas de place, sinon la position central ou il y a de la place
	 */
	private float trouverPlaceTableau(float largeurTableau, TypeObjet mur){
		Mur c = getCloison(mur.toString());
		
		if(c==null) return -1000.0f;
		
		// Cherche les positions des portes dans les murs 
		ArrayList<PositionLargeur> posLar = new ArrayList<Salle.PositionLargeur>();
		for(TrouMur t : c.getTrous()){
			posLar.add(new PositionLargeur(t.getLargeur(), t.getPosition()));
		}

		// Cherche s'il y a des tableaux déjà ajoutés dans la salle
		if(!c.getEsclave().isEmpty()){
			for(Situable s : c.getEsclave().values()){
				if(s.getId().contains(PREFIX_ID_TABLEAU)){
					Tableau t = (Tableau) s;
					posLar.add(new PositionLargeur(t.getTableau().getLargeur(), t.getPosition()));
				}
			}
		}

		// S'il n'y a pas de porte, ni de tableau dans la salle
		if(posLar.isEmpty()){
			if(c.getCloison().getLargeur() >= (largeurTableau + ESPACE_ENTRE_TABLEAU)){
				return c.getCloison().getLargeur()/2.0f;
			}
		}
		else{ // Sinon on cherche une place
			// On tri dans l'ordre croissant les positions de tableaux et des portes
			Collections.sort(posLar, new Comparator<PositionLargeur>() {
				public int compare(PositionLargeur p1, PositionLargeur p2){
					float c = p1.position - p2.position;

					if(c > 0) return -1;
					else if(c < 0) return 1;
					return 0;
				}
			});
			
			for(int i = 0; i < posLar.size(); i++){
				PositionLargeur pl = posLar.get(i);

				float pos;
				// A droite de l'element
				pos = pl.position - pl.largeur/2.0f - ESPACE_ENTRE_TABLEAU - largeurTableau/2.0f;
				if(isElementPosable(posLar, pos, largeurTableau, c.getCloison().getLargeur()))
					return pos;
				
				// A gauche de l'element
				pos = pl.position + pl.largeur/2.0f + ESPACE_ENTRE_TABLEAU + largeurTableau/2.0f;
				if(isElementPosable(posLar, pos, largeurTableau, c.getCloison().getLargeur()))
					return pos;
			}
		}
		return -1000.0f; // S'il n'y a pas de place
	}
	
	/**
	 * Verifie si la position proposée pour un élément n'est pas deja prise
	 * @param pos les positions des autres objets
	 * @param posTab la position du tableau a poser
	 * @param largeurTableau la largeur du tableau a poser
	 * @param largeurMur la largeur du mur sur lequel le tableau doit etre posé
	 * @return
	 */
	public boolean isElementPosable(List<PositionLargeur> pos, float posTab, float largeurTableau, float largeurMur){
		float posDroite, posGauche;
				
		// Extremite du mur à droite
		if(posTab - largeurTableau/2.0f - ESPACE_ENTRE_TABLEAU < 0){
			return false;
		}
					
		// Extremite du mur à gauche
		if(posTab + largeurTableau/2.0f + ESPACE_ENTRE_TABLEAU > largeurMur){
			return false;
		}				
		
		for(PositionLargeur pl : pos){
			posDroite = pl.position - pl.largeur/2.0f;
			posGauche = pl.position + pl.largeur/2.0f;
			
			// A droite de l'element
			if(Math.abs(posTab - posDroite) - ESPACE_ENTRE_TABLEAU - largeurTableau/2.0f < 0){
				return false;
			}
			
			// Sur l'element
			if(posTab < (posGauche + ESPACE_ENTRE_TABLEAU) && posTab > (posDroite - ESPACE_ENTRE_TABLEAU)){
				return false;
			}
			
			// A gauche de l'element
			if(Math.abs(posTab - posGauche) - ESPACE_ENTRE_TABLEAU - largeurTableau/2.0f < 0){
				return false;
			}
		}
		return true;
	}

	/**
	 * Ajout une liste de tableaux dans la salle
	 * @param listeTableau la liste des tableaux a ajouter
	 */
	public void ajouterTableau(List<Tableau> listeTableau) {
		for(Tableau t : listeTableau){
			ajouterTableau(t);
		}
	}

	/**
	 * Ajoute un tableau dans la salle
	 * @param t le tableau a ajouter
	 */
	public void ajouterTableau(Tableau t) {
		if(ajouterTableau(t, TypeObjet.MUR_AVANT)) {
			LOGGER.debug("salle : " + getId() + " - " + t.getId() + " sur mur avant ajouté !");
		}
		else if(ajouterTableau(t, TypeObjet.MUR_DROIT)){
			LOGGER.debug("salle : " + getId() + " - " + t.getId() + " sur mur droite ajouté !");
		}
		else if(ajouterTableau(t, TypeObjet.MUR_GAUCHE)){
			LOGGER.debug("salle : " + getId() + " - " + t.getId() + " sur mur gauche ajouté !");
		}
		else if(ajouterTableau(t, TypeObjet.MUR_ARRIERE)){
			LOGGER.debug("salle : " + getId() + " - " + t.getId() + " sur mur arriere ajouté !");
		}
		else{
			LOGGER.debug("salle : " + getId() + " - " + t.getId() + " n'a pas été ajouté !");
		}
	}

	/**
	 * Dessine la salle dans l'environnement 3D
	 * @param gl l'object GL2 avec lequel on dessine la salle
	 */
	public void dessiner(GL2 gl){
		for(Objet objet : objets.values()){
			objet.dessiner(gl) ;
		}
	}

	/**
	 * Actualise les "mouvements" faits dans la salle grace aux capteurs
	 * @param t 
	 * @param dt ???
	 */
	public void actualiser(float t, float dt, boolean salleCourant){
		for(Capteur capteur : capteurs.values()){
			//LOGGER.debug("teste capteur("+capteur.getId()+") salle:\t" + this.getId());
			capteur.tester(t); 
		}
		if(salleCourant){
			for(Capteur c : capteursTableaux.values()){
				c.tester(t);
			}
		}
	}

	/**
	 * Renvoie la position de la salle dans le repere 3D
	 * @return la position de la salle dans le repere 3D
	 */
	public Vec3 getPosition(){
		return this.repere.getPostiton();
	}

	/**
	 * 
	 * @return le plafond de la salle
	 */
	public Plafond getPlafond(){
		return (Plafond) objets.get(TypeObjet.PLAFOND.toString() + getId());
	}

	/**
	 * 
	 * @return le sol de la salle
	 */
	public Sol getSol(){
		return (Sol) objets.get(TypeObjet.SOL.toString() + getId());
	}

	/**
	 * 
	 * @return {@link #murAvant}
	 */
	public Mur getMurAvant(){
		return (Mur) objets.get(TypeObjet.MUR_AVANT.toString() + getId());
	}

	/**
	 * 
	 * @return {@link #murArriere}
	 */
	public Mur getMurArriere(){
		return (Mur) objets.get(TypeObjet.MUR_ARRIERE.toString() + getId());
	}

	/**
	 * 
	 * @return {@link #murDroite}
	 */
	public Mur getMurDroite(){
		return (Mur) objets.get(TypeObjet.MUR_DROIT.toString() + getId());
	}

	/**
	 * 
	 * @return {@link #murGauche}
	 */
	public Mur getMurGauche(){
		return (Mur) objets.get(TypeObjet.MUR_GAUCHE.toString() + getId());
	}

	/**
	 * Revoie la largeur de la salle
	 * @return la largeur de la salle
	 */
	public float getLargeur(){
		return largeur;
	}

	/**
	 * Revoie la hauteur de la salle
	 * @return la hauteur de la salle
	 */
	public float getHauteur(){
		return hauteur;
	}

	/**
	 * Revoie la profondeur de la salle
	 * @return la profondeur de la salle
	 */
	public float getProfondeur(){
		return profondeur;
	}
	
	/**
	 * Revoie l'epaisseur des murs de la salle
	 * @return l'epaisseur des murs de la salle
	 */
	public float getEpaisseurMur(){
		return epaisseurMur;
	}

	/**
	 * Revoie l'objet Cloison correspond au mur passe en paremetre
	 * @param mur indique le mur : {@link #DROITE}, {@link #GAUCHE},{@link #AVANT}, {@link #ARRIERE}
	 * @return la cloison correspond au mur indique en parametre
	 */
	public Mur getCloison(String mur){
		return (Mur) objets.get(mur+getId());
	}

	/**
	 * 
	 * @return true si l'avatar est a l'interieur de la piece, sinon false
	 */
	public boolean avatarPresent(){
		return capteurPresenceAvatar != null && capteurPresenceAvatar.interieur(); 
	}
	
	/**
	 * Supprime tous les tableaux presents dans la salle
	 */
	@SuppressWarnings("unused")
	private void suprimerTousTableaux(){
		List<Objet> liste = new ArrayList<Objet>();
		for(Objet objet : liste){
			if(objet instanceof Tableau){
				objets.remove(objet.getId());
			}
		}
	}
	
	
	public boolean isPlaced() {
		return placed;
	}

	public void setPlaced(boolean placed) {
		this.placed = placed;
	}
	
	public boolean isTableauxPlaced() {
		return tableauxPlaced;
	}

	public void setTableauxPlaced(boolean tableauxPlaced) {
		this.tableauxPlaced = tableauxPlaced;
	}

	/**
	 * Met a jour les "actions" faites par rapport a cette salle
	 */
	@Override
	public void update(String aspect, Object valeur, Observable de) {
		//LOGGER.debug("update salle("+this.getId()+"):\t"+aspect);
				/*if(aspect.equals(Capteur.ENTREE)){
					//MuseeIA.get().initSalle(this);
					Monde.get().setSalleCourante(this);
					LOGGER.info(noeudGraphe==null?"Entré dans la salle:\t"+
							this.getId()+"\tle noeud est null":
								":Entré dans la salle:\t"+
								this.getId()+"\tle noeud est:\t"+noeudGraphe.getId());
				}*/
	}
	
	/**
	 * Classe representant une position avec une largeur (utilise pour le placement des tableaux 
	 * et pour savoir s'il reste de la place sur un mur de la salle)
	 * @author Ronan
	 */
	private class PositionLargeur {
		float largeur;
		float position;

		/**
		 * Constructeur
		 * @param l la largeur
		 * @param p la position
		 */
		public PositionLargeur(float l, float p){
			this.largeur = l;
			this.position = p;
		}
	}
}
