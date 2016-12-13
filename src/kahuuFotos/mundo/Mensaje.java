package kahuuFotos.mundo;

import java.io.Serializable;



public class Mensaje implements Serializable
{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//---------------------------------------------------------------------------
	// CONSTANTES
	//---------------------------------------------------------------------------
	
	
	public final static String MENSAJE_ENVIO_FOTO = "ENVIANDO_FOTO";
	
	public final static String MENSAJE_ENVIO_SOLICITUD_FOTO = "	ENVIO SOLICITUD FOTO";
	
	public final static String MENSAJE_RECEPCION_SOLICITUD = "RECEPCION SOLICITUD FOTO";
	
	public final static String MENSAJE_RECEPCION_FOTO = "RECEPCION FOTO";
	
	public final static String MENSAJE_ENVIO_BUSQUEDA = "BUSQUEDA FOTO";
	
	
	//---------------------------------------------------------------------------
	// ATRIBUTOS
	//---------------------------------------------------------------------------
		
	private String tipoMensaje;
	private String peerSalida;
	private String peerLlegada;
	private String mensaje;
	
	private int numeroPaquete;

	
	
	//---------------------------------------------------------------------------
	// CONSTRUCTORES
	//---------------------------------------------------------------------------
		
	public Mensaje(String peerSalida, String peerLlegada, String mensaje,String tipoMensaje) {
		super();
		this.peerSalida = peerSalida;
		this.peerLlegada = peerLlegada;
		this.mensaje=mensaje;
		this.tipoMensaje=tipoMensaje;
		numeroPaquete = -1;
	}
	
	public Mensaje() {
		super();
	
	}
	
	//---------------------------------------------------------------------------
	// METODOS
	//---------------------------------------------------------------------------
	public String getPeerSalida() {
		return peerSalida;
	}
	public void setPeerSalida(String peerSalida) {
		this.peerSalida = peerSalida;
	}
	public String getPeerLlegada() {
		return peerLlegada;
	}
	public void setPeerLlegada(String peerLlegada) {
		this.peerLlegada = peerLlegada;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	
	
	public int getNumeroPaquete() {
		return numeroPaquete;
	}

	public void setNumeroPaquete(int numeroPaquete) {
		this.numeroPaquete = numeroPaquete;
	}

	public String toString()
	{
		return "{TIPO MENSAJE:"+tipoMensaje+" PEER SALIDA:"+peerSalida+" PEER LLEGADA:"+peerLlegada+" MENSAJE:"+mensaje + " PAQUETE:"+numeroPaquete + "}";
	}
	
	public int comparacion(Mensaje m)
	{
		int resp=-1;
		
		if(tipoMensaje.equals(MENSAJE_ENVIO_SOLICITUD_FOTO)&&m.getTipoMensaje().equals(MENSAJE_RECEPCION_SOLICITUD)
			&&peerSalida.equals(m.getPeerSalida())&&peerLlegada.equals(m.getPeerLlegada())	
			&&mensaje.equals(m.getMensaje()))
		{
			resp=0;
			
		}
		else if(tipoMensaje.equals(MENSAJE_ENVIO_FOTO)&&m.getTipoMensaje().equals(MENSAJE_RECEPCION_FOTO)
				&&peerSalida.equals(m.getPeerSalida())&&peerLlegada.equals(m.getPeerLlegada())	
				&&mensaje.equals(m.getMensaje())
				&&numeroPaquete == m.getNumeroPaquete())
			{
				System.out.println("IGUAL:" + m.getNumeroPaquete() + ":" + m.getTipoMensaje());
				System.out.println("IGUAL 2:" + getNumeroPaquete() + ":" + getTipoMensaje());

				
				resp=0;
				
			}
		
		else if(tipoMensaje.equals(MENSAJE_RECEPCION_SOLICITUD)&&m.getTipoMensaje().equals(MENSAJE_ENVIO_SOLICITUD_FOTO)
				&&peerSalida.equals(m.getPeerSalida())&&peerLlegada.equals(m.getPeerLlegada())	
				&&mensaje.equals(m.getMensaje()))
			{
			
			
				resp=0;
				
			}
			else if(tipoMensaje.equals(MENSAJE_RECEPCION_FOTO)&&m.getTipoMensaje().equals(MENSAJE_ENVIO_FOTO)
					&&peerSalida.equals(m.getPeerSalida())&&peerLlegada.equals(m.getPeerLlegada())	
					&&mensaje.equals(m.getMensaje())
					&&numeroPaquete == m.getNumeroPaquete())
				{
				
					System.out.println("IGUAL:" + m.getNumeroPaquete() + ":" + m.getTipoMensaje());
					System.out.println("IGUAL 2:" + getNumeroPaquete() + ":" + getTipoMensaje());
					resp=0;
					
				}
		
		
		return resp;
		
		
	}

}
