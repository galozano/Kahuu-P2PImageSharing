package kahuuFotos.mundo;

import java.io.File;
import java.io.Serializable;

public class Estado implements Serializable
{
	//---------------------------------------------------------------------------
	// CONSTANTES
	//---------------------------------------------------------------------------
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String NADA = "NADA";
	
	public final static String BUSCANDO_FOTO = "BUSCANDO FOTO";
	
	public final static String BUSCANDO_TAGS = "BUSCANDO TAGS";
	
	public final static String BUSCANDO_SIMILITUD = "BUSCANDO SIMILITUD";
	
	public final static String SOLICITANDO_DESCARGAR_FOTO = "SOLICITANDO DESCARGAR FOTO";
	
	public final static String ENVIANDO_FOTO = "ENVIANDO";
	
	public final static String RECIBIENDO_FOTO = "RECIBIENDO";
	
	public final static String AGREGANDO_FOTO = "AGREGANDO FOTO";
	
	public final static String AGREGANDO_FOTO_DESCARGADA = "AGREGANDO FOTO DESCARGADA";
	
	//---------------------------------------------------------------------------
	// ATRIBUTOS
	//---------------------------------------------------------------------------
	
	private String mensajeSolicitado;
	private String tipoEstado;
	//private String nombrePeerConsultado;
	private File archivoComparacion;
	
	
	//---------------------------------------------------------------------------
	// CONSTRUCTORES
	//---------------------------------------------------------------------------
	public Estado(String mensajeSolicitado, String tipoEstado,
			 File archivoComparacion) {
		super();
		this.mensajeSolicitado = mensajeSolicitado;
		this.tipoEstado = tipoEstado;
		//this.nombrePeerConsultado = nombrePeerConsultado;
		this.archivoComparacion = archivoComparacion;
	}
	public Estado() {
		super();
		
	}
	
	//---------------------------------------------------------------------------
	// METODOS
	//---------------------------------------------------------------------------
	public String getMensajeSolicitado() {
		return mensajeSolicitado;
	}
	public void setMensajeSolicitado(String mensajeSolicitado) {
		this.mensajeSolicitado = mensajeSolicitado;
	}
	public String getTipoEstado() {
		return tipoEstado;
	}
	public void setTipoEstado(String tipoEstado) {
		this.tipoEstado = tipoEstado;
	}
	
	public File getArchivoCOmparacion() {
		return archivoComparacion;
	}
	public void setArchivoCOmparacion(File archivoCOmparacion) {
		this.archivoComparacion = archivoCOmparacion;
	}

	
	public String toString()
	{
		return "TIPO ESTADO:"+tipoEstado+" MENSAJE SOLICITADO:"+mensajeSolicitado;
	}
	
	
}
