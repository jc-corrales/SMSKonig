package source;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Buffer <T>
{
	private Queue<T> buffer;
	private int tamanio;
	private ArrayList<Long> identificadoresThreadsClientesActivos;
//	private Object lleno, vacio;
	
	public Buffer (int tamanio)
	{
		this.setTamanio(tamanio);
		buffer = new LinkedList<T>();
		identificadoresThreadsClientesActivos = new ArrayList<Long>();
//		setLleno(new Object());
//		setVacio(new Object());
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

//	public Object getLleno() {
//		return lleno;
//	}
//
//	public void setLleno(Object lleno) {
//		this.lleno = lleno;
//	}
//
//	public Object getVacio() {
//		return vacio;
//	}
//
//	public void setVacio(Object vacio) {
//		this.vacio = vacio;
//	}
	
	public ArrayList<Long> getIdentificadoresThreadsClientesActivos() {
		return identificadoresThreadsClientesActivos;
	}

	public void setIdentificadoresThreadsClientesActivos(ArrayList<Long> identificadoresThreadsClientesActivos) {
		this.identificadoresThreadsClientesActivos = identificadoresThreadsClientesActivos;
	}

	public void almacenar (T i)throws ExceptionFullBuffer
	{
//		synchronized (lleno)
//		{
//			if(buffer.size() == tamanio)
//			{
//				Espera Pasiva
//				try
//				{
//					lleno.wait();
//				}
//				catch(InterruptedException e)
//				{
//					
//				}
//				Espera Activa
////				throw new ExceptionFullBuffer("Buffer Lleno.");
//			}
//		}
//		System.out.println("Tamanio Buffer Pre Synchronized: " + buffer.size());
		synchronized (buffer) 
		{
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
//		System.out.println("Tamanio Buffer Post synchronized: " + buffer.size());
//		synchronized (vacio) 
//		{
//			vacio.notify();
//		}
	}
	
	public T retirar()throws Exception
	{
//		System.out.println("Threads activos: " + identificadoresThreadsClientesActivos.size());
//		synchronized (vacio) {
//			while(buffer.size() == 0)
//			{
//				try
//				{
//					vacio.wait();
//				}
//				catch(InterruptedException e)
//				{
//					;
//				}
//			}
//		}
		T i;
		synchronized (buffer)
		{
			if(buffer.peek() != null)
			{
				throw new Exception("Buffer vacio");
			}
			i = buffer.poll();
		}
//		synchronized (lleno) {lleno.notify();}
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
			identificadoresThreadsClientesActivos.remove(idThread);
		}
	}
	
	public Boolean evaluarEstadoThreadsClientesActivos()
	{
		if(identificadoresThreadsClientesActivos.size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
