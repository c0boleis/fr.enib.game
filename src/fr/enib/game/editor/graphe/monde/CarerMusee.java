/**
 * 
 */
package fr.enib.game.editor.graphe.monde;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

/**
 * @author Corentin Boleis
 *
 */
public class CarerMusee {

	private SalleMusee salle = null;

	private List<Tableau> tableaux = new ArrayList<Tableau>();

	private List<Line> lines = new ArrayList<Line>();

	private String id = null;

	private int x = 1;

	private int y = 1;

	private int etage = 1;

	/**
	 *   		 haut
	 * 			*-------*	
	 *  		|		|
	 * 	gauche	|		| droit
	 * 			|		|
	 * 			*-------*		
	 * 				bas
	 */
	private TypeMurCarer murHaut = TypeMurCarer.murPorte;

	private TypeMurCarer murBas = TypeMurCarer.murPorte;

	private TypeMurCarer murDroit = TypeMurCarer.murPorte;

	private TypeMurCarer murGauche = TypeMurCarer.murPorte;

	private Point pointMaximun = null;

	private Point pointMinimun = null;
	
	private int indexTableau = 0;

	/**
	 * 
	 */
	public static final double DISTANCE_MURE = 0.45;

	/**
	 * 
	 */
	public static final double DISTANCE_MURE_A = 0.5;

	/**
	 * 
	 */
	public static final double TAILLE_PORTE = 0.2;

	private static final double TAILLE_TABLEAU = 0.2;

	/**
	 * 
	 * @param salleMusee
	 * @param x
	 * @param y
	 * @param etage 
	 * @param id 
	 */
	public CarerMusee(SalleMusee salleMusee,int x,int y,int etage,String id){
		this(x, y,etage,id);
		this.setSalle(salleMusee);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param etage 
	 * @param id 
	 */
	public CarerMusee(int x,int y,int etage,String id){
		this.x = x;
		this.y = y;
		this.etage = etage;
		this.id = id;
		double xMin = (double)x - DISTANCE_MURE_A;
		double yMin = (double)y - DISTANCE_MURE_A;
		double xMax = (double)x + DISTANCE_MURE_A;
		double yMax = (double)y + DISTANCE_MURE_A;

		pointMaximun = MuseeTest.getPoint(xMax, yMax);
		pointMinimun = MuseeTest.getPoint(xMin, yMin);
	}

	/**
	 * @return the murHaut
	 */
	public TypeMurCarer getMurHaut() {
		return murHaut;
	}

	/**
	 * @param murHaut the murHaut to set
	 */
	public void setMurHaut(TypeMurCarer murHaut) {
		this.murHaut = murHaut;
	}

	/**
	 * @return the murBas
	 */
	public TypeMurCarer getMurBas() {
		return murBas;
	}

	/**
	 * @param murBas the murBas to set
	 */
	public void setMurBas(TypeMurCarer murBas) {
		this.murBas = murBas;
	}

	/**
	 * @return the murDroit
	 */
	public TypeMurCarer getMurDroit() {
		return murDroit;
	}

	/**
	 * @param murDroit the murDroit to set
	 */
	public void setMurDroit(TypeMurCarer murDroit) {
		this.murDroit = murDroit;
	}

	/**
	 * @return the mureGauche
	 */
	public TypeMurCarer getMurGauche() {
		return murGauche;
	}

	/**
	 * @param mureGauche the mureGauche to set
	 */
	public void setMureGauche(TypeMurCarer mureGauche) {
		this.murGauche = mureGauche;
	}

	/**
	 * @return the salle
	 */
	public SalleMusee getSalle() {
		return salle;
	}

	/**
	 * @param salle the salle to set
	 */
	public void setSalle(SalleMusee salle) {
		this.salle = salle;
		this.lines.clear();
		this.tableaux.clear();
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @param carerMusee
	 * @return true si le carer donné en argument est adjacent
	 * false, si le carer est le même
	 */
	public boolean isAdjacent(CarerMusee carerMusee){
		if(this.x==carerMusee.getX()){
			if(Math.abs(this.y-carerMusee.getY())==1){
				return true;
			}
		}
		if(this.y==carerMusee.getY()){
			if(Math.abs(this.x-carerMusee.getX())==1){
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param g
	 */
	public void draw(Graphics g){
		initDraw();
		g.setColor(Color.BLACK);
		if(VisiteurEstPresent()){
			g.setColor(Color.BLUE);
		}
		for(Line line :lines){
			int[] tab  = line.getCoordonees();
			g.drawLine(tab[0], tab[1], tab[2], tab[3]);
		}
		for(Tableau line :tableaux){
			line.dessiner(g);
		}
	}
	
	private void initDraw(){
		/*
		 * on defenit toute les coordonées de chaque
		 * lignes un seul foit
		 */
		if(tableaux.isEmpty()){
			indexTableau = 0;
			if(getSalle()!=null){
				dessinerTableauBas();
				dessinerTableauxDroite();
				dessinerTableauxGauche();
				dessinerTableauxHaut();
			}
		}
		if(lines.isEmpty()){
			dessinerMurGauche();
			dessinerMurHaut();
			dessinerMurDroite();
			dessinerMurBas();
		}
	}

	/**
	 * @param gl
	 */
	public void display(GL2 gl) {
		initDraw();
		for(Line line :lines){
			line.display(gl);
		}
		for(Tableau line :tableaux){
			line.display(gl);
		}
	}

	private void dessinerMurDroite(){
		double distance_haut = DISTANCE_MURE;
		double distance_bas = DISTANCE_MURE;
		if(haveAdjacenceHaut()){
			distance_haut = DISTANCE_MURE_A;
			if(haveAdjacenceHautDroite()){
				distance_haut+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		if(haveAdjacenceBas()){
			distance_bas = DISTANCE_MURE_A;
			if(haveAdjacenceBasDroite()){
				distance_bas+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		switch (murDroit) {
		case murPorte:
			//on calcul les points des murs
			double yM1 = (double)y - distance_bas;
			double yP1 = (double)y - TAILLE_PORTE/2.0;
			double yM2 = (double)y + TAILLE_PORTE/2.0;
			double yP2 = (double)y + distance_haut;

			double xM  = (double)x +DISTANCE_MURE;
			Point p1 = MuseeTest.getPoint(xM, yM1);
			Point p2 = MuseeTest.getPoint(xM, yP1);
			Point p3 = MuseeTest.getPoint(xM, yP2);
			Point p4 = MuseeTest.getPoint(xM, yM2);

			//on dessine les murs
			lines.add(new Line(p1, p2, Line.Position.droite));
			lines.add(new Line(p3, p4, Line.Position.droite));
			break;
		case murVide:
			double yM12 = (double)y - distance_bas;
			double yM22 = (double)y + distance_haut;

			double xM2  = (double)x + DISTANCE_MURE;
			Point p12 = MuseeTest.getPoint(xM2, yM12);
			Point p22 = MuseeTest.getPoint(xM2, yM22);

			lines.add(new Line(p12, p22, Line.Position.droite));
			break;
		default:
			break;
		}
	}

	private void dessinerTableauxDroite(){
		double distance_haut = DISTANCE_MURE;
		double distance_bas = DISTANCE_MURE;
		if(haveAdjacenceHaut()){
			distance_haut = DISTANCE_MURE_A;
			if(haveAdjacenceHautGauche()){
				distance_haut+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		if(haveAdjacenceBas()){
			distance_bas = DISTANCE_MURE_A;
			if(haveAdjacenceBasGauche()){
				distance_bas+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		double xM  = (double)x +DISTANCE_MURE;
		switch (murDroit) {
		case murPorte:
			//on calcul les points des tabeleau
			double yTab1 = (double)y - ((TAILLE_PORTE/2.0)+((distance_bas-(TAILLE_PORTE/2.0))/2.0));
			double yM1 = yTab1 - (TAILLE_TABLEAU/2.0);
			double yP1 = yTab1 + (TAILLE_TABLEAU/2.0);

			double yTab2 = (double)y + ((TAILLE_PORTE/2.0)+((distance_haut-(TAILLE_PORTE/2.0))/2.0));
			double yM2 = yTab2 -(TAILLE_TABLEAU/2.0);
			double yP2 = yTab2 +(TAILLE_TABLEAU/2.0);

			Point p1 = MuseeTest.getPoint(xM, yM1);
			Point p2 = MuseeTest.getPoint(xM, yP1);
			Point p3 = MuseeTest.getPoint(xM, yP2);
			Point p4 = MuseeTest.getPoint(xM, yM2);
			//on dessine les tableux
			// l'ajout de +Musee.INVERSION_X*2 sert a ecarter le tableau du mur
			tableaux.add(new Tableau(new Line(p1.x-MuseeTest.INVERSION_X*2, p1.y, p2.x-MuseeTest.INVERSION_X*2, p2.y,Line.Position.droite),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			tableaux.add(new Tableau(new Line(p3.x-MuseeTest.INVERSION_X*2, p3.y, p4.x-MuseeTest.INVERSION_X*2, p4.y,Line.Position.droite),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			break;
		case murVide:
			double y1 = (double)y - 0.25;
			double y2 = (double)y ;
			double y3 = (double)y + 0.25;
			Point pTab11 = MuseeTest.getPoint(xM, y1-(TAILLE_TABLEAU/2.0));
			Point pTab12 = MuseeTest.getPoint(xM,y1+(TAILLE_TABLEAU/2.0));
			Point pTab21 = MuseeTest.getPoint(xM,y2-(TAILLE_TABLEAU/2.0));
			Point pTab22 = MuseeTest.getPoint(xM,y2+(TAILLE_TABLEAU/2.0));
			Point pTab31 = MuseeTest.getPoint(xM,y3-(TAILLE_TABLEAU/2.0));
			Point pTab32 = MuseeTest.getPoint(xM,y3+(TAILLE_TABLEAU/2.0));

			tableaux.add(new Tableau(new Line(pTab11.x-MuseeTest.INVERSION_X*2, pTab11.y, pTab12.x-MuseeTest.INVERSION_X*2, pTab12.y,Line.Position.droite),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			tableaux.add(new Tableau(new Line(pTab21.x-MuseeTest.INVERSION_X*2, pTab21.y, pTab22.x-MuseeTest.INVERSION_X*2, pTab22.y,Line.Position.droite),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			tableaux.add(new Tableau(new Line(pTab31.x-MuseeTest.INVERSION_X*2, pTab31.y, pTab32.x-MuseeTest.INVERSION_X*2, pTab32.y,Line.Position.droite),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			break;
		default:
			break;
		}
	}

	private void dessinerMurGauche(){
		double distance_haut = DISTANCE_MURE;
		double distance_bas = DISTANCE_MURE;
		if(haveAdjacenceHaut()){
			distance_haut = DISTANCE_MURE_A;
			if(haveAdjacenceHautGauche()){
				distance_haut+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		if(haveAdjacenceBas()){
			distance_bas = DISTANCE_MURE_A;
			if(haveAdjacenceBasGauche()){
				distance_bas+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		switch (murGauche) {
		case murPorte:
			double yM1 = (double)y - distance_bas;
			double yP1 = (double)y - TAILLE_PORTE/2.0;
			double yM2 = (double)y + TAILLE_PORTE/2.0;
			double yP2 = (double)y + distance_haut;

			double xM  = (double)x -DISTANCE_MURE;
			Point p1 = MuseeTest.getPoint(xM, yM1);
			Point p2 = MuseeTest.getPoint(xM, yP1);
			Point p3 = MuseeTest.getPoint(xM, yP2);
			Point p4 = MuseeTest.getPoint(xM, yM2);

			//on dessine les murs
			lines.add(new Line(p1, p2,Line.Position.gauche));
			lines.add(new Line(p3, p4,Line.Position.gauche));
			break;
		case murVide:
			double yM12 = (double)y - distance_bas;
			double yM22 = (double)y + distance_haut;

			double xM2  = (double)x -DISTANCE_MURE;
			Point p12 = MuseeTest.getPoint(xM2, yM12);
			Point p22 = MuseeTest.getPoint(xM2, yM22);

			lines.add(new Line(p12, p22,Line.Position.gauche));
			break;
		default:
			break;
		}
	}

	private void dessinerTableauxGauche(){
		double distance_haut = DISTANCE_MURE;
		double distance_bas = DISTANCE_MURE;
		if(haveAdjacenceHaut()){
			distance_haut = DISTANCE_MURE_A;
			if(haveAdjacenceHautGauche()){
				distance_haut+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		if(haveAdjacenceBas()){
			distance_bas = DISTANCE_MURE_A;
			if(haveAdjacenceBasGauche()){
				distance_bas+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		double xM  = (double)x -DISTANCE_MURE;
		switch (murGauche) {
		case murPorte:
			//on calcul les points des tabeleau
			double yTab1 = (double)y - ((TAILLE_PORTE/2.0)+((distance_bas-(TAILLE_PORTE/2.0))/2.0));
			double yM1 = yTab1 - (TAILLE_TABLEAU/2.0);
			double yP1 = yTab1 + (TAILLE_TABLEAU/2.0);

			double yTab2 = (double)y + ((TAILLE_PORTE/2.0)+((distance_haut-(TAILLE_PORTE/2.0))/2.0));
			double yM2 = yTab2 -(TAILLE_TABLEAU/2.0);
			double yP2 = yTab2 +(TAILLE_TABLEAU/2.0);

			Point p1 = MuseeTest.getPoint(xM, yM1);
			Point p2 = MuseeTest.getPoint(xM, yP1);
			Point p3 = MuseeTest.getPoint(xM, yP2);
			Point p4 = MuseeTest.getPoint(xM, yM2);
			// l'ajout de +Musee.INVERSION_X*2 sert a ecarter le tableau du mur
			tableaux.add(new Tableau(new Line(p1.x+MuseeTest.INVERSION_X*2, p1.y, p2.x+MuseeTest.INVERSION_X*2, p2.y,Line.Position.gauche),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			tableaux.add(new Tableau(new Line(p3.x+MuseeTest.INVERSION_X*2, p3.y, p4.x+MuseeTest.INVERSION_X*2, p4.y,Line.Position.gauche),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			break;
		case murVide:
			double y1 = (double)y - 0.25;
			double y2 = (double)y ;
			double y3 = (double)y + 0.25;
			Point pTab11 = MuseeTest.getPoint(xM, y1-(TAILLE_TABLEAU/2.0));
			Point pTab12 = MuseeTest.getPoint(xM,y1+(TAILLE_TABLEAU/2.0));
			Point pTab21 = MuseeTest.getPoint(xM,y2-(TAILLE_TABLEAU/2.0));
			Point pTab22 = MuseeTest.getPoint(xM,y2+(TAILLE_TABLEAU/2.0));
			Point pTab31 = MuseeTest.getPoint(xM,y3-(TAILLE_TABLEAU/2.0));
			Point pTab32 = MuseeTest.getPoint(xM,y3+(TAILLE_TABLEAU/2.0));

			tableaux.add(new Tableau(new Line(pTab11.x+MuseeTest.INVERSION_X*2, pTab11.y, pTab12.x+MuseeTest.INVERSION_X*2, pTab12.y,Line.Position.gauche),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			tableaux.add(new Tableau(new Line(pTab21.x+MuseeTest.INVERSION_X*2, pTab21.y, pTab22.x+MuseeTest.INVERSION_X*2, pTab22.y,Line.Position.gauche),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			tableaux.add(new Tableau(new Line(pTab31.x+MuseeTest.INVERSION_X*2, pTab31.y, pTab32.x+MuseeTest.INVERSION_X*2, pTab32.y,Line.Position.gauche),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			break;
		default:
			break;
		}
	}

	private void dessinerMurBas(){
		double distance_droite = DISTANCE_MURE;
		double distance_Gauche = DISTANCE_MURE;
		if(haveAdjacenceDroite()){
			distance_droite = DISTANCE_MURE_A;
			if(haveAdjacenceBasDroite()){
				distance_droite+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		if(haveAdjacenceGauche()){
			distance_Gauche = DISTANCE_MURE_A;
			if(haveAdjacenceBasGauche()){
				distance_Gauche+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		switch (murBas) {
		case murPorte:
			double xM1 = (double)x - distance_Gauche;
			double xP1 = (double)x - TAILLE_PORTE/2.0;
			double xP2 = (double)x + TAILLE_PORTE/2.0;
			double xM2 = (double)x + distance_droite;

			double yM  = (double)y -DISTANCE_MURE;
			Point p1 = MuseeTest.getPoint(xM1, yM);
			Point p2 = MuseeTest.getPoint(xP1, yM);
			Point p3 = MuseeTest.getPoint(xP2, yM);
			Point p4 = MuseeTest.getPoint(xM2, yM);

			lines.add(new Line(p1, p2,Line.Position.bas));
			lines.add(new Line(p3, p4,Line.Position.bas));
			break;
		case murVide:
			double xM12 = (double)x - distance_Gauche;
			double xM22 = (double)x + distance_droite;

			double yM2  = (double)y -DISTANCE_MURE;
			Point p12 = MuseeTest.getPoint(xM12, yM2);
			Point p22 = MuseeTest.getPoint(xM22, yM2);

			lines.add(new Line(p12, p22,Line.Position.bas));
			break;
		default:
			break;
		}
	}

	private void dessinerTableauBas(){
		double distance_droite = DISTANCE_MURE;
		double distance_Gauche = DISTANCE_MURE;
		if(haveAdjacenceDroite()){
			distance_droite = DISTANCE_MURE_A;
			if(haveAdjacenceBasDroite()){
				distance_droite+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		if(haveAdjacenceGauche()){
			distance_Gauche = DISTANCE_MURE_A;
			if(haveAdjacenceBasGauche()){
				distance_Gauche+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		double yM  = (double)y -DISTANCE_MURE;
		switch (murBas) {
		case murPorte:
			//on calcul les points des murs
			double xTab1 = (double)x + ((TAILLE_PORTE/2.0)+((distance_droite-(TAILLE_PORTE/2.0))/2.0));
			double xM1 = xTab1 - (TAILLE_TABLEAU/2.0);
			double xP1 = xTab1 + (TAILLE_TABLEAU/2.0);

			double xTab2 = (double)x - ((TAILLE_PORTE/2.0)+((distance_Gauche-(TAILLE_PORTE/2.0))/2.0));
			double xM2 = xTab2 -(TAILLE_TABLEAU/2.0);
			double xP2 = xTab2 +(TAILLE_TABLEAU/2.0);

			Point p1 = MuseeTest.getPoint(xM1, yM);
			Point p2 = MuseeTest.getPoint(xP1, yM);
			Point p3 = MuseeTest.getPoint(xP2, yM);
			Point p4 = MuseeTest.getPoint(xM2, yM);
			// l'ajout de +Musee.INVERSION_X*2 sert a ecarter le tableau du mur
			tableaux.add(new Tableau(new Line(p1.x, p1.y+MuseeTest.INVERSION_Y*2, p2.x, p2.y+MuseeTest.INVERSION_Y*2,Line.Position.bas),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			tableaux.add(new Tableau(new Line(p3.x, p3.y+MuseeTest.INVERSION_Y*2, p4.x, p4.y+MuseeTest.INVERSION_Y*2,Line.Position.bas),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			break;
		case murVide:
			double x1 = (double)x - 0.25;
			double x2 = (double)x ;
			double x3 = (double)x + 0.25;
			Point pTab11 = MuseeTest.getPoint(x1-(TAILLE_TABLEAU/2.0), yM);
			Point pTab12 = MuseeTest.getPoint(x1+(TAILLE_TABLEAU/2.0), yM);
			Point pTab21 = MuseeTest.getPoint(x2-(TAILLE_TABLEAU/2.0), yM);
			Point pTab22 = MuseeTest.getPoint(x2+(TAILLE_TABLEAU/2.0), yM);
			Point pTab31 = MuseeTest.getPoint(x3-(TAILLE_TABLEAU/2.0), yM);
			Point pTab32 = MuseeTest.getPoint(x3+(TAILLE_TABLEAU/2.0), yM);

			tableaux.add(new Tableau(new Line(pTab11.x, pTab11.y+MuseeTest.INVERSION_Y*2, pTab12.x, pTab12.y+MuseeTest.INVERSION_Y*2,Line.Position.bas),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			tableaux.add(new Tableau(new Line(pTab21.x, pTab21.y+MuseeTest.INVERSION_Y*2, pTab22.x, pTab22.y+MuseeTest.INVERSION_Y*2,Line.Position.bas),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			tableaux.add(new Tableau(new Line(pTab31.x, pTab31.y+MuseeTest.INVERSION_Y*2, pTab32.x, pTab32.y+MuseeTest.INVERSION_Y*2,Line.Position.bas),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			break;
		default:
			break;
		}
	}

	private void dessinerMurHaut(){
		double distance_droite = DISTANCE_MURE;
		double distance_Gauche = DISTANCE_MURE;
		if(haveAdjacenceDroite()){
			distance_droite = DISTANCE_MURE_A;
			if(haveAdjacenceHautDroite()){
				distance_droite+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		if(haveAdjacenceGauche()){
			distance_Gauche = DISTANCE_MURE_A;
			if(haveAdjacenceHautGauche()){
				distance_Gauche+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		switch (murHaut) {
		case murPorte:
			double xM1 = (double)x - distance_Gauche;
			double xP1 = (double)x - TAILLE_PORTE/2.0;
			double xP2 = (double)x + TAILLE_PORTE/2.0;
			double xM2 = (double)x + distance_droite;

			double yM  = (double)y + DISTANCE_MURE;
			Point p1 = MuseeTest.getPoint(xM1, yM);
			Point p2 = MuseeTest.getPoint(xP1, yM);
			Point p3 = MuseeTest.getPoint(xP2, yM);
			Point p4 = MuseeTest.getPoint(xM2, yM);

			lines.add(new Line(p1, p2,Line.Position.haut));
			lines.add(new Line(p3, p4,Line.Position.haut));
			break;
		case murVide:
			double xM12 = (double)x - distance_Gauche;
			double xM22 = (double)x + distance_droite;

			double yM2  = (double)y +DISTANCE_MURE;
			Point p12 = MuseeTest.getPoint(xM12, yM2);
			Point p22 = MuseeTest.getPoint(xM22, yM2);


			lines.add(new Line(p12, p22,Line.Position.haut));
			break;
		default:
			break;
		}
	}

	private void dessinerTableauxHaut(){
		double distance_droite = DISTANCE_MURE;
		double distance_Gauche = DISTANCE_MURE;
		if(haveAdjacenceDroite()){
			distance_droite = DISTANCE_MURE_A;
			if(haveAdjacenceHautDroite()){
				distance_droite+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		if(haveAdjacenceGauche()){
			distance_Gauche = DISTANCE_MURE_A;
			if(haveAdjacenceHautGauche()){
				distance_Gauche+=(DISTANCE_MURE_A-DISTANCE_MURE);
			}
		}
		double yM  = (double)y + DISTANCE_MURE;
		switch (murHaut) {
		case murPorte:

			//on calcul les points des tableau
			double xTab1 = (double)x + ((TAILLE_PORTE/2.0)+((distance_droite-(TAILLE_PORTE/2.0))/2.0));
			double xM1 = xTab1 - (TAILLE_TABLEAU/2.0);
			double xP1 = xTab1 + (TAILLE_TABLEAU/2.0);

			double xTab2 = (double)x - ((TAILLE_PORTE/2.0)+((distance_Gauche-(TAILLE_PORTE/2.0))/2.0));
			double xM2 = xTab2 -(TAILLE_TABLEAU/2.0);
			double xP2 = xTab2 +(TAILLE_TABLEAU/2.0);

			Point p1 = MuseeTest.getPoint(xM1, yM);
			Point p2 = MuseeTest.getPoint(xP1, yM);
			Point p3 = MuseeTest.getPoint(xP2, yM);
			Point p4 = MuseeTest.getPoint(xM2, yM);
			//on dessine les tableux
			// l'ajout de +Musee.INVERSION_X*2 sert a ecarter le tableau du mur
			tableaux.add(new Tableau(new Line(p1.x, p1.y-MuseeTest.INVERSION_Y*2, p2.x, p2.y-MuseeTest.INVERSION_Y*2,Line.Position.haut),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			tableaux.add(new Tableau(new Line(p3.x, p3.y-MuseeTest.INVERSION_Y*2, p4.x, p4.y-MuseeTest.INVERSION_Y*2,Line.Position.haut),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			break;
		case murVide:

			// on dessine la tableaux
			double x1 = (double)x - 0.25;
			double x2 = (double)x ;
			double x3 = (double)x + 0.25;
			Point pTab11 = MuseeTest.getPoint(x1-(TAILLE_TABLEAU/2.0), yM);
			Point pTab12 = MuseeTest.getPoint(x1+(TAILLE_TABLEAU/2.0), yM);
			Point pTab21 = MuseeTest.getPoint(x2-(TAILLE_TABLEAU/2.0), yM);
			Point pTab22 = MuseeTest.getPoint(x2+(TAILLE_TABLEAU/2.0), yM);
			Point pTab31 = MuseeTest.getPoint(x3-(TAILLE_TABLEAU/2.0), yM);
			Point pTab32 = MuseeTest.getPoint(x3+(TAILLE_TABLEAU/2.0), yM);

			tableaux.add(new Tableau(new Line(pTab11.x, pTab11.y-MuseeTest.INVERSION_Y*2, pTab12.x, pTab12.y-MuseeTest.INVERSION_Y*2,Line.Position.haut),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			tableaux.add(new Tableau(new Line(pTab21.x, pTab21.y-MuseeTest.INVERSION_Y*2, pTab22.x, pTab22.y-MuseeTest.INVERSION_Y*2,Line.Position.haut),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			tableaux.add(new Tableau(new Line(pTab31.x, pTab31.y-MuseeTest.INVERSION_Y*2, pTab32.x, pTab32.y-MuseeTest.INVERSION_Y*2,Line.Position.haut),getSalle(),getSalle().getTableaux(indexTableau)));
			indexTableau++;
			break;
		default:
			break;
		}
	}

	/**
	 * @return the etage
	 */
	public int getEtage() {
		return etage;
	}
	public boolean haveAdjacenceDroite(){
		if(salle==null){
			return false;
		}
		return salle.getCarer(x+1, y, etage)!=null;
	}
	
	public boolean haveAdjacenceMuseeDroite(){
		return MuseeTest.getCarerMusee(etage,x+1, y)!=null;
	}

	public boolean haveAdjacenceGauche(){
		if(salle==null){
			return false;
		}
		return salle.getCarer(x-1, y, etage)!=null;
	}
	
	public boolean haveAdjacenceMuseeGauche(){
		return MuseeTest.getCarerMusee(etage, x-1, y)!=null;
	}

	public boolean haveAdjacenceHaut(){
		if(salle==null){
			return false;
		}
		return salle.getCarer(x, y+1, etage)!=null;
	}
	
	public boolean haveAdjacenceMuseeHaut(){
		return MuseeTest.getCarerMusee(etage,x, y+1 )!=null;
	}

	public boolean haveAdjacenceBas(){
		if(salle==null){
			return false;
		}
		return salle.getCarer(x, y-1, etage)!=null;
	}
	
	public boolean haveAdjacenceMuseeBas(){
		return MuseeTest.getCarerMusee(etage,x, y-1 )!=null;
	}

	public boolean haveAdjacenceHautDroite(){
		if(salle==null){
			return false;
		}
		return salle.getCarer(x+1, y+1, etage)!=null;
	}

	public boolean haveAdjacenceHautGauche(){
		if(salle==null){
			return false;
		}
		return salle.getCarer(x-1, y+1, etage)!=null;
	}

	private boolean haveAdjacenceBasGauche(){
		if(salle==null){
			return false;
		}
		return salle.getCarer(x-1, y-1, etage)!=null;
	}

	private boolean haveAdjacenceBasDroite(){
		if(salle==null){
			return false;
		}
		return salle.getCarer(x+1, y-1, etage)!=null;
	}

	/**
	 * 
	 * @return true si l'utilisateur est dans la salle
	 */
	public boolean VisiteurEstPresent(){
		if(Visiteur.estPresent(pointMaximun,pointMinimun)){
			Visiteur.setCarerActuelle(this);
			return true;
		}
		return false;
	}

	public String toString(){
		return "Carer "+id+"("+pointMinimun.x+","+pointMinimun.y+")"+"("+pointMaximun.x+","+pointMaximun.x+")";
	}

	public Point visiteurCanMove(Point pOld,Point pNew){

		CollisionTest g = new CollisionTest();
		draw(g);
		if(g.haveCollision(pOld, pNew)){
			return pOld;
		}
		return pNew;
	}

	@SuppressWarnings("unused")
	private boolean canMoveHaut(Point pOld,Point pNew){
		if(pNew.y<=pointMaximun.y && MuseeTest.INVERSION_Y>0){
			return true;
		}
		if(pNew.y>=pointMaximun.y && MuseeTest.INVERSION_Y<0){
			return true;
		}
		if(murHaut==TypeMurCarer.murNull){
			return true;
		}
		System.err.println("can't move haut "+id);
		return false;
	}

	@SuppressWarnings("unused")
	private boolean canMoveBas(Point pOld,Point pNew){
		if(pNew.y>=pointMinimun.y && MuseeTest.INVERSION_Y>0){
			return true;
		}
		if(pNew.y<=pointMinimun.y && MuseeTest.INVERSION_Y<0){
			return true;
		}
		if(murBas==TypeMurCarer.murNull){
			return true;
		}else if(murBas == TypeMurCarer.murPorte){

		}
		System.err.println("can't move bas "+id);
		return false;
	}

	private boolean canMoveDroite(Point pOld,Point pNew){
		if(pNew.x<=pointMaximun.x){
			return true;
		}
		if(murDroit==TypeMurCarer.murNull){
			return true;
		}
		System.err.println("can't move droite "+id);
		return false;
	}

	private boolean canMoveGauche(Point pOld,Point pNew){
		if(pNew.x>=pointMinimun.x){
			return true;
		}
		if(murGauche==TypeMurCarer.murNull){
			return true;
		}
		System.err.println("can't move gauche "+id);
		return false;
	}

	/**
	 * @return l'id du carer
	 */
	public Object getId() {
		return this.id;
	}

	/**
	 * 
	 * @return tous les carer adjacent a celui ci
	 */
	public CarerMusee[] getCarerAdajacentSansSalle(){
		List<CarerMusee> listOut = new ArrayList<CarerMusee>();
		//les cotés
		CarerMusee carer= MuseeTest.getCarerMusee(etage,x+1,y);
		if(carer!=null){
			if(carer.getSalle()==null){
				listOut.add(carer);
			}
		}
		carer= MuseeTest.getCarerMusee(etage,x-1,y);
		if(carer!=null){
			if(carer.getSalle()==null){
				listOut.add(carer);
			}
		}
		carer= MuseeTest.getCarerMusee(etage,x,y+1);
		if(carer!=null){
			if(carer.getSalle()==null){
				listOut.add(carer);
			}
		}
		carer= MuseeTest.getCarerMusee(etage,x,y-1);
		if(carer!=null){
			if(carer.getSalle()==null){
				listOut.add(carer);
			}
		}
		//les coins
//		carer= Musee.getCarerMusee(etage,x+1,y-1);
//		if(carer!=null){
//			listOut.add(carer);
//		}
//		carer= Musee.getCarerMusee(etage,x-1,y+1);
//		if(carer!=null){
//			listOut.add(carer);
//		}
//		carer= Musee.getCarerMusee(etage,x+1,y+1);
//		if(carer!=null){
//			listOut.add(carer);
//		}
//		carer= Musee.getCarerMusee(etage,x-1,y-1);
//		if(carer!=null){
//			listOut.add(carer);
//		}
		return listOut.toArray(new CarerMusee[0]);
	}
	
	public CarerMusee[] getCarerAdajacentAvecSalle(){
		List<CarerMusee> listOut = new ArrayList<CarerMusee>();
		//les cotés
		CarerMusee carer= MuseeTest.getCarerMusee(etage,x+1,y);
		if(carer!=null){
			if(carer.getSalle()!=null){
				listOut.add(carer);
			}
		}
		carer= MuseeTest.getCarerMusee(etage,x-1,y);
		if(carer!=null){
			if(carer.getSalle()!=null){
				listOut.add(carer);
			}
		}
		carer= MuseeTest.getCarerMusee(etage,x,y+1);
		if(carer!=null){
			if(carer.getSalle()!=null){
				listOut.add(carer);
			}
		}
		carer= MuseeTest.getCarerMusee(etage,x,y-1);
		if(carer!=null){
			if(carer.getSalle()!=null){
				listOut.add(carer);
			}
		}
		//les coins
//		carer= Musee.getCarerMusee(etage,x+1,y-1);
//		if(carer!=null){
//			listOut.add(carer);
//		}
//		carer= Musee.getCarerMusee(etage,x-1,y+1);
//		if(carer!=null){
//			listOut.add(carer);
//		}
//		carer= Musee.getCarerMusee(etage,x+1,y+1);
//		if(carer!=null){
//			listOut.add(carer);
//		}
//		carer= Musee.getCarerMusee(etage,x-1,y-1);
//		if(carer!=null){
//			listOut.add(carer);
//		}
		return listOut.toArray(new CarerMusee[0]);
	}

	/**
	 * 
	 * @return la liste de tous les carer adjacent qui sont dans
	 * une salle diférente de celui-ci
	 */
	public CarerMusee[] getCarerAdajacentDiferenteSalle(){
		List<CarerMusee> listOut = new ArrayList<CarerMusee>();
		CarerMusee[] tmp = getCarerAdajacentSansSalle();
		if(getSalle()==null){
			/*
			 * si ce carer n'a pas salle, cela
			 * veut dire que tout ces carer 
			 * adjacent ne sont pas dans la 
			 * même salle que lui
			 */
			return tmp;
		}
		for(CarerMusee carer : tmp){
			if(carer.getSalle()==null){
				listOut.add(carer);
				continue;
			}
			if(!carer.getSalle().getId().equals(getSalle().getId())){
				listOut.add(carer);
			}
		}
		return listOut.toArray(new CarerMusee[0]);
	}

	/**
	 * 
	 * @return la liste des salle adajacente
	 */
	public SalleMusee[] getSalleAdajacent(){
		if(this.getSalle()==null){
			return new SalleMusee[0];
		}
		List<SalleMusee> listOut = new ArrayList<SalleMusee>();
		CarerMusee[] carers= getCarerAdajacentSansSalle();
		for(CarerMusee carer : carers){
			if(carer.getSalle()==null)continue;
			if(listOut.contains(carer.getSalle()))continue;
			if(carer.getSalle().getId().equals(this.getSalle().getId()))continue;
			listOut.add(carer.getSalle());
		}
		return listOut.toArray(new SalleMusee[0]);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o){
		if(!(o instanceof CarerMusee)){
			return false;
		}
		if(x!=((CarerMusee)o).x){
			return false;
		}
		if(y!=((CarerMusee)o).y){
			return false;
		}
		if(etage!=((CarerMusee)o).etage){
			return false;
		}
		return true;
	}

	public static List<List<CarerMusee>> sort(List<CarerMusee> list){
		if(list==null){
			return null;
		}
		List<List<CarerMusee>> listOut = new ArrayList<List<CarerMusee>>();
		CarerMusee carerAdd = null;
		List<CarerMusee> listTmp = new ArrayList<CarerMusee>();
		while(!list.isEmpty()){
			if(carerAdd==null){
				listTmp = new ArrayList<CarerMusee>();
				listOut.add(listTmp);
				carerAdd = getCoinsBasGauche(list);
				listTmp.add(carerAdd);
				list.remove(carerAdd);
			}
			carerAdd = getAdjacent(carerAdd,list);
			if(carerAdd!=null){
				listTmp.add(carerAdd);
				list.remove(carerAdd);
				/*
				 * on veut des list qui ne dépasse pas la taille
				 * maximal d'une salle
				 */
				if(listTmp.size()>=SalleMusee.TAILLE_MAXIMAL){
					carerAdd = null;
					listOut.add(listTmp);
				}
			}
//			else{
//				listOut.add(listTmp);
//			}
		}
		return listOut;
	}

	private static CarerMusee getCoinsBasGauche(List<CarerMusee> list){
		CarerMusee carerMusee = list.get(0);
		for(CarerMusee car : list){
			if(car.getX()<=carerMusee.getX() &&
					car.getY()<=carerMusee.getY() &&
					car.getEtage()<=carerMusee.getEtage()){
				carerMusee = car;
			}
		}
		return carerMusee;
	}
	
	private static CarerMusee getAdjacent(CarerMusee carer,List<CarerMusee> list){
		for(CarerMusee car : list){
			if(car.equals(carer))continue;
			if(car.x==carer.x){
				if(Math.abs(car.y-carer.y)==1){
					return car;
				}
			}else if(car.y==carer.y){
				if(Math.abs(car.x-carer.x)==1){
					return car;
				}
			}
		}
		return null;
	}

	/**
	 * @return le nombre de tableau affichable dans la carer
	 */
	public int getNombreDeTableaux() {
		int nombre = 0;
		nombre += getNombreDeTableaux(murBas);
		nombre += getNombreDeTableaux(murHaut);
		nombre += getNombreDeTableaux(murDroit);
		nombre += getNombreDeTableaux(murGauche);
		return nombre;
	}
	
	private int getNombreDeTableaux(TypeMurCarer type){
		switch (type) {
		case murPorte:
			return 2;
		case murVide:
			return 3;
		default:
			return 0;
		}
	}

	/**
	 * @return
	 */
	public boolean isVisible() {
		if(getSalle()==null){
			return false;
		}
		return getSalle().isVisible();
	}
}
