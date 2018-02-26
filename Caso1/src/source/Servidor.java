package source;


public class Servidor
{
	private Consumidor[] consumidores;
	
	private Buffer<Mensaje<Integer>> buffer;
	
	public Servidor(Integer nConsumidores, Buffer<Mensaje<Integer>> pBuffer)
	{
		consumidores = new Consumidor[nConsumidores];
		this.buffer = pBuffer;
		for(int i = 0; i < consumidores.length; i++)
		{
			consumidores[i] = new Consumidor(buffer, (long) (i+1000));
		}
	}
	
	
	public Consumidor[] getConsumidores() {
		return consumidores;
	}

	public void setConsumidores(Consumidor[] consumidores) {
		this.consumidores = consumidores;
	}


	public Buffer<Mensaje<Integer>> getBuffer() {
		return buffer;
	}


	public void setBuffer(Buffer<Mensaje<Integer>> buffer) {
		this.buffer = buffer;
	}
	
	public void iniciarConsumidores()
	{
		for(int i = 0; i < consumidores.length; i++)
		{
			consumidores[i].start();
		}
	}
	
}
