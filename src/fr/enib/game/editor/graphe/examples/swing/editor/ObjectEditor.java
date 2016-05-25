/**
 * 
 */
package fr.enib.game.editor.graphe.examples.swing.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import fr.enib.game.app.GraphEditeur;
import fr.enib.game.editor.graphe.model.mxCell;
import fr.enib.game.editor.graphe.util.mxUtils;
import fr.enib.game.editor.graphe.view.mxCellState;

/**
 * @author Corentin Boleis
 *
 */
public class ObjectEditor extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2479341679005648401L;
	
	private JPanel panelInformation;
	
	private JPanel panelDessin;
	
	private JLabel labelImageName;
	
	/**
	 * 
	 */
	public ObjectEditor(){
		this.setLayout(new BorderLayout());
		this.add(getPanelDessin(), BorderLayout.CENTER);
		this.add(getLabelImageName(), BorderLayout.SOUTH);
		runDraw();
	}

	/**
	 * @return the panelInformation
	 */
	public JPanel getPanelInformation() {
		if(panelInformation == null){
			panelInformation = new JPanel();
		}
		return panelInformation;
	}

	/**
	 * @return the panelDessin
	 */
	public JPanel getPanelDessin() {
		if(panelDessin == null){
			
			panelDessin = new JPanel(){
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				/*
				 * (non-Javadoc)
				 * @see javax.swing.JComponent#paint(java.awt.Graphics)
				 */
				@Override
				public void paint(Graphics g){
					super.paint(g);
					Image image = getImage();
					if(image != null){
						g.drawImage(image, 0, 0,
								panelDessin.getWidth(),
								panelDessin.getHeight(),
								Color.WHITE , null);
					}
				}
			};
		}
		return panelDessin;
	}
	
    private void runDraw() {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                getPanelDessin().repaint();
            }
        };
        Timer timer = new Timer(500,al);
        timer.start();
    }
    
    private Image getImage(){
    	Object obj = GraphEditeur.get().getGraphComponent().getGraph().getSelectionCell();
    	if(obj==null)return null;
    	if(!(obj instanceof mxCell))return null;
    	mxCellState state = GraphEditeur.get().getGraphComponent().getGraph().getView().createState(obj);
    	String image = (String) state.getStyle().get("image");
    	if(image == null)return null;
    	Image imageI = mxUtils.loadImage(image);
    	if(imageI==null)return null;
    	int k = image.lastIndexOf("/");
    	if(k<0){
    		k = image.lastIndexOf("\\");
    	}
    	if(k<0){
    		this.getLabelImageName().setText(image);
    	}
    	else{
    		this.getLabelImageName().setText(image.substring(k+1, image.length()));
    	}
    	return imageI;
    }

	/**
	 * @return the labelImageName
	 */
	public JLabel getLabelImageName() {
		if(labelImageName==null){
			labelImageName= new JLabel();
		}
		return labelImageName;
	}

}
