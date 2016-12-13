package kahuuFotos.mundo;

import java.io.Serializable;
import java.util.ArrayList;

public class EstadoRecibido implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipoEstado;
	private String mensaje;
	private String nombrePeer;
	private ArrayList<Mensaje> listaMensajesRecibidos;
	private ArrayList<Mensaje> listaMensajesEnviados;
	public EstadoRecibido(String nombrePeer,String tipoEstado, String mensaje)  {
		super();
		this.tipoEstado = tipoEstado;
		this.mensaje = mensaje;
		this.nombrePeer=nombrePeer;

		listaMensajesRecibidos=new ArrayList<Mensaje>();
		listaMensajesEnviados=new ArrayList<Mensaje>();
	}
	public String getTipoEstado() {
		return tipoEstado;
	}
	public void setTipoEstado(String tipoEstado) {
		this.tipoEstado = tipoEstado;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public void agregarMensajeEnviado(Mensaje m)
	{
		listaMensajesEnviados.add(m);
	}
	public void agregarMensajeRecibido(Mensaje m)
	{
		listaMensajesRecibidos.add(m);
	}
	public String getNombrePeer() {
		return nombrePeer;
	}
	public void setNombrePeer(String nombrePeer) {
		this.nombrePeer = nombrePeer;
	}

	public void eliminarMensajeListaRecibidos(Mensaje m)
	{
		listaMensajesRecibidos.remove(m);

	}

	public void eliminarMensajeListaEnviados(Mensaje m)
	{
		listaMensajesEnviados.remove(m);

	}




	public ArrayList<Mensaje> getListaMensajesRecibidos() {
		return listaMensajesRecibidos;
	}
	public void setListaMensajesRecibidos(ArrayList<Mensaje> listaMensajesRecibidos) {
		this.listaMensajesRecibidos = listaMensajesRecibidos;
	}
	public ArrayList<Mensaje> getListaMensajesEnviados() {
		return listaMensajesEnviados;
	}
	public void setListaMensajesEnviados(ArrayList<Mensaje> listaMensajesEnviados) {
		this.listaMensajesEnviados = listaMensajesEnviados;
	}
	public void compararMensajes(EstadoRecibido estado)
	{
		ArrayList<Mensaje>mensajesEnviados= estado.getListaMensajesEnviados();
		ArrayList<Mensaje>mensajesRecibidos= estado.getListaMensajesRecibidos();

		for (int i = 0; i < listaMensajesRecibidos.size(); i++) {
			Mensaje m1=listaMensajesRecibidos.get(i);

			for (int j = 0; j < mensajesEnviados.size(); j++) {
				Mensaje m2=mensajesEnviados.get(j);

				int resulta=m1.comparacion(m2);
				if(resulta==0)
				{
					eliminarMensajeListaRecibidos(m1);
					estado.eliminarMensajeListaEnviados(m2);
				}


			}

		}



		for (int i = 0; i < listaMensajesEnviados.size(); i++) {
			Mensaje m1=listaMensajesEnviados.get(i);

			for (int j = 0; j < mensajesRecibidos.size(); j++) {
				Mensaje m2=mensajesRecibidos.get(j);

				int resulta=m1.comparacion(m2);
				
				if(resulta==0)
				{
					eliminarMensajeListaEnviados(m1);
					estado.eliminarMensajeListaRecibidos(m2);
				}


			}

		}






	}
	public Mensaje buscarDescargaIncompleta() {
		for (int i = 0; i < listaMensajesEnviados.size(); i++) {
			Mensaje m1=listaMensajesEnviados.get(i);
			if(m1.getTipoMensaje().equals(Mensaje.MENSAJE_ENVIO_SOLICITUD_FOTO))
			{
				return m1;
			}
		}

		for (int i = 0; i < listaMensajesRecibidos.size(); i++) {
			Mensaje m1=listaMensajesRecibidos.get(i);
			if(m1.getTipoMensaje().equals(Mensaje.MENSAJE_RECEPCION_SOLICITUD))
			{
				return m1;
			}
		}
		return null;
	}

	
	public String toString()
	{
		
		String mensaje = "";
		for (int i = 0; i < listaMensajesEnviados.size(); i++) 
		{
			mensaje += listaMensajesEnviados.get(i).toString();
		}
		
		for (int i = 0; i < listaMensajesRecibidos.size(); i++) 
		{
			mensaje += listaMensajesRecibidos.get(i).toString();
		}
		
		return mensaje;
		
	}

}
