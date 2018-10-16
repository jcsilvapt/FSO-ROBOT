
public interface iMensagem {
	
	public static byte GESTOR 	= 1;
	public static byte GUI 		= 2;
	public static byte VAGUEAR 	= 3;
	public static byte EVITAR 	= 4;
	
	/* Accoes */
	public static byte AVANCAR 	= 5;
	public static byte RECUAR 	= 6;
	public static byte ESQ	    = 7;
	public static byte DRT     	= 8;
	public static byte PARAR 	= 9;
	
	public static byte STOQUE	= 10;
	
	/* Ligações */
	public static byte OPEN		= 11;
	public static byte CLOSE	= 12;
	
	void enviarMsg(byte[] msg, String name);
	
	String receberMsg();

	//void descodificar(String msg);
	
}
