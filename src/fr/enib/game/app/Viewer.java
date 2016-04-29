package fr.enib.game.app;

import java.awt.Cursor;
import java.awt.Frame ;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.util.Properties;

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

import fr.enib.game.monde.builder.Builder;
import fr.enib.game.monde.builder.Ihm;
import fr.enib.game.monde.builder.Monde;

/**
 * @author Ronan Morel
 *
 */
public class Viewer implements GLEventListener {
	
	private static Logger LOGGER = Logger.getLogger(Viewer.class);
	private  Builder fabrique ;
	private  Ihm ihm ;
	
	private static boolean stopOnCloseWindows = true;
	private static Frame frame;

	public final static int FRAME_WIDTH = 1024;
	public final static int FRAME_HEIGHT = 768;
	
	public static void main(String [] args){
		//on recupere les information de args
		
		if(args.length>0){
			String stopOnCloseWindowsString = args[0];
			try{
				stopOnCloseWindows = Boolean.parseBoolean(stopOnCloseWindowsString);
			}catch(Exception e){}
		}
		//configuration des fichiers de log
		Viewer viewer = new Viewer();
		
		// Creation de la surface d affichage
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		GLCanvas canvas = new GLCanvas(caps);
		
		// Creation de la fenetre
		frame = new Frame("Enib project : Game");
		frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		frame.add(canvas);
		frame.setVisible(true);
		
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		frame.setCursor(cursor);

		// by default, an AWT Frame doesn't do anything when you click
		// the close button; this bit of code will terminate the program when
		// the window is asked to close
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(stopOnCloseWindows)Ihm.quiter();
				stopSimulation();
			}	
		});

		canvas.addGLEventListener(viewer) ; 
		
		canvas.addMouseListener(viewer.ihm); 
		canvas.addMouseMotionListener(viewer.ihm); 
		canvas.addKeyListener(viewer.ihm); 
		canvas.requestFocus();
		
		FPSAnimator animator = new FPSAnimator(canvas, 60);
		animator.start();
	}
	
	/**
	 * arrete tous l'environement 3D
	 */
	private static void stopSimulation(){
		frame.dispose();
		//TODO ronan arréter proprement open gl
	}
	
	public Viewer(){
		fabrique = new Builder() ;
		ihm      = new Ihm() ;
		initLog();
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
		float t = (float)(System.currentTimeMillis()) ;
		Monde.get().display(drawable);
		Monde.get().actualiser(t) ; 
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

		float [] diffus = {1.0f,1.0f,1.0f,1.0f} ; 
		float [] position = {5.0f,1.0f,10.0f,1.0f} ;
		float [] ambient  = { 1.0f,1.0f,1.0f,1.0f} ; 
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE,diffus,0) ; 
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION,position,0) ; 
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT,ambient,0) ;

		fabrique.construire() ; 
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
		GL2 gl = drawable.getGL().getGL2() ; 
		GLU glu = new GLU() ;
		gl.glViewport(0,0,w,h) ; 
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION) ; 
		gl.glLoadIdentity() ; 
		glu.gluPerspective(60.0f,(float)w/h,0.1f,1000.f) ; 
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW) ; 
	}


}