package kahuuFotos.adv;

import net.jxta.document.Advertisement;
import net.jxta.id.ID;
import net.jxta.protocol.PipeAdvertisement;


/**
 * 
 * @author gustavolozano
 *
 */
public abstract class AdvertisementPeer extends Advertisement
{
	//------------------------------------------------------------------------------------------------------------------------------
	// Constantes
	//------------------------------------------------------------------------------------------------------------------------------

	private final static String advertisementType = "AdvertisementPeer";

	//------------------------------------------------------------------------------------------------------------------------------
	// Atributos
	//------------------------------------------------------------------------------------------------------------------------------

	private String nombrePeer;
	
	private PipeAdvertisement pipeAdv = null;
	
	
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

	public void setNombrePeer(String nombrePeer)
	{
		this.nombrePeer = nombrePeer;
	}

	public String getNombrePeer() 
	{
		return nombrePeer;
	}

	public PipeAdvertisement getPipeAdv() 
	{
		return pipeAdv;
	}

	public void setPipeAdv(PipeAdvertisement pipeAdv) 
	{
		this.pipeAdv = pipeAdv;
	}
}
