import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class Comunicar implements iMensagem {

	File caixaMsg;
	FileChannel canal;
	MappedByteBuffer buffer;

	final int BUFFER_MAX = 30;
	
	/* Caixas */
	private static byte GESTOR 	= 1;
	private static byte GUI 	= 2;
	private static byte VAGUEAR = 3;
	private static byte EVITAR 	= 4;
	
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

	public Comunicar(String nome) {
		caixaMsg = new File(nome + ".dat");

		try {
			canal = new RandomAccessFile(caixaMsg, "rw").getChannel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			buffer = canal.map(FileChannel.MapMode.READ_WRITE, 0, BUFFER_MAX);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void codificar(byte[] cmd) {
		
	}

	@Override
	public void enviarMsg(byte[] msg) {
		// FIXME Auto-generated method stub
		this.buffer.position(0);
		for(int i = 0; i < msg.length; i++) {
			buffer.put(msg[i]);
		}
		receberMsg();
	}

	@Override
	public String receberMsg() {
		for(int i = 0; i < 3; i++) {
			System.out.println(buffer.get(i));
		}
		return null;
	}

	public void fecharCanal() {
		try {
			canal.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
