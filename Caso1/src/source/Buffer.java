package source;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Buffer <T>
{
	/**
	 * Buffer en el que se almacenan los mensajes.
	 */
	private Queue<T> buffer;
	/**
	 * Tamaño definido del Buffer.
	 */
	private int tamanio;
	/**
	 * ArrayList que contiene los IDs de los Threads Clientes activos.
	 */
	private ArrayList<Long> identificadoresThreadsClientesActivos;
	/**
	 * Método constructor de la clase.
	 * @param tamanio int, Tamaño del buffer a establecer.
	 */
	public Buffer (int tamanio)
	{
		this.setTamanio(tamanio);
		buffer = new LinkedList<T>();
		identificadoresThreadsClientesActivos = new ArrayList<Long>();
	}
	
	public Queue<T> getBuffer() {
		return buffer;
	}

	public void setBuffer(Queue<T> buffer) {
		this.buffer = buffer;
	}

	public int getTamanio() {
		return tamanio;
	}

	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}
	
	public ArrayList<Long> getIdentificadoresThreadsClientesActivos() {
		return identificadoresThreadsClientesActivos;
	}

	public void setIdentificadoresThreadsClientesActivos(ArrayList<Long> identificadoresThreadsClientesActivos) {
		this.identificadoresThreadsClientesActivos = identificadoresThreadsClientesActivos;
	}

	public void almacenar (T i)throws ExceptionFullBuffer
	{
		synchronized (buffer) 
		{
			if(buffer.size() == tamanio)
			{
				throw new ExceptionFullBuffer("Buffer LLeno");
			}
			buffer.add(i);
//			System.out.println("Tamanio Buffer Post buffer.add(i): " + buffer.size());
		}
		synchronized (i) {
			try
			{
				System.out.println("pre wait.");
				i.wait();
				System.out.println("post wait.");
			}
			catch(InterruptedException e)
			{
				;
			}
		}
	}
	
	public T retirar()throws Exception
	{
		T i;
		synchronized (buffer)
		{
			if(buffer.peek() == null)
			{
				throw new Exception("Buffer vacio");
			}
			i = buffer.poll();
		}
		return i;
	}
	
	public void registrarNuevoThreadActivo(Long idThread)
	{
		synchronized (identificadoresThreadsClientesActivos) {
			identificadoresThreadsClientesActivos.add(idThread);
		}
	}
	
	public void retirarThreadActivo(Long idThread)
	{
		synchronized (identificadoresThreadsClientesActivos) {
			for(int i = 0; i < identificadoresThreadsClientesActivos.size(); i++) {
				if(identificadoresThreadsClientesActivos.get(i).equals(idThread))
				{
					identificadoresThreadsClientesActivos.remove(i);
				}
			}
		}
	}
	
	public Boolean evaluarEstadoThreadsClientesActivos()
	{
		synchronized (identificadoresThreadsClientesActivos) {
			if(identificadoresThreadsClientesActivos.size() > 0)
			{
				return true;
			}
			else
			{
				System.out.println("Ejecutó false");
				return false;
			}
		}
	}
}
