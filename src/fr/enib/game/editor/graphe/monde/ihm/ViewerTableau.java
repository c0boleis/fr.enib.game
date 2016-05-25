/**
 * 
 */
package fr.enib.game.editor.graphe.monde.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;



import fr.enib.game.editor.graphe.util.mxUtils;
import fr.enib.game.model.interfaces.ITableau;

/**
 * @author Corentin Boleis
 *
 */
public class ViewerTableau extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4208075286964487040L;
	
	private static ITableau tableau = null;
	
	private static JPanel panel = null;
	
	private static Image image = null;
	
	private static final ViewerTableau INSTANCE = new ViewerTableau();
	
	private ViewerTableau(){
		this.setLayout(new BorderLayout());
		this.add(getPanel(),BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * 
	 * @return {@link ViewerTableau}
	 */
	public static ViewerTableau get(){
		return INSTANCE;
	}

	/**
	 * @return the tableau
	 */
	public ITableau getTableau() {
		return tableau;
	}

	/**
	 * @param tab the tableau to set
	 */
	public static void setTableau(ITableau tab) {
		if(tab==null){
			return;
		}
		tableau = tab;
		image = mxUtils.loadImage(tableau.getUrlImage());
		int height = image.getHeight(panel);
		int width = image.getWidth(panel);
		System.err.println("h: "+height+",w: "+width);
		panel.setPreferredSize(new Dimension(width, height));
		INSTANCE.pack();
		panel.repaint();
	}

	private static JPanel getPanel() {
		if(panel==null){
			panel = new JPanel(){
				/**
				 * 
				 */
				private static final long serialVersionUID = -7056467373066954253L;

				/*
				 * (non-Javadoc)
				 * @see javax.swing.JComponent#paint(java.awt.Graphics)
				 */
				@Override
				public void paint(Graphics g){
					super.paint(g);
					if(image != null){
						g.drawImage(image, 0, 0,
								panel.getWidth(),
								panel.getHeight(),
								Color.WHITE , null);
					}
				}
			};
			panel.setPreferredSize(new Dimension(200, 200));
		}
		return panel;
	}

}
