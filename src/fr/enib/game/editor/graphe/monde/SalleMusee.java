/**
 * 
 */
package fr.enib.game.editor.graphe.monde;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jogamp.opengl.GL2;

import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.model.interfaces.ITableau;

/**
 * @author Corentin Boleis
 *
 */
public class SalleMusee {

	/**
	 * le nombre maximal qu'une 
	 */
	public static final int TAILLE_MAXIMAL = 4;

	private List<CarerMusee> espaceOcuppee = new ArrayList<CarerMusee>();

	private String id = null;
	
	private boolean visible = false;
	
	private INoeud noeud;
	
	private ArrayList<ITableau> tableaux;

	/**
	 * @param id 
	 * 
	 */
	public SalleMusee(String id){
		this.id = id;
	}

	public boolean equals(Object o){
		if(!(o instanceof SalleMusee)){
			return false;
		}
		SalleMusee salle = (SalleMusee)o;
		return salle.getId().equals(id);
	}

	/**
	 * 
	 */
	public void init(){
		for(CarerMusee car : espaceOcuppee){
			for(CarerMusee tmp : espaceOcuppee){
				if(tmp.isAdjacent(car)){
					if(tmp.getX()==car.getX()){
						int det = tmp.getY()-car.getY();
						if(det>0){
							tmp.setMurBas(TypeMurCarer.murNull);
						}
						else{
							tmp.setMurHaut(TypeMurCarer.murNull);
						}
					}else{
						int det = tmp.getX()-car.getX();
						if(det>0){
							tmp.setMureGauche(TypeMurCarer.murNull);
						}
						else{
							tmp.setMurDroit(TypeMurCarer.murNull);
						}
					}
				}
			}
		}
		for(CarerMusee car : espaceOcuppee){
			if(!car.haveAdjacenceMuseeBas()){
				car.setMurBas(TypeMurCarer.murVide);
			}
			if(!car.haveAdjacenceMuseeHaut()){
				car.setMurHaut(TypeMurCarer.murVide);
			}
			if(!car.haveAdjacenceMuseeDroite()){
				car.setMurDroit(TypeMurCarer.murVide);
			}
			if(!car.haveAdjacenceMuseeGauche()){
				car.setMureGauche(TypeMurCarer.murVide);
			}
		}
	}

	/**
	 * 
	 * @param p
	 * @return true si l'espace à été ajouter
	 */
	public boolean addEspace(CarerMusee p){
		if(p.getSalle() != null){
			return false;
		}
		else{
			p.setSalle(this);
		}
		if(espaceOcuppee.isEmpty()){
			espaceOcuppee.add(p);
			return true;
		}
		if(espaceOcuppee.size()>=TAILLE_MAXIMAL){
			return false;
		}
		for(CarerMusee pTmp : espaceOcuppee){
			if(p.isAdjacent(pTmp)){
				espaceOcuppee.add(p);
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @return les nombre de carer utiliser dans le musee
	 */
	public int getTaille(){
		return espaceOcuppee.size();
	}

	/**
	 * dessine la salle
	 * @param g
	 */
	public void draw(Graphics g){
		for(CarerMusee carerMusee : espaceOcuppee){
			carerMusee.draw(g);
		}
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param etage
	 * @return
	 */
	public CarerMusee getCarer(int x,int y,int etage){
		for(CarerMusee pTmp : espaceOcuppee){
			if(x==pTmp.getX() && y==pTmp.getY() && etage==pTmp.getEtage()){
				return pTmp;
			}
		}
		return null;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @return true si le visteur est dans cette salle
	 */
	public boolean visiteurEstPresent(){
		for(CarerMusee car : espaceOcuppee){
			if(car.VisiteurEstPresent()){
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param tab la taille du tableau corespond au nombre de
	 * Salles voulu, et il contiens le nombre de tableaux par salle
	 */
	public void rajouterDesSalleAutour(int[] tab){
		List<Integer> list = new ArrayList<Integer>();
		for(int nombre : tab){
			list.add(new Integer(nombre));
		}
		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});

		SalleMusee[] salles = getNewSalleAdajacente();
		int size = Math.min(salles.length, tab.length);
		for(int index = 0;index<size;index++){
			SalleMusee salle = salles[index];
			int nbrTableau = tab[index];
			while(salle.getTaille()<TAILLE_MAXIMAL 
					&& salle.getNombreDeTableaux()<nbrTableau){
				if(!salle.agrandir()){
					break;
				}
			}
		}
		if(salles.length>tab.length){
			for(int index = tab.length;index<salles.length;index++){
				salles[index].suprimer();
			}
		}
	}
	
	public void rajouterDesSalleAutour(INoeud[] tab){
		List<INoeud> list = Arrays.asList(tab);
		Collections.sort(list, new Comparator<INoeud>() {
			@Override
			public int compare(INoeud o1, INoeud o2) {
				Integer i1  = o1.getTableau().size();
				Integer i2 = o2.getTableau().size();
				return i1.compareTo(i2);
			}
		});

		SalleMusee[] salles = getNewSalleAdajacente();
		int size = Math.min(salles.length, tab.length);
		for(int index = 0;index<size;index++){
			SalleMusee salle = salles[index];
			salle.setNoeud(tab[index]);
			int nbrTableau = tab[index].getTableau().size();
			while(salle.getTaille()<TAILLE_MAXIMAL 
					&& salle.getNombreDeTableaux()<nbrTableau){
				if(!salle.agrandir()){
					break;
				}
			}
		}
		if(salles.length>tab.length){
			for(int index = tab.length;index<salles.length;index++){
				salles[index].suprimer();
			}
		}
	}

	/**
	 * 
	 */
	private boolean agrandir() {
		CarerMusee[] tmp = espaceOcuppee.toArray(new CarerMusee[0]);
		for(CarerMusee carer : tmp){
			CarerMusee[] tab = carer.getCarerAdajacentSansSalle();
			if(tab.length>0){
				if(!this.addEspace(tab[0])){
					System.err.println("error agrandir salle");
					return false;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public SalleMusee[] getNewSalleAdajacente(){
		List<CarerMusee> listCarer = new ArrayList<CarerMusee>();
		for(CarerMusee car : espaceOcuppee){
			CarerMusee[] carers = car.getCarerAdajacentSansSalle();
			for(CarerMusee carTmp : carers){
				if(!listCarer.contains(carTmp)){
					listCarer.add(carTmp);
				}
			}
		}

		List<List<CarerMusee>> list = CarerMusee.sort(listCarer);
		List<SalleMusee> listSalleOut = new ArrayList<SalleMusee>();
		for(List<CarerMusee> listForSalle : list){
			SalleMusee salle = 
					MuseeTest.createSalle(
							listForSalle.toArray(new CarerMusee[0]),null);
			if(salle !=null){
				listSalleOut.add(salle);
			}
		}
		return listSalleOut.toArray(new SalleMusee[0]);
	}

	private void suprimer(){
		for(CarerMusee car : espaceOcuppee){
			car.setSalle(null);
		}
		MuseeTest.suprimerSalle(this);
	}

	/**
	 * 
	 * @return le nombre de tableaux affichable dans la salle
	 */
	public int getNombreDeTableaux(){
		int nombre = 0;
		for(CarerMusee carer : espaceOcuppee){
			nombre += carer.getNombreDeTableaux();
		}
		return nombre;
	}


	public SalleMusee[] getSallesAdjacentes(){
		List<CarerMusee> listCarer = new ArrayList<CarerMusee>();
		for(CarerMusee car : espaceOcuppee){
			CarerMusee[] carers = car.getCarerAdajacentAvecSalle();
			for(CarerMusee carTmp : carers){
				if(!listCarer.contains(carTmp)){
					listCarer.add(carTmp);
				}
			}
		}

		List<SalleMusee> listSalleOut = new ArrayList<SalleMusee>();
		for(CarerMusee carer : listCarer){
			SalleMusee salle = carer.getSalle();
			if(salle ==null)continue;
			if(listSalleOut.contains(salle))continue;
			listSalleOut.add(salle);


		}
		return listSalleOut.toArray(new SalleMusee[0]);
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the noeud
	 */
	public INoeud getNoeud() {
		return noeud;
	}

	/**
	 * @param noeud the noeud to set
	 */
	public void setNoeud(INoeud noeud) {
		this.noeud = noeud;
	}

	/**
	 * @return the tableaux
	 */
	public ArrayList<ITableau> getTableaux() {
		if(tableaux==null){
			if(noeud!=null){
				tableaux = noeud.getTableau();
			}
			else{
				return new ArrayList<ITableau>();
			}
		}
		return tableaux;
	}

	/**
	 * @param indexTableau
	 * @return
	 */
	public ITableau getTableaux(int indexTableau) {
		if(indexTableau<getTableaux().size()){
			ITableau iTableau =  getTableaux().get(indexTableau);
			return iTableau;
		}
		return null;
	}

	/**
	 * @param gl
	 */
	public void display(GL2 gl) {
		for(CarerMusee carerMusee : espaceOcuppee){
			carerMusee.display(gl);
		}
	}

}
