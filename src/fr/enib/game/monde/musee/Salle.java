package fr.enib.game.monde.musee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.jogamp.opengl.GL2;

import fr.enib.game.monde.builder.Monde;
import fr.enib.game.monde.core.Observable;
import fr.enib.game.monde.core.Observer;
import fr.enib.game.monde.core.Situable;
import fr.enib.game.monde.geo.Vec3;
import fr.enib.game.monde.objet.Mur;
import fr.enib.game.monde.objet.Objet;
import fr.enib.game.monde.objet.Plafond;
import fr.enib.game.monde.objet.RessourceProvider;
import fr.enib.game.monde.objet.Sol;
import fr.enib.game.monde.objet.Tableau;
import fr.enib.game.monde.objet.TypeObjet;

public class Salle extends Situable implements Observer{
	private static Logger LOGGER = Logger.getLogger(Salle.class);

	//private Noeud noeudGraphe;

	//private boolean visiter = false;

	//public CapteurCubique capteurPresenceAvatar;

	public int nbrPorte;
	public float hauteurPorte, largeurPorte, epaisseurMur;
	protected float largeur, profondeur, hauteur;

	public HashMap<String,Objet>   objets;
	//public HashMap<String,Capteur> capteurs;
	public HashMap<String,Salle>   voisines;

	/**
	 * Construteur
	 * @param id l'id de la salle
	 * @param largeur la largeur de la salle
	 * @param profondeur la pronfondeur de la salle
	 * @param hauteur la hauteur de la salle
	 */
	public Salle(String id, float largeur, float profondeur, float hauteur){
		this(id, largeur, profondeur, hauteur, 0.5f, 2.0f, 3.5f);
	}

	/**
	 * Construteur
	 * @param id l'id de la salle
	 * @param largeur la largeur de la salle
	 * @param profondeur la pronfondeur de la salle
	 * @param hauteur la hauteur de la salle
	 * @param largeurPorte la largeur des portes de la salle
	 * @param hauteurPorte la hauteur des portes de la salle
	 */
	public Salle(String id, float largeur, float profondeur, float epaisseurMur, float hauteur, float largeurPorte, float hauteurPorte){
		super(id);
		Monde.get().ajouterSalle(this);

		this.objets     = new HashMap<String,Objet>() ;
		//this.capteurs   = new HashMap<String,Capteur>() ;
		this.voisines   = new HashMap<String,Salle>() ;

		this.nbrPorte = 0;
		this.largeur = largeur;
		this.profondeur = profondeur;
		this.hauteur = hauteur;

		this.largeurPorte = largeurPorte;
		this.hauteurPorte = hauteurPorte;
		this.epaisseurMur = epaisseurMur;

		// pour l'instant une salle ne peut pas avoir de maitre.
		this.setMaitre(null);
		
		//on met a jour les information du capteur
		//capteurPresenceAvatar.setSalle(this);

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
	/*public void ajouter(Capteur capteur){
		capteurs.put(capteur.getId(),capteur);
		//tout élément ajouté à la salle est esclave de celle ci.
		new CtxPose("ctxPose"+capteur.getId(), this, capteur, 0.0f, 0.0f, 0.0f, 0.0f);
		this.ajouterEsclave(capteur);
	}*/

	/**
	 * Ajout d'une salle voisine a la salle
	 * @param salle la salle voisine a ajouter
	 */
	public void ajouterSalleVoisine(Salle salle){
		voisines.put(salle.getId(),salle);
		salle.voisines.put(this.getId(), this);
		ajouterPorte(salle);
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
		/*List<Salle> listeTmp = new ArrayList<Salle>();
		listeTmp.addAll(voisines.values());
		for(Salle salleVoisine : listeTmp){
			if(!(salleVoisine instanceof Couloir)){
				this.ajouterPorte(salleVoisine);
			}
		}*/
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
		/*String id_porte = "trou0"+ nbrPorte + "_" + this.getId();
		float posTrou = 5.0f;
		float dM = 0.1f;
		float dx = salleVoisine.getPosition().x - this.getPosition().x;
		float dy = salleVoisine.getPosition().y - this.getPosition().y;

		Couloir porte = null;

		if(dx >= (salleVoisine.profondeur + this.profondeur)/2.0){ // Mur Avant
			dM = dx - (salleVoisine.profondeur + this.profondeur)/2.0f;

			if(dy >= 0){
				if(dy > largeur/2.0f) posTrou = largeur/2.0f;
				else posTrou = dy;
			}
			else if(dy < 0){
				if(-dy > largeur/2.0f) posTrou = largeur/2.0f;
				else posTrou = dy;
			}

			this.getMurAvant().ajouterTrou(new TrouPorte(id_porte, posTrou, largeurPorte,hauteurPorte));
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

			if(dy >= 0){
				if(dy > largeur/2.0f) posTrou = 0.0f;
				else posTrou = dy;
			}
			else if(dy < 0){
				if(-dy > largeur/2.0f) posTrou = 0.0f;
				else posTrou = -dy;
			}
			else if(dy == 0){
				posTrou = largeur/2.0f;
			}

			this.getMurArriere().ajouterTrou(new TrouPorte(id_porte, posTrou, largeurPorte,hauteurPorte));
			this.nbrPorte++;

			if(!voisines.values().isEmpty()){
				if(voisines.get("porte" + getId() + "_" + salleVoisine.getId()) == null &&
						voisines.get("porte" + salleVoisine.getId() + "_" + getId()) == null){
					porte = new Couloir("porte" + getId() + "_" + salleVoisine.getId(), largeurPorte, dM, hauteurPorte);
					ajouterCouloirPorte(porte, salleVoisine);
					porte.placer(new Vec3(dx/2.0f + getPosition().x, dy + this.getPosition().y, 0.0f));
				}
			}
		}
		else if(dy >= (salleVoisine.largeur + this.largeur)/2.0f){ // Mur Gauche
			dM = dy - (salleVoisine.largeur + this.largeur)/2.0f;

			if(dx > 0){
				posTrou = dx/2.0f + profondeur/2.0f;
			}
			else if(dx < 0){
				posTrou = -dx/2.0f;
			}
			else if(dx == 0){
				posTrou = 0.0f;
			}
			this.getMurGauche().ajouterTrou(new TrouPorte(id_porte, posTrou, largeurPorte,hauteurPorte));
			this.nbrPorte++;

			if(!voisines.values().isEmpty()){
				if(voisines.get("porte" + getId() + "_" + salleVoisine.getId()) == null &&
						voisines.get("porte" + salleVoisine.getId() + "_" + getId()) == null){
					porte = new Couloir("porte" + getId() + "_" + salleVoisine.getId(), largeurPorte, dM, hauteurPorte);
					ajouterCouloirPorte(porte, salleVoisine);
					//porte.placer(new Vec3(dx/2.0f + getPosition().x, getPosition().y + sv.getPosition().y, 0.0f));
					porte.placer(new Vec3(dx/2.0f + getPosition().x, dy/2.0f + getPosition().y, 0.0f));
					porte.orienter((float) Math.PI/2.0f);
				}
			}
		}
		else if(dy <= -(salleVoisine.largeur + this.largeur)/2.0f){ // Mur Droite
			dM = -dy - (salleVoisine.largeur + this.largeur)/2.0f;	

			if(dx > 0){
				posTrou = dx/2.0f;
			}
			else if(dx < 0){
				posTrou = -dx/2.0f + profondeur/2.0f;
			}
			else if(dx == 0){
				posTrou = 0.0f;
			}

			this.getMurDroite().ajouterTrou(new TrouPorte(id_porte, posTrou, largeurPorte,hauteurPorte));
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
		}*/
	}

	/**
	 * Ajoute un tableau a la salle
	 * @param tableau le tableau a ajouter
	 * @param mur le mur sur lequel on va mettre le tableau
	 * @return true si on a reussi a mettre le tableau, sinon false
	 */
	public boolean ajouterTableau(Tableau tableau, TypeObjet mur) {
		/*Mur c = getCloison(mur);
		if(c==null) return false;

		float posTab = trouverPlaceTableau(tableau.getLargeur(), mur);

		if(posTab > -999.0f){
			this.objets.put(tableau.getId(), tableau);

			tableau.setPosition(posTab);
			tableau.placer(-0.2f, posTab, this.getHauteur()/2);
			tableau.orienter((float) Math.PI);
			return c.ajouterEsclave(tableau);
		}*/
		return false;
	}

	/**
	 * Cherche s'il y a une place sur un mur pour metter un tableau
	 * @param largeurTableau la largeur du tableau a ajouter
	 * @param mur le mur sur lequel on veut mettre le tableau
	 * @return -1000.0 s'il n'y a pas de place, sinon la position central ou il y a de la place
	 */
//	private float trouverPlaceTableau(float largeurTableau, TypeObjet mur){
//		Mur c = getCloison(mur);
//		float espaceEntreTab = 0.2f;
//		if(c==null) return -1000.0f;
//
//		ArrayList<PositionLargeur> posLar = new ArrayList<Salle.PositionLargeur>();
//
//		for(TrouPorte t : c.getTrous()){
//			posLar.add(new PositionLargeur(t.getLargeur(), t.getPostionPourCloison()));
//		}
//
//		if(!c.getEsclave().isEmpty()){
//			for(Situable s : c.getEsclave().values()){
//				if(s.getId().contains("tab")){
//					Tableau t = (Tableau) s;
//					posLar.add(new PositionLargeur(t.getLargeur(), t.getPosition()));
//				}
//			}
//		}
//
//		if(posLar.isEmpty()){
//			if(c.getCloison().getLargeur() >= (largeurTableau + espaceEntreTab)){
//				return 0.0f;
//			}
//		}
//		else{
//			Collections.sort(posLar, new Comparator<PositionLargeur>() {
//				public int compare(PositionLargeur p1, PositionLargeur p2){
//					float c = p1.position - p2.position;
//
//					if(c > 0) return -1;
//					else if(c < 0) return 1;
//					return 0;
//				}
//			});
//			for(int i = 0; i < posLar.size(); i++){
//				PositionLargeur pl = posLar.get(i);
//
//				if(i == 0){
//					float posMurDep = c.getCloison().getLargeur()/2.0f;
//
//					if(posMurDep - (pl.position + pl.largeur/2.0f) >= (largeurTableau + espaceEntreTab)){
//						return posMurDep - largeurTableau/2.0f - espaceEntreTab;
//					}
//
//					if(posLar.size() == 1){
//						float posMurFin = c.getRepere().getPostiton().y - c.getCloison().getLargeur()/2.0f;
//						if(Math.abs(posMurFin - (pl.position - pl.largeur/2.0f)) >= (largeurTableau + espaceEntreTab)){
//							return pl.position - pl.largeur/2.0f - largeurTableau/2.0f - espaceEntreTab;
//						}
//					}
//				}
//				else{
//					if(i == posLar.size()-1){
//						float posMurFin = - c.getCloison().getLargeur()/2.0f;
//						if(Math.abs(posMurFin - (pl.position - pl.largeur/2.0f)) >= (largeurTableau + espaceEntreTab)){
//							return pl.position - pl.largeur/2.0f - largeurTableau/2.0f - espaceEntreTab;
//						}
//					}
//
//					PositionLargeur pl_av = posLar.get(i-1);
//					float dEntreObj = Math.abs((pl_av.position - pl_av.largeur/2.0f) - (pl.position + pl.largeur/2.0f));
//					if(dEntreObj >= largeurTableau){
//						return pl.position + pl.largeur/2.0f + largeurTableau/2.0f + espaceEntreTab;
//					}
//				}
//			}
//		}
//		return -1000.0f;
//	}

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
			//LOGGER.debug("Tableau " + t.getId() + "  ajouté !");
		}
		else if(ajouterTableau(t, TypeObjet.MUR_DROIT)){
			//LOGGER.debug("Tableau " + t.getId() + "  ajouté !");
		}
		else if(ajouterTableau(t, TypeObjet.MUR_GAUCHE)){
			//LOGGER.debug("Tableau " + t.getId() + "  ajouté !");
		}
		else if(ajouterTableau(t, TypeObjet.MUR_ARRIERE)){
			//LOGGER.debug("Tableau " + t.getId() + "  ajouté !");
		}

	}

	/**
	 * Met a jour les tableaux a ajouter par rapport au noeud du graphe
	 */
	public void miseAJourTableauxDuNoeudGraphe(){
		/*if(noeudGraphe!=null){
			suprimerTousTableaux();
			List<graphe.Tableau> listTabs = noeudGraphe.selectionerNTableaux(10);
			if(listTabs==null) return;
			if(!listTabs.isEmpty()){
				for(int i = 0; i < listTabs.size(); i++){
					this.ajouterTableau(new Tableau("tab0"+(i+1), Fabrique.PATH + listTabs.get(i).getId()));
				}
			}
		}*/
	}	

	/**
	 * Dessine la salle dans l'environnement 3D
	 * @param gl l'object GL2 avec lequel on dessine la salle
	 */
	public void dessiner(GL2 gl){
		//on dessine tous les objets de la salle
		for(Objet objet : objets.values()){
			objet.dessiner(gl) ;
		}
	}

	/**
	 * Actualise les "mouvements" faits dans la salle grace aux capteurs
	 * @param t 
	 * @param dt ???
	 */
	public void actualiser(float t, float dt){
		/*for(Capteur capteur : capteurs.values()){
			//LOGGER.debug("teste capteur("+capteur.getId()+") salle:\t" + this.getId());
			capteur.tester(t) ; 
		}*/
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
		//TODO return capteurPresenceAvatar.interieur(); 
		return false;
	}

	/**
	 * Definit la texture à appliquer aux murs de la salle
	 * @param textureMur le chemin de l'image de la texture
	 */
	/*public void setTextureMur(String textureMur) {
		getMurArriere().setTexture(textureMur);
		getMurAvant().setTexture(textureMur);
		getMurGauche().setTexture(textureMur);
		getMurDroite().setTexture(textureMur);
	}*/

	/**
	 * Definit la texture à appliquer au sol de la salle
	 * @param textureMur le chemin de l'image de la texture
	 */
	/*public void setTextureSol(String textureSol) {
		getSol().setTexture(textureSol);
	}*/


	/**
	 * Definit la texture à appliquer au plafond de la salle
	 * @param textureMur le chemin de l'image de la texture
	 */
	/*public void setTexturePlafond(String texturePlafond) {
		getPlafond().setTexture(texturePlafond);
	}*/

	/**
	 * Renvoie le noeud du graphe affecte a la salle
	 * @return le noeud du graphe affecte
	 */
	/*public Noeud getNoeudGraphe() {
		return noeudGraphe;
	}*/

	/**
	 * Change le noeud du graphe affecte a la salle
	 * @param noeud le noeud du graphe a affecter
	 */
	/*public void setNoeudGraphe(Noeud noeud) {
		this.noeudGraphe = noeud;
		//LOGGER.info("salle("+this.getId()+") noeud:\t"+noeud.getId());
	}*/

	/**
	 * Verifie si la salle est "visite" (creer)
	 * @return true si la salle est visite, sinon false
	 */
	/*public boolean estVisiter() {
		return visiter;
	}*/

	/**
	 * Change si la salle est visiter ou non
	 * @param visiter true si la salle est visite, sinon false
	 */
	/*public void setVisiter(boolean visiter) {
		this.visiter = visiter;
	}*/
	
	/**
	 * Supprime tous les tableaux presents dans la salle
	 */
	/*private void suprimerTousTableaux(){
		List<Objet> liste = new ArrayList<Objet>();
		for(Objet objet : liste){
			if(objet instanceof Tableau){
				objets.remove(objet.getId());
			}
		}
	}*/

	/**
	 * Met a jour les "actions" faites par rapport a cette salle
	 */
	@Override
	public void update(String aspect, Object valeur, Observable de) {
		//LOGGER.debug("update salle("+this.getId()+"):\t"+aspect);
		/*if(aspect.equals(Capteur.ENTREE)){
			//MuseeIA.get().initSalle(this);
			Monde.get().setSalleCourante(this);
			// genererNoeudSallesVoisineVoisine();
			// LOGGER.info(noeudGraphe==null?"Entré dans la salle:\t"+
			//		this.getId()+"\tle noeud est null":
			//			":Entré dans la salle:\t"+
			//			this.getId()+"\tle noeud est:\t"+noeudGraphe.getId());
		}*/
	}

	/**
	 * Genere les noeuds pour les salles voisine de cette salle
	 */
	public void genererNoeudSallesVoisineVoisine(){
		//on créer un copie de la liste des salles voisines
		//pour éviter : java.util.ConcurrentModificationException
		List<Salle> listeTmp = new ArrayList<Salle>();
		listeTmp.addAll(this.voisines.values());
		/*for(Salle salleVoisine : listeTmp){
			if(salleVoisine.getNoeudGraphe()!=null){
				genererNoeudSallesVoisine(this);
			}
		}*/
	}

	/**
	 * Genere les noeuds pour les salles voisine d'une salle
	 * @param salleOrigine la salle au plus haut du noeud
	 */
	public void genererNoeudSallesVoisine(Salle salleOrigine){
		/*if(salleOrigine==null){
			salleOrigine = this;
		}
		if(this.noeudGraphe==null) return;

		//List<Noeud> listeNoeud = this.getNoeudGraphe().getListeNoeudDescendants();
		List<Noeud> listeNoeud = this.getNoeudGraphe().getListeNoeudDescendants();
		
		int index = 0;
		List<Salle> sallesASuprimmerDesVoisines = new ArrayList<Salle>();
		for(Salle salleVoisine : this.voisines.values()){
			//si la salle est la salle this on ne fait rien
			if(!salleVoisine.getId().equals(salleOrigine.getId())){
				//on change le noeud pour toutes les salles non visitées
				if(!salleVoisine.estVisiter()){
					//si il reste des noeud dans la liste on change le noeud
					if(index<listeNoeud.size()){
						salleVoisine.setNoeudGraphe(listeNoeud.get(index));
						index++;
					}
					//sinon on ne peut pas créer de salleVoisine donc
					//on suprimer celle ci de la liste des salle voisine
					else{
						sallesASuprimmerDesVoisines.add(salleVoisine);
					}
				}
			}

		}
		//on suprime les salles qu'on n'a pas pue créer
		supprimerSallesVoisines(sallesASuprimmerDesVoisines);
		reInitSalleVoisine();*/
	}

	/**
	 * Supprime les salles voisine de la salle
	 * @param liste la liste des salles voisines a enlever
	 */
	/*private void supprimerSallesVoisines(List<Salle> liste){
		for(Salle salle : liste){
			voisines.remove(salle.getId());
		}
	}*/

	

	/**
	 * reinitialise les portes de la salle
	 */
	/*private void reInitSalleVoisine(){
		suprimerTousLesTrou();
		//on créer un copie de la liste des salles voisines
		//pour éviter : java.util.ConcurrentModificationException
		List<Salle> listeTmp = new ArrayList<Salle>();
		listeTmp.addAll(this.voisines.values());

		for(Salle salleVoisine : listeTmp){
			ajouterPorte(salleVoisine);
		}
	}*/

	/**
	 * Supprimer tous les portes (trous) presentes dans la salle
	 */
	/*private void suprimerTousLesTrou(){
		for(Objet objet : this.objets.values()){
			if(objet instanceof Mur){
				((Mur) objet).suprimerLesTrous();
			}
		}
	}*/

	/**
	 * Classe representant une position avec une largeur (utilise pour le placement des tableaux 
	 * et pour savoir s'il reste de la place sur un mur de la salle)
	 * @author ronan
	 */
//	private class PositionLargeur {
//		float largeur;
//		float position;
//
//		/**
//		 * Constructeur
//		 * @param l la largeur
//		 * @param p la position
//		 */
//		public PositionLargeur(float l, float p){
//			this.largeur = l;
//			this.position = p;
//		}
//	}
}
