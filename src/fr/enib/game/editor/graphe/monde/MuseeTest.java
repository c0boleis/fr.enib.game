/**
 * 
 */
package fr.enib.game.editor.graphe.monde;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.sun.javafx.collections.MappingChange.Map;

import fr.enib.game.app.ParcoursGraphe;
import fr.enib.game.model.Model;
import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.monde.geo.Repere;
import fr.enib.game.monde.geo.Vec3;
import fr.enib.game.monde.musee.Salle;
import fr.enib.game.parcours.graphe.Parcours;

/**
 * @author Corentin Boleis
 *
 */
public class MuseeTest {
	
	private static int idSalle=0;
	
	private static Repere repere;
	
	private static SalleMusee salleCourente = null;
	
	/**
	 * 
	 */
	public static final boolean FILL_ALL_SALLE = true;

	/**
	 * 
	 */
	public static final int NOMBRE_COLLONE = 11;
	
	/**
	 * 
	 */
	public static final int NOMBRE_LIGNE = 11;
	
	/**
	 * 
	 */
	public static final int NOMBRE_ETAGE = 1;

	protected static double scale = 1.0;

	private static int offsetX = 0;

	private static int offsetY = 0;

	/**
	 * 
	 */
	public static final int INVERSION_X = 1;

	/**
	 * 
	 */
	public static final int INVERSION_Y = -1;

	private static List<SalleMusee> salles = new ArrayList<SalleMusee>();

	private static HashMap<String, CarerMusee> carers = new HashMap<String, CarerMusee>();
	
	private static List<Porte> portes = new ArrayList<Porte>();

	private static final MuseeTest INSTANCE = new MuseeTest(600,600);
	
	private static Parcours parcoursGraphe;

	private MuseeTest(int width,int height){
		INoeud root = Model.get().getRoot();
		parcoursGraphe = new Parcours(root);
		//rien a faire dans le constructeure
		init(width,height);
		int xTmp = (int) Math.ceil((double)NOMBRE_COLLONE/2.0);
		int yTmp = (int) Math.ceil((double)NOMBRE_LIGNE/2.0);
		SalleMusee salle1 = new SalleMusee("salle_0");
		if(!salle1.addEspace(carers.get("(1,"+xTmp+","+yTmp+")"))){
			System.err.println("salle non ajouter");
		}
		salles.add(salle1);
		salle1.setNoeud(root);
		salle1.init();
//		if(!salle1.addEspace(carers.get("(1,3,2)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,2,3)"))){
//			System.err.println("salle non ajouter");
//		}
//		salle1.init();
//		salle1 = new SalleMusee("salle_2");
//		if(!salle1.addEspace(carers.get("(1,1,1)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,1,2)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,1,3)"))){
//			System.err.println("salle non ajouter");
//		}
//		salle1.init();
//		salle1 = new SalleMusee("salle_3");
//		if(!salle1.addEspace(carers.get("(1,2,1)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,3,1)"))){
//			System.err.println("salle non ajouter");
//		}
//		salle1.init();
//		salle1 = new SalleMusee("salle_4");
//		if(!salle1.addEspace(carers.get("(1,4,1)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,4,2)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,5,1)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,5,2)"))){
//			System.err.println("salle non ajouter");
//		}
//		salle1.init();
//		salle1 = new SalleMusee("salle_5");
//		if(!salle1.addEspace(carers.get("(1,4,3)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,5,3)"))){
//			System.err.println("salle non ajouter");
//		}
//		salle1.init();
//		salle1 = new SalleMusee("salle_6");
//		if(!salle1.addEspace(carers.get("(1,1,4)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,1,5)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,2,4)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,2,5)"))){
//			System.err.println("salle non ajouter");
//		}
//		salle1.init();
//		salle1 = new SalleMusee("salle_7");
//		if(!salle1.addEspace(carers.get("(1,3,4)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,4,4)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,5,4)"))){
//			System.err.println("salle non ajouter");
//		}
//		salle1.init();
//		salle1 = new SalleMusee("salle_8");
//		if(!salle1.addEspace(carers.get("(1,3,5)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,4,5)"))){
//			System.err.println("salle non ajouter");
//		}
//		if(!salle1.addEspace(carers.get("(1,5,5)"))){
//			System.err.println("salle non ajouter");
//		}
//		salle1.init();
		refreshSalleCourante();
	}

	/**
	 * 
	 * @return
	 */
	public static MuseeTest get(){
		return INSTANCE;
	}
	
	private final void init(int width,int height){
		initDimension(width, height);
		carers.clear();
		for(int etage = 1;etage <=NOMBRE_ETAGE;etage++){
			for(int x = 1;x<=NOMBRE_COLLONE;x++){
				for(int y = 1;y<=NOMBRE_LIGNE;y++){
					String index = "("+etage+","+x+","+y+")";
					carers.put(index, new CarerMusee(x, y,etage,index));
				}
			}
		}
		initPortes();
		
	}
	
	private static void initPortes(){
		CarerMusee[] tmp = carers.values().toArray(new CarerMusee[0]);
		portes.clear();
		for(CarerMusee carA : tmp){
			for(CarerMusee carB : tmp){
				try{
					if(carA.getSalle()==null && carB.getSalle()==null){
						continue;
					}
					Porte porte = new Porte(carA,carB);
					if(!portes.contains(porte)){
						portes.add(porte);
					}
				}catch(IllegalArgumentException e){
					// rien a faire cela peut arrivé
				}
			}
		}
	}

	/**
	 * create a new Salle
	 * @param tab
	 * @param idSalle
	 * @return
	 */
	public static SalleMusee createSalle(CarerMusee[] tab,String idSalle){
		if(idSalle==null){
			MuseeTest.idSalle++;
			idSalle = "Salle_"+MuseeTest.idSalle;
		}
		SalleMusee salleMusee = new SalleMusee(idSalle);
		for(CarerMusee carerMusee : tab){
			if(!salleMusee.addEspace(carerMusee)){
				return null;
			}
		}
		salles.add(salleMusee);
		for(SalleMusee salle : salles){
			salle.init();
		}
		initPortes();
		return salleMusee;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return un point avec les bonne valeur pour le mussee
	 */
	public static Point getPoint(double x,double y){
		int xOut = (int) ((INVERSION_X*x+offsetX)*scale);
		int yOut = (int) ((INVERSION_Y*y+offsetY)*scale);
		return new Point(xOut, yOut);
	}

	/**
	 * @param width
	 * @param height
	 */
	@SuppressWarnings("unused")
	public static void initDimension(int width,int height){
		if(INVERSION_Y>0){
			offsetY = 0;
		}
		else{
			offsetY = NOMBRE_LIGNE+1;
		}
		if(INVERSION_X>0){
			offsetX = 0;
		}
		else{
			offsetX = NOMBRE_COLLONE+1;
		}
		double scaleX = (double)width/(double)(NOMBRE_COLLONE+1);
		double scaleY = (double)height/(double)(NOMBRE_LIGNE+1);
		scale = Math.min(scaleX, scaleY);
	}
	
	/**
	 * affiche le musée en 3D
	 * @param gl
	 */
	public static void display(GLAutoDrawable gl){
		GL2 gl2 = gl.getGL().getGL2() ; 
		gl2.glClear(GL.GL_COLOR_BUFFER_BIT) ; 
		gl2.glClear(GL.GL_DEPTH_BUFFER_BIT) ;
		salleCourente.display(gl2);
		SalleMusee[] sallesTmp = salleCourente.getSallesAdjacentes();
		for(SalleMusee salle : salles){
			salle.setVisible(false);
		}
		salleCourente.setVisible(true);
		for(SalleMusee salle : sallesTmp){
			salle.display(gl2);
			salle.setVisible(true);
		}
//		dessinerPortes(g);
	}

	/**
	 * dessine le muser
	 * @param g
	 */
	public static void draw(Graphics g){
//		CarerMusee[] tmp = carers.values().toArray(new CarerMusee[0]);
//		for(CarerMusee carer : tmp){
//			carer.draw(g);
//		}
		salleCourente.draw(g);
		SalleMusee[] sallesTmp = salleCourente.getSallesAdjacentes();
		for(SalleMusee salle : salles){
			salle.setVisible(false);
		}
		salleCourente.setVisible(true);
		for(SalleMusee salle : sallesTmp){
			salle.draw(g);
			salle.setVisible(true);
		}
		dessinerPortes(g);
	}
	
	public String toString(){
		String out = "";
		for(int etage = 1;etage <=NOMBRE_ETAGE;etage++){
			for(int x = 1;x<=NOMBRE_COLLONE;x++){
				for(int y = 1;y<=NOMBRE_LIGNE;y++){
					String index = "("+etage+","+x+","+y+")";
					out+=carers.get(index).toString()+"\t";
				}
				out+="\n";
			}
		}
		return out;
	}
	
	private static void dessinerPortes(Graphics g){
		for(Porte porte : portes){
			porte.dessiner(g);
		}
	}

	/**
	 * @param etage
	 * @param x
	 * @param y
	 * @return {@link CarerMusee}
	 */
	public static CarerMusee getCarerMusee(int etage, int x, int y) {
		String indexCarer = "("+etage+","+x+","+y+")";
		return carers.get(indexCarer);
	}
	
	/**
	 * 
	 * @param salle à suprimer
	 */
	public static void suprimerSalle(SalleMusee salle){
		salles.remove(salle);
	}
	
	/**
	 * 
	 * @return la salle courante
	 */
	public static SalleMusee getSalleCourante(){
		for(SalleMusee salle : salles){
			if(salle.visiteurEstPresent()){
				return salle;
			}
		}
		return null;
	}
	
	/**
	 * 
	 */
	public static void refreshSalleCourante(){
		SalleMusee salle  = getSalleCourante();
		if(salle==null){
			return;
		}
		if(salleCourente!=null){
			if(salleCourente.equals(salle)){
				return;
			}
		}
		
		salleCourente = salle;
		parcoursGraphe.setNoeudCourant(salleCourente.getNoeud());
		INoeud[] noeuds = parcoursGraphe.calcul_Noeud_Suivant();
		List<INoeud> list = new ArrayList<INoeud>();
		int index = 0;
		for(INoeud noeud : noeuds){
			if(index>1){
				list.add(noeud);
			}
			index++;
		}
		salleCourente.rajouterDesSalleAutour(list.toArray(new INoeud[0]));
		for(SalleMusee s : salles){
			s.init();
		}
		initPortes();
	}

	/**
	 * @return the repere
	 */
	public static Repere getRepere() {
		if(repere==null){
			repere = new Repere(new Vec3(0, 0, 0));
		}
		return repere;
	}
	
	

}
