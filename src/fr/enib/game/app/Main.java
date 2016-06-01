package fr.enib.game.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -555378244760233777L;

	private JButton button3D;

	private JButton buttonEditeurGraphe;

	private JButton buttonQuiter;

	private JPanel panel;

	private static  Image fond = null;

	private static final Font font =  new Font("Courier New", Font.ITALIC, 20);
	
	private static final FocusListener focusListener = new FocusListener() {
		
		@Override
		public void focusLost(FocusEvent e) {
			frame.setCursor(Cursor.getDefaultCursor());
		}
		
		@Override
		public void focusGained(FocusEvent e) {
			 frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) );
			
		}
	};
	
	private static final MouseListener mouseListenerButton = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {}
		
		@Override
		public void mousePressed(MouseEvent e) {}
		
		@Override
		public void mouseExited(MouseEvent e) {
			frame.setCursor(Cursor.getDefaultCursor());
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			 frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) );
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {}
	};
	
	private static final MouseListener mouseListenerPanel = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {}
		
		@Override
		public void mousePressed(MouseEvent e) {}
		
		@Override
		public void mouseExited(MouseEvent e) {
			 frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) );
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			 frame.setCursor(Cursor.getDefaultCursor());
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {}
	};
	
	private static Main frame;

	private Main(){
		super();
		frame = this;
		this.setTitle("Virtual Art");
		this.setLayout(new BorderLayout());
		this.add(getPanel(),BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getPanel().repaint();
		this.setUndecorated(true);
		//on centre la frame
		this.pack();
		this.setLocation(-this.getWidth()/2, -this.getHeight()/2);
		this.setLocationRelativeTo(null);

	}

	public static void main(String[] args) {
		//		int res = choix();
		//		switch (res) {
		//			case 1:
		//				Viewer.main(null);
		//				break;
		//			case 2:
		//				GraphEditeur.main(null);
		//				break;
		//			case 3:
		//				System.exit(1);
		//				break;
		//			default:
		//				System.out.println("Aucun choix correcte, recommencez");
		//				System.out.println("--------------------------------");
		//				System.out.println("--------------------------------");
		//				System.out.println("");
		//				choix();
		//				break;
		//		}
		Main main = new Main();
		main.setVisible(true);
	}


	private static int choix(){
		System.out.println("Choix : ");
		System.out.println("1 - Démarrer l'environnement virtuel");
		System.out.println("2 - Lancer l'editeur de graphe");
		System.out.println("3 - Quittez");

		Scanner in= new Scanner(System.in);
		int res = in.nextInt();
		in.close();
		return res;
	}

	public JButton getButtonQuiter() {
		if(buttonQuiter == null){
			buttonQuiter = new JButton();
			buttonQuiter.setText("Quiter");
			
			//gestion du tyle du button
			buttonQuiter.setForeground(Color.BLACK);
			buttonQuiter.setFont(font);
			buttonQuiter.setBackground(Color.WHITE);
			Border line = new LineBorder(Color.BLACK);
			Border margin = new EmptyBorder(5, 15, 5, 15);
			Border compound = new CompoundBorder(line, margin);
			buttonQuiter.setBorder(compound);
			
			//gestion du comportement du button
			buttonQuiter.addMouseListener(mouseListenerButton);
			buttonQuiter.setFocusable(false);
			buttonQuiter.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.exit(1);
				}
			});
		}
		return buttonQuiter;
	}

	public JButton getButtonEditeurGraphe() {
		if(buttonEditeurGraphe == null){
			buttonEditeurGraphe = new JButton();
			buttonEditeurGraphe.setText("Editeur de Graph");
			
			//gestion du tyle du button
			buttonEditeurGraphe.setForeground(Color.BLACK);
			buttonEditeurGraphe.setFont(font);
			buttonEditeurGraphe.setBackground(Color.WHITE);
			Border line = new LineBorder(Color.BLACK);
			Border margin = new EmptyBorder(5, 15, 5, 15);
			Border compound = new CompoundBorder(line, margin);
			buttonEditeurGraphe.setBorder(compound);
			
			//gestion du comportement du button
			buttonEditeurGraphe.addMouseListener(mouseListenerButton);
			buttonEditeurGraphe.setFocusable(false);
			buttonEditeurGraphe.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();
					GraphEditeur.main(null);
				}
			});
		}
		return buttonEditeurGraphe;
	}

	public JButton getButton3D() {
		if(button3D == null){
			button3D = new JButton();
			button3D.setText("Musée 3D");
			
			//gestion du tyle du button
			button3D.setForeground(Color.BLACK);
			button3D.setFont(font);
			button3D.setBackground(Color.WHITE);
			Border line = new LineBorder(Color.BLACK);
			Border margin = new EmptyBorder(5, 15, 5, 15);
			Border compound = new CompoundBorder(line, margin);
			button3D.setBorder(compound);
			button3D.setOpaque(false);
			button3D.setContentAreaFilled(true);
			button3D.setBorderPainted(false);
			
			//gestion du comportement du button
			button3D.addMouseListener(mouseListenerButton);
			button3D.setFocusable(false);
			button3D.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();
					Viewer.main(null);
				}
			});
		}
		return button3D;
	}

	public JPanel getPanel() {
		if(panel == null){
			panel=  new JPanel(){

				/**
				 * 
				 */
				private static final long serialVersionUID = 8844897109892366793L;

				public void paint(Graphics g){
					super.paint(g);
					if(fond==null){
						try {
							fond =ImageIO.read(Main.class.getResourceAsStream("image_menu.jpg"));
						} catch (IOException e) {

						}	
					}
					g.drawImage(fond, 0, 0, 800, 550, null);
					getButton3D().repaint();
					getButtonEditeurGraphe().repaint();
					getButtonQuiter().repaint();
				}
			};
			
			panel.setLayout(new FlowLayout());
			panel.add(getButton3D());
			panel.add(getButtonEditeurGraphe());
			panel.setPreferredSize(new Dimension(800,550));
			panel.add(getButtonQuiter());
			panel.addMouseListener(mouseListenerPanel);

		}
		return panel;
	}
}