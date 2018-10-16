
public class Gestor extends Thread {
	
	public static final byte ID = 1;
	
	private Comunicar inbox;
	private Comunicar gui;
	private Comunicar evitar;
	private Comunicar vaguear;
	
	private myRobot robot;
	
	/**
	 * Constructor, Inicializa as restantes caixa automaticamente
	 */
	public Gestor() {
		inbox 	= new Comunicar("gestor");
		gui 	= new Comunicar("gui");
		evitar 	= new Comunicar("evitar");
		vaguear = new Comunicar("vaguear");
		this.robot = new myRobot();
	}
	
	public void le(){
		String msg = inbox.receberMsg();
		System.out.println();
		System.out.println(msg);
	}
	
	private boolean robotConnect(String name) {
		if(robot.OpenEV3(name)){
			return true;
		}else {
			return false;
		}
	}
	
}
