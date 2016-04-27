package fr.enib.game.monde.core;

public class Vec3 {

	public float x = 0.0f ;
	public float y = 0.0f ; 
	public float z = 0.0f ;
	
	public Vec3(){
	}
	
	public Vec3(float x){
		this.x = x ;
	}
	
	public Vec3(float x, float y){
		this.x = x ;
		this.y = y ;
	}
		
	public Vec3(float x, float y, float z){
		this.x = x ;
		this.y = y ;
		this.z = z ; 
	} 
	
	public String toStr(){
		return "( " + String.valueOf(x) + " , " + String.valueOf(y) + " , " + String.valueOf(z) + " )" ;
	}

	public void fixer(float x, float y, float z){
		this.x = x ;
		this.y = y ; 
		this.z = z ; 
	}
	
	public void copier(Vec3 u){
		this.x = u.x ;
		this.y = u.y ; 
		this.z = u.z ;
	}
	
	public void copier(float x, float y, float z){
		this.x = x ;
		this.y = y ;
		this.z = z ;
	}
	
	
	public Vec3 cloner(){
		return new Vec3(x,y,z) ; 
	}
	
	public void deVers(Vec3 de, Vec3 vers){
		this.x = vers.x - de.x ;
		this.y = vers.y - de.y ;
		this.z = vers.z - de.z ;
	}

	
	public void plus(Vec3 u, Vec3 v){
		this.x = u.x + v.x ;
		this.y = u.y + v.y ;
		this.z = u.z + v.z ; 
	}
	
	public void moins(Vec3 u, Vec3 v){
		this.x = u.x - v.x ;
		this.y = u.y - v.y ;
		this.z = u.z - v.z ;
	}
	
	public void alpha(float k){
		x *= k ;
		y *= k ;
		z *= k ;
	}
	
	
	public void accumuler(float k, Vec3 u){
		x += k*u.x ;
		y += k*u.y ;
		z += k*u.z ; 
	}
	
	/**
	 * 
	 * @param k
	 * @param u vecteur de direction
	 * @param kx coéficient de prise en compte de la composante en x du vecteur de diréction
	 * @param ky coéficient de prise en compte de la composante en y du vecteur de diréction
	 * @param kz coéficient de prise en compte de la composante en z du vecteur de diréction
	 */
	public void accumulerAxe(float k, Vec3 u,float kx,float ky,float kz){
		x += k*u.x*kx ;
		y += k*u.y*ky ;
		z += k*u.z*kz ; 
	}
	
	public void cross(Vec3 u, Vec3 v){
		x = u.y*v.z - u.z*v.y ;
		y = u.z*v.x - u.x*v.z ;
		z = u.x*v.y - u.z*v.x ;
	}
	
	public float dot(Vec3 u){
		return x*u.x + y*u.y + z*u.z ;
	}
	
	public float norme(){
		return (float)Math.sqrt((float)(this.dot(this))) ;
	}
	
	public float norme2(){
		return this.dot(this) ;
	}

	public float distance(Vec3 v){
		return (float)(Math.sqrt( (double)((x-v.x)*(x-v.x) + (y-v.y)*(y-v.y) + (z-v.z)*(z-v.z) ))) ;
	}
	
	public void unitaire(){
		float d = norme() ; 
		if(d>0.00001)
			alpha(1.0f/d) ; 
	}

	public float normer(){
		float d = norme() ; 
		if(d>0.00001)
			alpha(1.0f/d) ; 
		return d ; 
	}
	
	public void tronquer(float dmax){
		float d = norme() ; 
		if(d>dmax)
			alpha(dmax/d) ;		
	}
}
