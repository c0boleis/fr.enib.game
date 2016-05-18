package fr.enib.game.monde.builder;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import fr.enib.game.model.Model;
import fr.enib.game.monde.objet.Avatar;

/**
 * 
 * @author Ronan MOREL
 *
 */
public class Launcher extends JFrame implements GLEventListener, MouseListener, MouseMotionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(Launcher.class);
	
	private static final String TITLE = "Enib project : GAME";

	final private int width = 1000;
	final private int height = 800;

	private int dx, dy;
    private int centerX, centerY;
	private int oldX, oldY;
    
	private GLCanvas canvas;
	private Builder builder;
   // private Robot robot;
    private GLU glu = new GLU() ;
	
    private boolean loadFromFile;
    private static final boolean ASK_SAVE = false;
    
	private boolean lockMouse;
	private Avatar avatar;

	private float [] diffus = {1.0f,1.0f,1.0f,1.0f} ; 
	private float [] position = {5.0f,1.0f,10.0f,1.0f} ;
	private float [] ambient  = { 1.0f,1.0f,1.0f,1.0f} ; 

	/**
	 * 
	 * @param loadFromFile si false, on charge les donnees depuis l'éditeur, sinon on charge depuis un fichier
	 */
	public Launcher(boolean loadfromFile) {
		super(TITLE);

		initLog();
		avatar = Avatar.get();
		lockMouse = true;
		
		centerX = (int)(width/2.0f);
		centerY = (int)(height/2.0f);
		
		if(loadfromFile) loadData();
		builder = new Builder();
		
		this.loadFromFile = loadfromFile;
		
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		canvas = new GLCanvas(caps);

		this.setName(TITLE);
		this.getContentPane().add(canvas);

		this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		canvas.addGLEventListener(this);
		canvas.addMouseListener(this); 
		canvas.addMouseMotionListener(this); 
		canvas.addKeyListener(this);
		canvas.requestFocusInWindow();
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				quitter();
			}	
		});

		/*try {
            robot = new Robot();
        } catch (final AWTException e) {
            e.printStackTrace();
        }*/

		oldX = 0;
		oldY = 0;
		
		FPSAnimator animator = new FPSAnimator(canvas, 60, true);
		animator.start();
		
	}
	
	private void initLog(){
		try {
			Properties logProperties = new Properties();
			InputStream read = getClass().getClassLoader().getResourceAsStream("fr/enib/game/app/log4j.properties");
			logProperties.load(read);
			PropertyConfigurator.configure(logProperties);
			((RollingFileAppender )Logger.getRootLogger().getAppender("R")).rollOver();
		} catch (Exception e) {
			System.err.println("Could not initialize logger");
		}
		LOGGER.info("Application start");
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		//float t = (float)(System.currentTimeMillis()) ;
		if(!Monde.actualisationEnCours){
			Monde.get().display(drawable);
			Monde.get().actualiser(); 
		}
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2() ; 
		gl.glEnable(GL.GL_DEPTH_TEST) ;
		gl.glEnable(GL2.GL_CULL_FACE) ; 

		gl.glEnable(GL2.GL_LIGHTING) ; 
		gl.glEnable(GL2.GL_LIGHT0) ; 

		gl.glEnable(GL2.GL_TEXTURE_2D) ; 

		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE,diffus,0) ; 
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION,position,0) ; 
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT,ambient,0) ;

		builder.construire(); 
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
		GL2 gl = drawable.getGL().getGL2() ; 
		//GLU glu = new GLU() ;
		gl.glViewport(0,0,w,h) ; 
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION) ; 
		gl.glLoadIdentity() ; 
		glu.gluPerspective(60.0f,(float)w/h,0.1f,1000.f) ; 
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW) ; 
	}
	
	public void processKeyEvent(KeyEvent e, boolean pressed) {
		//int indice ;

		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				if(pressed){
					if(lockMouse) lockMouse = false;
					else lockMouse = true;
				}
				break ; 
			case KeyEvent.VK_SPACE:
				quitter();
				//System.out.println(lockMouse);
				break;
			case KeyEvent.VK_Q:
				avatar.deplacerAxe(0.0f,0.25f,0.0f,1.0f,1.0f,0.0f) ; 
				break;
			case KeyEvent.VK_D:
				avatar.deplacerAxe(0.0f,-0.25f,0.0f,1.0f,1.0f,0.0f) ; 
				break;
			case KeyEvent.VK_UP:
				avatar.avancerAxe(0.25f,1.0f,1.0f,0.0f) ; 
				break;
			case KeyEvent.VK_DOWN:
				avatar.avancerAxe(-0.25f,1.0f,1.0f,0.0f) ; 
				break;
			case KeyEvent.VK_LEFT:
				avatar.deplacerAxe(0.0f,0.25f,0.0f,1.0f,1.0f,0.0f) ; 
				break;
			case KeyEvent.VK_RIGHT:
				avatar.deplacerAxe(0.0f,-0.25f,0.0f,1.0f,1.0f,0.0f) ; 
				break;
			case KeyEvent.VK_Z:
				avatar.avancerAxe(0.25f,1.0f,1.0f,0.0f) ; 
				break;
			case KeyEvent.VK_PAGE_UP:
				avatar.monter(0.1f);
				break;
			case KeyEvent.VK_S:
				avatar.avancerAxe(-0.25f,1.0f,1.0f,0.0f) ; 
				break;
			case KeyEvent.VK_PAGE_DOWN:
				avatar.monter(-0.1f);
				break;
			case KeyEvent.VK_A:
				avatar.tournerGauche((float)(Math.PI/180.0)) ;
				//LOGGER.info("dir : " + avatar.getDirectionRegard().x);
				break;
			case KeyEvent.VK_E:
				avatar.tournerGauche(-(float)(Math.PI/180.0)) ;
				//LOGGER.info("dir : " + avatar.getDirectionRegard().x);
				break;
			case KeyEvent.VK_U:
				break;
			default:
				//do nothing
				break;

		}
	}
	
	/*public void mouseLock(){
		if(lockMouse){
			if (robot != null){
	            robot.mouseMove(getLocation().x + centerX, getLocation().y + centerY);
	            moveFromRobot = true;
			}
		}
	}*/
	
	/**
	 * Chargement des données depuis un fichier
	 */
	public void loadData(){
		//JFileChooser j = new JFileChooser();
		//j.showOpenDialog(this);
		//File f = j.getSelectedFile();
		File f = new File("C:/Users/ronan/git/fr.enib.game/test.xml");
		if(f != null){
			boolean res = Model.get().importerModel(f);
			if(!res){
				LOGGER.error("Erreur sur l'importation du fichier");
				System.exit(-1);
			}
		}
		/*else{
			LOGGER.error("Erreur , pas de fichier en entré");
			System.exit(-1);
		}*/
	}
	
	/**
	 * Procedure de fermeture du programme
	 */
	public void quitter(){
		if(loadFromFile){
			if(ASK_SAVE){
				JFileChooser j = new JFileChooser();
				j.showSaveDialog(this);
				File f = j.getSelectedFile();
				
				if(f != null){
					boolean res = Model.get().sauvegarderModel(f);
					if(!res){
						LOGGER.error("Erreur sauvegarde du model");
						JOptionPane.showMessageDialog(this, "Erreur de sauvegarde du fichier", "Error", JOptionPane.ERROR_MESSAGE);
						int res1 = JOptionPane.showConfirmDialog(this, "Etes-vous sur de quitter sans sauvegarder ?");
						if(res1 != JOptionPane.YES_OPTION){
							return;
						}
					}
				}
				else{
					int res = JOptionPane.showConfirmDialog(this, "Etes-vous sur de quitter sans sauvegarder ?");
					if(res != JOptionPane.YES_OPTION){
						return;
					}
				}
			}
			LOGGER.info("Arret du programme");
			System.exit(0);
		}
		else{
			dispose();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		processKeyEvent(e, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		processKeyEvent(e, false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		dXY(e.getX(), e.getY());
		moveLookAvatar();
	}
	
	private void moveLookAvatar(){
		if ((dx < 0) && (dx > -10)){
			avatar.tournerGauche((float)(Math.PI/180.0)) ;
		}

		else if ((dx > 0) && (dx < 10)){
			avatar.tournerGauche(-(float)(Math.PI/180.0)) ;
		}

		if ((dy < 0) && (dy > -10)){
			avatar.tournerHaut((float)(Math.PI/180.0)) ;
		}

		else if ((dy > 0) && (dy < 10)){
			avatar.tournerHaut(-(float)(Math.PI/180.0)) ;
		}
		
	}
	
	public boolean isCentre(int x, int y){
		return x == centerX && y == centerY;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		/*if(!moveFromRobot){
			if(lockMouse){
				int x = e.getX();
				int y = e.getY();
				if(!isCentre(x, y)){
					dXY(x, y);
					moveLookAvatar();
					//mouseLock();
				}
			}
		}
		else{
			moveFromRobot = false;
		}*/
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!lockMouse) lockMouse = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	public void dXY(int x, int y){
		dx =  x - oldX ;
		dy = y - oldY ; 
		
		oldX = x;
		oldY = y;
		//System.out.println(dx + " -- " + dy);
	}
}