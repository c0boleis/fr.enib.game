package fr.enib.game.monde.core;

import java.util.HashMap;

import fr.enib.game.monde.geo.Repere;
import fr.enib.game.monde.geo.Vec3;

/**
 * 
 * @author Ronan MOREL
 *
 */
public class Situable implements Observable{
	private String id ;

	public Repere repere = new Repere() ;

	public HashMap<String,Observer> observateurs = new HashMap<String,Observer>() ;

	private HashMap<String,Situable> esclaves = new HashMap<String,Situable>() ;

	private Situable maitre = null;

	public Situable(String i){
		this.id = i ; 
	}

	public String getId(){
		return id;
	}

	// ================================================================================

	@Override
	public void add(Observer obs){
		observateurs.put(obs.getId(), obs) ; 
	}

	@Override
	public void delete(String name){
		//TODO A COMPLETER 
	}

	@Override
	public void update(String aspect, Object value){
		for(Observer obs : observateurs.values()){
			obs.update(aspect, value, this) ;
		} 
	}

	// ================================================================================

	public void placer(Vec3 v){
		repere.placer(v);
		update(CtxPose.POSE,null) ; 
	}

	public void placer(float x, float y, float z){
		repere.placer(x,y,z);
		update(CtxPose.POSE,null);
	}

	public void orienter(float angleRadian){
		repere.orienter(angleRadian);
		update(CtxPose.POSE,null);
	}

	public void deplacer(float x, float y, float z){
		repere.deplacer(x,y,z);
		update(CtxPose.POSE,null);
	}

	/**
	 * 
	 * @param x deplacement en x
	 * @param y deplacement en y
	 * @param z deplacement en z
	 * @param kx coéficient de prise en compte de la composante en x du vecteur de diréction
	 * @param ky coéficient de prise en compte de la composante en y du vecteur de diréction
	 * @param kz coéficient de prise en compte de la composante en z du vecteur de diréction
	 */
	public void deplacerAxe(float x, float y, float z,float kx,float ky,float kz){
		repere.deplacerAxe(x,y,z, kx, ky, kz);
		update(CtxPose.POSE,null);
	}

	public void tournerGauche(float angleRadian){
		repere.tournerGauche(angleRadian);
		update(CtxPose.POSE,null);
	}

	public void tournerHaut(float angleRadian){
		repere.tournerHaut(angleRadian);
		update(CtxPose.POSE,null);
	}

	public void avancer(float l){
		repere.avancer(l);
		update(CtxPose.POSE,null);
	}

	public void avancerAxe(float l,float kx,float ky,float kz){
		repere.avancerAxe(l, kx, ky, kz);
		update(CtxPose.POSE,null);
	}

	public void monter(float h){
		repere.monter(h);
		update(CtxPose.POSE,null);
	}

	public void surGauche(float g){

	}

	public boolean ajouterEsclave(Situable s){
		if(!esclaves.containsKey(s.getId())){
			esclaves.put(s.getId(), s);
			//on indique a l'esclave qu'il a un maitre
			s.setMaitre(this);
			return true;

		}
		return false;
	}
	
	public HashMap<String,Situable> getEsclave(){
		HashMap<String,Situable> map = new HashMap<String,Situable>();
		map.putAll(esclaves);
		return map;
	}

	public boolean suprimerEsclave(Situable s){
		return esclaves.remove(s)!=null;
	}

	public boolean suprimerEsclave(String st){
		return esclaves.remove(st)!=null;
	}

	public void miseAJourEsclaves(){
		for(Situable s : esclaves.values()){
			s.getId();
			//TODO
		}
	}

	public Situable getMaitre() {
		return maitre;
	}

	public void setMaitre(Situable m) {
		this.maitre = m;
	}

	public Repere getRepere() {
		if(this.getMaitre()==null){
			// ce situable n'a pas de maitre donc on renvoi sont repere
			return repere;
		}
		else{
			Repere rep = repere.cloner();
			rep.accumuler(this.getMaitre().getRepere());
			return rep;
		}
	}
	
	public Vec3 getPositionRepere(){
		return repere.getPostiton();
	}
}
