package fr.enib.game.monde.builder;

import java.util.HashMap;

import fr.enib.game.model.Noeud;
import fr.enib.game.monde.core.Vec3;
import fr.enib.game.monde.object.Salle;

public class MuseeIA {
	//private static Logger LOGGER = Logger.getLogger(MuseeIA.class);
	
	private float largeurSalle;
	private float hauteurSalle;
	private float profondeurSalle;

	private Vec3 position;
	private float distanceMur;

	private HashMap<String,Salle> salles;
	
	private int indexLigneInf = 0;
	private int indexLigneSup = 0;
	private int indexColonneInf = 0;
	private int indexColonneSup = 0;
	
	private static MuseeIA INSTANCE = null; 
	
	
	/**
	 * Constructeur Singleton
	 */
	private MuseeIA(){
		this.distanceMur = 0.5f;
		this.position = new Vec3(0.0f, 0.0f, 0.0f);

		this.largeurSalle    = 10.0f;
		this.profondeurSalle = 10.0f;
		this.hauteurSalle    = 5.0f;

		salles = new HashMap<String,Salle>();

		// on créer les salles
		Salle salleInit =  new Salle("Salle_0,0", this.largeurSalle, this.profondeurSalle, this.hauteurSalle, 3.0f, 3.5f);

		salles.put("salle_0,0", salleInit);
	}
	
	/**
	 * Singleton
	 * @return l'unique instance du musee
	 */
	public static MuseeIA get(){
		if(INSTANCE == null){
			INSTANCE = new MuseeIA();
		}
		return INSTANCE;
	}
	
	/**
	 * Placement du musee
	 * @param x la position en x
	 * @param y la position en y
	 * @param z la position en z
	 * @param noeud le noeud a partir du quel on va generer le musee
	 */
	public void placer(float x, float y, float z, Noeud noeud){
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;

		Salle salleInit = getSalle(0, 0);
		//salleInit.setNoeudGraphe(noeud);
		//salleInit.placer(position.x, position.y, position.z);

		generer(salleInit);
		majPorteSalles();
		Monde.get().setSalleCourante(salleInit);
		majTableauxSalles();
	}
	
	/**
	 * Initialisation des salles
	 * @param salleInit la salle de depart
	 */
	public void  initSalle(Salle salleInit){
		if(salleInit.estVisiter()) return;
		salleInit.setVisiter(true);
		
		ajouterSallesVoisines(salleInit);
		salleInit.placer(position);
		
		//on place toutes les salle dans le musée
		for(int l = indexLigneInf; l <= indexLigneSup; l++){
			for(int c = indexColonneInf; c <= indexColonneSup; c++){
				Salle salle = getSalle(l, c);
				if(salle!=null){
					salle.placer(position.x+l*(largeurSalle+distanceMur), position.y+c*(profondeurSalle+distanceMur), position.z);
				}
			}
		}
	}
	
	/**
	 * Ajoute une ou plusieurs salle(s) voisine(s) a une salle 
	 * @param salle la salle a laquelle on ajoute les salles voisines
	 */
	private void ajouterSallesVoisines(Salle salle) {
		if(salle != null){
			String[] coord = salle.getId().split("_")[1].split(",");
			int l = Integer.valueOf(coord[0]);
			int c = Integer.valueOf(coord[1]);
			creerSallesVoisines(l, c);
		}
	}
	
	/**
	 * Ajoute une ou plusieurs salle(s) voisine(s) a une salle 
	 * @param l la coordonnee en l de la salle a laquelle on ajoute les salles voisines
	 * @param c la coordonnee en c de la salle a laquelle on ajoute les salles voisines
	 */
	private void creerSallesVoisines(int l,int c) {
		/*Salle salle = getSalle(l, c);
		Salle salleVoisine = null; 
		if(salle.getNoeudGraphe()!=null){
			List<graphe.Noeud> listeNoeud = salle.getNoeudGraphe().getListeNoeudDescendants();
			int index = 0;
			
			if(index>=listeNoeud.size())return;
			if(getSalle(l+1, c) == null){
				indexLigneSup++;
				salleVoisine= new Salle("Salle_"+(l+1)+","+c, this.largeurSalle, this.profondeurSalle, this.hauteurSalle, 3.0f, 3.5f);
				salleVoisine.setNoeudGraphe(listeNoeud.get(index));
				salles.put("salle_" + (l+1) + ","+ c, salleVoisine);
				index++;
				salle.ajouterSalleVoisine(salleVoisine,false);
			}
			
			if(index>=listeNoeud.size())return;
			if(getSalle(l-1, c) == null){
				indexLigneInf--;
				salleVoisine= new Salle("Salle_"+(l-1)+","+c, this.largeurSalle, this.profondeurSalle, this.hauteurSalle, 3.0f, 3.5f);
				salleVoisine.setNoeudGraphe(listeNoeud.get(index));
				salles.put("salle_" + (l-1) + ","+ c, salleVoisine);
				index++;
				salle.ajouterSalleVoisine(salleVoisine,false);
			}
			
			if(index>=listeNoeud.size())return;
			if(getSalle(l, c-1) == null){
				indexColonneInf--;
				salleVoisine= new Salle("Salle_"+l+","+(c-1), this.largeurSalle, this.profondeurSalle, this.hauteurSalle, 3.0f, 3.5f);
				salleVoisine.setNoeudGraphe(listeNoeud.get(index));
				salles.put("salle_" + l + ","+ (c-1), salleVoisine);
				index++;
				salle.ajouterSalleVoisine(salleVoisine,false);
			}
			
			if(index>=listeNoeud.size())return;
			if(getSalle(l, c+1) == null){
				indexColonneSup++;
				salleVoisine= new Salle("Salle_"+l+","+(c+1), this.largeurSalle, this.profondeurSalle, this.hauteurSalle, 3.0f, 3.5f);
				salleVoisine.setNoeudGraphe(listeNoeud.get(index));
				salles.put("salle_" + l + ","+ (c+1), salleVoisine);
				index++;
				salle.ajouterSalleVoisine(salleVoisine,false);
			}

		}*/

	}
	
	
	/**
	 * Revoie la salle celon la ligne et la colonne ou elle se trouve
	 * @param l la ligne ou se trouve la salle
	 * @param c la colonne ou se trouve la salle
	 * @return la salle correspondant à la ligne et la colonne si elle existe, sinon null
	 */
	public Salle getSalle(int l, int c){
		return salles.get("salle_" + l + ","+ c);
	}
	
	/**
	 * Mise a jour des tableaux a placer dans le musee
	 */
	private void majTableauxSalles(){
		for(int l = indexLigneInf; l <= indexLigneSup; l++){
			for(int c = indexColonneInf; c <= indexColonneSup; c++){
				Salle salle = getSalle(l, c);
				if(salle!=null){
					salle.miseAJourTableauxDuNoeudGraphe();
				}
			}
		}
	}
	
	/**
	 * Mise a jour des portes a placer dans les salles du musee
	 */
	private void majPorteSalles(){
		for(int l = indexLigneInf; l <= indexLigneSup; l++){
			for(int c = indexColonneInf; c <= indexColonneSup; c++){
				Salle salle = getSalle(l, c);
				if(salle!=null){
					salle.ajouterToutPorte();
				}
			}
		}
	}
	
	/**
	 * Generation des salles voisine par rapport a une salle
	 * @param salle la salle par rapport a laquelle on ajoute ses voisines
	 */
	private void generer(Salle salle){
		if(salle.estVisiter()) return;
		this.initSalle(salle);
		for(Salle salleVoisine : salle.voisines.values()){
			if(!salle.getId().equals(salleVoisine.getId())){
				/*if(!(salleVoisine instanceof Porte)){
					this.generer(salleVoisine);
				}*/
			}
		}
	}

}
