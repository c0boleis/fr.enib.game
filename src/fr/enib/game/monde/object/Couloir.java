package fr.enib.game.monde.object;

import java.io.File;

public class Couloir extends Salle{
	
	/**
	 * Constructeur
	 * @param id
	 * @param largeur
	 * @param profondeur
	 * @param hauteur
	 */
	public Couloir(String id, float largeur, float profondeur, float hauteur){
		super(id);
		
		this.largeur = largeur;
		this.profondeur = profondeur;
		this.hauteur = hauteur;
		
		this.textureMur = "data"+File.separator+"textures"+File.separator+"wood.jpg";
		this.texturePlafond = "data"+File.separator+"textures"+File.separator+"wood.jpg";
		this.textureSol = "data"+File.separator+"textures"+File.separator+"wood.jpg";
		
		ajouter(new Sol(SOL + getId() , this.textureSol, this.largeur, this.profondeur));
		ajouter(new Plafond(PLAFOND + getId(), this.texturePlafond, this.largeur, this.profondeur));  
		ajouter(new Mur(GAUCHE + getId(), this.textureMur, this.hauteur, this.profondeur));
		ajouter(new Mur(DROITE + getId() , this.textureMur, this.hauteur, this.profondeur));

		getPlafond().placer(0.0f,0.0f,getHauteur()); 
		getSol().placer(0.0f,0.0f,0.0f);

		getMurGauche().orienter((float) +Math.PI/2.0f); 
		getMurGauche().placer(0.0f,getLargeur()/2.0f,0.0f); 

		getMurDroite().orienter((float) -Math.PI/2.0f); 
		getMurDroite().placer(0.0f,-getLargeur()/2.0f,0.0f); 
	}
}
