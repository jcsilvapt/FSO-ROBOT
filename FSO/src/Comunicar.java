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
	private String eusou;
	//private String path = "..\\coms\\";

	final int BUFFER_MAX = 30;
	
	/* Caixas */


	@SuppressWarnings("resource")
	public Comunicar(String nome) {
		caixaMsg = new File(/*"..\\coms\\" +*/ nome + ".dat");
		this.eusou = nome;

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
	
	public void descodificar(String msg) {
		String[] msgAux = msg.split(";");
		
		System.out.println(Arrays.toString(msgAux));
		switch(msgAux[0]) {
		}
	}
	
	public void enviarMsg(byte[] msg, String name) {
		//int size;
		//boolean toConnect = false;
		for(;;) {
			buffer.position(0);
			if(buffer.get() == 0) {
				break;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		buffer.position(0);
		for(int x = 0; x < msg.length; ++x) {
			buffer.put(msg[x]);
		}
		buffer.put((byte)0);
		for(int y = 0; y < name.trim().length(); ++y) {
			buffer.putChar(name.charAt(y));
		}
		buffer.putChar('\0');
		
		
		
		
		/*
		if(name.length() == 0) {
			buffer.put((byte) 1);
		}else {
			size = msg.length;
			buffer.put((byte) size);
			toConnect = true;
		}
		for(int i = 0; i < msg.length; i++) {
			buffer.put(msg[i]);
			if(i == (msg.length-1) && toConnect){
				buffer.put((byte) 0);
				for(int n = 0 ; n < name.length(); n++){
					buffer.putChar(name.charAt(n));
				}
				buffer.putChar('\0');
			}
			else if (i == (msg.length-1) && !toConnect) {
				buffer.put((byte) 0);
				buffer.putChar('\0');
			}
		}
		*/
	}
	
	
	
//	@Override
//	public void enviarMsg(byte[] msg) {
//		// FIXME Auto-generated method stub
//		this.buffer.position(0);
//		for(int i = 0; i < msg.length; i++) {
//			buffer.put(msg[i]);
//		}
//	}

	@Override
	public String receberMsg() {
		buffer.position(0);
		StringBuilder msg = new StringBuilder();
		char aux;
		byte var;
		boolean ptVirgula = false;
		while((var = buffer.get()) != 0) {
			if (!ptVirgula) {
				msg.append(var);
				ptVirgula = true;
			} else { msg.append(";" + var); }
		}
		while((aux = buffer.getChar()) != '\0') {
			if(ptVirgula) {
				msg.append(";" + String.valueOf(aux)); 
				ptVirgula = false;
			}
			else { msg.append(String.valueOf(aux)); }
		}
		buffer.put(0, (byte) 0);
		return msg.toString();
	}

	public void fecharCanal() {
		try {
			canal.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
