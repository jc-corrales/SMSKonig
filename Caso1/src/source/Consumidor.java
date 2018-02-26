package source;

public class Consumidor extends Thread
{
	public final static Long TIMEOUT = (long)10000;
	
	private Long idThread;
	
	private Buffer<Mensaje<Integer>> buffer;
	
	private Integer iteracionActual;
	
	private Mensaje<Integer> mensaje;
	
	private Long time;
	
	public Consumidor(Buffer buffer, Long idThread)
	{
		this.buffer = buffer;
		this.iteracionActual = 0;
		time = (long)0;
		this.idThread = idThread;
	}
	
	
	@Override
	public void run()
	{
		time = System.currentTimeMillis();
		boolean timeout = false;
		while(!timeout)
		{
			try
			{
				System.out.println("Thread " + idThread + " obteniendo.");
				Mensaje<Integer> mensaje = buffer.retirar();
				mensaje.setContenido(mensaje.getContenido() + 1);
				synchronized (mensaje) {
					mensaje.notify();
				}
				time = System.currentTimeMillis();
			}
			catch(Exception e)
			{
				Long now = System.currentTimeMillis();
				if(now > (time+TIMEOUT))
				{
					timeout = true;
				}
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


	public Buffer getBuffer() {
		return buffer;
	}




	public void setBuffer(Buffer buffer) {
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
