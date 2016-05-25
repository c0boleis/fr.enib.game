/**
 * 
 */
package fr.enib.game.editor.graphe.monde;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * @author Corentin Boleis
 *
 */
public class DessinMusee extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6690147138421116505L;
	
	private JPanel panel;
	
	private static double vitesse = 0.05;
	
	private static int width = 600;
	
	private static int height = 600;
	
	private DessinMusee(){
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(getPanel(),BorderLayout.CENTER);
		this.pack();
		this.setResizable(false);
		this.getPanel().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("x: "+e.getX()+",y: "+e.getY());
				System.out.println(Visiteur.get().toString());
				System.out.println(MuseeTest.get().toString());
			}
		});
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
//				System.out.println(code);
				if(code == 38){
					Visiteur.deplacer(0, vitesse);
				}else if(code == 37){
					Visiteur.deplacer(-vitesse,0);
				}else if(code == 39){
					Visiteur.deplacer(vitesse,0 );
				}else if(code == 40){
					Visiteur.deplacer( 0,-vitesse);
				}else if(code == 109){
					Visiteur.avancer(vitesse);
				}else if(code == 107){
					Visiteur.avancer(-vitesse);
				}else if(code == 65){
					Visiteur.tourner(Math.PI/8);
				}else if(code == 81){
					Visiteur.tourner(-Math.PI/8);
				}
				MuseeTest.refreshSalleCourante();
				getPanel().repaint();
			}
		});
//		new Launcher3D(false);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DessinMusee dessinMusee = new DessinMusee();
		dessinMusee.setVisible(true);
	}

	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		if(panel == null){
			panel = new JPanel(){

				/**
				 * 
				 */
				private static final long serialVersionUID = -5836833502288710669L;
				
				public void paint(Graphics g){
					super.paint(g);
					MuseeTest.draw(g);
					Visiteur.draw(g);
				}
			};
			panel.setPreferredSize(new Dimension(width,height));
//			Musee.initDimension(width, height);
		}
		return panel;
	}

}
