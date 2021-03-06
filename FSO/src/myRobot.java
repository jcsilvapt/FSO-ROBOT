
public class myRobot {
	private final String ROBOTNAME = "EV3";
	
	public myRobot() {
		System.out.println("MyRobot");
	}
	
	public boolean OpenEV3(String name) {
		if(name.equalsIgnoreCase(ROBOTNAME)) {
			System.out.println("My Robot # Open **(" + name + ")");
			return true;
		}else {
			System.out.println("Granda falha");
		}
		return false;
	}
	
	public void CurvarDireita(int raio, int angulo) {
		System.out.println("A virar " + angulo + " graus num raio de " + raio + ".");
	}
	
	public void CurvarEsquerda(int raio, int angulo) {
		System.out.println("A virar " + angulo + " graus num raio de " + raio + ".");
	}
	
	public String Reta(int distancia) {
		if(distancia >= 0) {
			return "A andar " + distancia + ".";
		}else {
			return "A recuar " + Math.abs(distancia) + ".";
		}
	}
	
	public void Parar(boolean value) {
		if(value) {
			System.out.println("Parar!");
		}else {
			System.out.println("Parar assim que conseguir!");
		}
	}
	
	public String CloseEV3() {
		return "A desligar...";
	}
}
