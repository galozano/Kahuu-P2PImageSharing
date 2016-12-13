/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kahuuFotos.adv;

import net.jxta.document.Advertisement;
import net.jxta.id.ID;
import net.jxta.protocol.PipeAdvertisement;

/**
 * 
 * @author gustavolozano
 *
 */
public abstract class AdvertisementRecurso extends Advertisement 
{
	//------------------------------------------------------------------------------------------------------------------------------
	// Constantes
	//------------------------------------------------------------------------------------------------------------------------------

	private final static String advertisementType = "AdvertisementRecurso";

	//------------------------------------------------------------------------------------------------------------------------------
	// Atributos
	//------------------------------------------------------------------------------------------------------------------------------

	private String nombreAdvertisement;

	private String nombreFoto;

	private String tags;

	private String tiempoCreacion;

	private PipeAdvertisement pipeAdv = null;
	
	private String sensible;
	
	private String peerAgrego;

	//------------------------------------------------------------------------------------------------------------------------------
	// MŽtodos
	//------------------------------------------------------------------------------------------------------------------------------

	public static String getAdvertisementType()
	{
		return advertisementType;
	}

	@Override
	public ID getID()
	{
		return ID.nullID;
	}

	public void setNombreAdvertisement(String nombreAdvertisement)
	{
		this.nombreAdvertisement = nombreAdvertisement;
	}

	public String getNombreAdvertisement() 
	{
		return nombreAdvertisement;
	}

	public PipeAdvertisement getPipeAdv() 
	{
		return pipeAdv;
	}

	public void setPipeAdv(PipeAdvertisement pipeAdv) 
	{
		this.pipeAdv = pipeAdv;
	}

	public String getNombreFoto() 
	{
		return nombreFoto;
	}

	public void setNombreFoto(String nombreFoto) 
	{
		this.nombreFoto = nombreFoto;
	}

	public String getTags() 
	{
		return tags;
	}

	public void setTags(String tags)
	{
		this.tags = tags;
	}
	
	public String getPeerAgrego() 
	{
		return peerAgrego;
	}

	public void setPeerAgrego(String peerAgrego)
	{
		this.peerAgrego = peerAgrego;
	}

	public String getTiempoCreacion() 
	{
		return tiempoCreacion;
	}

	public void setTiempoCreacion(String tiempoCreacion) 
	{
		this.tiempoCreacion = tiempoCreacion;
	}

	public String isSensible() {
		return sensible;
	}

	public void setSensible(String sensible) {
		this.sensible = sensible;
	}
	
}
