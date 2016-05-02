package fr.enib.game.monde.geo;

import com.jogamp.opengl.GL2;

public class Repere {
	private Vec3 o ;
	private Vec3 u ; 
	private Vec3 v ;
	private Vec3 w ; 
	private double cap ; 
	private double capC ;

	public Repere(){
		o = new Vec3(0.0f,0.0f,0.0f) ; 
		u = new Vec3(1.0f,0.0f,0.0f) ; 
		v = new Vec3(0.0f,1.0f,0.0f) ;
		w = new Vec3(0.0f,0.0f,1.0f) ; 
		cap = 0.0 ;
		capC = -Math.PI/2.0;
	}

	public Repere(Vec3 position){
		o = new Vec3() ; 
		o.copier(position) ; 
		u = new Vec3(1.0f,0.0f,0.0f) ; 
		v = new Vec3(0.0f,1.0f,0.0f) ;
		w = new Vec3(0.0f,0.0f,1.0f) ; 
		cap = 0.0 ;
	}

	public Repere(Vec3 position, float orientation){
		cap = (double)orientation ;
		o = new Vec3() ; 
		o.copier(position) ; 
		u = new Vec3((float)Math.cos(cap), (float)Math.sin(cap)) ;
		v = new Vec3(-(float)Math.sin(cap), (float)Math.cos(cap)) ;
		w = new Vec3(0.0f,0.0f,1.0f) ; 

	}

	public Repere cloner(){
		Repere repere = new Repere() ; 
		repere.copier(this) ; 
		return repere ; 
	}

	public void copier(Repere repere){
		o.copier(repere.o) ; 
		u.copier(repere.u) ; 
		v.copier(repere.v) ; 
		w.copier(repere.w) ; 
		cap = repere.cap ; 
	}

	public Vec3 position(){return o ;}
	public Vec3 direction(){return u ;}
	public Vec3 gauche(){return v ;}
	public Vec3 verticale(){return w;}

	public void placer(Vec3 position){
		o.copier(position) ;
	}

	public void placer(float x, float y, float z){
		o.copier(x,y,z) ; 
	}

	public void orienter(){
		u.unitaire() ; 
		v.cross(w,u) ; 
		cap = Math.atan2((double)u.y, (double)u.x) ; 
	}

	public void orienter(float dir){
		cap = (double)dir ; 
		u.copier((float)Math.cos(cap),(float)Math.sin(cap),(float)Math.cos(capC)) ;
		v.copier(-(float)Math.sin(cap),(float)Math.cos(cap),(float)Math.cos(capC)) ;
		w.copier(0.0f,0.0f,1.0f) ; 
	}

	public void orienterHaut(float dir){
		capC = (double)dir ;
		u.copier((float)Math.cos(cap),(float)Math.sin(cap),(float)Math.cos(capC)) ;
		v.copier(-(float)Math.sin(cap),(float)Math.cos(cap),(float)Math.cos(capC)) ;
		w.copier(0.0f,0.0f,1.0f) ; 
	}

	public void deplacer(float du, float dv, float dw){
		o.accumuler(du,u) ;
		o.accumuler(dv,v) ; 
		o.accumuler(dw,w) ; 
	}
	
	/**
	 * 
	 * @param du deplacement en x
	 * @param dv deplacement en y
	 * @param dw deplacement en z
	 * @param kx coéficient de prise en compte de la composante en u du vecteur de diréction
	 * @param ky coéficient de prise en compte de la composante en v du vecteur de diréction
	 * @param kz coéficient de prise en compte de la composante en w du vecteur de diréction
	 */
	public void deplacerAxe(float du, float dv, float dw,float kx,float ky,float kz){
		o.accumulerAxe(du,u,kx,ky,kz) ;
		o.accumulerAxe(dv,v,kx,ky,kz) ; 
		o.accumulerAxe(dw,w,kx,ky,kz) ; 
	}

	public void deplacer(Vec3 t){
		o.accumuler(t.x,u) ; 
		o.accumuler(t.y,v) ; 
		o.accumuler(t.z,w) ; 
	}


	public void avancer(float l){
		o.accumuler(l,u)  ;
	}
	
	public void avancerAxe(float l,float kx,float ky,float kz){
		o.accumulerAxe(l,u, kx, ky, kz)  ;
	}

	public void reculer(float l){
		o.accumuler(-l,u) ;
	}
	
	public void reculerAxe(float l,float kx,float ky,float kz){
		o.accumulerAxe(-l,u, kx, ky, kz)  ;
	}
	
	public void monter(float h){
		o.accumuler(h,w) ;
	}

	public void descendre(float h){
		o.accumuler(-h,w) ;
	}

	public void surGauche(float d){
		o.accumuler(d,v)  ;
	}

	public void surDroite(float d){
		o.accumuler(-d,v) ;
	}

	public void tourner(float dTheta){
		orienter((float)cap+dTheta) ;
	}

	public void tournerGauche(float dTheta){
		orienter((float)cap+dTheta) ;
	}

	public void tournerHaut(float dTheta){
		orienterHaut((float) (capC+dTheta)) ;
	}

	public void tournerDroite(float dTheta){orienter((float)cap-dTheta) ;}

	public void push(GL2 gl){
		gl.glPushMatrix() ; 
		gl.glTranslatef(o.x, o.y, o.z) ; 
		gl.glRotatef((float)(cap * 180.0 /Math.PI),0.0f,0.0f,1.0f) ;
	}
	
	public void push(GL2 gl,Repere rep){
		gl.glPushMatrix() ; 
		double cosd = Math.cos(rep.cap);
		double sind = Math.sin(rep.cap);
		float cosf = (float) cosd;
		float sinf = (float) sind;
		if(Math.abs(sinf)<0.00001){
			sinf = 0.0f;
		}
		if(Math.abs(cosf)<0.00001){
			cosf = 0.0f;
		}
		float x = cosf*o.x-sinf*o.y+rep.getPostiton().x;
		float y = sinf*o.x+cosf*o.y+rep.getPostiton().y;
		gl.glTranslated(x, 
						y,
						o.z+rep.getPostiton().z) ;
		//ok
		gl.glRotatef((float)((cap +rep.cap)* 180.0 /Math.PI),0.0f,0.0f,1.0f) ;
	}

	public void pop(GL2 gl){
		gl.glPopMatrix() ; 
	}
	
	/**
	 * renvoi la position du repere
	 * @return {@link #o}
	 */
	public Vec3 getPostiton(){
		return o;
	}
	
	/**
	 * renvoi la direction du repere
	 * @return {@link #u}
	 */
	public Vec3 getDirection(){
		return u;
	}
	
	/**
	 * 
	 * @return {@link #cap}
	 */
	public double getCapHorizontal(){
		return cap;
	}
	
	/**
	 * 
	 * @return {@link #capC}
	 */
	public double getCapVerticale(){
		return capC;
	}

	public void accumuler(Repere rep) {

		o.accumuler(1.0f, rep.o);
//		u.accumuler(1.0f, rep.u);
//		v.accumuler(1.0f, rep.v);
//		w.accumuler(1.0f, rep.w);
		cap+=rep.cap;
		capC+=rep.capC;
		
	}
}
