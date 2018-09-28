
public class myRobot {
	private final String ROBOTNAME = "EV";
	
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
	
	public String CurvarDireita(int raio, int angulo) {
		return "A virar " + String.valueOf(angulo) + " graus num raio de " + String.valueOf(raio) + ".";
	}
	
	public String CurvarEsquerda(int raio, int angulo) {
		return "A virar " + String.valueOf(angulo+90) + " graus num raio de " + String.valueOf(raio) + ".";
	}
	
	public String Reta(int distancia) {
		if(distancia >= 0) {
			return "A andar " + String.valueOf(distancia) + ".";
		}else {
			return "A recuar " + String.valueOf(distancia) + ".";
		}
	}
	
	public boolean Parar(boolean value) {
		if(value) {
			System.out.println("Parar!");
		}else {
			System.out.println("Parar assim que conseguir!");
		}
		return value;
	}
	
	public String CloseEV3() {
		return "A desligar...";
	}
}
