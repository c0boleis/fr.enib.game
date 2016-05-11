package fr.enib.game.monde.musee;

import fr.enib.game.monde.objet.Mur;
import fr.enib.game.monde.objet.Plafond;
import fr.enib.game.monde.objet.RessourceProvider;
import fr.enib.game.monde.objet.Sol;
import fr.enib.game.monde.objet.TypeObjet;

/**
 * 
 * @author Ronan MOREL
 *
 */
public class Couloir extends Salle{

	/**
	 * 
	 * @param id
	 * @param largeur
	 * @param profondeur
	 * @param hauteur
	 */
	protected Couloir(String id, float largeur, float profondeur, float hauteur) {
		super(id);

		this.largeur = largeur;
		this.profondeur = profondeur;
		this.hauteur = hauteur;
		this.epaisseurMur = 0.05f;

		ajouter( new Sol(TypeObjet.SOL.toString() + getId() , RessourceProvider.pathTextureCouloir, largeur, profondeur));
		ajouter( new Plafond(TypeObjet.PLAFOND.toString() + getId(), RessourceProvider.pathTextureCouloir, largeur, profondeur));  
		ajouter( new Mur(TypeObjet.MUR_GAUCHE.toString() + getId(), RessourceProvider.pathTextureCouloir, hauteur, profondeur, epaisseurMur)); 
		ajouter( new Mur(TypeObjet.MUR_DROIT.toString() + getId() , RessourceProvider.pathTextureCouloir, hauteur, profondeur, epaisseurMur));

		getPlafond().placer(0.0f,0.0f,getHauteur()); 
		getSol().placer(0.0f,0.0f,0.0f);

		getMurGauche().orienter((float) +Math.PI/2.0f); 
		getMurGauche().placer(profondeur/2.0f,getLargeur()/2.0f,0.0f); 

		getMurDroite().orienter((float) -Math.PI/2.0f); 
		getMurDroite().placer(-profondeur/2.0f,-getLargeur()/2.0f,0.0f); 
	}
	
}
