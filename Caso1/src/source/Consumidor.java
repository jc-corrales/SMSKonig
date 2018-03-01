package source;

public class Consumidor extends Thread
{
	public final static Long TIMEOUT = (long)10000;
	
	private Long idThread;
	
	private Buffer<Mensaje<Integer>> buffer;
	
	private Integer iteracionActual;
	
	private Mensaje<Integer> mensaje;
	
	private Long time;
	
	public Consumidor(Buffer<Mensaje<Integer>> buffer, Long idThread)
	{
		this.buffer = buffer;
		this.iteracionActual = 0;
		time = (long)0;
		this.idThread = idThread;
	}
	
	
	@Override
	public void run()
	{
//		time = System.currentTimeMillis();
		while(buffer.evaluarEstadoThreadsClientesActivos())
		{
			try
			{
//				System.out.println("Thread " + idThread + " obteniendo.");
				Mensaje<Integer> mensaje = buffer.retirar();
//				System.out.println("Thread " + idThread + " modificando mensaje: " + mensaje.getTitulo());
				mensaje.setContenido(mensaje.getContenido() + 1);
				synchronized (mensaje) {
					mensaje.notify();
				}
//				time = System.currentTimeMillis();
			}
			catch(Exception e)
			{
//				Long now = System.currentTimeMillis();
//				if(now > (time+TIMEOUT))
//				{
//					timeout = true;
//				}
//				System.out.println(e.getMessage());
			}
		}
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


	public Long getTime() {
		return time;
	}


	public void setTime(Long time) {
		this.time = time;
	}
}
