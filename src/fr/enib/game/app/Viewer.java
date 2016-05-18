package fr.enib.game.app;

import fr.enib.game.monde.builder.Launcher;

/**
 * @author Ronan Morel
 *
 */
public class Viewer {
	
	public static void main(String [] args){
		boolean b = true;
		if(args != null && args.length >= 1){
			try{
				b = args[0].toLowerCase().equals("true");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		new Launcher(b);
	}

}