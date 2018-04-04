package source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProtocoloCliente
{
	public final static String OK = "OK";
	public final static String ERROR = "ERROR";
	public final static String ESTADO = "ESTADO";
	public final static String ALGORITMOS = "ALGORITMOS";
	public final static String HOLA = "HOLA";
	public final static String INICIO = "INICIO";
	public final static String CERTCLNT = "CERTCLNT";
	public final static String CERTSRV = "CERTSRV";
	public final static Integer FINALSTATE = 5;
	
	private static Socket socket;
	private static byte[] certificado;

	public static void procesar(//BufferedReader pIn,PrintWriter pOut
			) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter output = new PrintWriter(socket.getOutputStream(),true);
		String inputLine, outputLine;
		int estado = 0;
		output.println(HOLA);
		while (estado < FINALSTATE && (inputLine = input.readLine()) != null) {
			switch (estado) {
			case 0:
				if (!inputLine.equalsIgnoreCase(INICIO)) {
					outputLine = "ERROR-EsperabaHola";
					estado = 0;
					break;
				}
				String respuesta = obtenerAlgoritmos();
				outputLine = respuesta;
				estado++;
				break;
			case 1:
				String[] entrada1 = inputLine.split(":"); 
				if(!(entrada1[0].equals(ESTADO) &&entrada1[1].equals(OK)))
				{
					outputLine = "ERROR-EsperabaHola";
					estado = 0;
					break;
				}
				else if(entrada1[0].equals(ESTADO) &&entrada1[1].equals(ERROR))
				{
					outputLine = "ERROR-EsperabaHola";
					estado = 0;
					break;
				}
				output.println(CERTCLNT);
				outputLine = generarCertificado().toString();
				estado++;
				break;
			case 2:
				String[] entrada2 = inputLine.split(":");
				if(!(entrada2[0].equals(ESTADO) &&entrada2[1].equals(OK)))
				{
					outputLine = "ERROR-EsperabaHola";
					estado = 0;
					break;
				}
				else if(entrada2[0].equals(ESTADO) && entrada2[1].equals(ERROR))
				{
					outputLine = "ERROR-EsperabaHola";
					estado = 0;
					break;
				}
				inputLine = input.readLine();
				if(!inputLine.equals(CERTSRV))
				{
					outputLine = "ERROR-EsperabaHola";
					estado = 0;
					break;
				}
				inputLine = input.readLine();
				byte[] certificado = inputLine.getBytes();
				if(revisarCertificado(certificado))
				{
					outputLine = ESTADO + ":" + OK;
				}
				else
				{
					outputLine = ESTADO + ":" + ERROR;
				}
				estado++;
				break;
			case 3:
				try
				{
					if(!inputLine.equals(INICIO))
					{
						outputLine = "ERROR-EsperabaHola";
						estado = 0;
						break;
					}
					//TODO ACTO 1
					output.println("");
					//TODO ACTO 2
					outputLine = "";
					estado++;
					break;
				}
				catch(Exception e)
				{
					outputLine = ERROR;
					estado = FINALSTATE;
				}
				break;
			case 4:
				try
				{
					String[] entrada3 = inputLine.split(":");
					if(!(entrada3[0].equals(ESTADO) && entrada3[1].equals(OK)))
					{
						outputLine = "ERROR-EsperabaHola";
						estado = 0;
						break;
					}
					outputLine = "";
					estado++;
					break;
				}
				catch(Exception e)
				{
					estado++;
					outputLine = ERROR;
					break;
				}
			default:
				outputLine = "ERROR";
				estado = 0;
				break;
			}
			output.println(outputLine);
		}
	}
	
	public static String obtenerAlgoritmos()
	{
		//TODO implementar m�todo para obtener los algoritmos necesarios.
		return "";
	}
	
	public static byte[] generarCertificado()
	{
		//TODO implementar m�todo para obtener los algoritmos necesarios.
		byte[] respuesta = new byte[2];

		return respuesta;
	}
	
	public static boolean revisarCertificado (byte[] certificado)
	{
		//TODO implementar m�todo para revisar la veracidad del certificado.
		return true;
	}
	
	public static byte[] generarLlaveSimetrica()
	{
		//TODO implementar m�todo para generar llave sim�trica
		return "hola".getBytes();
	}
	
}
