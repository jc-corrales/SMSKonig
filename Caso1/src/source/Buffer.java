package source;

//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Buffer <T>
{
	private Queue<T> buffer;
	private int tamanio;
//	private Object lleno, vacio;
	
	public Buffer (int tamanio)
	{
		this.setTamanio(tamanio);
		buffer = new LinkedList<T>();
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
}
