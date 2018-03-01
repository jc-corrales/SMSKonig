package source;

import java.util.Random;

public class Productor extends Thread
{
	private Long idThread;
	
	private Buffer<Mensaje<Integer>> buffer;
	
	private Integer nIteraciones;
	
	private Integer iteracionActual;
	
	private Mensaje<Integer> mensaje;
	
	public Productor(Long id, Buffer<Mensaje<Integer>> buffer, Integer nIteraciones)
	{
		this.idThread = id;
		this.buffer = buffer;
		this.setnIteraciones(nIteraciones);
		this.iteracionActual = 0;
		buffer.registrarNuevoThreadActivo(idThread);
	}
	
	
	@Override
	public void run()
	{
//		System.out.println("Thread: " + idThread + ", nIteraciones" + nIteraciones);
		Random random = new Random();
		for(int i = 0; i < nIteraciones; i++)
		{
			Integer contenido = random.nextInt();
			String titulo = "Thread " + idThread + ", Mensaje " + i;
			Mensaje<Integer> mensaje = new Mensaje<Integer>(contenido,titulo);
			boolean exito = false;
			while(!exito)
			{
				try {
					System.out.println("Thread " + idThread + " almacenando, intento: " + iteracionActual);
					buffer.almacenar(mensaje);
					exito = true;
					iteracionActual++;
				} catch (ExceptionFullBuffer e) {
					System.out.println("Thread: " + idThread + ", " + e.getMessage());
					Thread.yield();
					// TODO Auto-generated catch block
//					e.printStackTrace();
				} 
			}
		}
		buffer.retirarThreadActivo(idThread);
		System.out.println("Termino Thread: " + idThread);
	}




	public Long getIdThread() {
		return idThread;
	}


	public void setIdThread(Long idThread) {
		this.idThread = idThread;
	}


	public Buffer<Mensaje<Integer>> getBuffer() {
		return buffer;
	}




	public void setBuffer(Buffer<Mensaje<Integer>> buffer) {
		this.buffer = buffer;
	}


	public Integer getnIteraciones() {
		return nIteraciones;
	}


	public void setnIteraciones(Integer nIteraciones) {
		this.nIteraciones = nIteraciones;
	}


	public Integer getIteracionActual() {
		return iteracionActual;
	}


	public void setIteracionActual(Integer iteracionActual) {
		this.iteracionActual = iteracionActual;
	}


	public Mensaje<Integer> getMensaje() {
		return mensaje;
	}


	public void setMensaje(Mensaje<Integer> mensaje) {
		this.mensaje = mensaje;
	}
}
