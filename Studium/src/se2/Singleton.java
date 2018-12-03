package se2;

public class Singleton {

	private static Singleton instance; 
	
	private Singleton() { // Damit nicht von au�en zugegriffen wird
		
	}
	
	public static Singleton getInstance() {
		
		if(instance == null) {
			instance = new Singleton();
		}
		return instance; 
	}
	
	public static void main(String[] args) {
		System.out.println(getInstance());
		System.out.println(getInstance()); //Gibt selbes Objekt zur�ck, da bereits schon erzeugt
		System.out.println(new Singleton());
		
	}
}


