/**
 * 
 */
package fr.enib.game.editor.graphe.monde;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Corentin Boleis
 *
 */
public class Porte {
	
	private CarerMusee carerMuseeA;
	
	private CarerMusee carerMuseeB;
	
	private List<Line> lines = new ArrayList<Line>();
	
	private List<Line> linesPorte = new ArrayList<Line>();
	
	private boolean fermer = true;
	
	/**
	 * 
	 * @param a
	 * @param b
	 */
	public Porte(CarerMusee a, CarerMusee b){
		if(!b.isAdjacent(a)){
			throw new IllegalArgumentException("a doit etre adjacent a b");
		}
		this.carerMuseeA = a;
		this.carerMuseeB = b;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Porte)){
			return false;
		}
		Porte porte = (Porte)o;
		if(porte.carerMuseeA.getId().equals(carerMuseeA.getId()) && 
				porte.carerMuseeB.getId().equals(carerMuseeB.getId())){
			return true;
		}
		if(porte.carerMuseeB.getId().equals(carerMuseeA.getId()) && 
				porte.carerMuseeA.getId().equals(carerMuseeB.getId())){
			return true;
		}
		return false;
	}
	
	public void dessiner(Graphics g){
		if(lines.isEmpty()){
			linesPorte.clear();
			if(carerMuseeA.getSalle()!= null && carerMuseeB.getSalle()!= null){
				//si les deux carer sont dans la même salle il ne peut pas y avoir de porte
				if(carerMuseeA.getSalle().getId().equals( carerMuseeB.getSalle().getId())){
					return;
				}
			}
			if(carerMuseeA.getX() == carerMuseeB.getX()){
				double ySup = Math.max(carerMuseeA.getY() , carerMuseeB.getY())-CarerMusee.DISTANCE_MURE;
				double yInf = Math.min(carerMuseeA.getY() , carerMuseeB.getY())+CarerMusee.DISTANCE_MURE;
				double xSup = carerMuseeA.getX()+CarerMusee.TAILLE_PORTE/2.0;
				double xInf = carerMuseeA.getX()-CarerMusee.TAILLE_PORTE/2.0;
				Point p1 = MuseeTest.getPoint(xInf, yInf);
				Point p2 = MuseeTest.getPoint(xInf, ySup);
				Point p3 = MuseeTest.getPoint(xSup, yInf);
				Point p4 = MuseeTest.getPoint(xSup, ySup);
				lines.add(new Line(p1,p2));
				lines.add(new Line(p3, p4));
				// ligne porte
				double yp = Math.max(carerMuseeA.getY() , carerMuseeB.getY())-CarerMusee.DISTANCE_MURE_A;
				double xpSup = carerMuseeA.getX()+CarerMusee.TAILLE_PORTE/2.0;
				double xpInf = carerMuseeA.getX()-CarerMusee.TAILLE_PORTE/2.0;
				
				Point pp1 = MuseeTest.getPoint(xpSup, yp);
				Point pp2 = MuseeTest.getPoint(xpInf, yp);
				
				linesPorte.add(new Line(pp1, pp2));
				linesPorte.add(new Line(pp1.x,pp1.y+1, pp2.x,pp2.y+1));
				
			}else if( carerMuseeA.getY() == carerMuseeB.getY()){
				double xSup = Math.max(carerMuseeA.getX() , carerMuseeB.getX())-CarerMusee.DISTANCE_MURE;
				double xInf = Math.min(carerMuseeA.getX() , carerMuseeB.getX())+CarerMusee.DISTANCE_MURE;
				double ySup = carerMuseeA.getY()+CarerMusee.TAILLE_PORTE/2.0;
				double yInf = carerMuseeA.getY()-CarerMusee.TAILLE_PORTE/2.0;
				Point p1 = MuseeTest.getPoint(xInf, yInf);
				Point p2 = MuseeTest.getPoint(xInf, ySup);
				Point p3 = MuseeTest.getPoint(xSup, yInf);
				Point p4 = MuseeTest.getPoint(xSup, ySup);
				lines.add(new Line(p1, p3));
				lines.add(new Line(p2, p4));
				
				// ligne porte
				double xp = Math.max(carerMuseeA.getX() , carerMuseeB.getX())-CarerMusee.DISTANCE_MURE_A;
				double ypSup = carerMuseeA.getY()+CarerMusee.TAILLE_PORTE/2.0;
				double ypInf = carerMuseeA.getY()-CarerMusee.TAILLE_PORTE/2.0;
				
				Point pp1 = MuseeTest.getPoint(xp, ypSup);
				Point pp2 = MuseeTest.getPoint(xp, ypInf);
				
				linesPorte.add(new Line(pp1, pp2));
				linesPorte.add(new Line(pp1.x+1,pp1.y, pp2.x+1,pp2.y));
			}
		}
		if(carerMuseeA.isVisible() || carerMuseeB.isVisible()){
			g.setColor(Color.black);
			for(Line line: lines){
				line.draw(g);
			}
			checkFermer();
			if(!fermer)return;
			for(Line line: linesPorte){
				line.draw(g);
			}
		}
		
		
	}
	
	private void checkFermer(){
		if(carerMuseeA.getSalle()!=null){
			if(carerMuseeA.getSalle().visiteurEstPresent()){
				fermer = carerMuseeB.getSalle()==null;
				return;
			}
		}
		if(carerMuseeB.getSalle()!=null){
			if(carerMuseeB.getSalle().visiteurEstPresent()){
				fermer = carerMuseeA.getSalle()==null;
				return;
			}
		}
		if(carerMuseeA.VisiteurEstPresent() || carerMuseeB.VisiteurEstPresent()){
			fermer = false;
			return;
		}
		fermer = true;
		
	}

}
