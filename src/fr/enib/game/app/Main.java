package fr.enib.game.app;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -555378244760233777L;

	private JButton button3D;
	
	private JButton buttonEditeurGraphe;
	
	private JButton buttonQuiter;
	
	private Main(){
		super();
		this.setTitle("Virtual Art");
		this.setLayout(new FlowLayout());
		this.add(getButton3D());
		this.add(getButtonEditeurGraphe());
		this.add(getButtonQuiter());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		
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
}