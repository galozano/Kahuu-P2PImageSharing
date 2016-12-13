package kahuuFotos.mundo;

import java.io.File;
import java.io.Serializable;

import net.jxta.id.ID;

public class Descarga implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int TAMANO_PAQUETE=10000;
	
	private int paquete;
	private long tamanoTotal;
	private String nombreImagen;
	private File archivoTemporal;
	private String tagPersonas;
	private ID pipeID;

	private String peerAgrego;
	
	public Descarga(int paquete, long tamanoTotal, String nombreImagen,
			File archivoTemporal, ID id,String peerAgrego) {
		super();
		this.paquete = paquete;
		this.tamanoTotal = tamanoTotal;
		this.nombreImagen = nombreImagen;
		this.archivoTemporal = archivoTemporal;
		this.peerAgrego=peerAgrego;
		
		
		pipeID=id;
	}
	public void setPeerAgrego(String peerAgrego) {
		this.peerAgrego = peerAgrego;
	}
	public ID getPipeID() {
		return pipeID;
	}
	public void setPipeID(ID pipeID) {
		this.pipeID = pipeID;
	}
	public int getPaquete() {
		return paquete;
	}
	public void setPaquete(int paquete) {
		this.paquete = paquete;
	}
	public long getTamanoTotal() {
		return tamanoTotal;
	}
	public void setTamanoTotal(long tamanoTotal) {
		this.tamanoTotal = tamanoTotal;
	}
	public String getNombreImagen() {
		return nombreImagen;
	}
	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}
	public File getArchivoTemporal() {
		return archivoTemporal;
	}
	public void setArchivoTemporal(File archivoTemporal) {
		this.archivoTemporal = archivoTemporal;
	}
	public void setTagPersonas(String personas)
	{
		tagPersonas=personas;
	}
	public void getTagPersonas(String personas)
	{
		tagPersonas=personas;
	}
	
	public int darProgreso()
	{
		return (int) (((paquete*TAMANO_PAQUETE)*100)/tamanoTotal);
	}
	
	
	
	public String toString()
	{
		return "Nombre imagen "+ nombreImagen+" - Ultimo paquete recibido "+paquete+" - Tamaño total "+tamanoTotal;
	}
	public String getPeerAgrego() {
		// TODO Auto-generated method stub
		return peerAgrego;
	}
	public boolean aunNoEstaTerminada() {
		// TODO Auto-generated method stub
		if(paquete*TAMANO_PAQUETE>=tamanoTotal)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	

}
