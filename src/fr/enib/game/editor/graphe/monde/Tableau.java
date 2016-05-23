/**
 * 
 */
package fr.enib.game.editor.graphe.monde;

import java.awt.Color;
import java.awt.Graphics;

import fr.enib.game.editor.graphe.monde.ihm.ViewerTableau;
import fr.enib.game.model.interfaces.ITableau;

/**
 * @author Corentin Boleis
 *
 */
public class Tableau {
	
	private Line line;
	
	private ITableau iTableau;
	
	private SalleMusee salleMusee;
	
	/**
	 * 
	 * @param line
	 * @param salle 
	 * @param tableau 
	 */
	public Tableau(Line line,SalleMusee salle,ITableau tableau){
		this.line = line;
		this.salleMusee = salle;
		this.iTableau = tableau;
	}
	
	/**
	 * 
	 * @param g
	 */
	public void dessiner(Graphics g){
		int[] tab = line.getCoordonees();
		g.setColor(Color.CYAN);
		if(estRegarder()){
			ViewerTableau.setTableau(iTableau);
			g.setColor(Color.orange);
		}
		g.drawLine(tab[0], tab[1], tab[2], tab[3]);
	}
	
	/**
	 * 
	 * @return true si le visiteur 
	 */
	public boolean estRegarder(){
		if(!Visiteur.isWatch(line)){
			return false;
		}
		if(salleMusee!=null){
			return salleMusee.visiteurEstPresent();
		}
		return true;
	}

}
