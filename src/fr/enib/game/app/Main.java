package fr.enib.game.app;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int res = choix();
		switch (res) {
			case 1:
				Viewer.main(null);
				break;
			case 2:
				GraphEditeur.main(null);
				break;
			case 3:
				System.exit(1);
				break;
			default:
				System.out.println("Aucun choix correcte, recommencez");
				System.out.println("--------------------------------");
				System.out.println("--------------------------------");
				System.out.println("");
				choix();
				break;
		}
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
}
