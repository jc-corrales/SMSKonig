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
		synchronized (this) 
		{
			buffer.add(i);
			synchronized (i) {
				try
				{
					i.wait();
				}
				catch(InterruptedException e)
				{
					;
				}
				
			}
			
		}
//		synchronized (vacio) 
//		{
//			vacio.notify();
//		}
	}
	
	public T retirar()throws Exception
	{
		System.out.println("Threads activos: " + identificadoresThreadsClientesActivos.size());
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
		synchronized (this)
		{
			if(buffer.peek() != null)
			{
				throw new Exception("Buffer vacio");
			}
		}
		synchronized (this) {i = buffer.poll();}
//		synchronized (lleno) {lleno.notify();}
		return i;
	}
	
	public void registrarNuevoThreadActivo(Long idThread)
	{
		identificadoresThreadsClientesActivos.add(idThread);
	}
	
	public void retirarThreadActivo(Long idThread)
	{
		identificadoresThreadsClientesActivos.remove(idThread);
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
