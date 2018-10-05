
public class Gestor {

	private myRobot robot;

	private Comunicar inbox;
	public static final String ID = "gestor";

	public Gestor() {
		inbox = new Comunicar(ID);
	}
	
	public Comunicar getInbox() {
		return inbox;
	}
	
	// Inicio para Movimenta��o

	/**
	 * Fun��o para movimentar o robot para a frente
	 */
	public void moveForward() {
		System.out.println(inbox.receberMsg());
	}

	
}
