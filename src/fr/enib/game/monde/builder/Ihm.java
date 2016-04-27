package fr.enib.game.monde.builder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.apache.log4j.Logger;

import fr.enib.game.monde.object.Avatar;


public class Ihm implements MouseListener, MouseMotionListener, KeyListener {
	
	private static Logger LOGGER = Logger.getLogger(Ihm.class);
	private int oldX ; 
	private int oldY ; 
	private int dx ;
	private int dy ;
	
	public static void quiter(){
		LOGGER.info("Arret du programme");
		System.exit(0);
	}

	public void dXY(MouseEvent e){
		int x ; 
		int y ; 
		x = e.getX() ;
		y = e.getY() ; 
		dx = x - oldX ;
		dy = y - oldY ; 
		oldX = x ; 
		oldY = y ; 
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e){

		dXY(e) ;
		if ((dx < 0) && (dx > -10)){
			Avatar.get().tournerGauche((float)(Math.PI/180.0)) ;
		}

		else if ((dx > 0) && (dx < 10)){
			Avatar.get().tournerGauche(-(float)(Math.PI/180.0)) ;
		}

		if ((dy < 0) && (dy > -10)){
			Avatar.get().tournerHaut((float)(Math.PI/180.0)) ;
		}

		else if ((dy > 0) && (dy < 10)){
			Avatar.get().tournerHaut(-(float)(Math.PI/180.0)) ;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		//do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//do nothing
		e.consume();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//do nothing
	}

	// ============================================================

	public void processKeyEvent(KeyEvent e, boolean pressed) {
		//int indice ;

		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			quiter();
			break ; 
		case KeyEvent.VK_Q:
			Avatar.get().deplacerAxe(0.0f,0.25f,0.0f,1.0f,1.0f,0.0f) ; 
			break;
		case KeyEvent.VK_D:
			Avatar.get().deplacerAxe(0.0f,-0.25f,0.0f,1.0f,1.0f,0.0f) ; 
			break;
		case KeyEvent.VK_UP:
			Avatar.get().avancerAxe(0.25f,1.0f,1.0f,0.0f) ; 
			break;
		case KeyEvent.VK_DOWN:
			Avatar.get().avancerAxe(-0.25f,1.0f,1.0f,0.0f) ; 
			break;
		case KeyEvent.VK_LEFT:
			Avatar.get().deplacerAxe(0.0f,0.25f,0.0f,1.0f,1.0f,0.0f) ; 
			break;
		case KeyEvent.VK_RIGHT:
			Avatar.get().deplacerAxe(0.0f,-0.25f,0.0f,1.0f,1.0f,0.0f) ; 
			break;
		case KeyEvent.VK_Z:
			Avatar.get().avancerAxe(0.25f,1.0f,1.0f,0.0f) ; 
			break;
		case KeyEvent.VK_PAGE_UP:
			Avatar.get().monter(0.1f);
			break;
		case KeyEvent.VK_S:
			Avatar.get().avancerAxe(-0.25f,1.0f,1.0f,0.0f) ; 
			break;
		case KeyEvent.VK_PAGE_DOWN:
			Avatar.get().monter(-0.1f);
			break;
		case KeyEvent.VK_A:
			Avatar.get().tournerGauche((float)(Math.PI/180.0)) ;
			break;
		case KeyEvent.VK_E:
			Avatar.get().tournerGauche(-(float)(Math.PI/180.0)) ;
			break;
		case KeyEvent.VK_U:
			break;
		default:
			//do nothing
			break;

		}
	}

	@Override
	public void keyTyped(KeyEvent e){

	}

	@Override
	public void keyPressed(KeyEvent e){
		processKeyEvent(e,true) ; 
	}

	@Override
	public void keyReleased(KeyEvent e){
		processKeyEvent(e,false) ;    
	}
}