package source;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Principal
{
	public final static String PATH = "data/variables.txt";
	private static Buffer<Mensaje<Integer>> buffer;

	private static Servidor servidor;

	private static ArrayList<Productor> clientes;

	public static void main(String[] args0)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
			String line = br.readLine();
			if(line != null)
			{
				String[] parts = line.split(";");
				Integer tamanioBuffer = Integer.parseInt(parts[0]);
				Integer nConsumidores = Integer.parseInt(parts[1]);
				buffer = new Buffer<Mensaje<Integer>>(tamanioBuffer);
				servidor = new Servidor(nConsumidores, buffer);
			}
			line = br.readLine();
			Long contadorConsumidores = (long)0;
			clientes = new ArrayList<Productor>();
			while (line != null) {
				contadorConsumidores++;
				Productor productor = new Productor( (contadorConsumidores-1), buffer,  Integer.parseInt(line));
				clientes.add(productor);
				line = br.readLine();                
			}           
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		servidor.iniciarConsumidores();
		for(int i = 0; i < clientes.size(); i++)
		{
			clientes.get(i).start();
		}
	}
}
